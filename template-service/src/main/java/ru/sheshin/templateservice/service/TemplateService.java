package ru.sheshin.templateservice.service;

import entity.PatientEntity;
import entity.TemplateEntity;
import entity.TemplatePatientEntity;
import org.mapstruct.factory.Mappers;
import ru.sheshin.templateservice.connection.Connection;
import ru.sheshin.templateservice.dto.*;
import ru.sheshin.templateservice.mapper.TemplateMapper;
import ru.sheshin.templateservice.pdf.PdfWriter;
import ru.sheshin.templateservice.repository.TemplatePatientRepository;
import ru.sheshin.templateservice.repository.TemplateRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TemplateService {
    private final TemplateRepository REPOSITORY = new TemplateRepository();
    private final TemplatePatientRepository TEMPLATE_PATIENT_REPOSITORY = new TemplatePatientRepository();
    private static final TemplateMapper TEMPLATE_MAPPER = Mappers.getMapper(TemplateMapper.class);
    public static final Connection CONNECTION = new Connection();

    public List<FoundTemplateDTO> getTemplatesByRequest(final Long doctorId,
                                                        final String request) {
        return REPOSITORY.getTemplatesByRequest(doctorId, request == null ? "" : request)
                .stream()
                .map(TEMPLATE_MAPPER::entityToFoundDTO)
                .toList();
    }

    public void addTemplate(final TemplateSimpleDTO addingTemplate) {
        REPOSITORY.addTemplate(
                TEMPLATE_MAPPER.addDTOtoEntity(addingTemplate)
        );
    }

    public void deleteTemplate(final Long id) {
        REPOSITORY.deleteTemplate(
                id
        );
    }

    public void updateTemplate(final Long id, final TemplateSimpleDTO templateToUpdate) {
        var templateEntity = TEMPLATE_MAPPER.addDTOtoEntity(templateToUpdate);
        templateEntity.setId(id);
        REPOSITORY.updateTemplate(templateEntity);
    }

    public TemplateDetailsDTO getTemplateById(Long doctorId, Long templateId) {
        var templateDTO = TEMPLATE_MAPPER.entityToDTO(
                REPOSITORY.getTemplateById(doctorId, templateId)
        );

        var diseasesDTOs = templateDTO
                .getDiseases()
                .stream()
                .map(el -> TEMPLATE_MAPPER.jsonDisesaseToDiseaseDTO(CONNECTION.getDiseaseById(el.getId())))
                .collect(Collectors.toSet());
        templateDTO.setDiseases(diseasesDTOs);

        return templateDTO;
    }

    public TemplatePatientDetailsDTO getTemplateByPatientAndDoctorIds(final Long patientId, final Long doctorId) {
        var queryResult = REPOSITORY.getTemplateByPatientAndDoctorIds(patientId, doctorId);

        if(queryResult == null) {
            return null;
        }

        var detailsDTO = new TemplatePatientDetailsDTO();

        detailsDTO.setTemplateId(queryResult.getTemplateId().getId());
        detailsDTO.setTemplateName(queryResult.getTemplateId().getTemplate_name());
        detailsDTO.setNextObservationDate(queryResult.getNextObservationDate());
        detailsDTO.setInspectionsFrequency(queryResult.getTemplateId().getInspections_frequency());

        detailsDTO.setDiseasesNames(
                queryResult
                        .getTemplateId()
                        .getDiseases()
                        .stream()
                        .map(el -> TEMPLATE_MAPPER.jsonDisesaseToDiseaseDTO(CONNECTION.getDiseaseById(el.getId())).getIcd_id())
                        .toList()
        );

        return detailsDTO;
    }

    public void updateNextObservationDate(UpdateObservationDateDto updateObservationDateDto) {
        REPOSITORY.updateNextObservationDate(
                updateObservationDateDto.getPatientId(),
                updateObservationDateDto.getDoctorId(),
                updateObservationDateDto.getNextObservationDate()
        );
    }

    public void setTemplateToPatient(final Long patientId, final Long templateId) {
        TemplateEntity template = REPOSITORY
                .getTemplateById(templateId);

        var startObservationDate = LocalDate.now();
        var endObservationDate = startObservationDate
                .plusYears(Integer.parseInt(template.getObservation_period()));

        var countOfMonths = 12 / template.getInspections_frequency();
        var nextObservationDate = startObservationDate.plusMonths(countOfMonths);

        var templatePatientEntity = new TemplatePatientEntity();

        templatePatientEntity.setTemplateId(template);

        var patientEntity = new PatientEntity();
        patientEntity.setId(patientId);

        templatePatientEntity.setPatientId(patientEntity);
        templatePatientEntity.setDoctorId(template.getDoctor());

        templatePatientEntity.setObservationStart(startObservationDate);
        templatePatientEntity.setObservationEnd(endObservationDate);
        templatePatientEntity.setNextObservationDate(nextObservationDate);

        TEMPLATE_PATIENT_REPOSITORY.addTemplatePatient(templatePatientEntity);
    }

    public void deleteInspection(final Long patientId, final Long templateId) {
        TEMPLATE_PATIENT_REPOSITORY.deleteInspection(patientId, templateId);
    }

    public List<InspectionInfoDTO> getPatientsWithInspectionBetweenDate(
            final Long doctorId,
            final String fromDate,
            final String toDate
    ) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate date1 = LocalDate.parse(fromDate, dateFormat);
        LocalDate date2 = LocalDate.parse(toDate, dateFormat);

        return
                TEMPLATE_PATIENT_REPOSITORY.getPatientWithInspectionBetweenDate(doctorId, date1, date2)
                        .stream()
                        .map(el -> {
                                    var dto = new InspectionInfoDTO();
                                    dto.setPatientDTO(
                                            TEMPLATE_MAPPER.jsonPatientToPatientDTO(
                                                    CONNECTION.getPatientById(el.getPatientId().getId())
                                            )
                                    );
                                    dto.setNextObservationDate(el.getNextObservationDate().format(dateFormat));

                                    return dto;
                                }
                        )

                        .toList();
    }

    public void getInspectionsInFile(
            final Long doctorId,
            final String fromDate,
            final String toDate
    ) {
        var patients = getPatientsWithInspectionBetweenDate(doctorId, fromDate, toDate);
        PdfWriter.writePatientsToPdf(patients);
    }
}

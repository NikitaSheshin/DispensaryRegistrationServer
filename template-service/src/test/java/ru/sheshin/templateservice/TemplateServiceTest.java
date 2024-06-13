package ru.sheshin.templateservice;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sheshin.templateservice.controller.TemplateController;
import ru.sheshin.templateservice.dto.*;
import ru.sheshin.templateservice.repository.TemplatePatientRepository;
import ru.sheshin.templateservice.repository.TemplateRepository;
import ru.sheshin.templateservice.service.TemplateService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TemplateServiceTest {
    private TemplateRepository repository;
    private TemplatePatientRepository templatePatientRepository;
    private TemplateService service;
    private TemplateDetailsDTO templateDetailsDTO;
    private FoundTemplateDTO foundTemplateDTO1;
    private FoundTemplateDTO foundTemplateDTO2;
    private TemplateSimpleDTO templateSimpleDTO;
    private TemplatePatientDetailsDTO templatePatientDetailsDTO;
    private TemplatePatientEntity templatePatientEntity;
    private TemplateEntity templateEntity1;
    private TemplateEntity templateEntity2;
    private TemplateEntity entityToAdd;

    @BeforeEach
    void setUp() {
        DiseaseDTO diseaseDTO1 = new DiseaseDTO();
        diseaseDTO1.setId(1L);
        diseaseDTO1.setIcd_id("D1");
        diseaseDTO1.setDisease_name("disease1");

        DiseaseDTO diseaseDTO2 = new DiseaseDTO();
        diseaseDTO2.setId(2L);
        diseaseDTO2.setIcd_id("D2");
        diseaseDTO2.setDisease_name("disease2");

        DiseaseEntity diseaseEntity1 = new DiseaseEntity();
        diseaseEntity1.setId(1L);
        diseaseEntity1.setIcd_id("D1");
        diseaseEntity1.setDisease_name("disease1");

        DiseaseEntity diseaseEntity2 = new DiseaseEntity();
        diseaseEntity2.setId(2L);
        diseaseEntity2.setIcd_id("D2");
        diseaseEntity2.setDisease_name("disease2");

        templateDetailsDTO = new TemplateDetailsDTO();
        templateDetailsDTO.setId(1L);
        templateDetailsDTO.setTemplate_name("Шаблон1");
        templateDetailsDTO.setObservation_period("5");
        templateDetailsDTO.setInspections_frequency((byte) 2);
        templateDetailsDTO.setDoctor_id(1L);
        templateDetailsDTO.setDiseases(Set.of(diseaseDTO1, diseaseDTO2));

        foundTemplateDTO1 = new FoundTemplateDTO();
        foundTemplateDTO1.setTemplate_name("Шаблон1");
        foundTemplateDTO1.setId(1L);
        foundTemplateDTO1.setObservation_period("5");
        foundTemplateDTO1.setInspections_frequency((byte) 2);

        foundTemplateDTO2 = new FoundTemplateDTO();
        foundTemplateDTO2.setTemplate_name("Шаблон2");
        foundTemplateDTO2.setId(2L);
        foundTemplateDTO2.setObservation_period("5");
        foundTemplateDTO2.setInspections_frequency((byte) 2);

        templateSimpleDTO = new TemplateSimpleDTO();
        templateSimpleDTO.setDoctor_id(1L);
        templateSimpleDTO.setTemplate_name("Шаблон1");
        templateSimpleDTO.setDiseases(Set.of(1L, 2L));
        templateSimpleDTO.setObservation_period("5");
        templateSimpleDTO.setInspections_frequency((byte) 2);

        templatePatientDetailsDTO = new TemplatePatientDetailsDTO();
        templatePatientDetailsDTO.setTemplateId(1L);
        templatePatientDetailsDTO.setDiseasesNames(List.of("Disease1", "Disease2"));
        templatePatientDetailsDTO.setTemplateName("Template");
        templatePatientDetailsDTO.setInspectionsFrequency((byte)2);
        templatePatientDetailsDTO.setNextObservationDate(LocalDate.now());


        templateEntity1 = new TemplateEntity();
        templateEntity1.setTemplate_name("Шаблон1");
        templateEntity1.setId(1L);
        templateEntity1.setObservation_period("5");
        templateEntity1.setInspections_frequency((byte) 2);
        var doctor = new DoctorEntity();
        doctor.setId(1L);
        templateEntity1.setDoctor(doctor);
        templateEntity1.setDiseases(Set.of(diseaseEntity1, diseaseEntity2));

        entityToAdd = new TemplateEntity();
        entityToAdd.setTemplate_name("Шаблон1");
        entityToAdd.setObservation_period("5");
        entityToAdd.setInspections_frequency((byte) 2);
        entityToAdd.setDoctor(doctor);
        entityToAdd.setDiseases(Set.of(diseaseEntity1, diseaseEntity2));

        templatePatientEntity = new TemplatePatientEntity();
        templatePatientEntity.setTemplateId(templateEntity1);
        templatePatientEntity.setDoctorId(doctor);
        var patient = new PatientEntity();
        patient.setId(1L);
        templatePatientEntity.setPatientId(patient);
        templatePatientEntity.setObservationStart(LocalDate.now());
        templatePatientEntity.setNextObservationDate(LocalDate.now());
        templatePatientEntity.setObservationEnd(LocalDate.now());

        templateEntity2 = new TemplateEntity();
        templateEntity2.setTemplate_name("Шаблон2");
        templateEntity2.setId(2L);
        templateEntity2.setObservation_period("5");
        templateEntity2.setInspections_frequency((byte) 2);

        repository = mock(TemplateRepository.class);
        templatePatientRepository = mock(TemplatePatientRepository.class);

        service = new TemplateService();
        setPrivateField(service, repository, "REPOSITORY");
        setPrivateField(service, templatePatientRepository, "TEMPLATE_PATIENT_REPOSITORY");
    }

    @Test
    void testGetTemplatesByQuery()  {
        when(repository.getTemplatesByRequest(1L, "Шаблон"))
                .thenReturn(Arrays.asList(templateEntity1, templateEntity2));

        var result = service.getTemplatesByRequest(1L, "Шаблон");

        assertEquals(2, result.size());
        assertEquals(foundTemplateDTO1, result.get(0));
        assertEquals(foundTemplateDTO2, result.get(1));

        verify(repository, times(1)).getTemplatesByRequest(1L, "Шаблон");
    }

    @Test
    void testGetTemplateById() {
        when(repository.getTemplateById(1L, 1L)).thenReturn(templateEntity1);

        var result = service.getTemplateById(1L, 1L);

        assertEquals(templateDetailsDTO, result);

        verify(repository, times(1)).getTemplateById(1L, 1L);
    }

    @Test
    void testAddTemplate() {
        doNothing().when(repository).addTemplate(entityToAdd);

        service.addTemplate(templateSimpleDTO);

        verify(repository).addTemplate(entityToAdd);
    }

    @Test
    void testUpdateTemplate() {
        doNothing().when(repository).updateTemplate(templateEntity1);

        service.updateTemplate(1L, templateSimpleDTO);

        verify(repository).updateTemplate(templateEntity1);
    }

    @Test
    void testDeleteTemplate() {
        doNothing().when(repository).deleteTemplate(1L);

        service.deleteTemplate(1L);

        verify(repository).deleteTemplate(1L);
    }

    @Test
    void testGetTemplateByPatientAndDoctorIds() {
        when(repository.getTemplateByPatientAndDoctorIds(1L, 1L)).thenReturn(templatePatientEntity);

        var result = service.getTemplateByPatientAndDoctorIds(1L, 1L);

        assertEquals(templatePatientDetailsDTO, result);

        verify(repository, times(1)).getTemplateByPatientAndDoctorIds(1L, 1L);
    }

    @Test
    void testUpdateNextObservationDate() {
        UpdateObservationDateDto updateObservationDateDto = new UpdateObservationDateDto();
        updateObservationDateDto.setNextObservationDate(LocalDate.now());
        updateObservationDateDto.setDoctorId(1L);
        updateObservationDateDto.setPatientId(1L);
        doNothing().when(repository).updateNextObservationDate(1L, 1L, LocalDate.now());

        service.updateNextObservationDate(updateObservationDateDto);

        verify(repository).updateNextObservationDate(1L, 1L, LocalDate.now());
    }

    @Test
    void testSetTemplateToPatient() {
        doNothing().when(templatePatientRepository).addTemplatePatient(templatePatientEntity);

        service.setTemplateToPatient(1L, 1L);

        verify(templatePatientRepository).addTemplatePatient(templatePatientEntity);
    }

    @Test
    void testRemoveTemplateFromPatient() {
        doNothing().when(templatePatientRepository).deleteInspection(1L, 1L);

        service.deleteInspection(1L, 1L);

        verify(templatePatientRepository).deleteInspection(1L, 1L);
    }

    @Test
    void testGetPatientsWithInspectionBetweenDate() {
        InspectionInfoDTO inspectionInfoDTO = new InspectionInfoDTO();
        inspectionInfoDTO.setNextObservationDate("01.01.2025");
        inspectionInfoDTO.setPatientDTO(null);
        when(templatePatientRepository.getPatientWithInspectionBetweenDate(1L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1)))
                .thenReturn(List.of(templatePatientEntity));

        service.getPatientsWithInspectionBetweenDate(1L, "01.01.2024", "01.01.2025");

        verify(templatePatientRepository).getPatientWithInspectionBetweenDate(
                1L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2025, 1, 1));
    }

    private void setPrivateField(Object object, Object value, String fieldName) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package ru.sheshin.patientservice.service;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.sheshin.patientservice.diseaseconnection.DiseaseConnection;
import ru.sheshin.patientservice.dto.*;
import ru.sheshin.patientservice.mapper.PatientMapper;
import ru.sheshin.patientservice.repository.PatientRepository;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("SERVICE")
public class PatientService {
    private static final PatientRepository REPOSITORY = new PatientRepository();
    private static final PatientMapper MAPPER = Mappers.getMapper(PatientMapper.class);
    public static final DiseaseConnection DISEASE_CONNECTION = new DiseaseConnection();

    public List<PatientDTO> getPatients() {
        return REPOSITORY.getPatients().stream().map(MAPPER::entityToDTO).toList();
    }

    public void addPatient(PatientDTO patient) {
        REPOSITORY.addPatient(MAPPER.dtoToEntity(patient));
    }

    public void updatePatient(PatientDTO patientToUpdate) {
        REPOSITORY.updatePatient(MAPPER.dtoToEntity(patientToUpdate));
    }

    public void deletePatient(final Long patientId) {
        REPOSITORY.deletePatient(patientId);
    }

    public PatientDetailsDTO getPatient(final Long patientId) {
        var patientDTO = MAPPER.entityToDetailsDTO(REPOSITORY.getPatientById(patientId));

        var diseasesDTOs = patientDTO
                .getDiseases()
                .stream()
                .map(el -> MAPPER.jsonDisesaseToDiseaseDTO(DISEASE_CONNECTION.getDiseaseById(el.getId())))
                .collect(Collectors.toSet());
        patientDTO.setDiseases(diseasesDTOs);

        return patientDTO;
    }

    public void deleteObserveFromPatient(final Long patientId) {
        REPOSITORY.removeObserveToPatient(patientId);
    }

    public void setObserveToPatient(final Long patientId) {
        REPOSITORY.setObserveToPatient(patientId);
    }

    public List<ReceptionInfoDTO> getTodayReceptions() {
        var patients = REPOSITORY
                .getPatients()
                .stream()
                .limit(6)
                .map(MAPPER::entityToReceptionDTO)
                .toList();

        patients.get(2).setReceptionTime(LocalTime.of(9, 0));
        patients.get(1).setReceptionTime(LocalTime.of(10, 30));
        patients.get(0).setReceptionTime(LocalTime.of(11, 0));
        patients.get(3).setReceptionTime(LocalTime.of(13, 0));
        patients.get(4).setReceptionTime(LocalTime.of(13, 30));
        patients.get(5).setReceptionTime(LocalTime.of(17, 0));

        return patients
                .stream()
                .sorted(Comparator.comparing(ReceptionInfoDTO::getReceptionTime))
                .toList();
    }

    public List<ReceptionInfoDTO> getPatientsByQuery(final String query) {
        return REPOSITORY
                .getPatientsByQuery(query)
                .stream()
                .map(MAPPER::entityToReceptionDTO)
                .toList();
    }
}

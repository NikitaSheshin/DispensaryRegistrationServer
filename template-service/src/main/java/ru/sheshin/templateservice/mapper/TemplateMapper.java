package ru.sheshin.templateservice.mapper;

import entity.DiseaseEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import entity.TemplateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.sheshin.templateservice.dto.*;
import ru.sheshin.templateservice.json.jsonbean.DiseaseJson;
import ru.sheshin.templateservice.json.jsonbean.PatientJson;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface TemplateMapper {
    @Mapping(source = "doctor", target = "doctor_id")
    TemplateDetailsDTO entityToDTO(TemplateEntity source);
    default Long mapDoctorEntityToDoctorId(DoctorEntity doctor) {
        return doctor.getId();
    }

    FoundTemplateDTO entityToFoundDTO(TemplateEntity destination);
    default Set<DiseaseDTO> mapDiseaseEntitiesToDTOs(Set<DiseaseEntity> diseaseEntities) {
        return diseaseEntities.stream()
                .map(diseaseEntity -> {
                    DiseaseDTO disease = new DiseaseDTO();
                    disease.setId(diseaseEntity.getId());
                    disease.setDisease_name(diseaseEntity.getDisease_name());
                    disease.setIcd_id(diseaseEntity.getIcd_id());
                    return disease;
                })
                .collect(Collectors.toSet());
    }

    @Mappings({
            @Mapping(source = "doctor_id", target = "doctor")
    })
    TemplateEntity addDTOtoEntity(TemplateSimpleDTO destination);
    default Set<DiseaseEntity> mapDiseaseIdsToEntities(Set<Long> diseaseIds) {
        return diseaseIds.stream()
                .map(diseaseId -> {
                    DiseaseEntity disease = new DiseaseEntity();
                    disease.setId(diseaseId);
                    return disease;
                })
                .collect(Collectors.toSet());
    }
    default DoctorEntity mapDoctorIdToDoctor(long doctorId) {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setId(doctorId);
        return doctor;
    }

    @Mappings({
            @Mapping(source = "icdId", target = "icd_id"),
            @Mapping(source = "diseaseName", target = "disease_name")
    })
    DiseaseDTO jsonDisesaseToDiseaseDTO(DiseaseJson destination);

    @Mapping(target = "diseases", ignore = true)
    PatientDTO jsonPatientToPatientDTO(PatientJson destination);
}

package ru.sheshin.patientservice.mapper;

import entity.DiseaseEntity;
import entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.sheshin.patientservice.dto.DiseaseDTO;
import ru.sheshin.patientservice.dto.PatientDTO;
import ru.sheshin.patientservice.dto.PatientDetailsDTO;
import ru.sheshin.patientservice.dto.ReceptionInfoDTO;
import ru.sheshin.patientservice.json.jsonbean.DiseaseJson;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface PatientMapper {
    PatientEntity dtoToEntity(PatientDTO destination);
    default Set<DiseaseEntity> mapDiseaseIdsToEntities(Set<Long> diseaseIds) {
        return diseaseIds.stream()
                .map(diseaseId -> {
                    DiseaseEntity disease = new DiseaseEntity();
                    disease.setId(diseaseId);
                    return disease;
                })
                .collect(Collectors.toSet());
    }

    PatientDTO entityToDTO(PatientEntity destination);
    default Set<Long> mapDiseaseEntitiesToDTOs(Set<DiseaseEntity> diseaseEntities) {
        return diseaseEntities.stream()
                .map(DiseaseEntity::getId)
                .collect(Collectors.toSet());
    }

    ReceptionInfoDTO entityToReceptionDTO(PatientEntity destination);

    PatientDetailsDTO entityToDetailsDTO(PatientEntity destination);

    @Mappings({
            @Mapping(source = "icdId", target = "icd_id"),
            @Mapping(source = "diseaseName", target = "disease_name")
    })
    DiseaseDTO jsonDisesaseToDiseaseDTO(DiseaseJson destination);
}

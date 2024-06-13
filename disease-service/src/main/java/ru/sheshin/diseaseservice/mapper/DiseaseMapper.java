package ru.sheshin.diseaseservice.mapper;

import entity.DiseaseEntity;
import org.mapstruct.Mapper;
import ru.sheshin.diseaseservice.dto.DiseaseDTO;

@Mapper
public interface DiseaseMapper {
    DiseaseDTO entityToDTO(DiseaseEntity destination);
}

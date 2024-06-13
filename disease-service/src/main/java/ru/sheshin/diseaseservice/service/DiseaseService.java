package ru.sheshin.diseaseservice.service;

import org.mapstruct.factory.Mappers;
import ru.sheshin.diseaseservice.dto.DiseaseDTO;
import ru.sheshin.diseaseservice.mapper.DiseaseMapper;
import ru.sheshin.diseaseservice.repository.DiseaseRepository;

import java.util.List;

public class DiseaseService {
    private final DiseaseMapper DISEASE_MAPPER = Mappers.getMapper(DiseaseMapper.class);
    private final DiseaseRepository REPOSITORY = new DiseaseRepository();

    public List<DiseaseDTO> getDiseases() {
        return REPOSITORY.getDiseases()
                .stream()
                .map(DISEASE_MAPPER::entityToDTO)
                .toList();
    }

    public DiseaseDTO getDiseaseById(final Long id) {
        return DISEASE_MAPPER.entityToDTO(REPOSITORY.getDiseaseById(id));
    }
}

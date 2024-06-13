package ru.sheshin.visitservice.service;

import org.mapstruct.factory.Mappers;
import ru.sheshin.visitservice.dto.VisitDTO;
import ru.sheshin.visitservice.mapper.VisitMapper;
import ru.sheshin.visitservice.repository.VisitRepository;

import java.util.List;

public class VisitService {
    private static final VisitRepository REPOSITORY = new VisitRepository();
    private static final VisitMapper MAPPER = Mappers.getMapper(VisitMapper.class);

    public void addVisit(VisitDTO visitDTO) {
        REPOSITORY.addVisit(MAPPER.dtoToEntity(visitDTO));
    }

    public List<VisitDTO> getVisitByPatient(final Long doctorId,
                                            final Long patientId) {
        return REPOSITORY.getVisitByPatientId(doctorId, patientId)
                .stream()
                .map(MAPPER::entityToDTO)
                .toList();
    }

    public VisitDTO getVisitById(final Long visitId) {
        return MAPPER
                .entityToDTO(
                        REPOSITORY.getVisitById(visitId)
                );
    }
}

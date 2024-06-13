package ru.sheshin.visitservice.mapper;

import entity.DoctorEntity;
import entity.PatientEntity;
import entity.VisitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.sheshin.visitservice.dto.VisitDTO;

@Mapper
public interface VisitMapper {
    @Mappings(
            {@Mapping(source = "doctorId", target = "doctor_id"),
                    @Mapping(source = "patientId", target = "patientId")}
    )
    VisitEntity dtoToEntity(VisitDTO visitDTO);
    default DoctorEntity mapDoctorIdToDoctor(Long doctorId) {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setId(doctorId);
        return doctor;
    }
    default PatientEntity mapPatientIdToPatient(Long patientId) {
        PatientEntity doctor = new PatientEntity();
        doctor.setId(patientId);
        return doctor;
    }

    @Mappings(
            {@Mapping(source = "doctor_id", target = "doctorId"),
                    @Mapping(source = "patientId", target = "patientId")}
    )
    VisitDTO entityToDTO(VisitEntity visitEntity);
    default Long mapPatientToPatientId(PatientEntity patientEntity) {
        return patientEntity.getId();
    }
    default Long mapDoctorToPatientId(DoctorEntity doctorEntity) {
        return doctorEntity.getId();
    }
}

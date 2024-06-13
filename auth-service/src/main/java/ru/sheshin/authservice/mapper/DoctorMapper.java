package ru.sheshin.authservice.mapper;

import entity.DoctorEntity;
import entity.DoctorsSpecialtiesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.sheshin.authservice.dto.DoctorDTO;

@Mapper
public interface DoctorMapper {
    @Mappings({
            @Mapping(source = "firstname", target = "firstName"),
            @Mapping(source = "lastname", target = "lastName")
    })
    DoctorDTO entityToDTO(DoctorEntity destination);
    default String mapDoctorSpecialtyEntityToString(DoctorsSpecialtiesEntity doctorsSpecialties) {
        return doctorsSpecialties.getName();
    }
}

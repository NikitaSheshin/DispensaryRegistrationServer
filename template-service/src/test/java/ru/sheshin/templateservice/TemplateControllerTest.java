package ru.sheshin.templateservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sheshin.templateservice.controller.TemplateController;
import ru.sheshin.templateservice.dto.*;
import ru.sheshin.templateservice.service.TemplateService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TemplateControllerTest {
    private TemplateService service;
    private TemplateController controller;
    private TemplateDetailsDTO templateDetailsDTO;
    private FoundTemplateDTO foundTemplateDTO1;
    private FoundTemplateDTO foundTemplateDTO2;
    private TemplateSimpleDTO templateSimpleDTO;
    private TemplatePatientDetailsDTO templatePatientDetailsDTO;

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

        templateDetailsDTO = new TemplateDetailsDTO();
        templateDetailsDTO.setId(1L);
        templateDetailsDTO.setTemplate_name("Шаблон");
        templateDetailsDTO.setObservation_period("5");
        templateDetailsDTO.setInspections_frequency((byte) 2);
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
        templateSimpleDTO.setTemplate_name("Шаблон");
        templateSimpleDTO.setDiseases(Set.of(1L, 2L));
        templateSimpleDTO.setObservation_period("5");
        templateSimpleDTO.setInspections_frequency((byte) 2);

        templatePatientDetailsDTO = new TemplatePatientDetailsDTO();
        templatePatientDetailsDTO.setTemplateId(1L);
        templatePatientDetailsDTO.setDiseasesNames(List.of("Disease1", "Disease2"));
        templatePatientDetailsDTO.setTemplateName("Template");
        templatePatientDetailsDTO.setInspectionsFrequency((byte)2);
        templatePatientDetailsDTO.setNextObservationDate(LocalDate.now());

        service = mock(TemplateService.class);

        controller = new TemplateController();
        setPrivateField(controller, service);
    }

    @Test
    void testGetTemplatesByQuery()  {
        when(service.getTemplatesByRequest(1L, "Шаблон"))
                .thenReturn(Arrays.asList(foundTemplateDTO1, foundTemplateDTO2));

        var result = controller.getTemplatesByRequest(1L, "Шаблон");

        assertEquals(2, Objects.requireNonNull(result.getBody()).size());
        assertEquals(foundTemplateDTO1, result.getBody().get(0));
        assertEquals(foundTemplateDTO2, result.getBody().get(1));

        verify(service, times(1)).getTemplatesByRequest(1L, "Шаблон");
    }

    @Test
    void testGetTemplateById() {
        when(service.getTemplateById(1L, 1L)).thenReturn(templateDetailsDTO);

        var result = controller.getTemplateById(1L, 1L);

        assertEquals(templateDetailsDTO, result.getBody());

        verify(service, times(1)).getTemplateById(1L, 1L);
    }

    @Test
    void testAddTemplate() {
        doNothing().when(service).addTemplate(templateSimpleDTO);

        controller.addTemplate(templateSimpleDTO);

        verify(service).addTemplate(templateSimpleDTO);
    }

    @Test
    void testUpdateTemplate() {
        doNothing().when(service).updateTemplate(1L, templateSimpleDTO);

        controller.updateTemplate(templateSimpleDTO, 1L);

        verify(service).updateTemplate(1L, templateSimpleDTO);
    }

    @Test
    void testDeleteTemplate() {
        doNothing().when(service).deleteTemplate(1L);

        controller.deleteTemplate(1L);

        verify(service).deleteTemplate(1L);
    }

    @Test
    void testGetTemplateByPatientAndDoctorIds() {
        when(service.getTemplateByPatientAndDoctorIds(1L, 1L)).thenReturn(templatePatientDetailsDTO);

        var result = controller.getTemplateByPatientAndDoctorIds(1L, 1L);

        assertEquals(templatePatientDetailsDTO, result.getBody());

        verify(service, times(1)).getTemplateByPatientAndDoctorIds(1L, 1L);
    }

    @Test
    void testUpdateNextObservationDate() {
        UpdateObservationDateDto updateObservationDateDto = new UpdateObservationDateDto();
        updateObservationDateDto.setNextObservationDate(LocalDate.now());
        updateObservationDateDto.setDoctorId(1L);
        updateObservationDateDto.setPatientId(1L);
        doNothing().when(service).updateNextObservationDate(updateObservationDateDto);

        controller.updateObservationDate(updateObservationDateDto);

        verify(service).updateNextObservationDate(updateObservationDateDto);
    }

    @Test
    void testSetTemplateToPatient() {
        doNothing().when(service).setTemplateToPatient(1L, 1L);

        controller.setTemplateToPatient(1L, 1L);

        verify(service).setTemplateToPatient(1L, 1L);
    }

    @Test
    void testRemoveTemplateFromPatient() {
        doNothing().when(service).deleteInspection(1L, 1L);

        controller.deleteInspection(1L, 1L);

        verify(service).deleteInspection(1L, 1L);
    }

    @Test
    void testGetInspectionFile() {
        doNothing().when(service).getInspectionsInFile(1L, "02.03.2024", "01.04.2024");

        controller.getReceptionFile("1", "02.03.2024", "01.04.2024");

        verify(service, times(1)).getInspectionsInFile(1L, "02.03.2024", "01.04.2024");
    }

    @Test
    void testGetPatientsWithInspectionBetweenDate() {
        InspectionInfoDTO inspectionInfoDTO = new InspectionInfoDTO();
        inspectionInfoDTO.setNextObservationDate("01.01.2025");
        inspectionInfoDTO.setPatientDTO(null);
        when(service.getPatientsWithInspectionBetweenDate(1L, "01.01.2024", "01.01.2025"))
                .thenReturn(List.of(inspectionInfoDTO));

        controller.getPatientsWithInspectionBetweenDate("1", "01.01.2024", "01.01.2025");

        verify(service).getPatientsWithInspectionBetweenDate(1L, "01.01.2024", "01.01.2025");
    }

    private void setPrivateField(Object object, Object value) {
        try {
            var field = object.getClass().getDeclaredField("TEMPLATE_SERVICE");
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package ru.sheshin.diseaseservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sheshin.diseaseservice.controller.DiseaseController;
import ru.sheshin.diseaseservice.dto.DiseaseDTO;
import ru.sheshin.diseaseservice.service.DiseaseService;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControllerTest {
    private DiseaseService service;
    private DiseaseController controller;
    private DiseaseDTO diseaseDTO1;
    private DiseaseDTO diseaseDTO2;

    @BeforeEach
    void setUp() {
        diseaseDTO1 = new DiseaseDTO();
        diseaseDTO1.setId(1L);
        diseaseDTO1.setIcd_id("D1");
        diseaseDTO1.setDisease_name("disease1");

        diseaseDTO2 = new DiseaseDTO();
        diseaseDTO2.setId(2L);
        diseaseDTO2.setIcd_id("D2");
        diseaseDTO2.setDisease_name("disease2");

        // Create mock for DiseaseRepository
        service = mock(DiseaseService.class);

        // Create DiseaseService instance and inject the mocked repository
        controller = new DiseaseController();
        // Set the private field REPOSITORY to the mocked repository
        setPrivateField(controller, service);
    }

    @Test
    void testGetDiseases()  {
        when(service.getDiseases()).thenReturn(Arrays.asList(diseaseDTO1, diseaseDTO2));

        var result = controller.getDiseases();

        assertEquals(2, Objects.requireNonNull(result.getBody()).size());
        assertEquals(diseaseDTO1, result.getBody().get(0));
        assertEquals(diseaseDTO2, result.getBody().get(1));

        verify(service, times(1)).getDiseases();
    }

    @Test
    void testGetDiseaseById() {
        when(service.getDiseaseById(1L)).thenReturn(diseaseDTO1);

        DiseaseDTO result = controller.getDiseaseById(1L);

        assertEquals(diseaseDTO1, result);

        verify(service, times(1)).getDiseaseById(1L);
    }

    private void setPrivateField(Object object, Object value) {
        try {
            var field = object.getClass().getDeclaredField("DISEASE_SERVICE");
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

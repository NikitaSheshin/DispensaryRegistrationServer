package ru.sheshin.diseaseservice;

import entity.DiseaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sheshin.diseaseservice.dto.DiseaseDTO;
import ru.sheshin.diseaseservice.repository.DiseaseRepository;
import ru.sheshin.diseaseservice.service.DiseaseService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DiseaseServiceTest {
    private DiseaseService service;
    private DiseaseRepository repository;

    private DiseaseEntity disease1;
    private DiseaseEntity disease2;
    private DiseaseDTO diseaseDTO1;
    private DiseaseDTO diseaseDTO2;

    @BeforeEach
    void setUp() {
        disease1 = new DiseaseEntity();
        disease1.setId(1L);
        disease1.setIcd_id("D1");
        disease1.setDisease_name("disease1");

        disease2 = new DiseaseEntity();
        disease2.setId(2L);
        disease2.setIcd_id("D2");
        disease2.setDisease_name("disease2");

        diseaseDTO1 = new DiseaseDTO();
        diseaseDTO1.setId(1L);
        diseaseDTO1.setIcd_id("D1");
        diseaseDTO1.setDisease_name("disease1");

        diseaseDTO2 = new DiseaseDTO();
        diseaseDTO2.setId(2L);
        diseaseDTO2.setIcd_id("D2");
        diseaseDTO2.setDisease_name("disease2");

        repository = mock(DiseaseRepository.class);

        service = new DiseaseService();
        setPrivateField(service, "REPOSITORY", repository);
    }

    @Test
    void testGetDiseases()  {
        when(repository.getDiseases()).thenReturn(Arrays.asList(disease1, disease2));

        List<DiseaseDTO> result = service.getDiseases();

        assertEquals(2, result.size());
        assertEquals(diseaseDTO1, result.get(0));
        assertEquals(diseaseDTO2, result.get(1));

        verify(repository, times(1)).getDiseases();
    }

    @Test
    void testGetDiseaseById() {
        when(repository.getDiseaseById(1L)).thenReturn(disease1);

        DiseaseDTO result = service.getDiseaseById(1L);

        assertEquals(diseaseDTO1, result);

        verify(repository, times(1)).getDiseaseById(1L);
    }

    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

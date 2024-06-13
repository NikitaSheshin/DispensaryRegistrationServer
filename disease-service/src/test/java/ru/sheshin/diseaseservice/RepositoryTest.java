package ru.sheshin.diseaseservice;

import entity.DiseaseEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.sheshin.diseaseservice.repository.DiseaseRepository;
import util.HibernateUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoryTest {
    @InjectMocks
    private DiseaseRepository diseaseRepository;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Query<DiseaseEntity> query;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(session.beginTransaction()).thenReturn(transaction);
        when(HibernateUtil.getSessionFactory().getCurrentSession()).thenReturn(session);
    }

    @Test
    void testGetDiseases() {
        DiseaseEntity disease1 = new DiseaseEntity();
        disease1.setId(1L);
        disease1.setDisease_name("Disease1");

        DiseaseEntity disease2 = new DiseaseEntity();
        disease2.setId(2L);
        disease2.setDisease_name("Disease2");

        List<DiseaseEntity> expectedDiseases = Arrays.asList(disease1, disease2);

        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery("FROM DiseaseEntity", DiseaseEntity.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedDiseases);

        List<DiseaseEntity> result = diseaseRepository.getDiseases();

        assertEquals(expectedDiseases, result);
        verify(session).beginTransaction();
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    void testGetDiseaseById() {
        Long diseaseId = 1L;
        DiseaseEntity expectedDisease = new DiseaseEntity();
        expectedDisease.setId(diseaseId);
        expectedDisease.setDisease_name("Disease1");

        when(session.createQuery("SELECT t FROM DiseaseEntity t WHERE t.id = :diseaseId")).thenReturn(query);
        when(query.setParameter("diseaseId", diseaseId)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(expectedDisease));

        DiseaseEntity result = diseaseRepository.getDiseaseById(diseaseId);

        assertEquals(expectedDisease, result);
        verify(session).beginTransaction();
        verify(session.getTransaction()).commit();
        verify(session).close();
    }
}

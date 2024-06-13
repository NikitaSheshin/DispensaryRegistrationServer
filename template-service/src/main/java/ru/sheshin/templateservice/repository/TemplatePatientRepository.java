package ru.sheshin.templateservice.repository;

import entity.TemplatePatientEntity;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class TemplatePatientRepository {
    public void addTemplatePatient(TemplatePatientEntity templatePatientEntity) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(templatePatientEntity);
            session.getTransaction().commit();
        }
    }

    public void deleteInspection(final Long patientId, final Long templateId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT t FROM TemplatePatientEntity t WHERE t.patientId.id = :patientId " +
                    "AND t.templateId.id = :templateId";
            Query query = session.createQuery(hql);
            query.setParameter("patientId", patientId);
            query.setParameter("templateId", templateId);

            var inspection = query.getSingleResult();
            session.delete(inspection);

            session.getTransaction().commit();
        }
    }

    public List<TemplatePatientEntity> getPatientWithInspectionBetweenDate(
            final Long doctorId,
            final LocalDate fromDate,
            final LocalDate toDate
    ) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT tp " +
                    "FROM TemplatePatientEntity tp " +
                    "WHERE tp.nextObservationDate BETWEEN :startDate AND :endDate " +
                    "AND tp.doctorId.id = :doctorId";

            Query query = session.createQuery(hql, TemplatePatientEntity.class);
            query.setParameter("startDate", fromDate);
            query.setParameter("endDate", toDate);
            query.setParameter("doctorId", doctorId);

            return query.getResultList();
        }
    }
}

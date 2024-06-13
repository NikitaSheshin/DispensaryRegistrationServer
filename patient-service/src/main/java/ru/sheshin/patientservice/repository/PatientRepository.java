package ru.sheshin.patientservice.repository;

import entity.PatientEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import util.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

@Repository
public class PatientRepository {
    public List<PatientEntity> getPatients() {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT t FROM PatientEntity t";
            Query query = session.createQuery(hql);

            List<PatientEntity> result = query.getResultList();
            session.getTransaction().commit();

            return result;
        }
    }

    public void addPatient(PatientEntity patientEntity) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(patientEntity);
            session.getTransaction().commit();
        }
    }

    public void updatePatient(final PatientEntity patientToUpdate) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.update(patientToUpdate);
            session.getTransaction().commit();
        }
    }

    public void deletePatient(final Long id) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            PatientEntity patientEntity = session.get(PatientEntity.class, id);
            if (patientEntity != null) {
                session.delete(patientEntity);
            }

            session.getTransaction().commit();
        }
    }

    public PatientEntity getPatientById(final Long patientId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT t FROM PatientEntity t WHERE t.id = :patientId ";
            Query query = session.createQuery(hql);
            query.setParameter("patientId", patientId);

            PatientEntity result = (PatientEntity) query.getResultList().get(0);
            session.getTransaction().commit();

            return result;
        }
    }

    public void setObserveToPatient(final Long patientId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            PatientEntity patientEntity = session.get(PatientEntity.class, patientId);
            patientEntity.setObserved(true);
            session.update(patientEntity);

            session.getTransaction().commit();
        }
    }

    public void removeObserveToPatient(final Long patientId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            PatientEntity patientEntity = session.get(PatientEntity.class, patientId);
            patientEntity.setObserved(false);
            session.update(patientEntity);

            session.getTransaction().commit();
        }
    }

    public List<PatientEntity> getPatientsByQuery(final String request) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT t FROM PatientEntity t " +
                    "WHERE t.snilsNumber LIKE :request OR " +
                    "CONCAT_WS(' ', t.passportSeries, t.passportNumber) LIKE :request OR " +
                    "t.omsPolicy LIKE :request OR " +
                    "CONCAT_WS(' ', t.lastName, t.firstName, t.patronymic) LIKE :request";
            Query query = session.createQuery(hql);
            query.setParameter("request", "%" + request + "%");

            List<PatientEntity> result = query.getResultList();
            session.getTransaction().commit();

            return result;
        }
    }
}

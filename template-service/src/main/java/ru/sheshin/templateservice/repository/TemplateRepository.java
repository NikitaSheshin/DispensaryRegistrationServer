package ru.sheshin.templateservice.repository;

import entity.TemplateEntity;
import entity.TemplatePatientEntity;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

public class TemplateRepository {
    public List<TemplateEntity> getTemplatesByRequest(final Long doctorId,
                                                      final String request) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT t FROM TemplateEntity t WHERE t.doctor.id = :doctorId AND t.template_name LIKE :request";
            Query query = session.createQuery(hql);
            query.setParameter("doctorId", doctorId);
            query.setParameter("request", "%" + request + "%");

            List<TemplateEntity> result = query.getResultList();
            session.getTransaction().commit();

            return result;
        }
    }

    public TemplateEntity getTemplateById(Long doctorId, Long templateId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT t FROM TemplateEntity t WHERE t.doctor.id = :doctorId " +
                    "AND t.id = :templateId";
            Query query = session.createQuery(hql);
            query.setParameter("doctorId", doctorId);
            query.setParameter("templateId", templateId);

            TemplateEntity result = (TemplateEntity) query.getResultList().get(0);
            session.getTransaction().commit();

            return result;
        }
    }

    public TemplateEntity getTemplateById(final Long templateId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT t FROM TemplateEntity t WHERE t.id = :templateId";
            Query query = session.createQuery(hql);
            query.setParameter("templateId", templateId);

            TemplateEntity result = (TemplateEntity) query.getResultList().get(0);
            session.getTransaction().commit();

            return result;
        }
    }

    public void addTemplate(TemplateEntity templateEntity) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(templateEntity);
            session.getTransaction().commit();
        }
    }

    public void updateTemplate(final TemplateEntity templateToUpdate) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.update(templateToUpdate);
            session.getTransaction().commit();
        }
    }

    public void deleteTemplate(final Long id) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            TemplateEntity templateEntity = session.get(TemplateEntity.class, id);
            if (templateEntity != null) {
                session.delete(templateEntity);
            }

            session.getTransaction().commit();
        }
    }

    public TemplatePatientEntity getTemplateByPatientAndDoctorIds(final Long patientId, final Long doctorId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT tp FROM TemplatePatientEntity tp " +
                    "WHERE tp.patientId.id = :patientId AND tp.doctorId.id = :doctorId";

            TemplatePatientEntity template = null;
            try {
                template = session.createQuery(hql, TemplatePatientEntity.class)
                        .setParameter("patientId", patientId)
                        .setParameter("doctorId", doctorId)
                        .getSingleResult();

            } catch (NoResultException ignored) {}

            session.getTransaction().commit();
            return template;
        }
    }

    public void updateNextObservationDate(final Long patientId,
                                          final Long doctorId,
                                          final LocalDate nextObservationDate) {
        var templateToUpdate = getTemplateByPatientAndDoctorIds(patientId, doctorId);
        templateToUpdate.setNextObservationDate(nextObservationDate);

        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.update(templateToUpdate);
            session.getTransaction().commit();
        }
    }
}

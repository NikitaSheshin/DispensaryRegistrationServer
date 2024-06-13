package ru.sheshin.visitservice.repository;

import entity.VisitEntity;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class VisitRepository {
    public VisitEntity getVisitById(final Long visitId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT t FROM VisitEntity t WHERE t.id = :visitId ";
            Query query = session.createQuery(hql);
            query.setParameter("visitId", visitId);

            VisitEntity result = (VisitEntity) query.getResultList().get(0);
            session.getTransaction().commit();

            return result;
        }
    }

    public void addVisit(final VisitEntity visitEntity) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(visitEntity);
            session.getTransaction().commit();
        }
    }

    public List<VisitEntity> getVisitByPatientId(final Long doctorId,
                                                 final Long patientId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT visit " +
                    "FROM VisitEntity visit " +
                    "WHERE visit.patientId.id = :patientId AND visit.doctor_id.id = :doctorId";

            Query query = session.createQuery(hql, VisitEntity.class);
            query.setParameter("patientId", patientId);
            query.setParameter("doctorId", doctorId);

            return query.getResultList();
        }
    }
}

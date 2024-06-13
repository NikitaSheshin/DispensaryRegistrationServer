package ru.sheshin.diseaseservice.repository;

import entity.DiseaseEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class DiseaseRepository  {
    public List<DiseaseEntity> getDiseases() {
        List<DiseaseEntity> result;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM DiseaseEntity";
            Query query = session.createQuery(hql, DiseaseEntity.class);
            result = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            result = new ArrayList<>();  // Здесь можно использовать логирование вместо печати стека исключений
        }

        return result;
    }

    public DiseaseEntity getDiseaseById(final Long id) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();

            String hql = "SELECT t FROM DiseaseEntity t WHERE t.id = :diseaseId ";
            Query query = session.createQuery(hql);
            query.setParameter("diseaseId", id);

            DiseaseEntity result = (DiseaseEntity) query.getResultList().get(0);
            session.getTransaction().commit();

            return result;
        }
    }
}

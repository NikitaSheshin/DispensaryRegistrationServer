package ru.sheshin.authservice.repository;

import entity.DoctorEntity;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class AuthRepository {
    public DoctorEntity authDoctor(final String login, final String password) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        session.beginTransaction();

        String hql = "FROM DoctorEntity " +
                "WHERE login = :nameParam AND password = :passParam";
        Query query = session.createQuery(hql);
        query.setParameter("nameParam", login);
        query.setParameter("passParam", password);

        List<DoctorEntity> result = (List<DoctorEntity>)query.getResultList();
        if(result.isEmpty()) {
            return null;
        }
        DoctorEntity doctorEntity = result.get(0);
        session.getTransaction().commit();

        return doctorEntity;
    }
}

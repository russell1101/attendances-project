package core.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import core.util.HibernateUtil;
import java.util.function.Function;

public interface CoreService {

    default <T> T doInTransaction(Function<Session, T> action) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        T result = null;
        
        try {
            tx = session.beginTransaction();
            result = action.apply(session);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        
        return result;
    }
}
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtilForTest {
    private static SessionFactory sessionFactoryH2;
    private static Session sessionH2;

    static {
        sessionFactoryH2 = new Configuration().configure("hibernate.h2.cfg.xml").buildSessionFactory();
        sessionH2 = sessionFactoryH2.openSession();
    }

    public static Session getSessionH2() {
        return sessionH2;
    }
}

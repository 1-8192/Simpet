package hibernate.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

public class AppUserTest {
    @Test
    public void testApp() {
        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        AppUser user = new AppUser("firstuser");
        session.save(user);

        session.getTransaction().commit();
        session.close();
    }
}
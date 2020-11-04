import entities.AddressDataSet;
import entities.PhoneDataSet;
import entities.User;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HibernateTest {

    private static HibernateManager hibernateManager = HibernateManagerImpl.of("hibernate.cfg.xml", User.class, PhoneDataSet.class, AddressDataSet.class);

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        User user = new User("TestUser", 26);
        AddressDataSet address = new AddressDataSet("Address");
        PhoneDataSet phone = new PhoneDataSet("123456789");
        user.setAddressDataSet(address);
        user.setPhoneDataSet(Collections.singletonList(phone));

        hibernateManager.create(user);
        assertNotNull(user.getId());

        User createdUser1 = (User) hibernateManager.load(1L, User.class);
        assertNotNull(createdUser1);

        createdUser1.setAge(27);
        createdUser1.getAddressDataSet().setStreet("new address");
        createdUser1.getPhoneDataSet().stream().findFirst().ifPresent(phoneData -> phoneData.setNumber("99999999999"));
        hibernateManager.update(createdUser1);

        createdUser1 = (User) hibernateManager.load(1L, User.class);
        assertNotNull(createdUser1);
        assertEquals(27L, (long) createdUser1.getAge());
    }

    @After
    public void after() {
        try (Session session = hibernateManager.getSessionFactory().openSession()) {
            session.beginTransaction();
            String dropUsersSql = "DROP TABLE USER CASCADE";
            String dropAddressSql = "DROP TABLE ADDRESS_DATA_SET";
            String dropPhoneSql = "DROP TABLE PHONE_DATA_SET";
            session.createSQLQuery(dropUsersSql).executeUpdate();
            session.createSQLQuery(dropAddressSql).executeUpdate();
            session.createSQLQuery(dropPhoneSql).executeUpdate();
            session.getTransaction().commit();
            hibernateManager.closeSessionFactory();
        }
    }

}

import core.model.Address;
import core.model.Phone;
import core.model.User;
import h2.DataSourceH2;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orm.DBService;
import orm.DBServiceImpl;
import orm.HibernateUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDBServiceTest {

    private final String DB_CONNECTION_URL = "jdbc:h2:tcp://localhost/~/test;AUTO_SERVER=TRUE";
    private DataSourceH2 dataBase;
    private SessionFactory sessionFactory;
    private DBService<User> userDBService;

    @BeforeEach
    void setup() throws SQLException {
        dataBase = new DataSourceH2(DB_CONNECTION_URL);
        sessionFactory = HibernateUtil.getSessionFactory(DB_CONNECTION_URL);
        userDBService = new DBServiceImpl<>(sessionFactory, User.class);
    }

    @AfterEach
    void cleanup() throws SQLException {
        sessionFactory.close();
        dataBase.close();
    }

    @Test
    @DisplayName("Select User from Database")
    void testUserSelection() throws SQLException {
        Set<Phone> phones = Set.of(new Phone(1, "123456789"), new Phone(2, "987654321"));
        dataBase.insertUser(1, "User 1", 25);
        dataBase.insertAddress(1, "Street");
        dataBase.insertPhone(1, "123456789", 1);
        dataBase.insertPhone(2, "987654321", 1);
        User user = userDBService.load(1);
        assertEquals("User 1", user.getName());
        assertEquals(25, user.getAge());
        assertEquals("Street", user.getAddress().getStreet());
        assertEquals(phones, user.getPhones());
    }

    @Test
    @DisplayName("Insert User into Database")
    void testUserInsertion() throws SQLException {
        Set<Phone> phones = Set.of(new Phone("11111111111"), new Phone("22222222222"));
        Address address = new Address("Another St");
        User user = new User("User 2", 23, address, phones);
        userDBService.create(user);
        User dataInBase = dataBase.selectUserById(user.getId());
        dataInBase.setAddress(dataBase.selectAddressById(user.getId()));
        dataBase.selectPhoneByUserId(user.getId()).forEach(dataInBase::addPhone);
        assertEquals(dataInBase, user);
    }

    @Test
    @DisplayName("Select all Users")
    void testSelectAllUsers() {
        Set<Phone> firstUserPhoneNumbers = Set.of(new Phone("123456789"), new Phone("987654321"));
        Address firstUserAddresses = new Address("Street");
        User user1 = new User("User", 23, firstUserAddresses, firstUserPhoneNumbers);

        Set<Phone> johnsPhones = Set.of(new Phone("11111111111"), new Phone("22222222222"));
        Address johnAddresses = new Address("Another St");
        User user2 = new User("User 2", 23, johnAddresses, johnsPhones);

        userDBService.create(user1);
        userDBService.create(user2);

        List<User> results = userDBService.load();
        assertEquals(2, results.size());
    }
}

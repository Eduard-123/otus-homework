import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.HibernateManager;
import ru.otus.HibernateManagerImpl;
import ru.otus.model.AddressDataSet;
import ru.otus.model.PhoneDataSet;
import ru.otus.model.User;
import ru.otus.orm.DBService;
import ru.otus.orm.DBServiceImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDBServiceTest {

    private final HibernateManager<User> hibernateManager = new HibernateManagerImpl<>();
    private final DBService<User> service = new DBServiceImpl<>(User.class,hibernateManager);
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();

        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("Random street");

        PhoneDataSet phoneDataSet1 = new PhoneDataSet();
        phoneDataSet1.setUser(user);
        phoneDataSet1.setNumber("123456789");

        PhoneDataSet phoneDataSet2 = new PhoneDataSet();
        phoneDataSet2.setNumber("987654321");
        phoneDataSet2.setUser(user);

        user.setName("User");
        user.setAge(50);
        user.setAddressDataSet(addressDataSet);
        user.setPhoneDataSets(Set.of(phoneDataSet1,phoneDataSet2));

    }

    @Test
    public void shouldCreateAndReadCascadeUser() {
        Long id = hibernateManager.create(user).getId();
        User savedUser = hibernateManager.load(id, User.class);
        assertEquals(user, savedUser);
    }

    @Test
    public void shouldUpdateAndReadCascadeUser() {
        User existedUser = hibernateManager.create(user);
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");
        existedUser.setAddressDataSet(addressDataSet);
        Long updatedUserId = hibernateManager.update(existedUser).getId();

        User updatedUser = hibernateManager.load(updatedUserId, User.class);
        assertEquals(addressDataSet, updatedUser.getAddressDataSet());
        assertEquals(existedUser.getId(), updatedUserId);
    }

    @Test
    public void shouldCreateCascadeUserIfNotExist() {
        user.setName("new created user");

        User createdUser = hibernateManager.createOrUpdate(user);
        assertEquals(user, createdUser);
    }

    @Test
    public void shouldUpdateCascadeUserIfExists() {
        User existedUser = hibernateManager.create(user);
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");
        existedUser.setAddressDataSet(addressDataSet);
        User updatedUser = hibernateManager.createOrUpdate(existedUser);
        assertEquals(existedUser.getId(), updatedUser.getId());
        assertEquals(existedUser, updatedUser);
    }

    @Test
    public void shouldCreateCascadeUser() {
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");

        PhoneDataSet phone = new PhoneDataSet();
        phone.setUser(user);

        user.setAddressDataSet(addressDataSet);
        user.setPhoneDataSets(new HashSet<>(Collections.singletonList(phone)));

        hibernateManager.create(user);
    }

    @Test
    public void saveCascadeUser() {
        User savedUser = service.save(user);
        assertEquals(user, savedUser);
    }

    @Test
    public void updateCascadeUser() {
        User savedUser = service.save(user);

        User updatedUser = service.update(savedUser);
        AddressDataSet addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");
        updatedUser.setAddressDataSet(addressDataSet);

        assertEquals(savedUser.getId(), updatedUser.getId());
        assertEquals(savedUser, updatedUser);
    }
}

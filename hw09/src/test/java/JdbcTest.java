import core.model.Account;
import core.model.User;
import core.service.DbServiceUser;
import core.service.DbServiceAccount;
import core.service.DbServiceAccountImpl;
import core.service.DbServiceUserImpl;
import h2.DataSourceH2;
import jdbc.DbExecutor;
import jdbc.DbExecutorImpl;
import jdbc.dao.AccountDaoJdbcMapper;
import jdbc.dao.UserDaoJdbcMapper;
import jdbc.mapper.JdbcMapper;
import jdbc.mapper.JdbcMapperEager;
import jdbc.mapper.ObjectConverter;
import jdbc.mapper.ObjectConverterImpl;
import jdbc.sessionmanager.SessionManagerJdbc;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcTest {

    private DbServiceAccount dbServiceAccount;
    private DbServiceUser dbServiceUser;

    @Before
    public void before() {
        var dataSource = new DataSourceH2();
        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<Account> accountDbExecutor = new DbExecutorImpl<>();
        ObjectConverter<Account> accountObjectConverter = new ObjectConverterImpl<>();
        JdbcMapper<Account> accountJdbcMapper = new JdbcMapperEager<>(sessionManager, accountDbExecutor, accountObjectConverter, Account.class);
        var accountDao = new AccountDaoJdbcMapper(sessionManager, accountJdbcMapper);
        dbServiceAccount = new DbServiceAccountImpl(accountDao);

        DbExecutor<User> userDbExecutor = new DbExecutorImpl<>();
        ObjectConverter<User> userObjectConverter = new ObjectConverterImpl<User>();
        JdbcMapper<User> userJdbcMapper = new JdbcMapperEager<>(sessionManager, userDbExecutor, userObjectConverter, User.class);
        var userDao = new UserDaoJdbcMapper(sessionManager, userJdbcMapper);
        dbServiceUser = new DbServiceUserImpl(userDao);

    }

    @Test
    public void test() {
        Account account = createAccount(1L, "dbServiceUser", 10);
        var expectedAccountId = 1L;
        assertThat(dbServiceAccount.saveAccount(account)).isEqualTo(expectedAccountId);
        assertThat(dbServiceAccount.getAccount(expectedAccountId))
                .isPresent().get()
                .isEqualToComparingFieldByField(account);

        User newUser = createUser(1L, "dbServiceUser", 10);
        var expectedUserId = 1L;
        assertThat(dbServiceUser.saveUser(newUser)).isEqualTo(expectedUserId);
        assertThat(dbServiceUser.getUser(expectedUserId))
                .isPresent().get()
                .hasFieldOrPropertyWithValue("name", newUser.getName())
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("age", newUser.getAge());

    }


    private Account createAccount(long no, String type, Number rest) {
        Account account = new Account();
        account.setNo(no);
        account.setType(type);
        account.setRest(rest);
        return account;
    }

    private User createUser(long id, String name, int age) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        return user;
    }
}

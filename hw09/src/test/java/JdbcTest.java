import jdbc.DbExecutor;
import jdbc.DbExecutorImpl;
import jdbc.Entity.Account;
import jdbc.Entity.User;
import jdbc.SqlManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JdbcTest {

    @Before
    public void before() throws Exception {
        String userTableSql = "CREATE TABLE IF NOT EXISTS User (id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))";
        String accountTableSql = "CREATE TABLE IF NOT EXISTS Account (no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)";

        try (Connection connection = SqlManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(userTableSql);
            statement.executeUpdate(accountTableSql);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void test() throws Exception {
        DbExecutor<User> userExecutor = DbExecutorImpl.of(User.class);
        User user = new User("User", 26);

        System.out.println("Создаем нового пользователя в базе");
        userExecutor.create(user);
        assertNotNull(user.getId());

        System.out.println("Проверяем что пользователь был создан запросив его по ID = 1:");
        User user1 = userExecutor.load(1L, User.class);
        assertNotNull(user1);
        System.out.println("Пользователь в базе с ID = 1: " + user1);

        System.out.println("Поменяем возраст пользователя на 27");
        userExecutor.update(new User(1L, "User", 27));

        System.out.println("Проверяем что возраст пользователя поменялся:");
        user1 = userExecutor.load(1L, User.class);
        assertNotNull(user1);
        assertEquals(27L, (long) user1.getAge());
        System.out.println("Пользователь в базе с ID = 1: " + user1);

        DbExecutor<Account> accountExecutor = DbExecutorImpl.of(Account.class);

        System.out.println("Проверяем что счет был создан запросив его по NO = 1:");
        Account account = accountExecutor.load(1L, Account.class);
        assertNotNull(account);
        System.out.println("Счет в базе с NO = 1: " + account);

        System.out.println("Проверяем что счет был создан запросив его по NO = 1:");
        account = accountExecutor.load(1L, Account.class);
        assertNotNull(account);
        assertEquals("ProdAccount", account.getType());
        System.out.println("Счет в базе с NO = 1: " + account);
    }

    @After
    public void after() throws Exception {
        String dropUsersSql = "DROP TABLE User";
        String dropAccountsSql = "DROP TABLE Account";

        try (Connection connection = SqlManager.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(dropAccountsSql);
            statement.executeUpdate(dropUsersSql);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

package jdbc;

import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.io.File;
import org.apache.commons.configuration2.Configuration;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static java.util.stream.Collectors.*;

public class SqlManager {

    public static Connection getConnection() throws Exception {
        try {
            Configuration config = new Configurations().properties(new File("application.properties"));
            Connection connection = DriverManager.getConnection(config.getString("database.url"),
                                                                config.getString("database.username"),
                                                                config.getString("database.password"));
            Class.forName(config.getString("database.jdbc.driver"));
            return connection;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static String generateSelect(List<Field> fields, Field primaryKey) {

        return "SELECT " + fields.stream().map(Field::getName).collect(joining(", ")) +
                " FROM " + fields.get(0).getDeclaringClass().getSimpleName() + " WHERE " + primaryKey.getName() + " = ?";
    }

    public static String generateInsert(List<Field> fields, Field primaryKey) {
        StringBuilder query = new StringBuilder();

        List<Field> fieldsWithoutId = fields.stream().filter(field -> !field.getName().equals(primaryKey.getName())).collect(toList());

        query.append("INSERT INTO ").append(fields.get(0).getDeclaringClass().getSimpleName())
             .append(" (").append(fieldsWithoutId.stream().map(Field::getName).collect(joining(", ")))
             .append(") VALUES (");

        for (int i = 0; i < fieldsWithoutId.size(); i++)
            query.append("?, ");

        query.append(")");
        return query.toString();
    }

    public static String generateUpdate(List<Field> fields, Field primaryKey) {
        StringBuilder query = new StringBuilder();

        query.append("UPDATE ").append(fields.get(0).getDeclaringClass().getSimpleName()).append(" SET ");
        query.append(fields.stream()
                           .filter(field -> !field.getName().equals(primaryKey.getName()))
                           .map(Field::getName).collect(joining(" = ?, "))).append(" = ?");

        query.append(" WHERE ").append(primaryKey.getName()).append(" = ?");
        return query.toString();
    }
}

package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static final String DB_URL = "jdbc:mysql://"
            + System.getenv("DB_HOST") + ":"
            + System.getenv("DB_PORT") + "/"
            + System.getenv("DB_NAME");
    public static final String DB_USER = System.getenv("DB_USER");
    public static final String DB_PASS = System.getenv("DB_PASS");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", DB_URL);
            configuration.setProperty("hibernate.connection.username", DB_USER);
            configuration.setProperty("hibernate.connection.password", DB_PASS);

            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            sessionFactory = configuration.buildSessionFactory(builder.build());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public static final String CREATE_USERS_SQL = """
        CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            last_name VARCHAR(255),
            age INT
        );
    """;

    public static final String DROP_USERS_SQL = "DROP TABLE IF EXISTS users;";
    public static final String TRUNCATE_USERS_SQL = "TRUNCATE TABLE users;";

    public UserDaoHibernateImpl() {
    }

    private void executeNativeNoRes(String query) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            tx.commit();
        }
    }

    @Override
    public void createUsersTable() {
        executeNativeNoRes(CREATE_USERS_SQL);
    }

    @Override
    public void dropUsersTable() {
        executeNativeNoRes(DROP_USERS_SQL);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.persist(user);

            tx.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }

            tx.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session
                    .createQuery("FROM User", User.class)
                    .getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        executeNativeNoRes(TRUNCATE_USERS_SQL);
    }
}

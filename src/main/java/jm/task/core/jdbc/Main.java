package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Jack", "Sparrow", (byte) 66);
        userService.saveUser("John", "Snow", (byte) 30);
        userService.saveUser("Иван", "Иванов", (byte) 1);
        userService.saveUser("Darth", "Vader", (byte) 60);

        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

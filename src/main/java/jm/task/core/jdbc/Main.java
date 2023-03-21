package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Mike","Ivanov", (byte) 32);
        userService.saveUser("Walter","White", (byte) 47);
        userService.saveUser("Hank","Black", (byte) 65);
        userService.saveUser("Pedro","Pascal", (byte) 56);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.closeConnection();
    }
}

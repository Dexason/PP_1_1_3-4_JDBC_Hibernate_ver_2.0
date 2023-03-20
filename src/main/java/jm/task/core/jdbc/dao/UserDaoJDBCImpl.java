package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static Statement statement;

    static {
        try {
            statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            statement.executeUpdate("CREATE TABLE Users(id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "username VARCHAR(255),\n" +
                    "lastName VARCHAR(255),\n" +
                    "age INT" + ")");
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }

    public void dropUsersTable() {
         try {
             statement.executeUpdate("DROP TABLE IF EXISTS Users");
         } catch (SQLException e) {
        e.printStackTrace();
         }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement("INSERT INTO Users(username, lastname, age) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement("DELETE FROM Users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> people = new ArrayList<>();
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement("SELECT * FROM Users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                people.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public void cleanUsersTable() {
        try {
            statement.executeUpdate("TRUNCATE TABLE Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConection;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = getConection();
    final private String SQL_CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS USER" +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
    final private String SQL_DROP_USERS_TABLE = "DROP TABLE IF EXISTS USER";
    final private String SQL_SAVE_USER = "INSERT INTO USER (name, lastName, age) VALUES (?, ?, ?)";
    final private String SQL_REMOVE_USER_BY_ID = "DELETE FROM USER WHERE ID = ?";
    final private String SQL_GET_ALL_USERS = "SELECT ID, NAME, LASTNAME, AGE FROM USER";
    final private String SQL_CLEAN_USERS_TABLE = "TRUNCATE TABLE USER";
    private List <User> userList = new ArrayList<>();

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Statement Statement = connection.createStatement()) {
            Statement.executeUpdate(SQL_CREATE_USERS_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement Statement = connection.createStatement()) {
            Statement.executeUpdate(SQL_DROP_USERS_TABLE);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_USERS)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL_CLEAN_USERS_TABLE);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConection;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = getConection();
    List <User> userList = new ArrayList<>();
    final private String sqlCreateUsersTable = "CREATE TABLE IF NOT EXISTS USER" +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
    final private String sqlDropUsersTable = "DROP TABLE IF EXISTS USER";
    final  private String sqlSaveUser = "INSERT INTO USER (name, lastName, age) VALUES (?, ?, ?)";
    final private String sqlRemoveUserById = "DELETE FROM USER WHERE ID = ?";
    final private String sqlGetAllUsers = "SELECT ID, NAME, LASTNAME, AGE FROM USER";
    final private String sqlCleanUsersTable = "TRUNCATE TABLE USER";

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Statement Statement = connection.createStatement()) {
            Statement.executeUpdate(sqlCreateUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement Statement = connection.createStatement()) {
            Statement.executeUpdate(sqlDropUsersTable);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSaveUser)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRemoveUserById)) {
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
             ResultSet resultSet = statement.executeQuery(sqlGetAllUsers)) {
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
            statement.executeUpdate(sqlCleanUsersTable);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

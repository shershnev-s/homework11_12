package by.tut.shershnev_s.repository.impl;

import by.tut.shershnev_s.repository.UserRepository;
import by.tut.shershnev_s.repository.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }

    @Override
    public Long findUser(Connection connection, User user) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        " SELECT role_id FROM user WHERE username=? AND password=?;"
                )
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            ResultSet rs = statement.executeQuery();
            Long result = null;
            while (rs.next()) {
                result = rs.getLong("role_id");
            }
            return result;
        }
    }

    @Override
    public User add(Connection connection, User user) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user(role_id, username, password, created_by) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setLong(1, user.getRoleId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setDate(4, user.getCreatedBy());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
            return user;
        }


    }
}

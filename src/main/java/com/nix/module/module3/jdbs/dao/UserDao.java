package com.nix.module.module3.jdbs.dao;

import com.nix.module.module3.jdbs.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public User findUserById(Long id) {
        User user = new User();
        try (PreparedStatement getOperation = connection.prepareStatement("SELECT * from users where id = ?")) {
            getOperation.setLong(1, id);

            String email;
            String name;
            String phone;

            ResultSet resultSet = getOperation.executeQuery();
            if (resultSet.next()) {
                email = resultSet.getString(2);
                name = resultSet.getString(3);
                phone = resultSet.getString(4);

                user.setEmail(email);
                user.setPhone(phone);
                user.setFullName(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}

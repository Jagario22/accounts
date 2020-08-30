package com.nix.module.module3;

import com.nix.module.module3.jdbs.OrderingMakerDao;
import com.nix.module.module3.jdbs.dao.AccountDao;
import com.nix.module.module3.jdbs.dao.OperationDao;
import com.nix.module.module3.jdbs.dao.UserDao;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

@Slf4j
public class JDBSApplication {
    public static void main(String[] args) {
        String username = args[0];
        String password = args[1];

        Properties props = loadProperties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        String url = props.getProperty("url");

        try (Connection connection = DriverManager.getConnection(url, props)) {
            String dateTo = "2020-12-03T10:15:30";
            String dateFrom= "2019-12-03T10:15:30";

            Scanner in = new Scanner(System.in);
            System.out.println("Введите account id: ");
            Long id = in.nextLong();

            final OperationDao operationDao = new OperationDao(connection);
            final UserDao userDao = new UserDao(connection);
            final AccountDao accountDao = new AccountDao(connection);

            String path = "order.txt";
            final OrderingMakerDao orderingMaker = new OrderingMakerDao(connection, operationDao, userDao, accountDao);

            orderingMaker.makeOrder(id, dateFrom, dateTo);
            orderingMaker.writeOrderIntoFile(path);
            System.out.println("Order is done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Properties loadProperties() {

        Properties props = new Properties();

        try (InputStream input = JDBSApplication.class.getResourceAsStream("JdbcBox.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return props;
    }
}

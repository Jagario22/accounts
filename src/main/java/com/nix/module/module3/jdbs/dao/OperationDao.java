package com.nix.module.module3.jdbs.dao;

import com.nix.module.module3.jdbs.entity.operation.Income;
import com.nix.module.module3.jdbs.entity.operation.Operation;
import com.nix.module.module3.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDao {
    private final Connection connection;

    public OperationDao(Connection connection) {
        this.connection = connection;
    }

    public List<Operation> findOperationsByAccountBetweenDates(Long accountId, String from, String to) {
        List<Operation> incomeOperations = new ArrayList<>();
        Operation operation;
        Long amount;
        Timestamp timestamp;

        try (PreparedStatement getOperation = connection.prepareStatement("SELECT * from operations where timestamp < " +
                "? and timestamp > ? and account_id = ?")) {

            getOperation.setTimestamp(1, DateUtil.getFormattedDate(to));
            getOperation.setTimestamp(2, DateUtil.getFormattedDate(from));
            getOperation.setLong(3, accountId);
            ResultSet resultSet = getOperation.executeQuery();

            while (resultSet.next()) {
                amount = resultSet.getLong(2);
                timestamp = resultSet.getTimestamp(3);

                operation = new Income();
                operation.setTimestamp(timestamp.toInstant());
                operation.setAmount(amount);
                operation.setAccountId(accountId);

                incomeOperations.add(operation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomeOperations;
    }
}

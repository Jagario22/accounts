package com.nix.module.module3.jdbs;

import com.nix.module.module3.csv.parser.CsvTable;
import com.nix.module.module3.jdbs.dao.AccountDao;
import com.nix.module.module3.jdbs.dao.UserDao;
import com.nix.module.module3.jdbs.entity.Account;
import com.nix.module.module3.jdbs.entity.User;
import com.nix.module.module3.jdbs.entity.operation.Operation;
import com.nix.module.module3.jdbs.dao.OperationDao;
import com.nix.module.module3.util.DateUtil;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Setter
public class OrderingMakerDao {
    private final Connection connection;
    private final OperationDao operationDao;
    private final UserDao userDao;
    private final AccountDao accountDao;
    private CsvTable csvTable;

    public OrderingMakerDao(Connection connection, OperationDao operationDao, UserDao userDao, AccountDao accountDao) {
        this.connection = connection;
        this.operationDao = operationDao;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    public CsvTable makeOrder(Long accountId, String from, String to) {
        Account account = accountDao.findAccountById(accountId);
        List<Operation> operations = operationDao.findOperationsByAccountBetweenDates(accountId, from, to);
        if (operations.size() == 0) {
            System.out.println("There were no operations during this time");
            return null;
        }
        User user = userDao.findUserById(account.getUserId());

        Long sumOfIncomes = findSumOfIncome(from, to);
        Long saldo = findSaldo(from, to, sumOfIncomes);


        csvTable = getCsvTable(account, user, operations, saldo, sumOfIncomes);

        return csvTable;
    }

    public void writeOrderIntoFile(String filePath) {
        if (csvTable != null)
            CsvTable.writeIntoFile(csvTable, filePath);
    }

    private Long findSumOfIncome(String from, String to) {
        Long sum = null;
        try (PreparedStatement incomesSum = connection.prepareStatement("select sum(amount) from operations where amount > 0 " +
                "and timestamp < ? and timestamp > ? and account_id = 1")) {
            incomesSum.setTimestamp(1, DateUtil.getFormattedDate(to));
            incomesSum.setTimestamp(2, DateUtil.getFormattedDate(from));

            ResultSet resultSet = incomesSum.executeQuery();
            if (resultSet.next())
                sum = resultSet.getLong(1);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sum;
    }

    private Long findSaldo(String from, String to, Long sumOfIncomes) {
        Long saldo = null;
        try (PreparedStatement incomesSum = connection.prepareStatement(
                "select sum(abs(amount)) from operations where amount < 0 and timestamp < ? " +
                        "and timestamp > ? and account_id = 1")) {
            incomesSum.setTimestamp(1, DateUtil.getFormattedDate(to));
            incomesSum.setTimestamp(2, DateUtil.getFormattedDate(from));

            ResultSet resultSet = incomesSum.executeQuery();

            if (resultSet.next()) {
                saldo = resultSet.getLong(1);
                saldo = sumOfIncomes - saldo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return saldo;
    }

    public CsvTable getCsvTable(Account account, User user, List<Operation> operations, Long saldo, Long sumOfIncomes) {
        List<String> headers = Arrays.asList("account id", "type", "amount", "time", "final balance",
                "saldo", "sum of income", "user name");
        String[][] cells = new String[operations.size()][headers.size()];

        int i = 0;
        int j = 0;
        for (Operation operation : operations) {
            String accountId = account.getId().toString();
            String type = getTypeOfOperation(operation.getAmount());
            String amount = operation.getAmount().toString();
            String time = DateUtil.formatDateToString(operation.getTimestamp());
            String finalBalance = account.getBalance().toString();
            String saldoString = saldo.toString();
            String incomesSum = sumOfIncomes.toString();
            String username = user.getFullName();

            cells[i][j++] = accountId;
            cells[i][j++] = type;
            cells[i][j++] = amount;
            cells[i][j++] = time;
            cells[i][j++] = finalBalance;
            cells[i][j++] = saldoString;
            cells[i][j++] = incomesSum;
            cells[i][j] = username;

            i++;
            j = 0;
        }
        return new CsvTable(headers, cells);
    }

    private String getTypeOfOperation(Long amount) {
        if (amount > 0)
            return "income";
        else if (amount < 0)
            return "expense";
        else
            throw new IllegalArgumentException("Not valid amount");
    }

}

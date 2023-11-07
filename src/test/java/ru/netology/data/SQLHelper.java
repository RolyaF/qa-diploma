package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }
    @SneakyThrows
    private static Connection getConnection() {
        var dbUrl = System.getProperty("db.url");
        var login = System.getProperty("app.user");
        var password = System.getProperty("app.password");
        final Connection connection = DriverManager.getConnection(dbUrl, login, password);
        return connection;
    }

    @SneakyThrows
    public static String getLastPayUserStatus() {
        getConnection();
        var payStatus = "SELECT status FROM payment_entity order by created desc LIMIT 1";
        var result = runner.query(getConnection(), payStatus, new ScalarHandler<String>());
        return result;
    }

    @SneakyThrows
    public static int getLastPayUserAmount() {
        getConnection();
        var amount = "SELECT amount FROM payment_entity order by created desc LIMIT 1";
        var result = runner.query(getConnection(), amount, new ScalarHandler<Integer>());
        return result;
    }

    @SneakyThrows
    public static String getLastPayOnCreditUserStatus() {
        getConnection();
        var creditStatus = "SELECT status FROM credit_request_entity order by created desc LIMIT 1";
        var result = runner.query(getConnection(), creditStatus, new ScalarHandler<String>());
        return result;
    }
}
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
    private static Connection getConn() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static String getLastPayUserStatus() {
        getConn();
        var payStatus = "SELECT status FROM payment_entity order by created desc LIMIT 1";
        var result = runner.query(getConn(), payStatus, new ScalarHandler<String>());
        return result;
    }

    @SneakyThrows
    public static int getLastPayUserAmount() {
        getConn();
        var amount = "SELECT amount FROM payment_entity order by created desc LIMIT 1";
        var result = runner.query(getConn(), amount, new ScalarHandler<Integer>());
        return result;
    }

    @SneakyThrows
    public static String getLastPayOnCreditUserStatus() {
        getConn();
        var creditStatus = "SELECT status FROM credit_request_entity order by created desc LIMIT 1";
        var result = runner.query(getConn(), creditStatus, new ScalarHandler<String>());
        return result;
    }
}
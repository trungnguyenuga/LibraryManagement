package com.aehanoidz123.librarymanagement.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL =
            "jdbc:mysql://librarymanagementdb.cti2u2mwu67l.us-east-2.rds.amazonaws.com:3306/librarymanagementdb";
    private static final String USER = "admin";
    private static final String PASSWORD = "v4tm587SdhRvsaqVkAX6";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database " + e.getMessage());
        }
        return connection;
    }
}

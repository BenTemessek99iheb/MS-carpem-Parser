package com.carpem.parser.Utils.tools;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DBConnectionCheck implements CommandLineRunner {
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/carpem?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource().getConnection()) {
            System.out.println("Successfully connected to the database.");
            DatabaseMetaData metaData = connection.getMetaData();
            // Retrieve all table names from carpem
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            // Check if there tAbles
            boolean hasTables = false;
            while (tables.next()) {
                hasTables = true;
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
            }
            if (!hasTables) {
                System.out.println("No tables found in the database.");
            }
        } catch (SQLException e) {
            //  connection errors
            System.err.println("Error while connecting to the database:");
            System.err.println("URL: jdbc:mysql://localhost:3306/carpem");
            System.err.println("Username: root");
            System.err.println("Please check the database configuration or ensure the database is running.");
            // Log the full exception details
            e.printStackTrace();
        }
    }
}

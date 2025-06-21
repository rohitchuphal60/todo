package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://mysql-db:3306/mydb"; // mysql-db is container name
        String username = "root";
        String password = "root";

        try {
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS hello (message VARCHAR(255))");
            stmt.execute("INSERT INTO hello (message) VALUES ('Hello from Java with JDBC!')");

            ResultSet rs = stmt.executeQuery("SELECT * FROM hello");
            while (rs.next()) {
                System.out.println("Message: " + rs.getString("message"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

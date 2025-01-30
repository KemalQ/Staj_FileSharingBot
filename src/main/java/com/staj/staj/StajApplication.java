package com.staj.staj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class StajApplication {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3307/mysql-latest";
		String username = "admin";
		String password = "root";

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			if (connection != null) {
				System.out.println("Successfully connected to the database.");
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		SpringApplication.run(StajApplication.class, args);
	}

}

package com.hexaware.util;

import java.io.IOException;

import java.io.InputStream;
import java.util.Properties;

public class DBPropertyUtil {

    public static String getPropertyString() {
        Properties prop = new Properties();
        String connectionString = "";

        try (InputStream input = DBPropertyUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input != null) {
                prop.load(input);

                String hostname = prop.getProperty("hostname");
                String port = prop.getProperty("port");
                String dbname = prop.getProperty("dbname");
                String username = prop.getProperty("username");
                String password = prop.getProperty("password");

                connectionString = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname +
                                    "?user=" + username + "&password=" + password;
            } else {
                System.out.println("Sorry, db.properties not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connectionString;
    }
}

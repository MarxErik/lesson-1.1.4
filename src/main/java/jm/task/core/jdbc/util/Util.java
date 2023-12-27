package jm.task.core.jdbc.util;

import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/sys";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "KJnfkjfnwkbhw2522";
    private static Connection connection = null;
    private static final Logger logger = Logger.getLogger(Util.class.getName());

    static {
        String path = Objects.requireNonNull(Util.class.getClassLoader()
                .getResource("logging.properties")).getFile();
        System.setProperty("java.util.logging.config.file", path);
    }

    public static Connection getConection() {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            logger.info("Конект с БД установлен");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

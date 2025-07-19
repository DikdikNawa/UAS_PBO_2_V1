package com.dikdik.uas_pbo2_v4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:konveksi.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL);
           } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}

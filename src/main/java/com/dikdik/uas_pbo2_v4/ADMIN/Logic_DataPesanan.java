package com.dikdik.uas_pbo2_v4.ADMIN;

import com.dikdik.uas_pbo2_v4.DBConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Logic_DataPesanan {
    private Panel_DataPesanan panel;

    public Logic_DataPesanan() {
        panel = new Panel_DataPesanan();
        panel.btnRefresh.addActionListener(e -> loadData());
        loadData();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadData() {
        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pesanan");

            ArrayList<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id_pesanan"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("no_telepon"),
                    rs.getString("jenis_pesanan"),
                    rs.getInt("jumlah"),
                    rs.getInt("uang_muka"),
                    rs.getString("status"),
                    rs.getObject("total_harga")
                };
                data.add(row);
            }
            panel.setData(data.toArray(new Object[0][]));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

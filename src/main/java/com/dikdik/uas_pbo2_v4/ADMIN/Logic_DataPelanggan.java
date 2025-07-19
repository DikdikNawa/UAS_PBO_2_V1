package com.dikdik.uas_pbo2_v4.ADMIN;

import com.dikdik.uas_pbo2_v4.DBConnection;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class Logic_DataPelanggan {
    private Panel_DataPelanggan panel;

    public Logic_DataPelanggan() {
        panel = new Panel_DataPelanggan();
        loadData();

        panel.btnRefresh.addActionListener(e -> loadData());
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadData() {
        try (Connection conn = DBConnection.connect()) {
            String sql = "SELECT id_pesanan, nama_pelanggan, no_telepon, alamat, total_harga FROM pesanan"; // atau pelanggan
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            Vector<Object[]> data = new Vector<>();
            while (rs.next()) {
                Object[] row = new Object[] {
                    rs.getInt("id_Pesanan"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("no_telepon"),
                    rs.getString("alamat"),
                    formatRupiah(rs.getInt("total_harga"))
                };
                data.add(row);
            }

            panel.setData(data.toArray(new Object[0][]));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat data pelanggan: " + e.getMessage());
        }
    }

    private String formatRupiah(int value) {
        return String.format("Rp %,d", value).replace(',', '.');
    }
}

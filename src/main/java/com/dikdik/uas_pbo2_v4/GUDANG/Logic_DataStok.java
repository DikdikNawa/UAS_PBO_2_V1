package com.dikdik.uas_pbo2_v4.GUDANG;

import com.dikdik.uas_pbo2_v4.DBConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.*;

public class Logic_DataStok {
    private Panel_DataStok panel;

    public Logic_DataStok() {
        panel = new Panel_DataStok();
        loadData();
        panel.btnRefresh.addActionListener(e -> loadData());
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadData() {
        loadStok();
        loadPemakaian();
        loadSummary();
    }

    private void loadStok() {
        DefaultTableModel m = panel.modelStok;
        m.setRowCount(0);

        String sql = "SELECT nama_bahan, harga_per_pcs, jumlah_tersedia FROM bahan_baku";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nama = rs.getString("nama_bahan");
                int harga = rs.getInt("harga_per_pcs");
                int jumlah = rs.getInt("jumlah_tersedia");
                int total = harga * jumlah;

                m.addRow(new Object[]{
                        nama,
                        "Rp " + formatRupiah(harga),
                        jumlah,
                        "Rp " + formatRupiah(total)
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadPemakaian() {
        DefaultTableModel m = panel.modelPemakaian;
        m.setRowCount(0);

        String sql = "SELECT id_produksi, nama_bahan, jumlah_digunakan, subtotal FROM produksi_stok";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idProd = rs.getInt("id_produksi");
                String bahan = rs.getString("nama_bahan");
                int jumlah = rs.getInt("jumlah_digunakan");
                int subtotal = rs.getInt("subtotal");

                m.addRow(new Object[]{
                        idProd,
                        bahan,
                        jumlah,
                        "Rp " + formatRupiah(subtotal)
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadSummary() {
        try (Connection conn = DBConnection.connect()) {

            // Total Bahan Terpakai dan Nilai
            String sql1 = "SELECT SUM(jumlah_digunakan) AS total_pakai, SUM(subtotal) AS total_nilai FROM produksi_stok";
            try (PreparedStatement ps1 = conn.prepareStatement(sql1);
                 ResultSet rs1 = ps1.executeQuery()) {
                if (rs1.next()) {
                    panel.lblTotalTerpakai.setText("Total Bahan Terpakai: " + rs1.getInt("total_pakai") + " pcs");
                    panel.lblTotalNilaiTerpakai.setText("Total Nilai Bahan Terpakai: Rp " + formatRupiah(rs1.getInt("total_nilai")));
                }
            }

            // Total Nilai Stok Saat Ini
            String sql2 = "SELECT SUM(harga_per_pcs * jumlah_tersedia) AS total_stok FROM bahan_baku";
            try (PreparedStatement ps2 = conn.prepareStatement(sql2);
                 ResultSet rs2 = ps2.executeQuery()) {
                if (rs2.next()) {
                    panel.lblTotalNilaiStok.setText("Total Nilai Stok Saat Ini: Rp " + formatRupiah(rs2.getInt("total_stok")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatRupiah(int value) {
        return String.format("%,d", value).replace(',', '.');
    }
}

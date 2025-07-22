package com.dikdik.uas_pbo2_v4.PEMILIK;

import com.dikdik.uas_pbo2_v4.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logic_DataKeuangan {
    private Panel_DataKeuangan panel;

    public Logic_DataKeuangan() {
        panel = new Panel_DataKeuangan();
        loadData();
        panel.btnRefresh.addActionListener(e -> loadData());
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadData() {
        DefaultTableModel m1 = (DefaultTableModel) panel.tablePemasukan.getModel();
        DefaultTableModel m2 = (DefaultTableModel) panel.tablePengeluaran.getModel();
        DefaultTableModel m3 = (DefaultTableModel) panel.tableDiproses.getModel();

        m1.setRowCount(0);
        m2.setRowCount(0);
        m3.setRowCount(0);

        int totalPemasukan = 0, totalPengeluaran = 0, totalUangMukaDiproses = 0;

        // ===== PEMASUKAN =====
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement("SELECT id_pesanan, nama_pelanggan, total_harga, uang_muka FROM pesanan WHERE total_harga > 0 AND status = 'Selesai'");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_pesanan");
                String nama = rs.getString("nama_pelanggan");
                int totalHarga = rs.getInt("total_harga");
                int dp = rs.getInt("uang_muka");
                int totalPemasukanPesanan = totalHarga + dp;

                totalPemasukan += totalPemasukanPesanan;

                m1.addRow(new Object[]{id, nama, totalHarga, dp, totalPemasukanPesanan});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== PENGELUARAN =====
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement("SELECT id_produksi, nama_bahan, jumlah_digunakan, subtotal FROM produksi_stok");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_produksi");
                String namaBahan = rs.getString("nama_bahan");
                int jumlah = rs.getInt("jumlah_digunakan");
                int subtotal = rs.getInt("subtotal");
                totalPengeluaran += subtotal;
                m2.addRow(new Object[]{id, namaBahan, jumlah, subtotal});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== DIPROSES =====
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement("SELECT id_pesanan, nama_pelanggan, jenis_pesanan, uang_muka, status FROM pesanan WHERE status != 'Selesai'");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_pesanan");
                String nama = rs.getString("nama_pelanggan");
                String jenis = rs.getString("jenis_pesanan");
                int dp = rs.getInt("uang_muka");
                String status = rs.getString("status");

                totalUangMukaDiproses += dp;
                m3.addRow(new Object[]{id, nama, jenis, dp, status});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== LABEL =====
        panel.lblTotalPemasukan.setText("Total Pemasukan: Rp " + formatRupiah(totalPemasukan));
        panel.lblTotalPengeluaran.setText("Total Pengeluaran: Rp " + formatRupiah(totalPengeluaran));
        panel.lblTotalDiproses.setText("Total Uang Muka Diproses: Rp " + formatRupiah(totalUangMukaDiproses));

        int keuntungan = totalPemasukan - totalPengeluaran;
        panel.lblTotalKeseluruhan.setText("Keuntungan: Rp " + formatRupiah(keuntungan));
    }

    private String formatRupiah(int amount) {
        return String.format("%,d", amount).replace(',', '.');
    }
}

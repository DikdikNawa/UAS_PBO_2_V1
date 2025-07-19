package com.dikdik.uas_pbo2_v4.ADMIN;

import com.dikdik.uas_pbo2_v4.DBConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Logic_TambahPesanan {
    private Panel_TambahPesanan panel;

    public Logic_TambahPesanan() {
        panel = new Panel_TambahPesanan();
        panel.btnSimpan.addActionListener(e -> simpanPesanan());
    }

    public JPanel getPanel() {
        return panel;
    }

    private void simpanPesanan() {
        String nama = panel.tfNama.getText().trim();
        String telepon = panel.tfTelepon.getText().trim();
        String alamat = panel.taAlamat.getText().trim();
        String jenis = (String) panel.cbJenisPesanan.getSelectedItem();
        String jumlahStr = panel.tfJumlah.getText().trim();
        String uangMukaStr = panel.tfUangMuka.getText().trim();
        String deskripsi = panel.taDeskripsi.getText().trim();

        if (nama.isEmpty() || telepon.isEmpty() || alamat.isEmpty() || jumlahStr.isEmpty() || uangMukaStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int jumlah = Integer.parseInt(jumlahStr);
            int dp = Integer.parseInt(uangMukaStr);

            Connection conn = DBConnection.connect();
            String sql = "INSERT INTO pesanan (nama_pelanggan, no_telepon, alamat, jenis_pesanan, jumlah, uang_muka, deskripsi) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nama);
            stmt.setString(2, telepon);
            stmt.setString(3, alamat);
            stmt.setString(4, jenis);
            stmt.setInt(5, jumlah);
            stmt.setInt(6, dp);
            stmt.setString(7, deskripsi);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pesanan berhasil disimpan!");
            bersihkanForm();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Jumlah dan Uang Muka harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menyimpan pesanan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bersihkanForm() {
        panel.tfNama.setText("");
        panel.tfTelepon.setText("");
        panel.taAlamat.setText("");
        panel.cbJenisPesanan.setSelectedIndex(0);
        panel.tfJumlah.setText("");
        panel.tfUangMuka.setText("");
        panel.taDeskripsi.setText("");
    }
}

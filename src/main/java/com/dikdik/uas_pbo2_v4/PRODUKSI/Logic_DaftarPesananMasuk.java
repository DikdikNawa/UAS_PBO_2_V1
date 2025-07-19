package com.dikdik.uas_pbo2_v4.PRODUKSI;

import com.dikdik.uas_pbo2_v4.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Logic_DaftarPesananMasuk {
    private Panel_DaftarPesananMasuk panel;
    public Logic_DaftarPesananMasuk(){
        panel = new Panel_DaftarPesananMasuk();
        loadData();
        panel.btnTerima.addActionListener(e->terimaPesanan());
    }
    public JPanel getPanel(){return panel;}

    private void loadData(){
        DefaultTableModel m=(DefaultTableModel)panel.table.getModel();
        m.setRowCount(0);
        String sql="SELECT id_pesanan,nama_pelanggan,jenis_pesanan,jumlah,deskripsi FROM pesanan WHERE status='Menunggu'";
        try(Connection conn=DBConnection.connect();
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                m.addRow(new Object[]{
                  rs.getInt(1), rs.getString(2), rs.getString(3),
                  rs.getInt(4), rs.getString(5)
                });
            }
        } catch(Exception ex){ex.printStackTrace();}
    }

    private void terimaPesanan(){
        int row = panel.table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Pilih pesanan.");
            return;
        }

        int id = (int) panel.table.getValueAt(row, 0);

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);

            // Ambil data dari pesanan
            String getData = "SELECT nama_pelanggan, jenis_pesanan, jumlah, deskripsi FROM pesanan WHERE id_pesanan = ?";
            String nama = "", jenis = "", deskripsi = "";
            int jumlah = 0;

            try (PreparedStatement ps = conn.prepareStatement(getData)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nama = rs.getString("nama_pelanggan");
                        jenis = rs.getString("jenis_pesanan");
                        jumlah = rs.getInt("jumlah");
                        deskripsi = rs.getString("deskripsi");
                    } else {
                        JOptionPane.showMessageDialog(null, "Data pesanan tidak ditemukan.");
                        return;
                    }
                }
            }

            // Insert ke tabel produksi
            String ins = "INSERT INTO produksi (id_pesanan, nama_pelanggan, jenis_pesanan, jumlah, deskripsi, status, total_biaya_produksi) VALUES (?, ?, ?, ?, ?, 'Diproses', 0)";
            try (PreparedStatement ps = conn.prepareStatement(ins)) {
                ps.setInt(1, id);
                ps.setString(2, nama);
                ps.setString(3, jenis);
                ps.setInt(4, jumlah);
                ps.setString(5, deskripsi);
                ps.executeUpdate();
            }

            // Update status di tabel pesanan
            String upd = "UPDATE pesanan SET status = 'Diproses' WHERE id_pesanan = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(upd)) {
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(null, "Pesanan diterima.");
            loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menerima pesanan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

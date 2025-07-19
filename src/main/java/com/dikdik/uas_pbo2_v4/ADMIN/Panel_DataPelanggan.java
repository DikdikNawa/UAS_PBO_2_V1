package com.dikdik.uas_pbo2_v4.ADMIN;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Panel_DataPelanggan extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    public JButton btnRefresh;

    public Panel_DataPelanggan() {
        setLayout(new BorderLayout());

        // === Judul Panel Atas dengan tombol Refresh ===
        btnRefresh = new JButton("Refresh");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(btnRefresh);
        add(topPanel, BorderLayout.NORTH);

        // === Tabel dan Model ===
        String[] kolom = {
            "ID", "Nama Pelanggan", "No Telepon", "Alamat", "Total Harga"
        };

        model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua kolom tidak bisa diedit langsung
            }
        };

        table = new JTable(model);
        table.setRowHeight(30); // jarak antar baris lebih renggang

        // Lebar kolom disesuaikan
        table.getColumnModel().getColumn(0).setPreferredWidth(80);   // ID Pesanan
        table.getColumnModel().getColumn(1).setPreferredWidth(180);  // Nama Pelanggan
        table.getColumnModel().getColumn(2).setPreferredWidth(130);  // No Telepon
        table.getColumnModel().getColumn(3).setPreferredWidth(220);  // Alamat
        table.getColumnModel().getColumn(4).setPreferredWidth(120);  // Total Harga

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Method untuk mengisi data ke tabel
    public void setData(Object[][] data) {
        model.setRowCount(0); // Hapus data lama
        for (Object[] row : data) {
            model.addRow(row);
        }
    }
}

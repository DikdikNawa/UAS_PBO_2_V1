package com.dikdik.uas_pbo2_v4.GUDANG;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Panel_DataStok extends JPanel {
    public JTable tableStok, tablePemakaian;
    public DefaultTableModel modelStok, modelPemakaian;
    public JButton btnRefresh;

    public JLabel lblTotalTerpakai, lblTotalNilaiTerpakai, lblTotalNilaiStok;

    public Panel_DataStok() {
        setLayout(new BorderLayout());

        // === Panel Atas: Tombol Refresh ===
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefresh = new JButton("Refresh");
        topPanel.add(btnRefresh);
        add(topPanel, BorderLayout.NORTH);

        // === Tabel Stok Saat Ini ===
        String[] kolomStok = {"Nama Bahan", "Harga per pcs", "Jumlah", "Total Harga Stok"};
        modelStok = new DefaultTableModel(kolomStok, 0);
        tableStok = new JTable(modelStok);
        JScrollPane scrollStok = new JScrollPane(tableStok);

        // === Tabel Riwayat Pemakaian Bahan ===
        String[] kolomPak = {"ID Produksi", "Nama Bahan", "Jumlah Digunakan", "Subtotal"};
        modelPemakaian = new DefaultTableModel(kolomPak, 0);
        tablePemakaian = new JTable(modelPemakaian);
        JScrollPane scrollPak = new JScrollPane(tablePemakaian);

        // === Panel Tabel Tengah (Gabung 2 Tabel) ===
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 10, 10));
        centerPanel.add(scrollStok);
        centerPanel.add(scrollPak);

        add(centerPanel, BorderLayout.CENTER);

        // === Panel Info Total ===
        lblTotalTerpakai = new JLabel("Total Bahan Terpakai: -");
        lblTotalNilaiTerpakai = new JLabel("Total Nilai Bahan Terpakai: -");
        lblTotalNilaiStok = new JLabel("Total Nilai Stok Saat Ini: -");

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(lblTotalTerpakai);
        infoPanel.add(lblTotalNilaiTerpakai);
        infoPanel.add(lblTotalNilaiStok);

        add(infoPanel, BorderLayout.SOUTH);
    }
}

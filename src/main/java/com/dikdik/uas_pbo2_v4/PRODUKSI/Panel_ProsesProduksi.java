package com.dikdik.uas_pbo2_v4.PRODUKSI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Panel_ProsesProduksi extends JPanel {
    public JTable table;
    public JButton btnSimpan, btnRefresh;
    public JTextField tfKain, tfBenang, tfResleting, tfKulit, tfKancing;

    public Panel_ProsesProduksi(){
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabel Produksi
        String[] cols = {"ID Produksi", "Nama Pelanggan", "Jenis", "Jumlah", "Status", "Total Biaya"};
        table = new JTable(new DefaultTableModel(cols, 0));
        table.setRowHeight(28);
        JScrollPane scrollPane = new JScrollPane(table);

        // Tombol Refresh
        btnRefresh = new JButton("Refresh");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(btnRefresh);

        // Panel Form Bahan Baku
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Penggunaan Bahan Baku"));

        tfKain = new JTextField("0");
        tfBenang = new JTextField("0");
        tfResleting = new JTextField("0");
        tfKulit = new JTextField("0");
        tfKancing = new JTextField("0");

        form.add(new JLabel("Kain:")); form.add(tfKain);
        form.add(new JLabel("Benang:")); form.add(tfBenang);
        form.add(new JLabel("Resleting:")); form.add(tfResleting);
        form.add(new JLabel("Kulit:")); form.add(tfKulit);
        form.add(new JLabel("Kancing:")); form.add(tfKancing);

        // Panel Bawah
        btnSimpan = new JButton("Simpan Produksi");
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(form, BorderLayout.CENTER);
        bottomPanel.add(btnSimpan, BorderLayout.SOUTH);

        // Tambahkan ke panel utama
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}

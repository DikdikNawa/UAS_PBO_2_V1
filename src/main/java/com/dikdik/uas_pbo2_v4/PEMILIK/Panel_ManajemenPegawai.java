package com.dikdik.uas_pbo2_v4.PEMILIK;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Panel_ManajemenPegawai extends JPanel {
    public JTable table;
    public JButton btnTambah, btnRefresh;

    public Panel_ManajemenPegawai() {
        setLayout(new BorderLayout());

        // Tombol atas
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambah = new JButton("Tambah Akun");
        btnRefresh = new JButton("Refresh");
        topPanel.add(btnTambah);
        topPanel.add(btnRefresh);
        add(topPanel, BorderLayout.NORTH);

        // Tabel Pegawai
        String[] cols = {"ID", "Nama", "Umur", "Alamat", "No Telepon", "Username", "Role", "Edit", "Hapus"};
        table = new JTable(new DefaultTableModel(cols, 0));
        table.setRowHeight(30);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}

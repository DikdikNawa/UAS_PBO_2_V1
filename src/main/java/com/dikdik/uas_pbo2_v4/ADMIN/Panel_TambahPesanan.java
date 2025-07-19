package com.dikdik.uas_pbo2_v4.ADMIN;

import javax.swing.*;
import java.awt.*;

public class Panel_TambahPesanan extends JPanel {
    public JTextField tfNama, tfTelepon, tfJumlah, tfUangMuka;
    public JTextArea taAlamat, taDeskripsi;
    public JComboBox<String> cbJenisPesanan;
    public JButton btnSimpan;

    public Panel_TambahPesanan() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        // Nama
        formPanel.add(new JLabel("Nama Pelanggan:"), gbc);
        tfNama = new JTextField();
        tfNama.setFont(font);
        gbc.gridx = 1;
        formPanel.add(tfNama, gbc);

        // Telepon
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("No Telepon:"), gbc);
        tfTelepon = new JTextField();
        tfTelepon.setFont(font);
        gbc.gridx = 1;
        formPanel.add(tfTelepon, gbc);

        // Alamat
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Alamat:"), gbc);
        taAlamat = new JTextArea(3, 20);
        taAlamat.setFont(font);
        taAlamat.setLineWrap(true);
        taAlamat.setWrapStyleWord(true);
        JScrollPane scrollAlamat = new JScrollPane(taAlamat);
        gbc.gridx = 1;
        formPanel.add(scrollAlamat, gbc);

        // Jenis Pesanan
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Jenis Pesanan:"), gbc);
        cbJenisPesanan = new JComboBox<>(new String[]{"Seragam", "Kaos", "Jaket", "Celana"});
        cbJenisPesanan.setFont(font);
        gbc.gridx = 1;
        formPanel.add(cbJenisPesanan, gbc);

        // Jumlah
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Jumlah (pcs):"), gbc);
        tfJumlah = new JTextField();
        tfJumlah.setFont(font);
        gbc.gridx = 1;
        formPanel.add(tfJumlah, gbc);

        // Uang Muka
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Uang Muka (DP):"), gbc);
        tfUangMuka = new JTextField();
        tfUangMuka.setFont(font);
        gbc.gridx = 1;
        formPanel.add(tfUangMuka, gbc);

        // Deskripsi
        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Deskripsi:"), gbc);
        taDeskripsi = new JTextArea(3, 20);
        taDeskripsi.setFont(font);
        taDeskripsi.setLineWrap(true);
        taDeskripsi.setWrapStyleWord(true);
        JScrollPane scrollDeskripsi = new JScrollPane(taDeskripsi);
        gbc.gridx = 1;
        formPanel.add(scrollDeskripsi, gbc);

        // Tombol Simpan
        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        btnSimpan = new JButton("Simpan Pesanan");
        formPanel.add(btnSimpan, gbc);

        add(formPanel, BorderLayout.CENTER);
    }
}

package com.dikdik.uas_pbo2_v4.PEMILIK;

import com.dikdik.uas_pbo2_v4.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Logic_ManajemenPegawai {
    private final Panel_ManajemenPegawai panel;

    public Logic_ManajemenPegawai() {
        panel = new Panel_ManajemenPegawai();
        loadData();

        panel.btnRefresh.addActionListener(e -> loadData());
        panel.btnTambah.addActionListener(e -> showForm(null));

        panel.table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int col = panel.table.getSelectedColumn();
                int row = panel.table.getSelectedRow();
                int id = (int) panel.table.getValueAt(row, 0);

                if (col == 7) { // Edit
                    showForm(id);
                } else if (col == 8) { // Hapus
                    int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus akun ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        hapusPegawai(id);
                        loadData();
                    }
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) panel.table.getModel();
        model.setRowCount(0);
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM pegawai");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_pegawai"),
                    rs.getString("nama_pegawai"),
                    rs.getInt("umur_pegawai"),
                    rs.getString("alamat_pegawai"),
                    rs.getString("no_telepon"),
                    rs.getString("username"),
                    rs.getString("role"),
                    "Edit", "Hapus"
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showForm(Integer id) {
        JDialog dialog = new JDialog((Frame) null, id == null ? "Tambah Akun" : "Edit Akun", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(8, 2, 8, 8));
        JTextField tfNama = new JTextField();
        JTextField tfUmur = new JTextField();
        JTextField tfAlamat = new JTextField();
        JTextField tfTelepon = new JTextField();
        JTextField tfUsername = new JTextField();
        JTextField tfPassword = new JTextField();
        JComboBox<String> cbRole = new JComboBox<>(new String[]{"Pemilik", "Admin", "Pegawai", "Gudang"});

        form.add(new JLabel("Nama:")); form.add(tfNama);
        form.add(new JLabel("Umur:")); form.add(tfUmur);
        form.add(new JLabel("Alamat:")); form.add(tfAlamat);
        form.add(new JLabel("No Telepon:")); form.add(tfTelepon);
        form.add(new JLabel("Username:")); form.add(tfUsername);
        form.add(new JLabel("Password:")); form.add(tfPassword);
        form.add(new JLabel("Role:")); form.add(cbRole);

        JButton btnSimpan = new JButton(id == null ? "Simpan" : "Update");
        form.add(btnSimpan);

        if (id != null) {
            try (Connection conn = DBConnection.connect();
                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM pegawai WHERE id_pegawai=?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        tfNama.setText(rs.getString("nama_pegawai"));
                        tfUmur.setText(String.valueOf(rs.getInt("umur_pegawai")));
                        tfAlamat.setText(rs.getString("alamat_pegawai"));
                        tfTelepon.setText(rs.getString("no_telepon"));
                        tfUsername.setText(rs.getString("username"));
                        tfPassword.setText(rs.getString("password"));
                        cbRole.setSelectedItem(rs.getString("role"));
                        tfUsername.setEnabled(false); // tidak boleh edit username
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        btnSimpan.addActionListener(e -> {
            String nama = tfNama.getText().trim();
            String alamat = tfAlamat.getText().trim();
            String telp = tfTelepon.getText().trim();
            String username = tfUsername.getText().trim();
            String password = tfPassword.getText().trim();
            String role = cbRole.getSelectedItem().toString().trim();
            String umurText = tfUmur.getText().trim();

            // Validasi input kosong
            if (nama.isEmpty() || alamat.isEmpty() || telp.isEmpty() ||
                username.isEmpty() || password.isEmpty() || umurText.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Harap isi semua data!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validasi angka
            int umur;
            try {
                umur = Integer.parseInt(umurText);
                Long.parseLong(telp); // validasi telp angka
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Umur dan No Telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DBConnection.connect()) {
                if (id == null) {
                    // INSERT
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO pegawai(nama_pegawai, umur_pegawai, alamat_pegawai, no_telepon, username, password, role) VALUES(?,?,?,?,?,?,?)")) {
                        ps.setString(1, nama); ps.setInt(2, umur); ps.setString(3, alamat);
                        ps.setString(4, telp); ps.setString(5, username); ps.setString(6, password);
                        ps.setString(7, role); ps.executeUpdate();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username,password,role) VALUES(?,?,?)")) {
                        ps.setString(1, username); ps.setString(2, password); ps.setString(3, role);
                        ps.executeUpdate();
                    }
                } else {
                    // UPDATE
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE pegawai SET nama_pegawai=?, umur_pegawai=?, alamat_pegawai=?, no_telepon=?, password=?, role=? WHERE id_pegawai=?")) {
                        ps.setString(1, nama); ps.setInt(2, umur); ps.setString(3, alamat);
                        ps.setString(4, telp); ps.setString(5, password); ps.setString(6, role); ps.setInt(7, id);
                        ps.executeUpdate();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE users SET password=?, role=? WHERE username=?")) {
                        ps.setString(1, password); ps.setString(2, role); ps.setString(3, username);
                        ps.executeUpdate();
                    }
                }

                JOptionPane.showMessageDialog(dialog, "Data berhasil disimpan.");
                dialog.dispose();
                loadData();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Gagal menyimpan data.");
            }
        });


        dialog.add(form);
        dialog.setVisible(true);
    }

    private void hapusPegawai(int id) {
        try (Connection conn = DBConnection.connect()) {
            String username = "";
            try (PreparedStatement ps = conn.prepareStatement("SELECT username FROM pegawai WHERE id_pegawai=?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        username = rs.getString(1);
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM pegawai WHERE id_pegawai=?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE username=?")) {
                ps.setString(1, username);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

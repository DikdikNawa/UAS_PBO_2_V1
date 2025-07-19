package com.dikdik.uas_pbo2_v4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Panel_Login extends JPanel {
    public JTextField tfUsername;
    public JPasswordField pfPassword;
    public JRadioButton rbPemilik, rbAdmin, rbProduksi, rbGudang;
    public ButtonGroup roleGroup;
    public JButton btnLogin;

    // Tambahan untuk status message
    private JLabel lblStatus;

    public Panel_Login() {
        setLayout(new GridLayout(1, 2)); // Kiri (form), kanan (info)

        // === Panel Kiri ===
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 14, 8, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 26);
        Font subtitleFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Judul
        JLabel lblTitle = new JLabel("SIKODI", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(new Color(0, 70, 140));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelForm.add(lblTitle, gbc);

        // Subjudul
        JLabel lblSubtitle = new JLabel("Sistem Konveksi Digital", SwingConstants.CENTER);
        lblSubtitle.setFont(subtitleFont);
        lblSubtitle.setForeground(new Color(90, 90, 90));
        gbc.gridy++;
        panelForm.add(lblSubtitle, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy++;
        gbc.gridx = 0;
        panelForm.add(new JLabel("Username:"), gbc);
        tfUsername = new JTextField();
        tfUsername.setFont(font);
        gbc.gridx = 1;
        panelForm.add(tfUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Password:"), gbc);
        pfPassword = new JPasswordField();
        pfPassword.setFont(font);
        gbc.gridx = 1;
        panelForm.add(pfPassword, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Role:"), gbc);

        JPanel rolePanel = new JPanel(new GridLayout(2, 2, 10, 5));
        rolePanel.setOpaque(false);
        rbPemilik = new JRadioButton("Pemilik");
        rbAdmin = new JRadioButton("Admin");
        rbProduksi = new JRadioButton("Produksi");
        rbGudang = new JRadioButton("Gudang");

        JRadioButton[] roles = { rbPemilik, rbAdmin, rbProduksi, rbGudang };
        roleGroup = new ButtonGroup();
        for (JRadioButton rb : roles) {
            rb.setBackground(Color.WHITE);
            rb.setFont(font);
            roleGroup.add(rb);
            rolePanel.add(rb);
        }

        gbc.gridx = 1;
        panelForm.add(rolePanel, gbc);

        // Tombol Login
        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(font);
        btnLogin.setBackground(new Color(10, 120, 210));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(0, 90, 180));
            }
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(new Color(10, 120, 210));
            }
        });

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelForm.add(btnLogin, gbc);

        // Status Message (Tambahan)
        lblStatus = new JLabel(" ", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridy++;
        panelForm.add(lblStatus, gbc);

        // Padding bawah
        gbc.gridy++;
        panelForm.add(Box.createVerticalStrut(20), gbc);

        add(panelForm);

        // === Panel Kanan ===
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(new Color(10, 90, 170));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.CENTER;

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(10, 90, 170));
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 20));

        JLabel lblWelcome = new JLabel("SELAMAT DATANG!");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        textPanel.add(lblWelcome);

        Font infoFont = new Font("Segoe UI", Font.PLAIN, 16);
        String[] teksInfo = {
            "-> Sistem Konveksi Digital berbasis Desktop",
            "-> Sistem Manajemen Digital",
            "-> Cepat dan Responsif",
            "-> Integrasi Database SQLite"
        };

        for (String teks : teksInfo) {
            JLabel lbl = new JLabel(teks);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(infoFont);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            lbl.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
            textPanel.add(lbl);
        }

        panelInfo.add(textPanel, gbc2);
        add(panelInfo);
    }

    public String getSelectedRole() {
        if (rbPemilik.isSelected()) return "Pemilik";
        if (rbAdmin.isSelected()) return "Admin";
        if (rbProduksi.isSelected()) return "Produksi";
        if (rbGudang.isSelected()) return "Gudang";
        return null;
    }

    // Tambahan untuk ubah status message
    public void setStatusMessage(String message, Color color) {
        lblStatus.setText(message);
        lblStatus.setForeground(color);
    }
}

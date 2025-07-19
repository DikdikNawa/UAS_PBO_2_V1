package com.dikdik.uas_pbo2_v4.PEMILIK;

import javax.swing.*;
import java.awt.*;

public class Panel_Pemilik extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    private JButton activeButton = null;

    public JButton btnDashboard, btnKeuangan, btnPegawai, btnAkun, btnLogout;

    public Panel_Pemilik(String username) {
        setLayout(new BorderLayout());

        // === Panel Menu Kiri ===
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(180, getHeight()));
        menuPanel.setBackground(new Color(0, 102, 204)); // biru cerah

        JPanel menuItems = new JPanel(new GridLayout(4, 1, 5, 5));
        menuItems.setBackground(new Color(0, 102, 204));
        menuItems.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        Font menuFont = new Font("Segoe UI", Font.BOLD, 13);

        // === Menu Button ===
        btnDashboard = new JButton("Dashboard");
        btnKeuangan  = new JButton("Data Keuangan");
        btnPegawai   = new JButton("Data Pegawai");
        btnAkun      = new JButton("Buat Akun");

        JButton[] menuButtons = {btnDashboard, btnKeuangan, btnPegawai, btnAkun};
        for (JButton btn : menuButtons) {
            btn.setBackground(new Color(0, 102, 204));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(menuFont);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setPreferredSize(new Dimension(160, 35));
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

            // === Action Listener ===
            btn.addActionListener(e -> {
                for (JButton b : menuButtons) {
                    b.setBackground(new Color(0, 102, 204));
                    b.setForeground(Color.WHITE);
                }
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(0, 102, 204));
                activeButton = btn;

                cardLayout.show(contentPanel, btn.getText());
            });

            menuItems.add(btn);
        }

        // === Tombol Logout ===
        btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setFont(menuFont);
        btnLogout.setPreferredSize(new Dimension(160, 30));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.setBackground(new Color(0, 102, 204));
        logoutPanel.add(btnLogout);

        menuPanel.add(menuItems, BorderLayout.NORTH);
        menuPanel.add(logoutPanel, BorderLayout.SOUTH);

        // === Panel Konten Kanan ===
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        contentPanel.add(panelWithText("Selamat Datang, " + username + "!"), "Dashboard");
        contentPanel.add(panelWithText("Ini halaman Data Keuangan"), "Data Keuangan");
        contentPanel.add(panelWithText("Ini halaman Data Pegawai"), "Data Pegawai");
        contentPanel.add(panelWithText("Ini halaman Buat Akun Pengguna"), "Buat Akun");

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Aktifkan Dashboard saat awal
        btnDashboard.doClick();
    }

    // === Membuat panel konten dengan teks rata tengah ===
    private JPanel panelWithText(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        panel.add(label, BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        return panel;
    }
}

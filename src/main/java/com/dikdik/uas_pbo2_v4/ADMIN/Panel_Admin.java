package com.dikdik.uas_pbo2_v4.ADMIN;

import javax.swing.*;
import java.awt.*;

public class Panel_Admin extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    private JButton activeButton = null;

    public JButton btnTambahPesanan, btnPesanan, btnPelanggan, btnLogout;

    public Panel_Admin(String username) {
        setLayout(new BorderLayout());

        // Panel Menu Kiri
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(180, getHeight()));
        menuPanel.setBackground(new Color(0, 102, 204));

        JPanel menuItems = new JPanel(new GridLayout(3, 1, 5, 5));
        menuItems.setBackground(new Color(0, 102, 204));
        menuItems.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        Font menuFont = new Font("Segoe UI", Font.BOLD, 13);

        btnTambahPesanan = new JButton("Tambah Pesanan");
        btnPesanan  = new JButton("Data Pesanan");
        btnPelanggan   = new JButton("Data Pelanggan");

        JButton[] menuButtons = {btnTambahPesanan, btnPesanan, btnPelanggan};
        for (JButton btn : menuButtons) {
            btn.setBackground(new Color(0, 102, 204));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(menuFont);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setPreferredSize(new Dimension(160, 35));
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
            menuItems.add(btn);

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
        }

        // Tombol Logout
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

        // Konten
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        contentPanel.add(new Logic_TambahPesanan().getPanel(), "Tambah Pesanan");
        contentPanel.add(new Logic_DataPesanan().getPanel(), "Data Pesanan");
        contentPanel.add(new Logic_DataPelanggan().getPanel(), "Data Pelanggan");

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        btnTambahPesanan.doClick();
    }

    private JPanel panelWithText(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        panel.add(label, BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        return panel;
    }
}

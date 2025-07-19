package com.dikdik.uas_pbo2_v4.PRODUKSI;

import javax.swing.*;
import java.awt.*;

public class Panel_Produksi extends JPanel {
    private CardLayout cardLayout;
    private JPanel contentPanel;

    private JButton activeButton = null;

    public JButton btnDashboard, btnStatusPesanan, btnLogout;

    public Panel_Produksi(String username) {
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(180, getHeight()));
        menuPanel.setBackground(new Color(0, 102, 204));

        JPanel menuItems = new JPanel(new GridLayout(2, 1, 5, 5));
        menuItems.setBackground(new Color(0, 102, 204));
        menuItems.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        Font menuFont = new Font("Segoe UI", Font.BOLD, 13);

        btnDashboard = new JButton("Daftar Pesanan Masuk");
        btnStatusPesanan = new JButton("Proses Produksi");

        JButton[] menuButtons = {btnDashboard, btnStatusPesanan};
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

        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        contentPanel.add(new Logic_DaftarPesananMasuk().getPanel(), "Daftar Pesanan Masuk");
        contentPanel.add(new Logic_ProsesProduksi().getPanel(), "Proses Produksi");

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        btnDashboard.doClick();
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

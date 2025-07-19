package com.dikdik.uas_pbo2_v4.ADMIN;

import com.dikdik.uas_pbo2_v4.Logic_Login;
import javax.swing.*;

public class Logic_Admin {
    public Logic_Admin(String username) {
        JFrame frame = new JFrame("Dashboard Admin - SIKODI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        Panel_Admin panel = new Panel_Admin(username);
        frame.setContentPane(panel);
        frame.setVisible(true);

        panel.btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                new Logic_Login();
            }
        });
    }
}

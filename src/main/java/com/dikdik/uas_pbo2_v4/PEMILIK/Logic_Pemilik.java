package com.dikdik.uas_pbo2_v4.PEMILIK;

import com.dikdik.uas_pbo2_v4.Logic_Login;
import javax.swing.*;

public class Logic_Pemilik extends JFrame {
    public Logic_Pemilik(String username) {
        setTitle("Dashboard Pemilik - SIKODI");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Panel_Pemilik panel = new Panel_Pemilik(username);
        setContentPane(panel);
        setVisible(true);

        // Contoh logout
        panel.btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Logic_Login();
            }
        });
    }
}

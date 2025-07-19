package com.dikdik.uas_pbo2_v4;

import com.dikdik.uas_pbo2_v4.ADMIN.Logic_Admin;
import com.dikdik.uas_pbo2_v4.PEMILIK.Logic_Pemilik;
import com.dikdik.uas_pbo2_v4.PRODUKSI.Logic_Produksi;
import com.dikdik.uas_pbo2_v4.GUDANG.Logic_Gudang;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Logic_Login {
    private JFrame frame;
    private Panel_Login panel;

    public Logic_Login() {
        frame = new JFrame("Login - SIKODI");
        panel = new Panel_Login();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setContentPane(panel);
        frame.setVisible(true);

        // Event tombol login
        panel.btnLogin.addActionListener(e -> cekLoginAsync());
    }

    // Versi asynchronous menggunakan SwingWorker
    private void cekLoginAsync() {
        String username = panel.tfUsername.getText().trim();
        String password = new String(panel.pfPassword.getPassword()).trim();
        String role = panel.getSelectedRole();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            panel.setStatusMessage("Lengkapi semua field!", Color.RED);
            return;
        }

        panel.setStatusMessage("Loading...", Color.GRAY);
        panel.btnLogin.setEnabled(false);

        new SwingWorker<Boolean, Void>() {
            boolean loginSukses = false;

            @Override
            protected Boolean doInBackground() {
                try (Connection conn = DBConnection.connect();
                     PreparedStatement stmt = conn.prepareStatement(
                             "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?")) {

                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.setString(3, role);

                    ResultSet rs = stmt.executeQuery();
                    loginSukses = rs.next();
                    Thread.sleep(1500); // Simulasi loading
                } catch (SQLException | InterruptedException ex) {
                    SwingUtilities.invokeLater(() ->
                            panel.setStatusMessage("Error: " + ex.getMessage(), Color.RED)
                    );
                    return false;
                }
                return true;
            }

            @Override
            protected void done() {
                if (loginSukses) {
                    panel.setStatusMessage("Selamat datang, " + username + "!", new Color(0, 130, 0));
                    Timer timer = new Timer(100, e -> {
                        frame.dispose();
                        switch (role) {
                            case "Admin":
                                new Logic_Admin(username);
                                break;
                            case "Pemilik":
                                new Logic_Pemilik(username);
                                break;
                            case "Produksi":
                                new Logic_Produksi(username);
                                break;
                            case "Gudang":
                                new Logic_Gudang(username);
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Role tidak dikenal.");
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    panel.setStatusMessage("Username / password / role salah!", Color.RED);
                    panel.btnLogin.setEnabled(true);
                }
            }
        }.execute();
    }
}

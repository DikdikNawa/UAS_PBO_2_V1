package com.dikdik.uas_pbo2_v4.PRODUKSI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Panel_DaftarPesananMasuk extends JPanel {
    public JTable table;
    public JButton btnTerima, btnRefresh;

    public Panel_DaftarPesananMasuk() {
        setLayout(new BorderLayout());
        String[] cols = {"ID Pesanan","Nama","Jenis","Jumlah","Deskripsi"};
        DefaultTableModel model = new DefaultTableModel(cols,0){@Override public boolean isCellEditable(int r,int c){return false;}};
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnTerima = new JButton("Terima Pesanan");
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.RIGHT)); bp.add(btnTerima);
        add(bp, BorderLayout.SOUTH);
    }
}

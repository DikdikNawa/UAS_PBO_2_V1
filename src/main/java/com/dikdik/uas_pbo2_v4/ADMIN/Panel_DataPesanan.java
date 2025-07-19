package com.dikdik.uas_pbo2_v4.ADMIN;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class Panel_DataPesanan extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    public JButton btnRefresh;

    public Panel_DataPesanan() {
        setLayout(new BorderLayout());

        String[] kolom = {
            "ID", "Nama Pelanggan", "No Telepon", "Jenis",
            "pcs", "DP", "Status", "Total Harga"
        };

        model = new DefaultTableModel(kolom, 0) {
            // Supaya sel tidak bisa di-edit
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28); // <-- INI UNTUK MENGATUR JARAK BARIS
        JScrollPane scrollPane = new JScrollPane(table);


        // === Atur lebar kolom khusus ===
        // Kolom ID Pesanan
        TableColumn colId = table.getColumnModel().getColumn(0);
        colId.setPreferredWidth(30);
        colId.setMaxWidth(60);
        colId.setMinWidth(30);
        
        TableColumn colNamaPelanggan = table.getColumnModel().getColumn(1);
        colNamaPelanggan.setPreferredWidth(200);
        colNamaPelanggan.setMaxWidth(200);
        colNamaPelanggan.setMinWidth(30);
        // Kolom Jumlah
        TableColumn colJumlah = table.getColumnModel().getColumn(4);
        colJumlah.setPreferredWidth(50);
        colJumlah.setMaxWidth(80);
        colJumlah.setMinWidth(40);
        
        TableColumn colDP = table.getColumnModel().getColumn(5);
        TableColumn colTotal = table.getColumnModel().getColumn(7);

        // === Formatter Rupiah ===
        DefaultTableCellRenderer rupiahRenderer = new DefaultTableCellRenderer() {
            NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

            public void setValue(Object value) {
                if (value instanceof Number) {
                    setText(rupiah.format(value));
                } else if (value != null) {
                    try {
                        setText(rupiah.format(Integer.parseInt(value.toString())));
                    } catch (NumberFormatException e) {
                        setText("Rp. 0");
                    }
                } else {
                    setText("Rp. 0");
                }
            }
        };

        rupiahRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        colDP.setCellRenderer(rupiahRenderer);
        colTotal.setCellRenderer(rupiahRenderer);

        // === Refresh Button ===
        btnRefresh = new JButton("Refresh");
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(btnRefresh);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setData(Object[][] data) {
        model.setRowCount(0); // hapus semua data lama
        for (Object[] row : data) {
            model.addRow(row);
        }
    }
}

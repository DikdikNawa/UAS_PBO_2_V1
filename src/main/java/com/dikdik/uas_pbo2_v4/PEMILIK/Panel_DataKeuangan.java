package com.dikdik.uas_pbo2_v4.PEMILIK;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Panel_DataKeuangan extends JPanel {
    public JTable tablePemasukan, tablePengeluaran, tableDiproses;
    public JLabel lblTotalPemasukan, lblTotalPengeluaran, lblTotalDiproses, lblTotalKeseluruhan;
    public JButton btnRefresh;

    public Panel_DataKeuangan() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Data Keuangan", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Tombol refresh
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRefresh = new JButton("Refresh");
        btnPanel.add(btnRefresh);
        add(btnPanel, BorderLayout.SOUTH);

        // Panel kiri (Pemasukan + Diproses)
        JPanel kiriPanel = new JPanel();
        kiriPanel.setLayout(new BoxLayout(kiriPanel, BoxLayout.Y_AXIS));

        // Tabel Pemasukan
        JPanel pemasukanPanel = new JPanel(new BorderLayout());
        tablePemasukan = new JTable(new DefaultTableModel(new Object[]{"ID", "Pelanggan", "Total Harga", "Uang Muka", "Total"}, 0));
        pemasukanPanel.add(new JLabel("Pemasukan (Selesai)", SwingConstants.CENTER), BorderLayout.NORTH);
        pemasukanPanel.add(new JScrollPane(tablePemasukan), BorderLayout.CENTER);
        lblTotalPemasukan = new JLabel("Total Pemasukan: Rp 0", SwingConstants.CENTER);
        pemasukanPanel.add(lblTotalPemasukan, BorderLayout.SOUTH);

        // Tabel Diproses
        JPanel diprosesPanel = new JPanel(new BorderLayout());
        tableDiproses = new JTable(new DefaultTableModel(new Object[]{"ID", "Pelanggan", "Jenis", "Uang Muka", "Status"}, 0));
        diprosesPanel.add(new JLabel("Diproses (Belum Selesai)", SwingConstants.CENTER), BorderLayout.NORTH);
        diprosesPanel.add(new JScrollPane(tableDiproses), BorderLayout.CENTER);
        lblTotalDiproses = new JLabel("Total Uang Muka Diproses: Rp 0", SwingConstants.CENTER);
        diprosesPanel.add(lblTotalDiproses, BorderLayout.SOUTH);

        kiriPanel.add(pemasukanPanel);
        kiriPanel.add(Box.createVerticalStrut(10));
        kiriPanel.add(diprosesPanel);

        // Panel kanan (Pengeluaran)
        JPanel kananPanel = new JPanel(new BorderLayout());
        tablePengeluaran = new JTable(new DefaultTableModel(new Object[]{"ID", "Nama Bahan", "Jumlah", "Subtotal"}, 0));
        kananPanel.add(new JLabel("Pengeluaran", SwingConstants.CENTER), BorderLayout.NORTH);
        kananPanel.add(new JScrollPane(tablePengeluaran), BorderLayout.CENTER);
        lblTotalPengeluaran = new JLabel("Total Pengeluaran: Rp 0", SwingConstants.CENTER);
        kananPanel.add(lblTotalPengeluaran, BorderLayout.SOUTH);

        // Gabungkan panel kiri dan kanan
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        mainPanel.add(kiriPanel);
        mainPanel.add(kananPanel);
        add(mainPanel, BorderLayout.CENTER);

        // Label Keuntungan / Rugi Total
        lblTotalKeseluruhan = new JLabel("Keuntungan: Rp 0", SwingConstants.CENTER);
        lblTotalKeseluruhan.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(lblTotalKeseluruhan, BorderLayout.PAGE_END);
    }
}

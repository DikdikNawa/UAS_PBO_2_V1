package com.dikdik.uas_pbo2_v4.PRODUKSI;

import com.dikdik.uas_pbo2_v4.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Logic_ProsesProduksi {
    private Panel_ProsesProduksi panel;
    private final String[] bahan={"Kain","Benang","Resleting","Kulit","Kancing"};

    public Logic_ProsesProduksi(){
        panel=new Panel_ProsesProduksi();
        loadData();
        panel.btnRefresh.addActionListener(e -> loadData());
        panel.btnSimpan.addActionListener(e->simpan());
    }

    public JPanel getPanel(){ return panel; }

    private void loadData(){
        DefaultTableModel m=(DefaultTableModel)panel.table.getModel();
        m.setRowCount(0);
        String sql="SELECT pr.id_produksi, p.nama_pelanggan, p.jenis_pesanan, p.jumlah, pr.status, pr.total_biaya_produksi "
                  +"FROM produksi pr JOIN pesanan p ON pr.id_pesanan=p.id_pesanan "
                  +"WHERE pr.status IN ('Diproses', 'Selesai')";
        try(Connection conn=DBConnection.connect();
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                m.addRow(new Object[]{
                  rs.getInt(1), rs.getString(2), rs.getString(3),
                  rs.getInt(4), rs.getString(5), rs.getInt(6)
                });
            }
        }catch(Exception ex){ ex.printStackTrace(); }
    }

   private void simpan(){
    int row=panel.table.getSelectedRow();
    if(row < 0){
        JOptionPane.showMessageDialog(null,"Pilih produksi."); return;
    }

    int idProd=(int)panel.table.getValueAt(row,0);
    JTextField[] tf={panel.tfKain, panel.tfBenang, panel.tfResleting, panel.tfKulit, panel.tfKancing};

    try(Connection conn=DBConnection.connect()){
        conn.setAutoCommit(false);
        int totalBahan = 0;

        for(int i=0; i<bahan.length; i++){
            int jml=Integer.parseInt(tf[i].getText().trim());
            if(jml > 0){
                // Validasi stok & hitung total bahan
                try(PreparedStatement ps=conn.prepareStatement("SELECT harga_per_pcs, jumlah_tersedia FROM bahan_baku WHERE nama_bahan=?")){
                    ps.setString(1, bahan[i]);
                    try(ResultSet rs=ps.executeQuery()){
                        if(!rs.next()) throw new SQLException("Bahan tidak ditemukan: "+bahan[i]);
                        int harga = rs.getInt(1);
                        int stok = rs.getInt(2);
                        if(jml > stok) throw new SQLException("Stok "+bahan[i]+" tidak cukup");
                        totalBahan += harga * jml;
                    }
                }

                // Simpan ke produksi_stok
                try(PreparedStatement ins=conn.prepareStatement(
                    "INSERT INTO produksi_stok(id_produksi, nama_bahan, jumlah_digunakan, subtotal) VALUES(?,?,?,?)")){
                    ins.setInt(1, idProd);
                    ins.setString(2, bahan[i]);
                    ins.setInt(3, jml);
                    ins.setInt(4, jml * getHarga(conn, bahan[i]));
                    ins.executeUpdate();
                }

                // Kurangi stok bahan
                try(PreparedStatement upd=conn.prepareStatement(
                    "UPDATE bahan_baku SET jumlah_tersedia = jumlah_tersedia - ? WHERE nama_bahan = ?")){
                    upd.setInt(1, jml);
                    upd.setString(2, bahan[i]);
                    upd.executeUpdate();
                }
            }
        }

        // Ambil id_pesanan dari produksi
        int idPesanan = getIdPesananDariProduksi(conn, idProd);

        // Ambil uang muka dari pesanan
        int uangMuka = 0;
        try(PreparedStatement ps = conn.prepareStatement("SELECT uang_muka FROM pesanan WHERE id_pesanan = ?")){
            ps.setInt(1, idPesanan);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) uangMuka = rs.getInt("uang_muka");
            }
        }

        // Hitung biaya jasa 30% dari total bahan
        double jasa = 0.3 * totalBahan;

        // Hitung total akhir: (biaya bahan - uang muka) + jasa
        double totalFinal = (totalBahan - uangMuka) + jasa;
        if (totalFinal < 0) totalFinal = 0; // Hindari minus

        // Update produksi
        try(PreparedStatement up2=conn.prepareStatement(
            "UPDATE produksi SET total_biaya_produksi = ?, status = 'Selesai' WHERE id_produksi = ?")){
            up2.setInt(1, totalBahan);
            up2.setInt(2, idProd);
            up2.executeUpdate();
        }

        // Update pesanan
        try(PreparedStatement psPesanan = conn.prepareStatement(
            "UPDATE pesanan SET status = 'Selesai', total_harga = ? WHERE id_pesanan = ?")){
            psPesanan.setInt(1, (int)Math.round(totalFinal));
            psPesanan.setInt(2, idPesanan);
            psPesanan.executeUpdate();
        }

        conn.commit();

        JOptionPane.showMessageDialog(null,
            "Produksi selesai.\n"
            + "Total bahan: Rp " + String.format("%,d", totalBahan).replace(',', '.') + "\n"
            + "Uang muka: Rp " + String.format("%,d", uangMuka).replace(',', '.') + "\n"
            + "Biaya jasa (30%): Rp " + String.format("%,d", (int)jasa).replace(',', '.') + "\n"
            + "Tagihan akhir: Rp " + String.format("%,d", (int)totalFinal).replace(',', '.')
        );

        loadData();
    }catch(Exception ex){
        ex.printStackTrace();
    }
}


    private int getHarga(Connection conn, String bahan) throws SQLException {
        try(PreparedStatement ps=conn.prepareStatement("SELECT harga_per_pcs FROM bahan_baku WHERE nama_bahan=?")){
            ps.setString(1, bahan);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    private int getIdPesananDariProduksi(Connection conn, int idProduksi) throws SQLException {
        String sql = "SELECT id_pesanan FROM produksi WHERE id_produksi = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idProduksi);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return rs.getInt("id_pesanan");
            }
        }
        throw new SQLException("ID Pesanan tidak ditemukan untuk ID Produksi: " + idProduksi);
    }
}

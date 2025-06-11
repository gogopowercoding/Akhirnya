package model;

import java.sql.*;

public class PajakDAO {
    
    public void save(PajakKendaraan pajak) {
        String query = "INSERT INTO pajak (kendaraan_id, jumlah_pajak, denda, total_bayar, jatuh_tempo, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = KoneksidB.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pajak.getKendaraanId());
            stmt.setDouble(2, pajak.getJumlahPajak());
            stmt.setDouble(3, pajak.getDenda());
            stmt.setDouble(4, pajak.getTotalBayar());
            stmt.setDate(5, new java.sql.Date(pajak.getJatuhTempo().getTime()));
            stmt.setString(6, pajak.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
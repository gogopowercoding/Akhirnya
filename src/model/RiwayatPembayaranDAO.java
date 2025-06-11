package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RiwayatPembayaranDAO {
    private Connection connection;

    public RiwayatPembayaranDAO(Connection connection) {
        this.connection = connection;
    }
    public List<RiwayatPembayaran> getRiwayatByUserId(int userId) throws SQLException {
        List<RiwayatPembayaran> riwayatList = new ArrayList<>();
        String query = "SELECT p.id, p.pajak_id, p.tanggal_bayar, p.jumlah_bayar, p.metode, "
                    + "k.nomor_polisi, pj.jumlah_pajak, pj.denda, pj.total_bayar "
                    + "FROM pembayaran p "
                    + "JOIN pajak pj ON p.pajak_id = pj.id "
                    + "JOIN kendaraan k ON pj.kendaraan_id = k.id "
                    + "WHERE k.user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RiwayatPembayaran riwayat = new RiwayatPembayaran(
                    rs.getInt("id"),
                    rs.getInt("pajak_id"),
                    rs.getTimestamp("tanggal_bayar"),
                    rs.getDouble("jumlah_bayar"),
                    rs.getString("metode"),
                    rs.getString("nomor_polisi"),
                    rs.getDouble("jumlah_pajak"),
                    rs.getDouble("denda"),
                    rs.getDouble("total_bayar")
                );
                riwayatList.add(riwayat);
            }
        }
        return riwayatList;
    }
}
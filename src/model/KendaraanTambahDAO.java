package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KendaraanTambahDAO {
    
        public int getUserIdByNik(String nik) {
        int userId = -1;
        String sql = "SELECT id FROM users WHERE nik = ?";

        try (Connection conn = KoneksidB.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nik);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
       }

        public List<String> getAllNiks() {
        List<String> niks = new ArrayList<>();
        String query = "SELECT nik FROM users";

        try (Connection conn = KoneksidB.getKoneksi();
             java.sql.Statement stmt = conn.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                niks.add(rs.getString("nik"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return niks;
    }

    public boolean insert(KendaraanTambah kendaraan) throws SQLException {
        Connection conn = KoneksidB.getKoneksi();
        String sql = "INSERT INTO kendaraan (user_id, nomor_polisi, merk, jenis, tahun, harga_kendaraan, cc) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, kendaraan.getUserId());
        pstmt.setString(2, kendaraan.getNomorPolisi());
        pstmt.setString(3, kendaraan.getMerk());
        pstmt.setString(4, kendaraan.getJenis());
        pstmt.setInt(5, kendaraan.getTahun());
        pstmt.setDouble(6, kendaraan.getHarga());
        pstmt.setString(7, kendaraan.getCc());

        int result = pstmt.executeUpdate();
        pstmt.close();
        conn.close();
        return result > 0;
    }
    
    public KendaraanTambah findById(int id) throws SQLException {
        KendaraanTambah kendaraan = null;
        String sql = "SELECT * FROM kendaraan WHERE id = ?";
        try (Connection conn = KoneksidB.getKoneksi();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                kendaraan = new KendaraanTambah(
                    rs.getInt("user_id"),
                    rs.getString("nomor_polisi"),
                    rs.getString("merk"),
                    rs.getString("jenis"),
                    rs.getInt("tahun"),
                    rs.getDouble("harga_kendaraan"),
                    rs.getString("cc")
                );
                kendaraan.setId(rs.getInt("id")); // Set ID secara manual
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kendaraan;
    }
}

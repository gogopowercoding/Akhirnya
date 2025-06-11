package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class BayarPajakDAO {
    private Connection connection;

    public BayarPajakDAO() throws SQLException {
        this.connection = KoneksidB.getKoneksi();
        if (this.connection == null) {
            throw new SQLException("Koneksi ke database gagal.");
        }
    }

    public void save(BayarPajak pembayaran) throws SQLException {
        String insertPembayaran = "INSERT INTO pembayaran (pajak_id, tanggal_bayar, jumlah_bayar) VALUES (?, ?, ?)";
        String updatePajakStatus = "UPDATE pajak SET status = 'Sudah_Lunas' WHERE id = ?"; 

        PreparedStatement stmtPembayaran = null;
        PreparedStatement stmtPajak = null;

        try {
            connection.setAutoCommit(false);

            stmtPembayaran = connection.prepareStatement(insertPembayaran);
            stmtPembayaran.setInt(1, pembayaran.getPajakId());
            stmtPembayaran.setTimestamp(2, new java.sql.Timestamp(pembayaran.getTanggalBayar().getTime()));
            stmtPembayaran.setDouble(3, pembayaran.getJumlahBayar());
            stmtPembayaran.executeUpdate();

            stmtPajak = connection.prepareStatement(updatePajakStatus);
            stmtPajak.setInt(1, pembayaran.getPajakId());
            stmtPajak.executeUpdate();

            connection.commit();
            JOptionPane.showMessageDialog(null, "Pembayaran berhasil disimpan!");
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menyimpan pembayaran: " + e.getMessage());
            throw e;
        } finally {
            if (stmtPembayaran != null) stmtPembayaran.close();
            if (stmtPajak != null) stmtPajak.close();
        }
    }

    public ResultSet getKendaraanPajakData(int userId) throws SQLException {
        System.out.println("Mengambil data untuk userId: " + userId);
        String query = "SELECT p.id, k.nomor_polisi, k.merk, k.jenis, k.tahun, k.harga_kendaraan, k.cc, p.total_bayar, p.jatuh_tempo, p.status " +
                      "FROM pajak p " +
                      "JOIN kendaraan k ON p.kendaraan_id = k.id " +
                      "WHERE k.user_id = ? AND p.status = 'Belum_Lunas'";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, userId);
        return stmt.executeQuery();
    }

    public double getTotalBayarByPajakId(int pajakId) throws SQLException {
        String query = "SELECT total_bayar FROM pajak WHERE pajak_id = ?"; // Diubah dari 'id' menjadi 'pajak_id'
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, pajakId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_bayar");
            } else {
                throw new SQLException("Pajak dengan ID " + pajakId + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }

    public List<Object[]> getPajakByKendaraanId(int kendaraanId) throws SQLException {
        String query = "SELECT id, status FROM pajak WHERE kendaraan_id = ? AND status = 'Belum_Lunas'";
        List<Object[]> pajakList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, kendaraanId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] pajakData = new Object[2];
                pajakData[0] = rs.getInt("id");
                pajakData[1] = rs.getString("status");
                pajakList.add(pajakData);
            }
            return pajakList;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat data pajak: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }

    public boolean deletePajakByKendaraanId(int kendaraanId, int userId) throws SQLException {
        if (userId != 1) {
            JOptionPane.showMessageDialog(null, "Hanya admin yang dapat menghapus data pajak.", "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String query = "DELETE FROM pajak WHERE kendaraan_id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, kendaraanId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menghapus pajak: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
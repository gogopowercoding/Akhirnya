package controller;

import model.KendaraanModel;
import model.BayarPajakDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class KendaraanController {
    private KendaraanModel model;
    private BayarPajakDAO pajakDAO;

    public KendaraanController() {
        try {
            this.model = new KendaraanModel();
            this.pajakDAO = new BayarPajakDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal inisialisasi KendaraanController: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Object[]> getAllKendaraan(int userId, String statusFilter) throws SQLException {
        try {
            List<Object[]> kendaraanList;
            if (userId == 1) { 
                kendaraanList = model.getKendaraanByStatus(statusFilter);
            } else {
                kendaraanList = model.getKendaraanByUser(userId); 
            }
            if (kendaraanList.isEmpty()) {
                System.out.println("No kendaraan data found for userId: " + userId + " with status: " + statusFilter + " at " + new java.util.Date());
            } else {
                System.out.println("Retrieved " + kendaraanList.size() + " kendaraan records for userId: " + userId + " with status: " + statusFilter);
            }
            return kendaraanList;
        } catch (SQLException e) {
            System.out.println("SQL Error in getAllKendaraan for userId " + userId + " with status " + statusFilter + ": " + e.getMessage());
            throw e;
        }
    }

    public Object[] getKendaraanDetail(int kendaraanId, int userId) throws SQLException {
        try {
            Object[] kendaraan;
            if (userId == 1) { 
                kendaraan = model.getKendaraanById(kendaraanId);
            } else {
                kendaraan = model.getKendaraanByIdAndUser(kendaraanId, userId);
            }
            if (kendaraan == null) {
                System.out.println("No kendaraan data found for kendaraanId: " + kendaraanId + ", userId: " + userId);
            } else {
                System.out.println("Retrieved kendaraan detail for kendaraanId " + kendaraanId + ", userId: " + userId);
            }
            return kendaraan;
        } catch (SQLException e) {
            System.out.println("SQL Error in getKendaraanDetail for kendaraanId " + kendaraanId + ", userId " + userId + ": " + e.getMessage());
            throw e;
        }
    }

    public boolean update(int kendaraanId, int userId, String nomorPolisi, String merk, String jenis, int tahun, double harga, String cc) throws SQLException {
        try {
            boolean result;
            if (userId == 1) { 
                result = model.updateKendaraan(kendaraanId, nomorPolisi, merk, jenis, tahun, harga, cc);
            } else {
                result = model.updateKendaraan(kendaraanId, userId, nomorPolisi, merk, jenis, tahun, harga, cc);
            }
            System.out.println("Update result for kendaraanId " + kendaraanId + ": " + result);
            return result;
        } catch (SQLException e) {
            System.out.println("SQL Error in update for kendaraanId " + kendaraanId + ": " + e.getMessage());
            throw e;
        }
    }

    public boolean delete(int kendaraanId, int userId) throws SQLException {
        try {
            List<Object[]> pajakList = pajakDAO.getPajakByKendaraanId(kendaraanId);
            if (!pajakList.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(null,
                    "Kendaraan ini memiliki " + pajakList.size() + " entri pajak yang belum dibayar. Hapus juga entri pajak?",
                    "Konfirmasi Penghapusan",
                    JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return false;
                }
            }
            
            boolean pajakDeleted = pajakDAO.deletePajakByKendaraanId(kendaraanId, userId);
            if (!pajakDeleted && userId != 1) {
                System.out.println("No pajak entries to delete for kendaraanId: " + kendaraanId + ", userId: " + userId);
            }

            boolean result;
            if (userId == 1) {
                result = model.deleteKendaraanAdmin(kendaraanId);
            } else {
                result = model.deleteKendaraan(kendaraanId, userId);
            }
            System.out.println("Delete result for kendaraanId " + kendaraanId + ": " + result);
            return result;
        } catch (SQLException e) {
            System.out.println("SQL Error in delete for kendaraanId " + kendaraanId + ": " + e.getMessage());
            throw e;
        }
    }

    public boolean deletePajakByKendaraanId(int kendaraanId, int userId) throws SQLException {
        try {
            boolean result = pajakDAO.deletePajakByKendaraanId(kendaraanId, userId);
            System.out.println("Delete pajak result for kendaraanId " + kendaraanId + ": " + result);
            return result;
        } catch (SQLException e) {
            System.out.println("SQL Error in deletePajakByKendaraanId for kendaraanId " + kendaraanId + ": " + e.getMessage());
            throw e;
        }
    }
}               

package controller;

import model.BayarPajak;
import model.BayarPajakDAO;
import javax.swing.JOptionPane;
import java.sql.SQLException;

public class BayarPajakController {
    private BayarPajakDAO pembayaranDAO;

   
    public BayarPajakController() {
        try {
            this.pembayaranDAO = new BayarPajakDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal inisialisasi BayarPajakDAO: " + e.getMessage());
        }
    }

    public void prosesPembayaran(int pajakId, double jumlahBayar) throws IllegalArgumentException, SQLException {
        // Validate input
        if (pajakId <= 0) {
            throw new IllegalArgumentException("ID Pajak tidak valid.");
        }
        if (jumlahBayar <= 0) {
            throw new IllegalArgumentException("Jumlah bayar harus lebih dari 0.");
        }

        // Check if jumlahBayar is sufficient
        double totalBayar = pembayaranDAO.getTotalBayarByPajakId(pajakId);
        if (jumlahBayar < totalBayar) {
            String message = String.format("Jumlah bayar (%.2f) kurang dari total bayar yang diperlukan (%.2f).", jumlahBayar, totalBayar);
            JOptionPane.showMessageDialog(null, message, "Pembayaran Tidak Valid", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException(message);
        }

        // Create BayarPajak object and save
        BayarPajak pembayaran = new BayarPajak(pajakId, jumlahBayar);
        try {
            pembayaranDAO.save(pembayaran);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memproses pembayaran: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }
}
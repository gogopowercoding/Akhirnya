package controller;

import model.KendaraanTambah;
import model.KendaraanTambahDAO;
import model.PajakKendaraan;

import java.sql.SQLException;

public class PajakController {
    private KendaraanTambahDAO kendaraanDAO;

    public PajakController() {
        this.kendaraanDAO = new KendaraanTambahDAO();
    }

    public PajakKendaraan hitungPajak(int kendaraanId, double denda) throws SQLException {
        KendaraanTambah kendaraan = kendaraanDAO.findById((int) kendaraanId);
        if (kendaraan == null) {
            throw new SQLException("Kendaraan dengan ID " + kendaraanId + " tidak ditemukan.");
        }
        return new PajakKendaraan(kendaraanId, denda);
    }
}
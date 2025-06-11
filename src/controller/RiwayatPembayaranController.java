package controller;

import model.RiwayatPembayaranDAO;
import model.RiwayatPembayaran;
import view.RiwayatPembayaranView;
import java.sql.Connection;
import java.util.List;

public class RiwayatPembayaranController {
    private RiwayatPembayaranDAO riwayatDAO;
    private RiwayatPembayaranView view;

    public RiwayatPembayaranController(Connection connection, RiwayatPembayaranView view) {
        this.riwayatDAO = new RiwayatPembayaranDAO(connection);
        this.view = view;
    }

    public void tampilkanRiwayatPembayaran(int userId) {
        try {
            List<RiwayatPembayaran> riwayatList = riwayatDAO.getRiwayatByUserId(userId);
            view.displayRiwayatPembayaran(riwayatList);
        } catch (Exception e) {
            view.displayError("Gagal memuat riwayat pembayaran: " + e.getMessage());
        }
    }
}
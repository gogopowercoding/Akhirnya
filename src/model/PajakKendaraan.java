package model;

import java.sql.SQLException;
import java.util.Date;

public class PajakKendaraan implements PerhitunganPajak {
    private int id;
    private int kendaraanId;
    private double denda;
    private double jumlahPajak;
    private double totalBayar;
    private Date jatuhTempo;
    private String status;

    public PajakKendaraan() {
    }

    public PajakKendaraan(int kendaraanId, double denda) throws SQLException {
        this.kendaraanId = kendaraanId;
        this.denda = denda;
        KendaraanTambahDAO kendaraanDAO = new KendaraanTambahDAO();
        KendaraanTambah kendaraan = kendaraanDAO.findById((int) kendaraanId); // Cast ke int
        if (kendaraan != null) {
            this.jumlahPajak = hitungTotal(kendaraan.getHarga(), kendaraan.getCc(), denda);
            this.totalBayar = jumlahPajak + denda;
        } else {
            this.jumlahPajak = 0;
            this.totalBayar = denda; // Default jika kendaraan tidak ditemukan
        }
        this.jatuhTempo = new Date(); // Set default ke tanggal saat ini
        this.status = "Belum_Lunas";  // Default status
    }

    @Override
    public double hitungPajak() {
        KendaraanTambahDAO kendaraanDAO = new KendaraanTambahDAO();
        try {
            KendaraanTambah kendaraan = kendaraanDAO.findById((int) kendaraanId);
            if (kendaraan != null) {
                return hitungTotal(kendaraan.getHarga(), kendaraan.getCc(), denda);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Atau log error
        }
        return 0; // Default jika kendaraan tidak ditemukan atau ada error
    }

    public static double hitungTotal(double harga, String cc, double denda) {
        double pkb = 0.009 * harga;
        double opsen = 0.66 * pkb;
        double stnk = 100000;
        double plat = 60000;
        double swdkllj = cc.equals("50-250") ? 35000 : 50000;
        return pkb + opsen + stnk + plat + swdkllj + denda;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getKendaraanId() { return kendaraanId; }
    public void setKendaraanId(int kendaraanId) { this.kendaraanId = kendaraanId; }
    public double getDenda() { return denda; }
    public void setDenda(double denda) { this.denda = denda; }
    public double getJumlahPajak() { return jumlahPajak; }
    public void setJumlahPajak(double jumlahPajak) { this.jumlahPajak = jumlahPajak; }
    public double getTotalBayar() { return totalBayar; }
    public void setTotalBayar(double totalBayar) { this.totalBayar = totalBayar; }
    public Date getJatuhTempo() { return jatuhTempo; }
    public void setJatuhTempo(Date jatuhTempo) { this.jatuhTempo = jatuhTempo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
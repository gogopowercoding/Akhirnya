package model;

import java.util.Date;

public class RiwayatPembayaran {
    private int id;
    private int pajakId;
    private Date tanggalBayar;
    private double jumlahBayar;
    private String metode;
    private String nomorPolisi;
    private double jumlahPajak;
    private double denda;
    private double totalBayar;

    // Constructor
    public RiwayatPembayaran(int id, int pajakId, Date tanggalBayar, double jumlahBayar, 
                            String metode, String nomorPolisi, double jumlahPajak, 
                            double denda, double totalBayar) {
        this.id = id;
        this.pajakId = pajakId;
        this.tanggalBayar = tanggalBayar;
        this.jumlahBayar = jumlahBayar;
        this.metode = metode;
        this.nomorPolisi = nomorPolisi;
        this.jumlahPajak = jumlahPajak;
        this.denda = denda;
        this.totalBayar = totalBayar;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPajakId() { return pajakId; }
    public void setPajakId(int pajakId) { this.pajakId = pajakId; }
    public Date getTanggalBayar() { return tanggalBayar; }
    public void setTanggalBayar(Date tanggalBayar) { this.tanggalBayar = tanggalBayar; }
    public double getJumlahBayar() { return jumlahBayar; }
    public void setJumlahBayar(double jumlahBayar) { this.jumlahBayar = jumlahBayar; }
    public String getMetode() { return metode; }
    public void setMetode(String metode) { this.metode = metode; }
    public String getNomorPolisi() { return nomorPolisi; }
    public void setNomorPolisi(String nomorPolisi) { this.nomorPolisi = nomorPolisi; }
    public double getJumlahPajak() { return jumlahPajak; }
    public void setJumlahPajak(double jumlahPajak) { this.jumlahPajak = jumlahPajak; }
    public double getDenda() { return denda; }
    public void setDenda(double denda) { this.denda = denda; }
    public double getTotalBayar() { return totalBayar; }
    public void setTotalBayar(double totalBayar) { this.totalBayar = totalBayar; }
}
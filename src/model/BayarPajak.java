package model;

import java.util.Date;

public class BayarPajak {
    private int id;
    private int pajakId;
    private Date tanggalBayar;
    private double jumlahBayar;

    public BayarPajak() {
    }

    public BayarPajak(int pajakId, double jumlahBayar) {
        this.pajakId = pajakId;
        this.tanggalBayar = new Date(); // Set to current date
        this.jumlahBayar = jumlahBayar;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPajakId() {
        return pajakId;
    }

    public void setPajakId(int pajakId) {
        this.pajakId = pajakId;
    }

    public Date getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(Date tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }
}
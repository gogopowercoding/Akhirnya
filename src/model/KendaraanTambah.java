package model;

public class KendaraanTambah {
    private int id;
    private int userId;
    private String nomorPolisi;
    private String merk;
    private String jenis;
    private int tahun;
    private double harga;
    private String cc;

    public KendaraanTambah(int userId, String nomorPolisi, String merk, String jenis, int tahun, double harga, String cc) {
        this.userId = userId;
        this.nomorPolisi = nomorPolisi;
        this.merk = merk;
        this.jenis = jenis;
        this.tahun = tahun;
        this.harga = harga;
        this.cc = cc;
    }

   // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getNomorPolisi() { return nomorPolisi; }
    public String getMerk() { return merk; }
    public String getJenis() { return jenis; }
    public int getTahun() { return tahun; }
    public double getHarga() { return harga; }
    public String getCc() { return cc; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setNomorPolisi(String nomorPolisi) { this.nomorPolisi = nomorPolisi; }
    public void setMerk(String merk) { this.merk = merk; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    public void setTahun(int tahun) { this.tahun = tahun; }
    public void setHarga(double harga) { this.harga = harga; }
    public void setCc(String cc) { this.cc = cc; }

}

package controller;

import java.util.List;
import javax.swing.JComboBox;
import model.KendaraanTambah;
import model.KendaraanTambahDAO;
import view.TambahKendaraanForm;

public class KendaraanControllerTambah {
    private TambahKendaraanForm form; // 
    private KendaraanTambahDAO kendaraanDAO = new KendaraanTambahDAO();

    public KendaraanControllerTambah(TambahKendaraanForm form) {
        this.form = form;
        this.kendaraanDAO = new KendaraanTambahDAO();
        loadUserNiks(); 
    }

   private void loadUserNiks() {
    List<String> niks = kendaraanDAO.getAllNiks();
    JComboBox<String> combo = form.getComboUserId();
    combo.removeAllItems();
    for (String nik : niks) {
        combo.addItem(nik);
    }
}


    public boolean tambahKendaraan(KendaraanTambah kendaraan) throws Exception {
        return kendaraanDAO.insert(kendaraan);
    }
}

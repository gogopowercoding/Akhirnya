package view;

import controller.KendaraanControllerTambah;
import model.KendaraanTambah;
import model.KendaraanTambahDAO;

import javax.swing.*;
import java.awt.*;

public class TambahKendaraanForm extends JFrame {
    private int userId;
    private JTextField nomorPolisiField, merkField, tahunField, hargaField;
    private JComboBox<String> jenisComboBox, ccComboBox;
    private JComboBox<String> comboUserId; // Ini akan berisi NIK
    private KendaraanControllerTambah controller;

    public TambahKendaraanForm(int userId) {
        this.userId = userId;
        initComponents();
        controller = new KendaraanControllerTambah(this); 
    }

    private void initComponents() {
        setTitle("Tambah Data Kendaraan");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel header = new JLabel("Tambah Data Kendaraan Baru");
        header.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(header, gbc);

        gbc.gridwidth = 1;
        int row = 1;

        // NIK ComboBox (bukan langsung User ID)
        panel.add(new JLabel("NIK (User):"), createGbc(0, row));
        comboUserId = new JComboBox<>();
        panel.add(comboUserId, createGbc(1, row++));

        panel.add(new JLabel("Nomor Polisi:"), createGbc(0, row));
        nomorPolisiField = new JTextField(15);
        panel.add(nomorPolisiField, createGbc(1, row++));

        panel.add(new JLabel("Merk:"), createGbc(0, row));
        merkField = new JTextField(15);
        panel.add(merkField, createGbc(1, row++));

        panel.add(new JLabel("Jenis:"), createGbc(0, row));
        jenisComboBox = new JComboBox<>(new String[]{"Sepeda Motor", "Mobil", "Truk"});
        panel.add(jenisComboBox, createGbc(1, row++));

        panel.add(new JLabel("Tahun:"), createGbc(0, row));
        tahunField = new JTextField(15);
        panel.add(tahunField, createGbc(1, row++));

        panel.add(new JLabel("Harga Kendaraan:"), createGbc(0, row));
        hargaField = new JTextField(15);
        panel.add(hargaField, createGbc(1, row++));

        panel.add(new JLabel("CC:"), createGbc(0, row));
        ccComboBox = new JComboBox<>(new String[]{"50-250", ">250"});
        panel.add(ccComboBox, createGbc(1, row++));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton simpanBtn = new JButton("Simpan");
        JButton kembaliBtn = new JButton("Kembali");

        simpanBtn.addActionListener(e -> simpanKendaraan());
        kembaliBtn.addActionListener(e -> kembaliToDashboard());

        buttonPanel.add(simpanBtn);
        buttonPanel.add(kembaliBtn);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    public JComboBox<String> getComboUserId() {
        return comboUserId;
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private void simpanKendaraan() {
        String nik = (String) getComboUserId().getSelectedItem();
        int userId = new KendaraanTambahDAO().getUserIdByNik(nik);

        try {
            String nomorPol = nomorPolisiField.getText().trim();
            String merk = merkField.getText().trim();
            String jenis = (String) jenisComboBox.getSelectedItem();
            String tahunStr = tahunField.getText().trim();
            String hargaStr = hargaField.getText().trim();

            if (nomorPol.isEmpty() || merk.isEmpty() || jenis == null || tahunStr.isEmpty() || hargaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int tahun = Integer.parseInt(tahunStr);
            double harga = Double.parseDouble(hargaStr);

            if (userId == -1) {
                JOptionPane.showMessageDialog(this, "NIK tidak ditemukan di database.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            KendaraanTambah kendaraan = new KendaraanTambah(
                userId, nomorPol, merk, jenis, tahun, harga,
                ccComboBox.getSelectedItem().toString()
            );
            boolean success = controller.tambahKendaraan(kendaraan);

            if (success) {
                JOptionPane.showMessageDialog(this, "Data kendaraan berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data kendaraan.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun dan Harga harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nomorPolisiField.setText("");
        merkField.setText("");
        tahunField.setText("");
        hargaField.setText("");
        jenisComboBox.setSelectedIndex(0);
        ccComboBox.setSelectedIndex(0);
    }

    private void kembaliToDashboard() {
        new AdminDashboard(userId).setVisible(true);
        this.dispose();
    }
}

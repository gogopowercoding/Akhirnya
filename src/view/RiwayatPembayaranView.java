package view;

import model.RiwayatPembayaran;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class RiwayatPembayaranView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public RiwayatPembayaranView() {
        initComponents();
    }

    private void initComponents() {
    setTitle("Riwayat Pembayaran");
    setSize(900, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel header = new JLabel("Riwayat Pembayaran", SwingConstants.CENTER);
    header.setFont(new Font("Arial", Font.BOLD, 16));
    mainPanel.add(header, BorderLayout.NORTH);

    String[] columnNames = {"ID", "Nomor Polisi", "Tanggal Bayar", "Jumlah Bayar", 
                           "Metode", "Jumlah Pajak", "Denda", "Total Bayar"};
    tableModel = new DefaultTableModel(columnNames, 0);
    table = new JTable(tableModel);
    table.setFillsViewportHeight(true);
    table.setRowHeight(25);
    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

    JScrollPane scrollPane = new JScrollPane(table);
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    JButton backButton = new JButton("Kembali");
    backButton.setFont(new Font("Arial", Font.PLAIN, 12));

    // ✅ Ganti bagian ini:
    backButton.addActionListener(e -> {
        if (userId > 0) {
            new PembayarDashboard(userId).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "User ID tidak valid, tidak bisa kembali ke dashboard.");
        }
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(backButton);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(mainPanel);
}


    public void displayRiwayatPembayaran(List<RiwayatPembayaran> riwayatList) {
        // Clear existing rows
        tableModel.setRowCount(0);

        if (riwayatList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada riwayat pembayaran untuk pengguna ini.");
            return;
        }

        // Populate table
        for (RiwayatPembayaran riwayat : riwayatList) {
            Object[] row = {
                riwayat.getId(),
                riwayat.getNomorPolisi(),
                dateFormat.format(riwayat.getTanggalBayar()),
                riwayat.getJumlahBayar(),
                riwayat.getMetode(),
                riwayat.getJumlahPajak(),
                riwayat.getDenda(),
                riwayat.getTotalBayar()
            };
            tableModel.addRow(row);
        }
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, "Error: " + message);
    }

    // Getter untuk userId (diperlukan untuk kembali ke dashboard)
    private int userId =0; // Inisialisasi default
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
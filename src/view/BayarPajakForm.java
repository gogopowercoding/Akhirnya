package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import controller.BayarPajakController;
import model.BayarPajakDAO;

public class BayarPajakForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton bayarButton;
    private JButton backButton;
    private int selectedPajakId = -1;
    private int userId;
    private BayarPajakDAO pajakDAO;

    public BayarPajakForm(int userId) {
        if (userId <= 0) {
            JOptionPane.showMessageDialog(null, "User ID tidak valid. Kembali ke dashboard.");
            new PembayarDashboard(1).setVisible(true);
            dispose();
            return;
        }

        this.userId = userId;

        try {
            this.pajakDAO = new BayarPajakDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menginisialisasi DAO: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); // Hanya tutup form, jangan exit seluruh app
            return;
        }

        setTitle("Form Pembayaran Pajak - User ID: " + this.userId);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 450);
        setLocationRelativeTo(null);

        initComponents();
        loadData(this.userId);

        setVisible(true);
    }

    private void initComponents() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Pajak ID");
        tableModel.addColumn("Nomor Polisi");
        tableModel.addColumn("Merk");
        tableModel.addColumn("Jenis");
        tableModel.addColumn("Tahun");
        tableModel.addColumn("Harga Kendaraan");
        tableModel.addColumn("CC");
        tableModel.addColumn("Total Bayar");
        tableModel.addColumn("Jatuh Tempo");
        tableModel.addColumn("Status");

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    selectedPajakId = (int) tableModel.getValueAt(selectedRow, 0);
                    bayarButton.setEnabled(true);
                } else {
                    bayarButton.setEnabled(false);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        bayarButton = new JButton("Bayar");
        bayarButton.setEnabled(false);
        bayarButton.addActionListener(e -> prosesPembayaran());

        backButton = new JButton("Kembali ke Dashboard");
        backButton.addActionListener(e -> {
            new PembayarDashboard(userId).setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(bayarButton);
        bottomPanel.add(backButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void prosesPembayaran() {
        if (selectedPajakId == -1 || selectedPajakId == 0) {
            JOptionPane.showMessageDialog(this, "Silakan pilih baris yang valid.");
            return;
        }

        try {
            String input = JOptionPane.showInputDialog(this, "Masukkan jumlah bayar:", "Input Jumlah Bayar", JOptionPane.QUESTION_MESSAGE);
            if (input == null || input.trim().isEmpty()) return;

            double jumlahBayar = Double.parseDouble(input.trim());

            BayarPajakController controller = new BayarPajakController();
            controller.prosesPembayaran(selectedPajakId, jumlahBayar);

            JOptionPane.showMessageDialog(this, "Pembayaran berhasil diproses!");
            loadData(this.userId); // Refresh data setelah pembayaran
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah bayar yang valid.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadData(int userId) {
        tableModel.setRowCount(0);
        ResultSet rs = null;

        try {
            rs = pajakDAO.getKendaraanPajakData(userId);
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("nomor_polisi"));
                row.add(rs.getString("merk"));
                row.add(rs.getString("jenis"));
                row.add(rs.getInt("tahun"));
                row.add(rs.getDouble("harga_kendaraan"));
                row.add(rs.getString("cc"));
                row.add(rs.getDouble("total_bayar"));
                row.add(rs.getDate("jatuh_tempo"));
                row.add(rs.getString("status"));
                tableModel.addRow(row);
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Tidak ada data kendaraan untuk user ID: " + userId, "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

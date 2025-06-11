package view;

import model.PajakKendaraan;
import controller.PajakController;
import model.PajakDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PajakKendaraanForm extends JFrame {
    private JTextField kendaraanIdField;
    private JTextField dendaField;
    private JLabel jumlahPajakLabel;
    private JLabel totalBayarLabel;
    private JLabel jatuhTempoLabel;
    private JLabel statusLabel;
    private PajakController controller;
    private PajakDAO pajakDAO;

    public PajakKendaraanForm() {
        // Initialize controller and DAO
        controller = new PajakController();
        pajakDAO = new PajakDAO();

        // Set up the frame
        setTitle("Perhitungan Pajak Kendaraan");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ubah ke DISPOSE agar tidak menutup aplikasi sepenuhnya
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        // Custom styling
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background
        setFont(new Font("Arial", Font.PLAIN, 14));

        // Create components
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel kendaraanIdLabel = new JLabel("ID Kendaraan:");
        kendaraanIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        kendaraanIdField = new JTextField(10);
        kendaraanIdField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel dendaLabel = new JLabel("Denda:");
        dendaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        dendaField = new JTextField(10);
        dendaField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton calculateButton = new JButton("Hitung Pajak");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setBackground(new Color(70, 130, 180)); // Steel blue
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);

        // Result panel
        JPanel resultPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Hasil Perhitungan"));
        resultPanel.setBackground(new Color(245, 245, 245)); // Light gray

        jumlahPajakLabel = new JLabel("Jumlah Pajak: -");
        totalBayarLabel = new JLabel("Total Bayar: -");
        jatuhTempoLabel = new JLabel("Jatuh Tempo: -");
        statusLabel = new JLabel("Status: -");

        jumlahPajakLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        totalBayarLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        jatuhTempoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(kendaraanIdLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(kendaraanIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(dendaLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(dendaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(calculateButton, gbc);

        resultPanel.add(jumlahPajakLabel);
        resultPanel.add(totalBayarLabel);
        resultPanel.add(jatuhTempoLabel);
        resultPanel.add(statusLabel);

        add(inputPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);

        // Add action listener to the button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePajak();
            }
        });

        // Display the frame
        setVisible(true);
    }

    private void calculatePajak() {
        try {
            int kendaraanId = Integer.parseInt(kendaraanIdField.getText().trim());
            double denda = Double.parseDouble(dendaField.getText().trim());

            // Call controller to calculate pajak
            PajakKendaraan pajak = controller.hitungPajak(kendaraanId, denda);

            // Update labels with results
            jumlahPajakLabel.setText(String.format("Jumlah Pajak: %.2f", pajak.getJumlahPajak()));
            totalBayarLabel.setText(String.format("Total Bayar: %.2f", pajak.getTotalBayar()));
            jatuhTempoLabel.setText(String.format("Jatuh Tempo: %s", pajak.getJatuhTempo()));
            statusLabel.setText(String.format("Status: %s", pajak.getStatus()));

            // Save to database
            pajakDAO.save(pajak); // Memanggil metode save dari PajakKendaraanDAO
            JOptionPane.showMessageDialog(this, "Data pajak berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid untuk ID Kendaraan dan Denda!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error mengakses database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
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
        controller = new PajakController();
        pajakDAO = new PajakDAO();

        setTitle("Perhitungan Pajak Kendaraan");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data"));
        inputPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel kendaraanIdLabel = new JLabel("ID Kendaraan:");
        kendaraanIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        kendaraanIdField = new JTextField(15);
        kendaraanIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel dendaLabel = new JLabel("Denda:");
        dendaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dendaField = new JTextField(15);
        dendaField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton calculateButton = new JButton("Hitung Pajak");
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        calculateButton.setBackground(new Color(52, 152, 219));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(calculateButton, gbc);

        // Result Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Hasil Perhitungan"));
        resultPanel.setBackground(new Color(250, 250, 250));

        jumlahPajakLabel = new JLabel("Jumlah Pajak: -");
        totalBayarLabel = new JLabel("Total Bayar: -");
        jatuhTempoLabel = new JLabel("Jatuh Tempo: -");
        statusLabel = new JLabel("Status: -");

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        jumlahPajakLabel.setFont(labelFont);
        totalBayarLabel.setFont(labelFont);
        jatuhTempoLabel.setFont(labelFont);
        statusLabel.setFont(labelFont);

        jumlahPajakLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalBayarLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jatuhTempoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        resultPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(jumlahPajakLabel);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(totalBayarLabel);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(jatuhTempoLabel);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(statusLabel);
        resultPanel.add(Box.createVerticalStrut(10));

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);

        // Button action
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePajak();
            }
        });

        setVisible(true);
    }

    private void calculatePajak() {
        try {
            int kendaraanId = Integer.parseInt(kendaraanIdField.getText().trim());
            double denda = Double.parseDouble(dendaField.getText().trim());

            PajakKendaraan pajak = controller.hitungPajak(kendaraanId, denda);

            jumlahPajakLabel.setText(String.format("Jumlah Pajak: %.2f", pajak.getJumlahPajak()));
            totalBayarLabel.setText(String.format("Total Bayar: %.2f", pajak.getTotalBayar()));
            jatuhTempoLabel.setText("Jatuh Tempo: " + pajak.getJatuhTempo());
            statusLabel.setText("Status: " + pajak.getStatus());

            pajakDAO.save(pajak);
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

package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserView extends JFrame {
    private JTextField nikField;
    private JPasswordField passwordField;
    private UserController controller = new UserController();

    public UserView() {
        // Set up the frame
        setTitle("Login Pengguna");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Main panel with BorderLayout and padding
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Title label
        JLabel titleLabel = new JLabel("Selamat Datang", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 150, 243)); // Blue accent color
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel with GridBagLayout for better control
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // NIK field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel nikLabel = new JLabel("NIK:");
        nikLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nikLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        nikField = new JTextField(15);
        nikField.setFont(new Font("Arial", Font.PLAIN, 14));
        nikField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(nikField, gbc);

        // Password field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setBackground(new Color(33, 150, 243)); // Solid blue
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false); // Remove default border
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        loginBtn.addActionListener(e -> handleLogin());
        buttonPanel.add(loginBtn);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(255, 153, 0)); // Solid orange
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false); // Remove default border
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        registerButton.addActionListener(e -> new RegisterView().setVisible(true));
        buttonPanel.add(registerButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handleLogin() {
        String nik = nikField.getText();
        String password = new String(passwordField.getPassword());

        User user = controller.login(nik, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Selamat datang, " + user.getNama());
            if ("admin".equalsIgnoreCase(user.getRole())) {
                new AdminDashboard(user.getId()).setVisible(true);
            } else {
                new PembayarDashboard(user.getId()).setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "NIK atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}
package View;

import Controller.Button;
import Controller.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class Registration extends JFrame {

    public Registration() {
        this.setTitle("Registration - Pratama Group");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500, 650);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        this.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel logoLabel = new JLabel(resizeIcon("company.png", 100, 100));
        panel.add(logoLabel, gbc);

        gbc.gridy++;
        JLabel appNameLabel = new JLabel("Pratama Group");
        appNameLabel.setFont(new Font("Magneto Bold", Font.BOLD, 20));
        appNameLabel.setForeground(new Color(0, 102, 204));
        panel.add(appNameLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel nameLabel = new JLabel("Full Name:");
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel phoneLabel = new JLabel("Phone Number:");
        panel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField phoneField = new JTextField(15);
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel addressLabel = new JLabel("Address:");
        panel.add(addressLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField addressField = new JTextField(15);
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        Button buttonCreator = new Button();

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JButton registerButton = buttonCreator.createButton("Register", e -> {
            String fullName = nameField.getText();
            String phoneNumber = phoneField.getText();
            String address = addressField.getText();
            String password = new String(passwordField.getPassword());

            if (fullName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (isPhoneNumberExists(phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Phone number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                saveUserData(fullName, phoneNumber, address, password);
                JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new MainMenu(); 
                dispose();
            }
        }, 0, 0, 0, 0);
        panel.add(registerButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JButton backButton = buttonCreator.createButton("Back", e -> {
            new MainMenu();
            dispose();
        }, 0, 0, 0, 0);
        panel.add(backButton, gbc);

        this.setVisible(true);
    }

    private boolean isPhoneNumberExists(String phoneNumber) {
        boolean exists = false;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customer WHERE phone = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, phoneNumber);

            ResultSet resultSet = statement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return exists;
    }

    private void saveUserData(String fullName, String phoneNumber, String address, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO customer (name, password, address, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, fullName);
            statement.setString(2, password);
            statement.setString(3, address);
            statement.setString(4, phoneNumber);
            

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database insertion error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        try {
            Image img = ImageIO.read(new File(path)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (IOException e) {
            e.printStackTrace();
            return new ImageIcon(); 
        }
    }
}

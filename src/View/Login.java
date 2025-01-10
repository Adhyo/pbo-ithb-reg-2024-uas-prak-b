package View;

import Controller.Button;
import Controller.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Login extends JFrame {

    public Login() {
        this.setTitle("Login - Pratama Group");
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
        JLabel phoneLabel = new JLabel("Phone Number:");
        panel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField phoneField = new JTextField(15);
        panel.add(phoneField, gbc);

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
        JButton loginButton = buttonCreator.createButton("Login", e -> {
            String phoneNumber = phoneField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(phoneNumber, password)) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                
                MainMenu mainMenu = new MainMenu();
                mainMenu.setLoggedIn(true); 
                mainMenu.setVisible(true);
                
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Login Failed. Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }, 0, 0, 0, 0);
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JButton backButton = buttonCreator.createButton("Back", e -> {
            new MainMenu();
            dispose();
        }, 0, 0, 0, 0);
        panel.add(backButton, gbc);

        this.setVisible(true);
    }

    private boolean validateLogin(String phoneNumber, String password) {
        boolean isValid = false;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM customer WHERE phone = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, phoneNumber);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            isValid = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return isValid;
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

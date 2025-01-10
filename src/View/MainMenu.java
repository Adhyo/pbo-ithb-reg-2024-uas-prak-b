package View;

import Controller.Button;
import javax.swing.*;
import java.awt.*;


public class MainMenu extends JFrame {
    private boolean isLoggedIn = false;  

    public MainMenu() {
        this.setTitle("Pratama Group");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(500, 650);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null); 
        panel.setBackground(new Color(240, 240, 240)); 
        this.add(panel);

        JLabel titleLabel = new JLabel("Welcome to Pratama Group");
        titleLabel.setBounds(100, 50, 300, 30);
        titleLabel.setFont(new Font("Magneto Bold", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        Button button = new Button();

        JButton login = button.createButton("Login", e -> {
            if (isLoggedIn == false) {
                new Login(); 
                this.dispose(); 
            }
            else {
                JOptionPane.showMessageDialog(null, "You Alredy Logged in!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }, 150, 150, 200, 40);
        
        JButton register = button.createButton("Register", e -> {
            new Registration(); 
            this.dispose(); 
        }, 150, 220, 200, 40);

        JButton transaksi = button.createButton("Transaksi", e -> {
            if (isLoggedIn) {
                new Transaksi(); 
                this.dispose(); 
            }
            else {
                JOptionPane.showMessageDialog(null, "Please Login first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }, 150, 290, 200, 40);
        
        JButton history = button.createButton("History", e -> {
            if (isLoggedIn) {
                new History(); 
                this.dispose(); 
            }
            else{
                JOptionPane.showMessageDialog(null, "Please Login first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }, 150, 360, 200, 40);

        panel.add(login);
        panel.add(register);
        panel.add(transaksi);
        panel.add(history);

        this.setVisible(true);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
package View;

import Controller.Button;
import javax.swing.*;
import javax.xml.soap.Detail;

import java.awt.*;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Transaksi extends JFrame {
    private boolean isLoggedIn = true;  

    public Transaksi() {
        this.setTitle("Transaksi - Pratama Group");
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

        Button buttonCreator = new Button();
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton tambahTransaksiButton = buttonCreator.createButton("Tambah Transaksi", e -> {
            new TambahTransaksi();
            this.dispose();
        }, 0, 0, 0, 0); 
        panel.add(tambahTransaksiButton, gbc);

        gbc.gridy++;
        JButton detailTransaksiButton = buttonCreator.createButton("Detail Transaksi", e -> {
            new DetailTransaksi();
            this.dispose();
        }, 0, 0, 0, 0);
        panel.add(detailTransaksiButton, gbc);

        gbc.gridy++;
        JButton backButton = buttonCreator.createButton("Back", e -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.setLoggedIn(true);  
            mainMenu.setVisible(true);
            this.dispose();
        }, 0, 0, 0, 0);
        panel.add(backButton, gbc);

        this.setVisible(true);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
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

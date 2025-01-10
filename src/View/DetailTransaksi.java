package View;

import Controller.Button;
import Controller.TransactionController;
import Model.DeliveryStatus;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Date;

public class DetailTransaksi extends JFrame {

    private String evidencePath = ""; 

    public DetailTransaksi() {
        this.setTitle("Detail Transaksi - Pratama Group");
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
        JLabel transactionIdLabel = new JLabel("Transaction ID:");
        panel.add(transactionIdLabel, gbc);

        gbc.gridx = 1;
        JTextField transactionIdField = new JTextField(15);
        panel.add(transactionIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel statusLabel = new JLabel("Status:");
        panel.add(statusLabel, gbc);

        gbc.gridx = 1;
        JComboBox<DeliveryStatus> statusComboBox = new JComboBox<>(DeliveryStatus.values());
        panel.add(statusComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel currentPositionLabel = new JLabel("Current Position:");
        panel.add(currentPositionLabel, gbc);

        gbc.gridx = 1;
        JTextField currentPositionField = new JTextField(15);
        panel.add(currentPositionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel evidenceLabel = new JLabel("Evidence:");
        panel.add(evidenceLabel, gbc);

        gbc.gridx = 1;
        JButton uploadEvidenceButton = new JButton("Upload Evidence");
        panel.add(uploadEvidenceButton, gbc);

        gbc.gridy++;
        JLabel evidenceFileNameLabel = new JLabel("No file selected");
        panel.add(evidenceFileNameLabel, gbc);

        uploadEvidenceButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File evidenceFile = fileChooser.getSelectedFile();
                evidenceFileNameLabel.setText(evidenceFile.getName());
                evidencePath = evidenceFile.getAbsolutePath();
            }
        });

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel updatedByLabel = new JLabel("Updated By:");
        panel.add(updatedByLabel, gbc);

        gbc.gridx = 1;
        JTextField updatedByField = new JTextField(15);
        panel.add(updatedByField, gbc);

        Button buttonCreator = new Button();
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton saveButton = buttonCreator.createButton("Simpan", e -> {
            String transactionId = transactionIdField.getText();
            DeliveryStatus status = (DeliveryStatus) statusComboBox.getSelectedItem();
            String currentPosition = currentPositionField.getText();
            String updatedBy = updatedByField.getText();
            Date currentDate = new Date(); 

            if (TransactionController.validateFields(transactionId, currentPosition, updatedBy, evidencePath,status)) {
                if (TransactionController.saveTransactionDetails(transactionId, status, currentPosition, evidencePath, updatedBy, currentDate)) {
                    JOptionPane.showMessageDialog(this, "Data berhasil disimpan!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new Transaksi(); 
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi, termasuk bukti (image).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }, 0, 0, 0, 0);
        panel.add(saveButton, gbc);

        gbc.gridy++;
        JButton backButton = buttonCreator.createButton("Back", e -> {
            new Transaksi(); 
            this.dispose();
        }, 0, 0, 0, 0);
        panel.add(backButton, gbc);

        this.setVisible(true);
    }
}

package View;

import Controller.Button;
import Controller.TransactionController;
import javax.swing.*;
import java.awt.*;

public class TambahTransaksi extends JFrame {

    public TambahTransaksi() {
        this.setTitle("Create Transaction - Pratama Group");
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
        JLabel nameLabel = new JLabel("Recipient Name:");
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel addressLabel = new JLabel("Recipient Address:");
        panel.add(addressLabel, gbc);

        gbc.gridx = 1;
        JTextField addressField = new JTextField(15);
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel phoneLabel = new JLabel("Recipient Phone:");
        panel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        JTextField phoneField = new JTextField(15);
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel weightLabel = new JLabel("Weight (kg):");
        panel.add(weightLabel, gbc);

        gbc.gridx = 1;
        JTextField weightField = new JTextField(15);
        panel.add(weightField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel deliveryLabel = new JLabel("Delivery Type:");
        panel.add(deliveryLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> deliveryComboBox = new JComboBox<>();
        panel.add(deliveryComboBox, gbc);
        TransactionController.loadDeliveryTypes(deliveryComboBox);

        Button buttonCreator = new Button();
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton saveButton = buttonCreator.createButton("Save", e -> {
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            String weightStr = weightField.getText();
            String deliveryType = (String) deliveryComboBox.getSelectedItem();

            if (TransactionController.validateTransactionFields(name, address, phone, weightStr, deliveryType)) {
                int customerId = 1;  
                if (TransactionController.saveTransaction(customerId, name, address, phone, Integer.parseInt(weightStr), deliveryType)) {
                    JOptionPane.showMessageDialog(this, "Transaction successfully saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new Transaksi();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Transaction failed to save", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }, 0, 0, 0, 0);
        panel.add(saveButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JButton backButton = buttonCreator.createButton("Back", e -> {
            new Transaksi();
            this.dispose();
        }, 0, 0, 0, 0);
        panel.add(backButton, gbc);

        this.setVisible(true);
    }
}

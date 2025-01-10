package View;

import Controller.DatabaseConnection;
import Controller.Button;
import Model.DeliveryStatus;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Vector;

public class DetailHistory extends JFrame {

    public DetailHistory(String transactionId) {
        this.setTitle("Detail History - Pratama Group");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        this.add(panel);

        String[] columnNames = {"Status", "Evidence", "Date", "Updated By"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable detailTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(detailTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        Button buttonCreator = new Button();
        JButton backButton = buttonCreator.createButton("Back", e -> {
            new History(); 
            this.dispose();
        }, 0, 0, 100, 40);
        panel.add(backButton, BorderLayout.SOUTH);

        loadDetailData(tableModel, transactionId);

        JPanel additionalInfoPanel = new JPanel(new GridLayout(4, 2));
        additionalInfoPanel.setBackground(new Color(240, 240, 240));
        additionalInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel transactionIdLabel = new JLabel("Transaction ID: " + transactionId);
        JLabel deliveryTypeLabel = new JLabel("Delivery Type: ");
        JLabel packageWeightLabel = new JLabel("Package Weight: ");
        JLabel totalCostLabel = new JLabel("Total Cost: ");
        
        additionalInfoPanel.add(transactionIdLabel);
        additionalInfoPanel.add(deliveryTypeLabel);
        additionalInfoPanel.add(packageWeightLabel);
        additionalInfoPanel.add(totalCostLabel);

        panel.add(additionalInfoPanel, BorderLayout.NORTH);

        this.setVisible(true);
    }

    private void loadDetailData(DefaultTableModel tableModel, String transactionId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT status, evidence, date, updated_by " +
                           "FROM delivery_details " +
                           "WHERE transaction_id = ? " +
                           "ORDER BY date DESC";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, transactionId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String status = resultSet.getString("status");
                String evidence = resultSet.getString("evidence");
                Timestamp date = resultSet.getTimestamp("date");
                String updatedBy = resultSet.getString("updated_by");

                Vector<Object> row = new Vector<>();
                row.add(status);
                row.add(evidence);
                row.add(date);
                row.add(updatedBy);

                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading delivery details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

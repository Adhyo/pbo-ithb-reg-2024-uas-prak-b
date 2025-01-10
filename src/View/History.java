package View;

import Controller.DatabaseConnection;
import Controller.Button;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Vector;

public class History extends JFrame {

    public History() {
        this.setTitle("History Pengiriman - Pratama Group");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        this.add(panel);

        String[] columnNames = {"Transaction ID", "Package Type", "Package Weight", "Total Cost", "Created At", "Updated At", "Action"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable historyTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        Button buttonCreator = new Button();
        JButton backButton = buttonCreator.createButton("Back", e -> {
            MainMenu mainMenu = new MainMenu();
                mainMenu.setLoggedIn(true);  
                mainMenu.setVisible(true);
                
                this.dispose();
        }, 0, 0, 100, 40);
        panel.add(backButton, BorderLayout.SOUTH);

        loadHistoryData(tableModel);

        historyTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        historyTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JButton()));

        this.setVisible(true);
    }

    private void loadHistoryData(DefaultTableModel tableModel) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT DISTINCT t.id, t.delivery_type, t.expected_weight, t.total_cost, t.created_at, " +
                           "MAX(d.date) as updated_at " +
                           "FROM transaction t " +
                           "LEFT JOIN delivery_details d ON t.id = d.transaction_id " +
                           "GROUP BY t.id, t.delivery_type, t.expected_weight, t.total_cost, t.created_at";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String transactionId = resultSet.getString("id");
                String deliveryType = resultSet.getString("delivery_type");
                String packageWeight = resultSet.getString("expected_weight");
                double totalCost = resultSet.getDouble("total_cost");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Timestamp updatedAt = resultSet.getTimestamp("updated_at");

                Vector<Object> row = new Vector<>();
                row.add(transactionId);
                row.add(deliveryType);
                row.add(packageWeight);
                row.add(totalCost);
                row.add(createdAt);
                row.add(updatedAt);

                row.add("Lihat Detail");

                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading history data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Lihat Detail");
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private String transactionId;

        public ButtonEditor(JButton button) {
            super(new JCheckBox()); 
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            transactionId = (String) table.getValueAt(row, 0); 
            JButton button = new JButton("Lihat Detail");

            button.addActionListener(e -> {
                new DetailHistory(transactionId);
                stopCellEditing();
                dispose();
            });

            return button;
        }
    }
}

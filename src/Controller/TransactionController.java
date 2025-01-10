package Controller;

import Model.Transaction;
import Model.Customer;
import Model.DeliveryStatus;
import java.sql.*;
import java.util.Date;
import javax.swing.*;

public class TransactionController {

    public static void loadDeliveryTypes(JComboBox<String> deliveryComboBox) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT type FROM category_delivery";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                deliveryComboBox.addItem(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading delivery types", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean saveTransaction(int customerId, String name, String address, String phone, 
                                           int weight, String deliveryType) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String feeQuery = "SELECT base_rate FROM category_delivery WHERE type = ?";
            PreparedStatement feeStatement = connection.prepareStatement(feeQuery);
            feeStatement.setString(1, deliveryType);
            ResultSet feeResultSet = feeStatement.executeQuery();

            if (feeResultSet.next()) {
                int fee = feeResultSet.getInt("base_rate");
                int totalCost = weight * fee;

                String insertQuery = "INSERT INTO Transaction (customer_id, delivery_type, expected_weight, total_cost, created_at, receipt_name, receipt_address, receipt_phone) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, customerId); 
                insertStatement.setString(2, deliveryType);
                insertStatement.setInt(3, weight);
                insertStatement.setInt(4, totalCost);
                insertStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                insertStatement.setString(6, name);
                insertStatement.setString(7, address);
                insertStatement.setString(8, phone);

                int rowsAffected = insertStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving transaction", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public static boolean validateTransactionFields(String name, String address, String phone, String weightStr, String deliveryType) {
        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || weightStr.isEmpty() || deliveryType == null) {
            JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            int weight = Integer.parseInt(weightStr);
            if (weight <= 0) {
                JOptionPane.showMessageDialog(null, "Weight must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid weight input", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    public static boolean validateFields(String transactionId, String currentPosition, String evidence, String updatedBy, DeliveryStatus status) {
        if (transactionId.isEmpty() || currentPosition.isEmpty() || evidence.isEmpty() || updatedBy.isEmpty() || status == null) {
            JOptionPane.showMessageDialog(null, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean saveTransactionDetails(String transactionId, DeliveryStatus status, String currentPosition, String evidence, String updatedBy, Date currentDate) {
        boolean isSaved = DatabaseController.saveTransactionDetail(transactionId, status, currentPosition, evidence, updatedBy, currentDate);
        if (isSaved) {
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return isSaved;
    }
}

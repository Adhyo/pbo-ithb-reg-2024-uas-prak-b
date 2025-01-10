package Controller;

import Model.DeliveryStatus;
import java.sql.*;
import java.util.Date;

public class DatabaseController {

    public static boolean saveTransactionDetail(String transactionId, DeliveryStatus status, String currentPosition, String evidence, String updatedBy, Date currentDate) {
        boolean isSaved = false;
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO delivery_details (transaction_id, status, current_position, evidence, updated_by, date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, transactionId);
            statement.setString(2, status.name());
            statement.setString(3, currentPosition);
            statement.setString(4, evidence);
            statement.setString(5, updatedBy);
            statement.setTimestamp(6, new Timestamp(currentDate.getTime()));
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                isSaved = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return isSaved;
    }
}

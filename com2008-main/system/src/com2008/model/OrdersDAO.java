package com2008.model;

import com2008.UserSession;
import com2008.util.UniqueUserIDGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

//import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.ResultSetMetaData;
public class OrdersDAO {
    public boolean confirmOrder(Connection connection){
        boolean orderConfirmed = false;
        PreparedStatement preparedStatement = null;

        try{
            String currentUserId = UserSession.getCurrentUserId();

            String sql = "SELECT userId, firstName, passwordHash FROM Users where userId = ?";

            String updateSQL1 = "UPDATE Orders SET orderState=? WHERE userId=?";

            preparedStatement = connection.prepareStatement(updateSQL1);

            preparedStatement.setString(1, "CONFIRMED");
            preparedStatement.setString(2, currentUserId);
            int rowsAffectedUsers = preparedStatement.executeUpdate();

            //while (re)
            if (rowsAffectedUsers > 0){
                System.out.println(rowsAffectedUsers + " order confirmed");
                UserSession.endSession();

                orderConfirmed = true;
            } else {
                System.out.println("Cannot confirm order");
            }

            return orderConfirmed;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkFirstTimeOrder(Connection connection){
        //get currentUserId
        //check if null then true
        //else false
        boolean firstTimeOrder = false;
        String currentUserId = UserSession.getCurrentUserId();

        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM BankDetails WHERE userId = ?");
            statement.setString(1, currentUserId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                int count = resultSet.getInt(1);
                // Inverting the logic here
                firstTimeOrder = count == 0;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return firstTimeOrder;
    }

    public boolean checkOrderExists(Connection connection){
        boolean orderExists = false;
        String currentUserId = UserSession.getCurrentUserId();
        //find userId and status = PENDING

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM Orders WHERE userId = ? AND orderState = 'PENDING'");
            statement.setString(1, currentUserId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                orderExists = count > 0;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        return orderExists;
    }

    public boolean isValidBankNo(String bankNo){

        boolean isValidBankNo = bankNo.matches("[0-9]{16}");
        return isValidBankNo;
    }

    public boolean isValidExpDate(String expDate){
        boolean isExpDateValid = expDate.matches("(0[1-9]|1[0-2])/[0-9]{2}");
        return isExpDateValid;
    }

    public boolean isValidCvv(Integer cvv){
        boolean isCvvValid = String.valueOf(cvv).matches("[0-9]{3}");
        return isCvvValid;
    }
    
    public boolean createNewOrderLine(Connection connection,String userId, String productId, int quantity, double orderLineCosts){
        boolean orderLineStatus = false;

        try{
            String insertSQL = "INSERT INTO OrderLine (userId, productId, quantity, orderLineCost) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, productId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, orderLineCosts);  // Set userId

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                orderLineStatus = true;
                // If the insert was successful, retrieve the auto-generated userId

                System.out.println(rowsAffected + " user created");
            }
            return orderLineStatus;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getOrderLineList(Connection connection,String userId){
        ResultSet resultSet = null;

        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT p.productId, p.brandName, p.productName, o.quantity, o.orderLineCost, o.cartId FROM " +
                "OrderLine AS o INNER JOIN Products as p ON p.productId = o.productId WHERE o.orderNo IS NULL AND o.userId = ?");
            statement.setString(1, userId);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
         return resultSet;
    }

    public boolean deleteOrderLine(Connection connection, int cartId){
        boolean resultSet = false;
 
        String deleteSQL2 = String.format(
            "DELETE FROM OrderLine WHERE cartId = ?"
        );
        try(PreparedStatement statement2 = connection.prepareStatement(deleteSQL2)){
            statement2.setInt(1, cartId);
            int rowsAffected = statement2.executeUpdate();
            if (rowsAffected > 0){
                System.out.println(rowsAffected + " row(s) deleted successfully in products");
                resultSet = true;
            } else {
                System.out.println("No rows were deleted");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean updateQuantityPrice(Connection connection, int orderLineId, int quantity, double newPrice) {
        boolean resultSet = false;
 
        String updateSQL2 = "UPDATE OrderLine SET quantity=?, orderLineCost =? WHERE cartId=?";
        try(PreparedStatement statement2 = connection.prepareStatement(updateSQL2)){
            statement2.setInt(1, quantity);
            statement2.setDouble(2, newPrice);
            statement2.setDouble(3, orderLineId);
            
            int rowsAffected = statement2.executeUpdate();
            if (rowsAffected > 0){
                System.out.println(rowsAffected + " row(s) updated successfully in products");
            } else {
                System.out.println("No rows were updated");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }
}
   

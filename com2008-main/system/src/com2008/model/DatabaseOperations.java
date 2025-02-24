package com2008.model;

import com2008.BankDetails;
import com2008.User;
import com2008.UserSession;
import com2008.util.HashedPasswordGenerator;
import com2008.util.UniqueUserIDGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//import com.mysql.cj.jdbc.result.ResultSetMetaData;


public class DatabaseOperations {

    // public checkUserRoles(Connection connection, String email){
        
    //     return MANAGER;
    // }

    public boolean checkIfUserExists(Connection connection, String email){
        boolean userExists = false;
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM Users WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                int count = resultSet.getInt(1);
                userExists = count > 0;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return userExists;
    }


    
    public boolean register(Connection connection, String email, String hashedPassword)  {
       boolean registrationStatus = false;

        try{
            String insertSQL = "INSERT INTO Users (email, passwordHash, userId, userRole) VALUES (?,?,?, ?)";
            String uniqueUserID = UniqueUserIDGenerator.generateUniqueUserID();
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, uniqueUserID);  // Set userId
            preparedStatement.setString(4, "CUSTOMER");

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                registrationStatus = true;
                UserSession.setCurrentUserId(uniqueUserID);
                // If the insert was successful, retrieve the auto-generated userId

                System.out.println(rowsAffected + " user created");
            }
            return registrationStatus;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean newPersonalInfo(Connection connection, String firstName, String lastName,
                                   String houseNumber, String roadNumber, String cityName, String postCode) {
        boolean newPersonalInfoStatus = false;
        String currentUserId = String.valueOf(UserSession.getCurrentUserId());

        try {
            connection.setAutoCommit(false);  // Start the transaction

            String updateSQL1 = "UPDATE Users SET firstName=?, lastName=? WHERE userId=?";
//            System.out.println("SQL Query: " + updateSQL1);

            //String updateSQL1 = "UPDATE Users SET firstName=?, lastName=? WHERE email=?";
            String updateSQL2 = "INSERT INTO Address (houseNo, roadName, cityName, postCode, userId)" +
                    " VALUES (?,?,?,?,?)";

            PreparedStatement userStatement = connection.prepareStatement(updateSQL1);
            PreparedStatement addressStatement = connection.prepareStatement(updateSQL2);

            userStatement.setString(1, firstName);
            userStatement.setString(2, lastName);
            userStatement.setString(3, currentUserId);
            int rowsAffectedUsers = userStatement.executeUpdate();

            addressStatement.setString(1, houseNumber);
            addressStatement.setString(2, roadNumber);
            addressStatement.setString(3, cityName);
            addressStatement.setString(4, postCode);
            addressStatement.setString(5, currentUserId);
            int rowsAffectedAddress = addressStatement.executeUpdate();

            if (rowsAffectedUsers > 0 && rowsAffectedAddress > 0) {
                System.out.println(rowsAffectedUsers + " row(s) inserted successfully in users.");
                System.out.println(rowsAffectedAddress + " row(s) inserted successfully in address.");
                System.out.println("Registration successful");
                UserSession.endSession();
                connection.commit();  // Commit the transaction
                newPersonalInfoStatus = true;
            } else {
                System.out.println("No rows were updated");
            }

            return newPersonalInfoStatus;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();  // Rollback the transaction if an exception occurs
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);  // Reset auto-commit to true
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean verifyLogin(Connection connection, String email, char[] hashedPassword){
        boolean loggedin = false;


        try {
            // Query the database to fetch user information
            String sql = "SELECT userId, firstName, passwordHash FROM Users where email = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("userId");
                String storedPasswordHash = resultSet.getString("passwordHash");
                String firstName = resultSet.getString("firstName");

                //Verify the entered password against the stored hash
                if (verifyPassword(hashedPassword, storedPasswordHash)) {
                    //UserSession.setCurrentUserId(Integer.parseInt(userId)); //user session activated
                    UserSession.setCurrentUserId(userId);
                    loggedin = true;
                    CurrentUser user = new CurrentUser(userId, email, userId, getRolesForUserId(connection, userId));
                    CurrentUser.setCurrentUser(user);
                    System.out.println("Login successful for " + user + ": " + firstName);

                } else{
                    // Incorrect password, update failed login attempts
                    System.out.println("Incorrect password. Try Again.");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return loggedin;
    }

    private static boolean verifyPassword(char[] enteredPassword, String storedPasswordHash) {
        try {
            String hashedEnteredPassword = HashedPasswordGenerator.hashPassword(enteredPassword);
            return hashedEnteredPassword.equals(storedPasswordHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

  /**
     * Gets the userId based on the username from the 'Users' table.
     *
     * @param connection The database connection.
     * @param username   The username for which to retrieve the userId.
     * @return The userId corresponding to the given username.
     */
    public String getUserIdByUsername(Connection connection, String email) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Prepare the SQL statement to select the userId based on the username
            String sql = "SELECT userId FROM Users WHERE email = ?";
            preparedStatement = connection.prepareStatement(sql);

            // Set the parameter for the prepared statement
            preparedStatement.setString(1, email);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if a result is found
            if (resultSet.next()) {
                return resultSet.getString("userId");
            } else {
                System.out.println("User with username " + email + " not found.");
                return null; // Or throw an exception or handle the case as appropriate for your application
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
            return null;
        } finally {
            // Close the resources in a finally block to ensure they're closed even if an exception occurs
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception according to your application's needs
            }
        }
    }

        /**
     * Gets the user roles based on the userId.
     *
     * @param connection  The database connection.
     * @param userId  The userId for which to retrieve roles.
     * @return The user's roles.
     */
    public Role getRolesForUserId(Connection connection, String userId) {
        try {
            String sql = "SELECT userRole FROM Users WHERE userId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return Role.fromString(resultSet.getString("userRole"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean promoteToStaff(Connection connection, String emailEntered){
        boolean roleUpdated = false;
        PreparedStatement preparedStatement = null;

        try{
            String userId = getUserIdByUsername(connection, emailEntered);
            String sql = "SELECT userId, firstName, passwordHash FROM Users where userId = ?";

            String updateSQL1 = "UPDATE Users SET userRole=? WHERE userId=?";

            preparedStatement = connection.prepareStatement(updateSQL1);

            preparedStatement.setString(1, "STAFF");
            preparedStatement.setString(2, userId);
            int rowsAffectedUsers = preparedStatement.executeUpdate();

            //while (re)
            if (rowsAffectedUsers > 0){
                System.out.println(rowsAffectedUsers + " row(s) inserted successfully in users.");
                UserSession.endSession();

                roleUpdated = true;
            } else {
                System.out.println("No rows were updated");
            }

            return roleUpdated;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean demoteToCustomer(Connection connection, String emailEntered){
        boolean roleUpdated = false;
        PreparedStatement preparedStatement = null;

        try{
            String userId = getUserIdByUsername(connection, emailEntered);
            String sql = "SELECT userId, firstName, passwordHash FROM Users where userId = ?";

            String updateSQL1 = "UPDATE Users SET userRole=? WHERE userId=?";

            preparedStatement = connection.prepareStatement(updateSQL1);

            preparedStatement.setString(1, "CUSTOMER");
            preparedStatement.setString(2, userId);
            int rowsAffectedUsers = preparedStatement.executeUpdate();


            if (rowsAffectedUsers > 0){
                System.out.println(rowsAffectedUsers + " row(s) inserted successfully in users.");
                UserSession.endSession();

                roleUpdated = true;
            } else {
                System.out.println("No rows were updated");
            }

            return roleUpdated;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getStaffList(Connection connection) throws SQLException{
        ResultSet resultSet = null;
        try {
            // Establish a database connection

            // Execute the SQL query
            String sqlQuery = "SELECT email, firstName, lastName " +
                    "FROM Users " +
                    "WHERE userRole = 'STAFF' ";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery); 

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }


//
    public boolean newBankingInfo(Connection connection, String bankName, String cardHolderName,
                                   String bankNo, String expDate, Integer cvv) {
        boolean bankingInfoStatus = false;
        String currentUserId = UserSession.getCurrentUserId();


        try{
            String insertSQL = "INSERT INTO BankDetails (bankName, cardHolderName," +
                    " bankNo, expDate, cvv, userId) VALUES (?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, bankName);
            preparedStatement.setString(2, cardHolderName);
            preparedStatement.setString(3, bankNo);
            preparedStatement.setString(4, expDate);
            preparedStatement.setInt(5, cvv);
            preparedStatement.setString(6, currentUserId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                bankingInfoStatus = true;

                System.out.println("Banking Information Updated");
            }
            return bankingInfoStatus;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public User getUserDetails(Connection connection, String userId) {
        try {
            String selectSQL = "SELECT firstName, lastName, houseNo, roadName, cityName, postCode FROM Users JOIN Address ON Users.userId = Address.userId WHERE Users.userId=?";
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("getUserDetails work");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String houseNumber = resultSet.getString("houseNo");
                String roadName = resultSet.getString("roadName");
                String cityName = resultSet.getString("cityName");
                String postCode = resultSet.getString("postCode");

                return new User(userId, firstName, lastName, houseNumber, roadName, cityName, postCode);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean editUserDetails(Connection connection, String userId, String firstName, String lastName,
                                   String houseNumber, String roadName, String cityName, String postCode) {

//        try {
//            String selectSQL = "SELECT postCode FROM Address WHERE userId=?";
//            String selectSQL2 = "SELECT firstName FROM Address WHERE userId=?";
//            PreparedStatement statement = connection.prepareStatement(selectSQL);
//            PreparedStatement statement2 = connection.prepareStatement(selectSQL2);
//            statement2.setString(1, userId);
//            statement.setString(1, userId);
//            String originalPostcode = null;
//            String originalFirstName = null;
//
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                originalPostcode = resultSet.getString("postCode");
//                originalFirstName = resultSet.getString("firstName");
//            }
//
//            if (originalPostcode  != null && originalFirstName != null) {
//                // Update the Address table
//                System.out.println("User is not null");
//                String updateSQL = "UPDATE Address SET houseNo=?, roadName=?, cityName=?, postCode=? WHERE userId=?";
//                String updateSQL2 = "UPDATE Users SET firstName=?, lastName=? WHERE userId=?";
//                PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
//                PreparedStatement updateStatement2 = connection.prepareStatement(updateSQL2);
//                    updateStatement.setString(1, houseNumber);
//                    updateStatement.setString(2, roadName);
//                    updateStatement.setString(3, cityName);
//                    updateStatement.setString(4, postCode);
//                    updateStatement.setString(5, userId);
//                    updateStatement2.setString(1, firstName);
//                    updateStatement2.setString(2, lastName);
//                    updateStatement2.setString(3, userId);
//
//                    int rowsUpdated = updateStatement.executeUpdate();
//                    int rowsUpdated2 = updateStatement2.executeUpdate();
//
//                    // Check if the update was successful
//                    return rowsUpdated > 0;
//
//
//
//            }
//
//
//            resultSet.close();
//            statement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
////        } finally {
////            try {
////                connection.close();
////            } catch (SQLException ex) {
////                ex.printStackTrace();
////            }
////        }


        boolean editStatus = false;

        try {
            connection.setAutoCommit(false);

            String updateSQL1 = "UPDATE Users SET firstName=?, lastName=? WHERE userId=?";
            String updateSQL2 = "UPDATE Address SET houseNo=?, roadName=?, cityName=?, postCode=? WHERE userId=?";

            try (PreparedStatement userStatement = connection.prepareStatement(updateSQL1);
                 PreparedStatement addressStatement = connection.prepareStatement(updateSQL2)) {

                userStatement.setString(1, firstName);
                userStatement.setString(2, lastName);
                userStatement.setString(3, userId);
                int rowsAffectedUsers = userStatement.executeUpdate();

                addressStatement.setString(1, houseNumber);
                addressStatement.setString(2, roadName);
                addressStatement.setString(3, cityName);
                addressStatement.setString(4, postCode);
                addressStatement.setString(5, userId);
                int rowsAffectedAddress = addressStatement.executeUpdate();

                if (rowsAffectedUsers > 0 && rowsAffectedAddress > 0) {
                    System.out.println(rowsAffectedUsers + " row(s) updated successfully in users.");
                    System.out.println(rowsAffectedAddress + " row(s) updated successfully in address.");
                    connection.commit();
                    editStatus = true;
                } else {
                    System.out.println("No rows were updated");
                }
            }

            return editStatus;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public BankDetails getBankDetails(Connection connection, String userId) {
        try {
            String selectSQL = "SELECT bankName, bankNo, expDate, cvv, cardHolderName FROM BankDetails WHERE userId=?";
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String bankName = resultSet.getString("bankName");
                String bankNo = resultSet.getString("bankNo");
                String expDate = resultSet.getString("expDate");
                int cvv = resultSet.getInt("cvv");
                String cardHolderName = resultSet.getString("cardHolderName");

                return new BankDetails(userId, bankName, bankNo, expDate, cvv, cardHolderName);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean editBankDetails(Connection connection, String userId, String bankName, String bankNo,
                                   String expDate, int cvv, String cardHolderName) {

        boolean editStatus = false;

        try {
            connection.setAutoCommit(false);

            String updateSQL = "UPDATE BankDetails SET bankName=?, bankNo=?, expDate=?, cvv=?, cardHolderName=? WHERE userId=?";

            try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {

                statement.setString(1, bankName);
                statement.setString(2, bankNo);
                statement.setString(3, expDate);
                statement.setInt(4, cvv);
                statement.setString(5, cardHolderName);
                statement.setString(6, userId);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println(rowsAffected + " row(s) updated successfully in bank details.");
                    connection.commit();
                    editStatus = true;
                } else {
                    System.out.println("No rows were updated");
                }
            }

            return editStatus;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public boolean isValidBankNo(String bankNo){
//
//        boolean isValidBankNo = bankNo.matches("[0-9]{16}");
//        return isValidBankNo;
//    }
//
//    public boolean isValidExpDate(String expDate){
//        boolean isExpDateValid = expDate.matches("(0[1-9]|1[0-2])/[0-9]{2}");
//        return isExpDateValid;
//    }
//
//    public boolean isValidCvv(Integer cvv){
//        boolean isCvvValid = String.valueOf(cvv).matches("[0-9]{3}");
//        return isCvvValid;
//    }
}

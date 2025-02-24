package com2008.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com2008.UserSession;
import com2008.util.UniqueUserIDGenerator;

//import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.ResultSetMetaData;

public class ProductsDAO {

    public boolean checkIfProductExists(Connection connection, String productId){
        boolean productExists = false;
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM Products WHERE productId = ?");
            statement.setString(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                int count = resultSet.getInt(1);
                productExists = count > 0;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return productExists;
    }
    
    public boolean addNewTrainSets(Connection connection, String brandName, String productName, String productCode, 
            int quantity, double retailPrice, Gauges gauge, ProductsE productType, String eraCode) {
       boolean addProductStatus = false;

       try {
 
        String updateSQL1 = "INSERT INTO Products (productId, brandName, productName, retailPrice, modellingGauge, productType, stockLevel)" +
                " VALUES (?,?,?,?,?,?,?)";
        String updateSQL2 = "INSERT INTO trainSet (trainSetId, eracode, productId)" +
                " VALUES (?,?,?)";

        PreparedStatement productStatement = connection.prepareStatement(updateSQL1);
        PreparedStatement trainSetStatement = connection.prepareStatement(updateSQL2);

        productStatement.setString(1, productCode);
        productStatement.setString(2, brandName);
        productStatement.setString(3, productName);
        productStatement.setDouble(4, retailPrice);
        productStatement.setString(5, gauge.toString());
        productStatement.setString(6, productType.toString());
        productStatement.setInt(7, quantity);


        int rowsAffectedProducts = productStatement.executeUpdate();

        trainSetStatement.setString(1, productCode);
        trainSetStatement.setString(2, eraCode);
        trainSetStatement.setString(3, productCode);

        int rowsAffectedTrainSets = trainSetStatement.executeUpdate();

        if (rowsAffectedProducts > 0 && rowsAffectedTrainSets > 0) {
            System.out.println(rowsAffectedProducts + " row(s) inserted successfully in products.");
            System.out.println(rowsAffectedTrainSets + " row(s) inserted successfully in trainSets.");
            System.out.println("Product added successfully");
            addProductStatus = true;
        } else {
            System.out.println("No rows were updated");
        }

        return addProductStatus;

        } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }

    }

    public boolean addNewTrackPack(Connection connection, String brandName, String productName, String productCode, 
            int quantity, double retailPrice, Gauges gauge, ProductsE productType) {
       boolean addProductStatus = false;

       try {
 
        String updateSQL1 = "INSERT INTO Products (productId, brandName, productName, retailPrice, modellingGauge, productType, stockLevel)" +
                " VALUES (?,?,?,?,?,?,?)";
        String updateSQL2 = "INSERT INTO TrackPack (trackPackId, productId)" +
                " VALUES (?,?)";

        PreparedStatement productStatement = connection.prepareStatement(updateSQL1);
        PreparedStatement trackSetStatement = connection.prepareStatement(updateSQL2);

        productStatement.setString(1, productCode);
        productStatement.setString(2, brandName);
        productStatement.setString(3, productName);
        productStatement.setDouble(4, retailPrice);
        productStatement.setString(5, gauge.toString());
        productStatement.setString(6, productType.toString());
        productStatement.setInt(7, quantity);


        int rowsAffectedProducts = productStatement.executeUpdate();

        trackSetStatement.setString(1, productCode);
        trackSetStatement.setString(2, productCode);

        int rowsAffectedTracknSets = trackSetStatement.executeUpdate();

        if (rowsAffectedProducts > 0 && rowsAffectedTracknSets > 0) {
            System.out.println(rowsAffectedProducts + " row(s) inserted successfully in products.");
            System.out.println(rowsAffectedTracknSets + " row(s) inserted successfully in trackPack.");
            System.out.println("Product added successfully");
            addProductStatus = true;
        } else {
            System.out.println("No rows were updated");
        }

        return addProductStatus;

        } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }

    }

    public boolean addNewLocomotive(Connection connection, String brandName, String productName, String productCode, 
            int quantity, double retailPrice, Gauges gauge, ProductsE productType, String eraCode, DCC dcc) {
       boolean addProductStatus = false;

       try {
 
        String updateSQL1 = "INSERT INTO Products (productId, brandName, productName, retailPrice, modellingGauge, productType, stockLevel)" +
                " VALUES (?,?,?,?,?,?,?)";
        String updateSQL2 = "INSERT INTO Locomotive (locomotivesId, eracode, dccCode, productId)" +
                " VALUES (?,?,?,?)";

        PreparedStatement productStatement = connection.prepareStatement(updateSQL1);
        PreparedStatement trainSetStatement = connection.prepareStatement(updateSQL2);

        productStatement.setString(1, productCode);
        productStatement.setString(2, brandName);
        productStatement.setString(3, productName);
        productStatement.setDouble(4, retailPrice);
        productStatement.setString(5, gauge.toString());
        productStatement.setString(6, productType.toString());
        productStatement.setInt(7, quantity);


        int rowsAffectedProducts = productStatement.executeUpdate();

        trainSetStatement.setString(1, productCode);
        trainSetStatement.setString(2, eraCode);
        trainSetStatement.setString(3, dcc.toString());
        trainSetStatement.setString(4, productCode);

        int rowsAffectedTrainSets = trainSetStatement.executeUpdate();

        if (rowsAffectedProducts > 0 && rowsAffectedTrainSets > 0) {
            System.out.println(rowsAffectedProducts + " row(s) inserted successfully in products.");
            System.out.println(rowsAffectedTrainSets + " row(s) inserted successfully in trainSets.");
            System.out.println("Product added successfully");
            addProductStatus = true;
        } else {
            System.out.println("No rows were updated");
        }

        return addProductStatus;

        } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }

    }

    public boolean addNewRollingStock(Connection connection, String brandName, String productName, String productCode, 
            int quantity, double retailPrice, Gauges gauge, ProductsE productType, String eraCode) {
       boolean addProductStatus = false;

       try {
 
        String updateSQL1 = "INSERT INTO Products (productId, brandName, productName, retailPrice, modellingGauge, productType, stockLevel)" +
                " VALUES (?,?,?,?,?,?,?)";
        String updateSQL2 = "INSERT INTO RollingStock (rollingId, eracode, productId)" +
                " VALUES (?,?,?)";

        PreparedStatement productStatement = connection.prepareStatement(updateSQL1);
        PreparedStatement trainSetStatement = connection.prepareStatement(updateSQL2);

        productStatement.setString(1, productCode);
        productStatement.setString(2, brandName);
        productStatement.setString(3, productName);
        productStatement.setDouble(4, retailPrice);
        productStatement.setString(5, gauge.toString());
        productStatement.setString(6, productType.toString());
        productStatement.setInt(7, quantity);


        int rowsAffectedProducts = productStatement.executeUpdate();

        trainSetStatement.setString(1, productCode);
        trainSetStatement.setString(2, eraCode);
        trainSetStatement.setString(3, productCode);

        int rowsAffectedTrainSets = trainSetStatement.executeUpdate();

        if (rowsAffectedProducts > 0 && rowsAffectedTrainSets > 0) {
            System.out.println(rowsAffectedProducts + " row(s) inserted successfully in products.");
            System.out.println(rowsAffectedTrainSets + " row(s) inserted successfully in trainSets.");
            System.out.println("Product added successfully");
            addProductStatus = true;
        } else {
            System.out.println("No rows were updated");
        }

        return addProductStatus;

        } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }

    }

    public boolean addNewTrack(Connection connection, String brandName, String productName, String productCode, 
            int quantity, double retailPrice, Gauges gauge, ProductsE productType) {
       boolean addProductStatus = false;

       try {
 
        String updateSQL1 = "INSERT INTO Products (productId, brandName, productName, retailPrice, modellingGauge, productType, stockLevel)" +
                " VALUES (?,?,?,?,?,?,?)";
        String updateSQL2 = "INSERT INTO Tracks (tracksId, productId)" +
                " VALUES (?,?)";

        PreparedStatement productStatement = connection.prepareStatement(updateSQL1);
        PreparedStatement trackSetStatement = connection.prepareStatement(updateSQL2);

        productStatement.setString(1, productCode);
        productStatement.setString(2, brandName);
        productStatement.setString(3, productName);
        productStatement.setDouble(4, retailPrice);
        productStatement.setString(5, gauge.toString());
        productStatement.setString(6, productType.toString());
        productStatement.setInt(7, quantity);


        int rowsAffectedProducts = productStatement.executeUpdate();

        trackSetStatement.setString(1, productCode);
        trackSetStatement.setString(2, productCode);

        int rowsAffectedTracknSets = trackSetStatement.executeUpdate();

        if (rowsAffectedProducts > 0 && rowsAffectedTracknSets > 0) {
            System.out.println(rowsAffectedProducts + " row(s) inserted successfully in products.");
            System.out.println(rowsAffectedTracknSets + " row(s) inserted successfully in trackPack.");
            System.out.println("Product added successfully");
            addProductStatus = true;
        } else {
            System.out.println("No rows were updated");
        }

        return addProductStatus;

        } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }

    }

    public boolean addNewController(Connection connection, String brandName, String productName, String productCode, 
            int quantity, double retailPrice, Gauges gauge, ProductsE productType) {
       boolean addProductStatus = false;

       try {
 
        String updateSQL1 = "INSERT INTO Products (productId, brandName, productName, retailPrice, modellingGauge, productType, stockLevel)" +
                " VALUES (?,?,?,?,?,?,?)";
        String updateSQL2 = "INSERT INTO Controller (controllerId, productId)" +
                " VALUES (?,?)";

        PreparedStatement productStatement = connection.prepareStatement(updateSQL1);
        PreparedStatement trackSetStatement = connection.prepareStatement(updateSQL2);

        productStatement.setString(1, productCode);
        productStatement.setString(2, brandName);
        productStatement.setString(3, productName);
        productStatement.setDouble(4, retailPrice);
        productStatement.setString(5, gauge.toString());
        productStatement.setString(6, productType.toString());
        productStatement.setInt(7, quantity);


        int rowsAffectedProducts = productStatement.executeUpdate();

        trackSetStatement.setString(1, productCode);
        trackSetStatement.setString(2, productCode);

        int rowsAffectedTracknSets = trackSetStatement.executeUpdate();

        if (rowsAffectedProducts > 0 && rowsAffectedTracknSets > 0) {
            System.out.println(rowsAffectedProducts + " row(s) inserted successfully in products.");
            System.out.println(rowsAffectedTracknSets + " row(s) inserted successfully in trackPack.");
            System.out.println("Product added successfully");
            addProductStatus = true;
        } else {
            System.out.println("No rows were updated");
        }

        return addProductStatus;

        } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }

    }

    public ResultSet getProductList(Connection connection) throws SQLException{
        ResultSet resultSet = null;
        try {
            // Establish a database connection

            // Execute the SQL query
            String sqlQuery = "SELECT productId, brandName, productName, retailPrice, modellingGauge, productType, stockLevel " +
                    "FROM Products ";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery(sqlQuery); 

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getFilterResultSet(Connection connection, String category) throws SQLException{
        ResultSet resultSet = null;
        try {
            // Establish a database connection

            // Execute the SQL query
            String sqlQuery = "SELECT productId, brandName, productName, retailPrice, modellingGauge, productType, stockLevel " +
                    "FROM Products WHERE productType=?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, category);
            resultSet = statement.executeQuery(); 

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    
    public ResultSet getQuantity(Connection connection, String productId) {
        ResultSet resultSet = null;
        try{
            String sqlQuery = "SELECT stockLevel " + "FROM Products WHERE productId=?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, productId);
            resultSet = statement.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
    return resultSet;
    }

    public ResultSet getRetailPrice(Connection connection, String productId) {
        ResultSet resultSet = null;
        try{
            String sqlQuery = "SELECT retailPrice " + "FROM Products WHERE productId=?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, productId);
            resultSet = statement.executeQuery();
        } catch (Exception e){
            e.printStackTrace();
        }
    return resultSet;
    }

    public boolean updateStock(Connection connection, String productId, int quantity) {
        boolean resultSet = false;
 
        String updateSQL2 = "UPDATE Products SET stockLevel=? WHERE productId=?";
        try(PreparedStatement statement2 = connection.prepareStatement(updateSQL2)){
            statement2.setInt(1, quantity);
            statement2.setString(2, productId);
            
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

    public boolean deleteProduct(Connection connection, String tableProducts, String table2, String productId) {
        boolean resultSet = false;
 
        String deleteSQL2 = String.format(
            "DELETE %1$s, %2$s FROM %1$s INNER JOIN %2$s ON %1$s.productId = %2$s.productId WHERE %1$s.productId = ?",
            tableProducts, table2
        );
        System.out.println(deleteSQL2);
        try(PreparedStatement statement2 = connection.prepareStatement(deleteSQL2)){
            statement2.setString(1, productId);
            System.out.println(statement2);
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
}

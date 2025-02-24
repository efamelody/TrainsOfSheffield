package com2008.views;

import com2008.User;
import com2008.model.DatabaseOperations;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class Edit_Page extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField houseNumberField;
    private JTextField roadNameField;
    private JTextField cityNameField;
    private JTextField postCodeField;
    private String userId; // Store the user ID to identify the user being edited
    private String originalFirstName = "";  // Initialize with appropriate values
    private String originalLastName = "";
    private String originalHouseNumber = "";
    private String originalRoadName = "";
    private String originalCityName = "";
    private String originalPostCode = "";


    public Edit_Page(Connection connection, String userId) {
        this.userId = userId; // Set the user ID

        setTitle("Edit Personal Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        initializeEditPage(connection);
        setVisible(true);
    }

    private void initializeEditPage(Connection connection){


        // Create the frame
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(26, 31, 45, 13);
        contentPane.add(firstNameLabel);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(26, 54, 45, 13);
        contentPane.add(lastNameLabel);
        System.out.println("Before populating text fields");
        firstNameField = new JTextField();
        firstNameField.setBounds(94, 28, 96, 19);
        firstNameField.setText(originalFirstName);
        contentPane.add(firstNameField); //get text
        firstNameField.setColumns(10);

        lastNameField = new JTextField();
        lastNameField.setBounds(94, 51, 96, 19);
        lastNameField.setText(originalLastName);
        contentPane.add(lastNameField);
        lastNameField.setColumns(10);

        JLabel houseNumberLabel = new JLabel("House Number: ");
        houseNumberLabel.setBounds(26, 94, 45, 13);
        contentPane.add(houseNumberLabel);

        houseNumberField = new JTextField();
        houseNumberField.setBounds(94, 91, 96, 19);
        houseNumberField.setText(originalHouseNumber);
        contentPane.add(houseNumberField);
        houseNumberField.setColumns(10);

        JLabel roadNameLabel = new JLabel("Road Name\r\n");
        roadNameLabel.setBounds(26, 117, 45, 13);
        contentPane.add(roadNameLabel);

        JLabel cityNameLabel = new JLabel("City Name:");
        cityNameLabel.setBounds(26, 148, 45, 13);
        contentPane.add(cityNameLabel);

        JLabel postCodeLabel = new JLabel("PostCode");
        postCodeLabel.setBounds(26, 183, 45, 13);
        contentPane.add(postCodeLabel);

        roadNameField = new JTextField();
        roadNameField.setBounds(94, 120, 96, 19);
        roadNameField.setText(originalRoadName);
        contentPane.add(roadNameField);
        roadNameField.setColumns(10);

        cityNameField = new JTextField();
        cityNameField.setBounds(94, 145, 96, 19);
        cityNameField.setText(originalCityName);
        contentPane.add(cityNameField);
        cityNameField.setColumns(10);

        postCodeField = new JTextField();
        postCodeField.setBounds(94, 180, 96, 19);
        postCodeField.setText(originalPostCode);
        contentPane.add(postCodeField);
        postCodeField.setColumns(10);
        System.out.println("After populating text fields");

        DatabaseOperations databaseOperations = new DatabaseOperations();
        User user = databaseOperations.getUserDetails(connection, userId);

        if (user != null) {
            System.out.println("User Not Null");
            // Populate the text fields with user details
            firstNameField.setText(user.getFirstName());
            lastNameField.setText(user.getLastName());
            houseNumberField.setText(user.getHouseNumber());
            roadNameField.setText(user.getRoadName());
            cityNameField.setText(user.getCityName());
            postCodeField.setText(user.getPostCode());

            // Store the original values for future reference
            originalFirstName = user.getFirstName();
            originalLastName = user.getLastName();
            originalHouseNumber = user.getHouseNumber();
            originalRoadName = user.getRoadName();
            originalCityName = user.getCityName();
            originalPostCode = user.getPostCode();
        } else {
            System.out.println("User details are null. Check the getUserDetails method.");
        }

        JButton btnNewButton = new JButton("Edit");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String houseNumber = houseNumberField.getText();
                String roadName = roadNameField.getText();
                String cityName = cityNameField.getText();
                String postCode = postCodeField.getText();

                DatabaseOperations databaseOperations = new DatabaseOperations();

                // Assuming you have a method to update user details in the database
                boolean editStatus = databaseOperations.editUserDetails(connection, userId, firstName,
                        lastName, houseNumber, roadName, cityName, postCode);

                if (editStatus) {
                    JOptionPane.showMessageDialog(null, "Edit Successful");
                    try {
                        // Navigate to another page or perform other actions as needed
                        Customer_Page customerPage = new Customer_Page(connection);
                        customerPage.setVisible(true);
                        dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Edit Failed");
                }
            }
        });
        btnNewButton.setBounds(67, 232, 85, 21);
        contentPane.add(btnNewButton);
    }



}

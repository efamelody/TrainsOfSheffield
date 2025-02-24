package com2008.views;

import com2008.BankDetails;

import com2008.model.DatabaseOperations;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class Edit_BankDetails extends JFrame {
    private JTextField bankNameField;
    private JTextField bankNoField;
    private JTextField expDateField;
    private JTextField cvvField;
    private JTextField cardHolderNameField;
    private String userId; // Store the user ID to identify the user being edited
    private String originalBankName = "";
    private String originalBankNo = "";
    private String originalExpDate = "";
    private String originalCvv = "";
    private String originalCardHolderName = "";

    public Edit_BankDetails(Connection connection, String userId) {
        this.userId = userId; // Set the user ID

        setTitle("Edit Bank Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        initializeEditBankDetailsPage(connection);
        setVisible(true);
    }

    private void initializeEditBankDetailsPage(Connection connection) {
        // Create the frame
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel bankNameLabel = new JLabel("Bank Name:");
        bankNameLabel.setBounds(26, 31, 80, 13);
        contentPane.add(bankNameLabel);

        JLabel bankNoLabel = new JLabel("Bank Number:");
        bankNoLabel.setBounds(26, 54, 80, 13);
        contentPane.add(bankNoLabel);

        JLabel expDateLabel = new JLabel("Expiration Date:");
        expDateLabel.setBounds(26, 94, 100, 13);
        contentPane.add(expDateLabel);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(26, 117, 45, 13);
        contentPane.add(cvvLabel);

        JLabel cardHolderNameLabel = new JLabel("Card Holder Name:");
        cardHolderNameLabel.setBounds(26, 148, 120, 13);
        contentPane.add(cardHolderNameLabel);

        bankNameField = new JTextField();
        bankNameField.setBounds(150, 28, 150, 19);
        bankNameField.setText(originalBankName);
        contentPane.add(bankNameField);
        bankNameField.setColumns(10);

        bankNoField = new JTextField();
        bankNoField.setBounds(150, 51, 150, 19);
        bankNoField.setText(originalBankNo);
        contentPane.add(bankNoField);
        bankNoField.setColumns(10);

        expDateField = new JTextField();
        expDateField.setBounds(150, 91, 150, 19);
        expDateField.setText(originalExpDate);
        contentPane.add(expDateField);
        expDateField.setColumns(10);

        cvvField = new JTextField();
        cvvField.setBounds(150, 114, 150, 19);
        cvvField.setText(originalCvv);
        contentPane.add(cvvField);
        cvvField.setColumns(10);

        cardHolderNameField = new JTextField();
        cardHolderNameField.setBounds(150, 145, 150, 19);
        cardHolderNameField.setText(originalCardHolderName);
        contentPane.add(cardHolderNameField);
        cardHolderNameField.setColumns(10);

        DatabaseOperations databaseOperations = new DatabaseOperations();
        BankDetails bankDetails = databaseOperations.getBankDetails(connection, userId);

        if (bankDetails != null) {
            // Populate the text fields with bank details
            bankNameField.setText(bankDetails.getBankName());
            bankNoField.setText(bankDetails.getBankNo());
            expDateField.setText(bankDetails.getExpDate());
            cvvField.setText(String.valueOf(bankDetails.getCvv()));
            cardHolderNameField.setText(bankDetails.getCardHolderName());

            // Store the original values for future reference
            originalBankName = bankDetails.getBankName();
            originalBankNo = bankDetails.getBankNo();
            originalExpDate = bankDetails.getExpDate();
            originalCvv = String.valueOf(bankDetails.getCvv());
            originalCardHolderName = bankDetails.getCardHolderName();
        } else {
            System.out.println("Bank details are null. Check the getBankDetails method.");
        }

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bankName = bankNameField.getText();
                String bankNo = bankNoField.getText();
                String expDate = expDateField.getText();
                String cvvText = cvvField.getText();
                int cvv = Integer.parseInt(cvvText); // Assuming cvv is an integer
                String cardHolderName = cardHolderNameField.getText();

                DatabaseOperations bankOps = new DatabaseOperations();

                // Assuming you have a method to update bank details in the database
                boolean editStatus = bankOps.editBankDetails(connection, userId, bankName,
                        bankNo, expDate, cvv, cardHolderName);

                if (editStatus) {
                    JOptionPane.showMessageDialog(null, "Edit Successful");
                    try {
                        // Navigate to another page or perform other actions as needed
                        Login_Page loginPage = new Login_Page(connection);
                        loginPage.setVisible(true);
                        dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Edit Failed");
                }
            }
        });
        editButton.setBounds(67, 232, 85, 21);
        contentPane.add(editButton);
    }
}


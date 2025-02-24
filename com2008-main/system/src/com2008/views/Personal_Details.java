package com2008.views;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

import com2008.model.DatabaseOperations;

public class Personal_Details extends JFrame {

	private JPanel contentPane;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField houseNumberField;
	private JTextField roadNameField;
	private JTextField cityNameField;
	private JTextField postcodeField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Personal_Details frame = new Personal_Details();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Personal_Details(Connection connection) {
		setVisible(true);
		setTitle("Personal Details");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setBounds(26, 31, 45, 13);
		contentPane.add(firstNameLabel);

		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setBounds(26, 54, 45, 13);
		contentPane.add(lastNameLabel);

		firstNameField = new JTextField();
		firstNameField.setBounds(94, 28, 96, 19);
		contentPane.add(firstNameField);
		firstNameField.setColumns(10);

		lastNameField = new JTextField();
		lastNameField.setBounds(94, 51, 96, 19);
		contentPane.add(lastNameField);
		lastNameField.setColumns(10);

		JLabel houseNumberLabel = new JLabel("House Number: ");
		houseNumberLabel.setBounds(26, 94, 45, 13);
		contentPane.add(houseNumberLabel);

		houseNumberField = new JTextField();
		houseNumberField.setBounds(94, 91, 96, 19);
		contentPane.add(houseNumberField);
		houseNumberField.setColumns(10);

		JLabel roadNameLabel = new JLabel("Road Name\r\n");
		roadNameLabel.setBounds(26, 117, 45, 13);
		contentPane.add(roadNameLabel);

		JLabel cityNameLabel = new JLabel("City Name:");
		cityNameLabel.setBounds(26, 148, 45, 13);
		contentPane.add(cityNameLabel);

		JLabel postcodeLabel = new JLabel("Postcode");
		postcodeLabel.setBounds(26, 183, 45, 13);
		contentPane.add(postcodeLabel);

		roadNameField = new JTextField();
		roadNameField.setBounds(94, 120, 96, 19);
		contentPane.add(roadNameField);
		roadNameField.setColumns(10);

		cityNameField = new JTextField();
		cityNameField.setBounds(94, 145, 96, 19);
		contentPane.add(cityNameField);
		cityNameField.setColumns(10);

		postcodeField = new JTextField();
		postcodeField.setBounds(94, 180, 96, 19);
		contentPane.add(postcodeField);
		postcodeField.setColumns(10);

		JButton btnNewButton = new JButton("Enter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String firstName = firstNameField.getText();
				String lastName = lastNameField.getText();
				String houseNumber = houseNumberField.getText();
				String roadName = roadNameField.getText();
				String cityName = cityNameField.getText();
				String postcode = postcodeField.getText();

				DatabaseOperations databaseOperations = new DatabaseOperations();

				boolean newPersonalInfoStatus = databaseOperations.newPersonalInfo(connection, firstName,
						lastName, houseNumber, roadName, cityName, postcode);

				if (newPersonalInfoStatus) {
					JOptionPane.showMessageDialog(null, "Registration Successful");
					try {
						Login_Page loginPage = new Login_Page(connection);
						loginPage.setVisible(true); // Set the login page visible
						dispose(); // Dispose the current window
					} catch (SQLException ex) {
						// Handle the exception here (e.g., show an error message)
						ex.printStackTrace();
					}
					dispose();
				}


			}
		});
		btnNewButton.setBounds(67, 232, 85, 21);
		contentPane.add(btnNewButton);
	}

}

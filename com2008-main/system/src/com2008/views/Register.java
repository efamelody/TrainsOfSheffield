package com2008.views;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

import com2008.util.HashedPasswordGenerator;
import com2008.model.DatabaseOperations;


public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField emailField;
	private JPasswordField passwordField;


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Register frame = new Register();
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
	public Register(Connection connection)  {
		setVisible(true);
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(10, 28, 45, 13);
		contentPane.add(emailLabel);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 51, 45, 13);
		contentPane.add(passwordLabel);

		emailField = new JTextField();
		emailField.setBounds(88, 25, 96, 19);
		contentPane.add(emailField);
		emailField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(88, 48, 96, 19);
		contentPane.add(passwordField);
		passwordField.setColumns(10);

		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If first time user, prompt to fill in details Personal_Details
				// If user exists prompt to login

				String email = emailField.getText();
				char[] passwordChars = passwordField.getPassword();
				String hashedPassword = HashedPasswordGenerator.hashPassword(passwordChars);
				DatabaseOperations databaseOperations = new DatabaseOperations();

				//Check if the user already exists in the database
				boolean userExists = databaseOperations.checkIfUserExists(connection,email);

				if (userExists){
					JOptionPane.showMessageDialog(null, "Account already existed");

				} else{
					//User doesn't exist, it's their first time registering
					new Personal_Details(connection);
					dispose();

					//Register new user
					boolean registrationStatus = databaseOperations.register(connection, email, hashedPassword);

					if (registrationStatus) {
						JOptionPane.showMessageDialog(null, "Account Created");
					}
				}

			}
		});

		btnNewButton.setBounds(65, 101, 85, 21);
		contentPane.add(btnNewButton);
	}
}

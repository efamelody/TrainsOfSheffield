package com2008.views;

import java.awt.EventQueue;

import javax.management.RuntimeErrorException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.sql.Connection;

import com2008.util.HashedPasswordGenerator;
import com2008.model.DatabaseOperations;
import com2008.model.Role;
import com2008.model.CurrentUser;

public class Login_Page extends JFrame {

	private JPanel contentPane;
	private JTextField emailField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Login_Page frame = new Login_Page();
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
	public Login_Page(Connection connection) throws SQLException {
		setPreferredSize(new Dimension(400, 400));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel email = new JLabel("Email:");
		email.setFont(new Font("Tahoma", Font.PLAIN, 16));
		email.setBounds(10, 7, 101, 36);
		contentPane.add(email);

		emailField = new JTextField();
		emailField.setBounds(121, 7, 186, 19);
		contentPane.add(emailField);
		emailField.setColumns(10);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String email = emailField.getText();
				char[] passwordChars = passwordField.getPassword();

				DatabaseOperations databaseOperations = new DatabaseOperations();
				boolean loggedin = databaseOperations.verifyLogin(connection, email, passwordChars);

				if (loggedin) {
					Role userRole = CurrentUser.getCurrentUser().getRoles();
					if (userRole.equals(Role.MANAGER)){
						Manager_View newWindow = null;
						try{
							newWindow = new Manager_View(connection);
							dispose();
						} catch (SQLException ex){
							//throw new RuntimeErrorException(ex);
						}
						newWindow.setVisible(true);
					}
					else if (userRole.equals(Role.STAFF)){
						Staff_Page staffPage = new Staff_Page(connection);
						dispose(); // Dispose the current login window
					}
					else {
						try {
							Customer_Page customerPage = new Customer_Page(connection);
							customerPage.setVisible(true); // Set the customer page visible
							dispose(); // Dispose the current login window
						} catch (SQLException ex) {
							ex.printStackTrace(); // Handle SQL exception
						}
					}
					JOptionPane.showMessageDialog(null, "Log In Successful.");
					
				} else{
					JOptionPane.showMessageDialog(null, "Wrong email, or have you registered ?");
				}

			}
		});

		btnNewButton.setBounds(10, 127, 101, 45);
		contentPane.add(btnNewButton);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordLabel.setBounds(10, 61, 101, 29);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(121, 68, 186, 19);
		contentPane.add(passwordField);

		JButton btnNewButton_1 = new JButton("Forgot Password");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Forgot_Password();
				dispose();
			}
		});
		btnNewButton_1.setBounds(121, 127, 109, 45);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Register");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Register(connection);
				dispose();
				// if no data found in database prompt to register
				// Check if email or password is correct
				// If data found, prompt user to login
				// If
			}
		});
		btnNewButton_2.setBounds(240, 127, 101, 45);
		contentPane.add(btnNewButton_2);


	}
}

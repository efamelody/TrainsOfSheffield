package com2008.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com2008.model.CurrentUser;
import com2008.model.DatabaseOperations;
import com2008.model.DatabaseConnectionHandler;
import com2008.model.Role;
import javax.swing.JScrollPane;


public class Manager_View extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userSelected;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// 	EventQueue.invokeLater(new Runnable() {
	// 		public void run() {
	// 			DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
	// 			try {
	// 				databaseConnectionHandler.openConnection();
	// 				Manager_View frame = new Manager_View(databaseConnectionHandler.getConnection());
	// 				frame.setVisible(true);
	// 			} catch (Exception e) {
	// 				databaseConnectionHandler.closeConnection();
	// 				throw new RuntimeException(e);
	// 			}
	// 		}
	// 	});
	// }
	private void DisplayStaffList(Connection connection){
		DefaultTableModel model = new DefaultTableModel();
		table_1 = new JTable(model);
		model.addColumn("Email");
		model.addColumn("First Name");
		model.addColumn("Last Name");

		DatabaseOperations databaseOperations = new DatabaseOperations();
		try (ResultSet resultSet = databaseOperations.getStaffList(connection)) {
			while (resultSet.next()){
				model.addRow(new Object[]{
					resultSet.getString("email"),
					resultSet.getString("firstName"),
					resultSet.getString("lastName")
				});
			}
		}catch (SQLException e){
			e.printStackTrace();
		};

		//resultSet.close();
		//connection.close();

		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(33, 39, 307, 144);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table_1);
	}
	/**
	 * Create the frame.
	 */
	public Manager_View(Connection connection) throws SQLException {
		setTitle("Staff List");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		DisplayStaffList(connection);

		userSelected = new JTextField();
		userSelected.setBounds(139, 215, 96, 19);
		contentPane.add(userSelected);
		userSelected.setColumns(10);
		
		// table = new JTable();
		// table.setBounds(0, 31, 376, 166);
		// contentPane.add(table);
		// Connection con = 
		// table.setModel();
		JButton btnNewButton = new JButton("Promote");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = userSelected.getText().strip();

				DatabaseOperations databaseOperations = new DatabaseOperations();
				boolean userExists = databaseOperations.checkIfUserExists(connection, email);
				String userId = databaseOperations.getUserIdByUsername(connection, email);
				Role userRole = databaseOperations.getRolesForUserId(connection, userId);

				
				if (userExists){
					
					if (userRole.equals(Role.STAFF)) {
						JOptionPane.showMessageDialog(null, email + " is already a staff, can't be promoted.");
					} else if (userRole.equals(Role.CUSTOMER)){
						// Ask for confirmation
						int dialogResult = JOptionPane.showConfirmDialog(null,
								"Are you sure you want to promote " + email + " to Staff?", "Confirmation",
								JOptionPane.YES_NO_OPTION);

						// Check the user's choice
						if (dialogResult == JOptionPane.YES_OPTION) {
							// User confirmed, promote the selected user to Moderator
							databaseOperations.promoteToStaff(connection, email);
							JOptionPane.showMessageDialog(null, email + " has been promoted to Staff.");
							DisplayStaffList(connection);
						} else {
							// User canceled the action
							JOptionPane.showMessageDialog(null, "Promotion canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
						}
					} else {
							JOptionPane.showMessageDialog(null, "You can't promote yourself", "Canceled", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "User doesn't exist, check and retype the email.");
				}
			}
		});

		btnNewButton.setBounds(245, 214, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Staff");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Staff_Page staffPage = new Staff_Page(connection);
					staffPage.setVisible(true); // Set the customer page visible
					dispose(); // Dispose the current login window
				} catch (RuntimeException ex) {
					ex.printStackTrace(); // Handle SQL exception
				}
				dispose();
			}
		});
		btnNewButton_1.setBounds(340, 10, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JButton btnDemote = new JButton("Demote");
		btnDemote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = userSelected.getText().strip();

				DatabaseOperations databaseOperations = new DatabaseOperations();
				boolean userExists = databaseOperations.checkIfUserExists(connection, email);
				String userId = databaseOperations.getUserIdByUsername(connection, email);
				Role userRole = databaseOperations.getRolesForUserId(connection, userId);

				if (userExists){
					if (userRole.equals(Role.CUSTOMER)) {
						JOptionPane.showMessageDialog(null, email + " is already a customer, can't be demoted.");
					} else if (userRole.equals(Role.STAFF)){
							// Ask for confirmation
						int dialogResult = JOptionPane.showConfirmDialog(null,
								"Are you sure you want to demote " + email + " to Cusomter?", "Confirmation",
								JOptionPane.YES_NO_OPTION);

						// Check the user's choice
						if (dialogResult == JOptionPane.YES_OPTION) {
							// User confirmed, promote the selected user to Moderator
							databaseOperations.demoteToCustomer(connection, email);
							JOptionPane.showMessageDialog(null, email + " has been demoted to Customer.");
							DisplayStaffList(connection);
						} else {
							// User canceled the action
							JOptionPane.showMessageDialog(null, "Promotion canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "You can't demote yourself", "Canceled", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "User doesn't exist, check and retype the email.");
				}
			}
		});
		btnDemote.setBounds(340, 214, 85, 21);
		contentPane.add(btnDemote);
		
		JButton btnNewButton_2 = new JButton("Log Out");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Login_Page loginPage = new Login_Page(connection);
					dispose();
				} catch (SQLException ex){
					
				}
			}
		});
		btnNewButton_2.setBounds(10, 10, 85, 21);
		contentPane.add(btnNewButton_2);
		
	}

	private boolean checkUserIdentity(Role role){
		Role roleOfCurrentUser = CurrentUser.getCurrentUser().getRoles();
		if (roleOfCurrentUser.equals(role)){
			return true;
		}
		return false;

	}
}

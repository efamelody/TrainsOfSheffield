package com2008.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com2008.model.CurrentUser;
import com2008.model.Role;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Order_History extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JButton btnNewButton_2;
	private JLabel lblNewLabel_1;
	private JTextField textField_1;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Order_History frame = new Order_History();
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
	public Order_History(Connection connection) throws SQLException {
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Role currentUserRole = CurrentUser.getCurrentUser().getRoles();		
		
		if (currentUserRole.equals(Role.CUSTOMER)){
			setTitle("Order History");
			JButton btnNewButton = new JButton("Back");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Customer_Page customerPage = new Customer_Page(connection);
						customerPage.setVisible(true); // Set the customer page visible
						dispose(); // Dispose the current login window
					} catch (SQLException ex) {
						ex.printStackTrace(); // Handle SQL exception
					}
					dispose();
				}
			});
			btnNewButton.setBounds(327, 232, 53, 21);
			contentPane.add(btnNewButton);
		} else {
			setTitle("Pending Orders");
			btnNewButton_2 = new JButton("Back");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Staff_Page staffPage = new Staff_Page(connection);
					dispose();
				}
			});
			btnNewButton_2.setBounds(248, 232, 85, 21);
			contentPane.add(btnNewButton_2);
			
			btnNewButton_3 = new JButton("Fulfill");
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//TODO
				}
			});
			btnNewButton_3.setBounds(353, 232, 85, 21);
			contentPane.add(btnNewButton_3);
			
			btnNewButton_4 = new JButton("Delete");
			btnNewButton_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO
				}
			});
			btnNewButton_4.setBounds(461, 232, 85, 21);
			contentPane.add(btnNewButton_4);
			
		}

		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 294, 201);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Order ID");
		lblNewLabel.setBounds(322, 28, 78, 13);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(322, 51, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("View Details");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{ 
					Order_Details orderDetailsPage = new Order_Details(connection);
				} catch (SQLException ex){
				}
			}
		});
		btnNewButton_1.setBounds(428, 50, 96, 21);
		contentPane.add(btnNewButton_1);
		
		lblNewLabel_1 = new JLabel("Date Created");
		lblNewLabel_1.setBounds(322, 80, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(322, 113, 96, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		
		
		
	}
}

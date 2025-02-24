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


public class Order_Details extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JTextField textField;
	private JLabel lblNewLabel_1;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Order_Details frame = new Order_Details();
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
	public Order_Details(Connection connection) throws SQLException {
		setVisible(true);
		setTitle("Order Details");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 574, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		Role userRole = CurrentUser.getCurrentUser().getRoles();
			if ( (userRole.equals(Role.MANAGER) || (userRole.equals(Role.STAFF)) ) ){
				JButton btnNewButton = new JButton("Fulfill");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TODO
						dispose();
					}
				});
				btnNewButton.setBounds(287, 232, 85, 21);
				contentPane.add(btnNewButton);
			
				JButton btnNewButton_1 = new JButton("Delete");
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//TODO
						dispose();
					}
				});
				btnNewButton_1.setBounds(401, 232, 85, 21);
				contentPane.add(btnNewButton_1);
			}


		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 34, 300, 179);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		lblNewLabel = new JLabel("Order ID");
		lblNewLabel.setBounds(332, 36, 45, 13);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(332, 54, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Date Created");
		lblNewLabel_1.setBounds(332, 91, 80, 13);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(332, 116, 96, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(332, 175, 96, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Total Price");
		lblNewLabel_2.setBounds(332, 145, 80, 19);
		contentPane.add(lblNewLabel_2);
		
		
	}
}

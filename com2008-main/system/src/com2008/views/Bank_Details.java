 package com2008.views;

 import com2008.model.DatabaseOperations;
 import com2008.model.OrdersDAO;

 import java.awt.EventQueue;

 import javax.swing.*;
 import javax.swing.border.EmptyBorder;
 import java.awt.Font;
 import java.awt.event.ActionListener;
 import java.awt.event.ActionEvent;
 import java.sql.SQLException;
 import java.sql.Connection;
 import java.util.Date;


 public class Bank_Details extends JFrame {

 	private JPanel contentPane;
 	private JTextField textField;
 	private JTextField textField_1;
 	private JTextField textField_2;
 	private JLabel lblNewLabel_3;
 	private JTextField textField_3;
 	private JLabel lblNewLabel_4;
 	private JTextField textField_4;

 	/**
 	 * Launch the application.
 	 */
 //	public static void main(String[] args) {
 //		EventQueue.invokeLater(new Runnable() {
 //			public void run() {
 //				try {
 //					Bank_Details frame = new Bank_Details();
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
 	public Bank_Details(Connection connection) throws SQLException {
 		setVisible(true);
 		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		setBounds(100, 100, 450, 300);
 		contentPane = new JPanel();
 		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

 		setContentPane(contentPane);
 		contentPane.setLayout(null);
		
 		JLabel lblNewLabel = new JLabel("Bank Name:");
 		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
 		lblNewLabel.setBounds(25, 29, 109, 20);
 		contentPane.add(lblNewLabel);
		
 		JLabel lblNewLabel_1 = new JLabel("Expiry Date: ");
 		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
 		lblNewLabel_1.setBounds(25, 120, 109, 20);
 		contentPane.add(lblNewLabel_1);
		
 		JLabel lblNewLabel_2 = new JLabel("Security Code:");
 		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
 		lblNewLabel_2.setBounds(25, 150, 109, 20);
 		contentPane.add(lblNewLabel_2);
		
 		textField = new JTextField();
 		textField.setBounds(194, 32, 131, 19);
 		contentPane.add(textField);
 		textField.setColumns(10);
		
 		textField_1 = new JTextField();
 		textField_1.setBounds(194, 123, 131, 19);
 		contentPane.add(textField_1);
 		textField_1.setColumns(10);
		
 		textField_2 = new JTextField();
 		textField_2.setBounds(194, 153, 131, 19);
 		contentPane.add(textField_2);
 		textField_2.setColumns(10);

 		JButton btnNewButton = new JButton("Enter");
 		 btnNewButton.addActionListener(new ActionListener() {
 		 	public void actionPerformed(ActionEvent e) {
 		 		String expDate = textField_1.getText();
 		 		String bankName = textField.getText();
 		 		String cvvString = textField_2.getText();
 		 		String bankNo = textField_3.getText();
 		 		String cardHolderName = textField_4.getText();

 		 		OrdersDAO ordersDAO = new OrdersDAO();

 		 		try {
 		 			int cvv = Integer.parseInt(cvvString);

 		 			DatabaseOperations databaseOperations = new DatabaseOperations();

 		 			if (!ordersDAO.isValidBankNo(bankNo)) {
 		 				JOptionPane.showMessageDialog(null, "Bank Card Number should be a 16-digit number.");
 		 			} else if (!ordersDAO.isValidExpDate(expDate)) {
 		 				JOptionPane.showMessageDialog(null, "Expiry Date should be in the format mm/yy.");
 		 			} else if (!ordersDAO.isValidCvv(cvv)) {
 		 				JOptionPane.showMessageDialog(null, "CVV should be a 3-digit number.");
 		 			} else {
 		 				boolean bankDetailStatus = databaseOperations.newBankingInfo(connection, bankName,
 		 						cardHolderName, bankNo, expDate, cvv);

 		 				if (bankDetailStatus) {
 		 					JOptionPane.showMessageDialog(null, "Bank Details added.");
 		 					ordersDAO.confirmOrder(connection);
 		 					JOptionPane.showMessageDialog(null, "Order Confirmed");
 		 					Customer_Page customerPage = new Customer_Page(connection);
 		 					customerPage.setVisible(true); // Set the customer page visible
 		 					dispose(); // Dispose the current login window
 		 				} else {
 		 					JOptionPane.showMessageDialog(null, "Failed to add Bank Details.");
 		 				}
 		 			}
 		 		} catch (NumberFormatException ex) {
 		 			JOptionPane.showMessageDialog(null, "CVV should be a valid integer.");
 		 		} catch (SQLException ex) {
 		 			ex.printStackTrace(); // Handle SQL exception
 		 			JOptionPane.showMessageDialog(null, "Error while adding Bank Details.");
 		 		}

// 				String expDate = textField_1.getText();
// 				String bankName = textField.getText();
// 				String cvvString = textField_2.getText();
// 				String bankNo = textField_3.getText();
// 				String cardHolderName = textField_4.getText();
//
// 				int cvv = Integer.parseInt(cvvString);
//
// 				DatabaseOperations databaseOperations = new DatabaseOperations();
// 				boolean validBankNo = databaseOperations.isValidBankNo(bankNo);
// 				boolean validExpDate =databaseOperations.isValidExpDate(expDate);
// 				boolean validCvv = databaseOperations.isValidCvv(cvv);
//
// 				boolean bankDetailStatus = databaseOperations.newBankingInfo(connection, bankName,
// 						cardHolderName, bankNo, expDate, cvv);
//
// 				if (!validBankNo){
// 					JOptionPane.showMessageDialog(null, "Bank Card Number should be 16 " +
// 							"digit number only.");
// 				} else if (!validExpDate){
// 					JOptionPane.showMessageDialog(null, "Expiry Date should be " +
// 							"in the format mm/yy");
// 				} else if (!validCvv) {
// 					JOptionPane.showMessageDialog(null, "Cvv should be 3 digit number");
// 				} else {
// 					try{
// 						JOptionPane.showMessageDialog(null, "Bank Details added.");
// 						Customer_Page customerPage = new Customer_Page(connection);
// 						customerPage.setVisible(true); // Set the customer page visible
// 						dispose(); // Dispose the current login window
//
// 					} catch (SQLException ex) {
// 						ex.printStackTrace(); // Handle SQL exception
// 					}
// 					dispose();
// 				}
//
 			}
 		});
 		btnNewButton.setBounds(85, 185, 140, 43);
 		contentPane.add(btnNewButton);

 		lblNewLabel_3 = new JLabel("Bank Card Number:");
 		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
 		lblNewLabel_3.setBounds(25, 90, 159, 20);
 		contentPane.add(lblNewLabel_3);

 		textField_3 = new JTextField();
 		textField_3.setBounds(194, 93, 131, 19);
 		contentPane.add(textField_3);
 		textField_3.setColumns(10);

 		lblNewLabel_4 = new JLabel("Card Holder Name:");
 		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
 		lblNewLabel_4.setBounds(25, 60, 159, 20);
 		contentPane.add(lblNewLabel_4);

 		textField_4 = new JTextField();
 		textField_4.setBounds(194, 64, 131, 19);
 		contentPane.add(textField_4);
 		textField_4.setColumns(10);
 	}

 }

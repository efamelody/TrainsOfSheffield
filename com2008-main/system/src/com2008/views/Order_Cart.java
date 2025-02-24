 package com2008.views;

 import java.awt.EventQueue;

 import javax.swing.*;
 import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
 import java.awt.event.ActionEvent;
 import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com2008.UserSession;
import com2008.model.DatabaseOperations;
 import com2008.model.OrdersDAO;
import com2008.model.ProductsDAO;


 public class Order_Cart extends JFrame {

 	private static final long serialVersionUID = 1L;
 	private JPanel contentPane;
 	private JTable table;
	private int orderLineId;
	private JTextField orderLineIdField;
	private JTextField quantityField;
	private int newQuantity;
	private double newPrice;
	private JTextField productIdField;
 	/**
 	 * Launch the application.
 	 */
 //	public static void main(String[] args) {
 //		EventQueue.invokeLater(new Runnable() {
 //			public void run() {
 //				try {
 //					Order_Cart frame = new Order_Cart();
 //					frame.setVisible(true);
 //				} catch (Exception e) {
 //					e.printStackTrace();
 //				}
 //			}
 //		});
 //	}
	private void DisplayOrderLineList(Connection connection, String userId){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		OrdersDAO databaseOperations = new OrdersDAO();
		try (ResultSet resultSet = databaseOperations.getOrderLineList(connection, userId)) {
			while (resultSet.next()){
				//orderLineId = resultSet.getInt("cartId");
				
				model.addRow(new Object[]{
					resultSet.getString("productId"),
					resultSet.getString("brandName"),
					resultSet.getString("productName"),
					resultSet.getString("quantity"),
					resultSet.getString("orderLineCost"),
					resultSet.getInt("cartId")
				});
			}
		}catch (SQLException e){
			e.printStackTrace();
		};
	}
 	/**
 	 * Create the frame.
 	 */
 	public Order_Cart(Connection connection) throws SQLException {
 		setVisible(true);
 		setTitle("Shopping Cart");
 		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		setBounds(100, 100, 689, 360);
 		contentPane = new JPanel();
 		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

 		setContentPane(contentPane);
 		contentPane.setLayout(null);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Product Code", "Brand Name", "Product Name", "Quantity", "Total Cost", "Order Line ID"}
		){
			public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				}
		});
		
 		JScrollPane scrollPane = new JScrollPane();
 		scrollPane.setBounds(10, 55, 360, 146);
 		contentPane.add(scrollPane);
		
 		
 		scrollPane.setViewportView(table);
		
 		DatabaseOperations databaseOperations = new DatabaseOperations();
		ProductsDAO productsDAO = new ProductsDAO();
 		OrdersDAO ordersDAO = new OrdersDAO();
		
		String currentUserId = UserSession.getCurrentUserId();

		DisplayOrderLineList(connection, currentUserId);

 		JButton btnNewButton = new JButton("Confirm");
 		btnNewButton.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				boolean firstTimeOrder = ordersDAO.checkFirstTimeOrder(connection);
 				boolean emptyShoppingCart = !ordersDAO.checkOrderExists(connection);

 				try {
 					if (emptyShoppingCart){
 						JOptionPane.showMessageDialog(null, "Shopping Cart is empty.");
 					} else{
 						if (firstTimeOrder){
 							Bank_Details bankDetails = new Bank_Details(connection);
 							bankDetails.setVisible(true);
 						} else {
 							ordersDAO.confirmOrder(connection);
 							JOptionPane.showMessageDialog(null, "Order Confirmed");

 						}
 					}

 //					Customer_Page customerPage = new Customer_Page(connection);
 //					customerPage.setVisible(true); // Set the customer page visible
 					dispose(); // Dispose the current login window
 				} catch (SQLException ex) {
 					ex.printStackTrace(); // Handle SQL exception
 				}
 				dispose();
 				// if cart empty- jmessage prompt it is empty, back to shopping view
 				// check database if bank details exist else prompt to bank details page
 //				new Bank_Details();
 //				dispose();
 			}
 		});
 		btnNewButton.setBounds(341, 232, 85, 21);
 		contentPane.add(btnNewButton);

		
		
 		JButton btnNewButton_2 = new JButton("Update Banking Details");
 		btnNewButton_2.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				try {
 					Bank_Details bankDetails = new Bank_Details(connection);
 					bankDetails.setVisible(true); // Set the customer page visible
 					dispose(); // Dispose the current login window
 				} catch (SQLException ex) {
 					ex.printStackTrace(); // Handle SQL exception
 				}
 				dispose();
 			}
 		});
 		btnNewButton_2.setBounds(194, 232, 137, 21);
 		contentPane.add(btnNewButton_2);
		
 		JButton btnNewButton_1 = new JButton("Delete");
 		btnNewButton_1.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				// Delete button of orderline
				orderLineId = Integer.parseInt(orderLineIdField.getText());
				
				int dialogResult = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to delete this line ?", "Confirmation",
						JOptionPane.YES_NO_OPTION);

				// Check the user's choice
				if (dialogResult == JOptionPane.YES_OPTION) {
					ordersDAO.deleteOrderLine(connection, orderLineId);
					DisplayOrderLineList(connection, currentUserId);
				} else {
					// User canceled the action
					JOptionPane.showMessageDialog(null, "Action canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
				}
 			}
 		});
 		btnNewButton_1.setBounds(508, 77, 105, 21);
 		contentPane.add(btnNewButton_1);
 		
 		JLabel orderLineIdLabel = new JLabel("Order Line Id");
 		orderLineIdLabel.setBounds(397, 55, 93, 13);
 		contentPane.add(orderLineIdLabel);
 		
 		orderLineIdField = new JTextField();
 		orderLineIdField.setBounds(394, 78, 96, 19);
 		contentPane.add(orderLineIdField);
 		orderLineIdField.setColumns(10);
 		
 		JLabel lblNewLabel = new JLabel("Quantity");
 		lblNewLabel.setBounds(397, 141, 45, 13);
 		contentPane.add(lblNewLabel);
 		
 		quantityField = new JTextField();
 		quantityField.setBounds(397, 164, 96, 19);
 		contentPane.add(quantityField);
 		quantityField.setColumns(10);
 		
 		JButton updateButton = new JButton("Update Quantity");
 		updateButton.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
				int orderLineId = Integer.parseInt(orderLineIdField.getText());
				if (AddNewProduct.isInteger(quantityField.getText())){
					newQuantity = Integer.parseInt(quantityField.getText());
					String productId = productIdField.getText();
					try (ResultSet resultSet2 = productsDAO.getRetailPrice(connection, productId)){
						if (resultSet2.next()){
							double newPrice = resultSet2.getDouble("retailPrice") * newQuantity;
							int dialogResult = JOptionPane.showConfirmDialog(null,
								"Are you sure you want to update " + productId + " to" + newQuantity +"?", "Confirmation",
								JOptionPane.YES_NO_OPTION);

							// Check the user's choice
							if (dialogResult == JOptionPane.YES_OPTION) {
								// User confirmed, promote the selected user to Moderator
								ordersDAO.updateQuantityPrice(connection, orderLineId, newQuantity, newPrice);
								JOptionPane.showMessageDialog(null, "Quantity has been updated.");
								DisplayOrderLineList(connection, currentUserId);
							} else {
								// User canceled the action
								JOptionPane.showMessageDialog(null, "Action canceled.", "Canceled", JOptionPane.WARNING_MESSAGE);
							}
						}
					} catch (SQLException ex){
						ex.printStackTrace();
					}	
				}
							
 			}
 		});
 		updateButton.setBounds(508, 163, 105, 21);
 		contentPane.add(updateButton);
 		
 		JLabel lblNewLabel_1 = new JLabel("Product ID");
 		lblNewLabel_1.setBounds(397, 103, 93, 13);
 		contentPane.add(lblNewLabel_1);
 		
 		productIdField = new JTextField();
 		productIdField.setBounds(397, 124, 96, 19);
 		contentPane.add(productIdField);
 		productIdField.setColumns(10);
 		
 		JButton btnNewButton_3 = new JButton("Back");
 		btnNewButton_3.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
				try{
					new Customer_Page(connection);
					dispose();
				} catch (SQLException ex){
					ex.printStackTrace();
				}
 			}
 		});
 		btnNewButton_3.setBounds(102, 232, 85, 21);
 		contentPane.add(btnNewButton_3);
 	}
 }

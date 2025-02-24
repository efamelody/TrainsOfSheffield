package com2008.views;

import java.awt.Color;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.List;
import java.awt.Button;
import javax.swing.JMenuBar;
import javax.swing.JCheckBoxMenuItem;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

import com2008.UserSession;
import com2008.model.CurrentUser;
import com2008.model.Role;
import com2008.model.DatabaseOperations;
import com2008.model.OrdersDAO;
import com2008.model.ProductsDAO;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Customer_Page extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField productNameField;
	private JTextField productIdField;
	private JTextField quantityField;
	private JTextField brandNameField;
	private String productId;
	private int quantityToBuy;

	private void DisplayProductList(Connection connection){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		ProductsDAO databaseOperations = new ProductsDAO();
		try (ResultSet resultSet = databaseOperations.getProductList(connection)) {
			while (resultSet.next()){
				model.addRow(new Object[]{
					resultSet.getString("productId"),
					resultSet.getString("brandName"),
					resultSet.getString("productName"),
					resultSet.getString("retailPrice"),
					resultSet.getString("modellingGauge"),
					resultSet.getString("productType"),
					resultSet.getString("stockLevel")
				});
			}
		}catch (SQLException e){
			e.printStackTrace();
		};
	}

	private void DisplayFilteredProductList(Connection connection, String category){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		ProductsDAO databaseOperations = new ProductsDAO();
		try (ResultSet resultSet = databaseOperations.getFilterResultSet(connection, category)) {
			while (resultSet.next()){
				model.addRow(new Object[]{
					resultSet.getString("productId"),
					resultSet.getString("brandName"),
					resultSet.getString("productName"),
					resultSet.getString("retailPrice"),
					resultSet.getString("modellingGauge"),
					resultSet.getString("productType"),
					resultSet.getString("stockLevel")
				});
			}
		}catch (SQLException e){
			e.printStackTrace();
		};
	}
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Customer_Page frame = new Customer_Page();
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
	public Customer_Page(Connection connection) throws SQLException{
		setVisible(true);
		setTitle("Customer ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Product Code", "Brand Name", "Product Name", "Retail Price", "Modelling Gauge", "Product Type", "Quantity"}
		){
			public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				}
		});
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 54, 369, 156);
		contentPane.add(scrollPane);

		DisplayProductList(connection);
		
		Role currentUserRole = CurrentUser.getCurrentUser().getRoles();

		String currentUserId = UserSession.getCurrentUserId();

		JButton btnNewButton_1 = new JButton("Change details");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Edit_Page editPage = new Edit_Page(connection, currentUserId);
				editPage.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(120, 374, 99, 21);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Log Out");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				new Login_Page();
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
		});
		btnNewButton_1_1.setBounds(10, 21, 99, 21);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("Shopping Cart");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
				 	Order_Cart orderCart = new Order_Cart(connection);
				 	orderCart.setVisible(true); // Set the customer page visible
				 	dispose(); // Dispose the current login window
				 } catch (SQLException ex) {
				 	ex.printStackTrace(); // Handle SQL exception
				 }
				dispose();
			}
		});
		btnNewButton_1_1_1.setBounds(421, 371, 99, 21);
		contentPane.add(btnNewButton_1_1_1);
		
		JButton btnNewButton_1_2 = new JButton("Order History");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Order_History orderHistory = new Order_History(connection);
					orderHistory.setVisible(true); // Set the customer page visible
					dispose(); // Dispose the current login window
				} catch (SQLException ex) {
					ex.printStackTrace(); // Handle SQL exception
				}
				dispose();
			}
		});
		btnNewButton_1_2.setBounds(312, 371, 99, 21);
		contentPane.add(btnNewButton_1_2);
		
		JLabel lblNewLabel = new JLabel("Product Name");
		lblNewLabel.setBounds(404, 139, 75, 13);
		contentPane.add(lblNewLabel);
		
		productNameField = new JTextField();
		productNameField.setEditable(false);
		productNameField.setBounds(400, 152, 96, 19);
		contentPane.add(productNameField);
		productNameField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Product Code");
		lblNewLabel_1.setBounds(400, 54, 75, 13);
		contentPane.add(lblNewLabel_1);
		
		productIdField = new JTextField();
		productIdField.setBounds(400, 77, 96, 19);
		contentPane.add(productIdField);
		productIdField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Quantity to buy");
		lblNewLabel_2.setBounds(399, 181, 95, 13);
		contentPane.add(lblNewLabel_2);
		
		quantityField = new JTextField();
		quantityField.setBounds(400, 204, 96, 19);
		contentPane.add(quantityField);
		quantityField.setColumns(10);
		
		JButton btnNewButton = new JButton("Add to cart");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productId = productIdField.getText();
				System.out.println(productId);
				if (AddNewProduct.isInteger(quantityField.getText())){
					quantityToBuy = Integer.parseInt(quantityField.getText());
					System.out.println(quantityToBuy);
					ProductsDAO databaseOperation = new ProductsDAO();
					ResultSet resultSet = databaseOperation.getQuantity(connection, productId);
					try {
						if (resultSet.next()){
							int stockLevel = resultSet.getInt("stockLevel");
							System.out.println(stockLevel);
							if (quantityToBuy > stockLevel){
								JOptionPane.showMessageDialog(null, "Not enough stock to sell");
							} else{
								System.out.println("else entered");
								try (ResultSet resultSet2 = databaseOperation.getRetailPrice(connection, productId)){
									if (resultSet2.next()){
										System.out.println("executed");
										double orderLineCosts = resultSet2.getDouble("retailPrice") * quantityToBuy;
										OrdersDAO ordersDAO = new OrdersDAO();
										ordersDAO.createNewOrderLine(connection, currentUserId, productId, quantityToBuy, orderLineCosts);
									}
								} catch (SQLException ex){
									ex.printStackTrace();
								}

								//databaseOperation.createNewOrderLine()
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				} else{
					JOptionPane.showMessageDialog(null, "Please enter a valid quantity");
				}
				
				//TODO
				
			}
		});
		btnNewButton.setBounds(390, 244, 85, 21);
		contentPane.add(btnNewButton);
		
		JComboBox category = new JComboBox();
		category.addItem("All");
		category.addItem("Train Set");
		category.addItem("Track Pack");
		category.addItem("Locomotive");
		category.addItem("Rolling Stock");
		category.addItem("Track");
		category.addItem("Controller");

		category.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedCategory = category.getSelectedItem().toString();
				switch (selectedCategory) {
					case "All":
						DisplayProductList(connection);
						break;
					case "Train Set":
						DisplayFilteredProductList(connection, "TRAINSET");
						break;
					case "Track Pack":
						DisplayFilteredProductList(connection, "TRACKPACK");
						break;
					case "Locomotive":
						DisplayFilteredProductList(connection, "LOCOMOTIVE");
						break;
					case "Rolling Stock":
						DisplayFilteredProductList(connection, "ROLLINGSTOCK");
						break;
					case "Track":
						DisplayFilteredProductList(connection, "TRACK");
						break;
					case "Controller":
						DisplayFilteredProductList(connection, "CONTROLLER");
						break;
					// Add more cases if there are more categories
					default:
						// Optional: handle unexpected category
						break;
				}
		
				// Refresh the GUI
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
		category.setBounds(142, 21, 160, 21);
		contentPane.add(category);
		
		JLabel lblNewLabel_3 = new JLabel("Brand Name");
		lblNewLabel_3.setBounds(404, 106, 45, 13);
		contentPane.add(lblNewLabel_3);
		
		brandNameField = new JTextField();
		brandNameField.setEditable(false);
		brandNameField.setBounds(400, 123, 96, 19);
		contentPane.add(brandNameField);
		brandNameField.setColumns(10);
		if (currentUserRole.equals(Role.STAFF) || currentUserRole.equals(Role.MANAGER)){
			// Appear only if role is staff
			JButton btnNewButton_1_1_2 = new JButton("Staff");
			btnNewButton_1_1_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Staff_Page staffPage = new Staff_Page(connection);
					dispose();
				}
			});
			btnNewButton_1_1_2.setBounds(445, 17, 99, 21);
			contentPane.add(btnNewButton_1_1_2);
		}
		
		JComboBox cb = new JComboBox();
		//JTable table = new JTable();
		
	}
}

package com2008.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.log.Log;

import com2008.Products;
import com2008.model.CurrentUser;
import com2008.model.DatabaseOperations;
import com2008.model.ProductsDAO;
import com2008.model.ProductsE;
import com2008.model.Role;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Staff_Page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField productIdField;
	private JTextField productNameField;
	private JTextField productBrandField;
	private JTextField quantityField;
	private String productId;
	private int stockLevel;
	private int quantityIncrease;

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
	
	public void mouseClicked(java.awt.event.MouseEvent evt) {
		int row = table.getSelectedRow();
		if (row >= 0){
			System.out.println("Is it working");
			DefaultTableModel modelClicked = (DefaultTableModel) table.getModel();
			productIdField.setText(modelClicked.getValueAt(row, 0).toString());
			productBrandField.setText(modelClicked.getValueAt(row, 1).toString());
			productNameField.setText(modelClicked.getValueAt(row, 2).toString());
			productId = productIdField.getText();
			String quantityInStr = quantityField.getText();
			quantityIncrease = Integer.parseInt(quantityInStr);
			//quantityIncrease = modelClicked.getValueAt(row, 6);
			// 
		}
	}
	/**
	 * Create the frame.
	 */
	public Staff_Page(Connection connection) {
		setVisible(true);
		setTitle("Product Inventory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 452);
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
		scrollPane.setBounds(25, 54, 491, 170);
		contentPane.add(scrollPane);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseClicked(e);
			}
		});
		
		DisplayProductList(connection);


		Role userRole = CurrentUser.getCurrentUser().getRoles();

		//DisplayProductList(connection);

		JButton btnNewButton = new JButton("Increase Stock");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ToBE REOMOVED
				productId = productIdField.getText();

				if (AddNewProduct.isInteger(quantityField.getText())){
					quantityIncrease = Integer.parseInt(quantityField.getText());
					ProductsDAO databaseOperations = new ProductsDAO();
					int dialogResult = JOptionPane.showConfirmDialog(null, "Confirm to increase " + productId + "by " + quantityField.getText() + "?", 
						"Confirmation", JOptionPane.YES_NO_OPTION);
						if (dialogResult == JOptionPane.YES_OPTION){
							System.out.println(productId);
							try (ResultSet resultSet = databaseOperations.getQuantity(connection, productId)){
								if (resultSet.next()){
									stockLevel = resultSet.getInt("stockLevel");
									stockLevel += quantityIncrease;
									System.out.println(stockLevel);
								}
							} catch (SQLException ex){
								ex.printStackTrace();
							}
							databaseOperations.updateStock(connection, productId, stockLevel);
							//productId
							DisplayProductList(connection);
						} else{
							JOptionPane.showMessageDialog(null, "Increase cancelled", "Canceled", JOptionPane.WARNING_MESSAGE);
						}
				} else {
					JOptionPane.showMessageDialog(null, " Please enter an integer for Quantity");
				}
			}
		});
		btnNewButton.setBounds(653, 202, 92, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Pending Orders");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Order_History pendingOrders = new Order_History(connection);
					dispose();
				} catch (SQLException ex){
					
				}
				
			}
		});
		btnNewButton_1.setBounds(370, 234, 102, 25);
		contentPane.add(btnNewButton_1);
		
		if (userRole.equals(Role.MANAGER)){
			JButton btnNewButton_2 = new JButton("Manager");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						Manager_View newWindow = new Manager_View(connection);
						dispose();
					} catch (SQLException ex){
						//throw new RuntimeErrorException(ex);
					}
				}	
			});
			btnNewButton_2.setBounds(317, 10, 85, 21);
			contentPane.add(btnNewButton_2);
		}
		
		
		JButton btnNewButton_3 = new JButton("Customer");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Customer_Page customerPage = new Customer_Page(connection);
					dispose();
				} catch (SQLException ex) {
				}
			}
		});
		btnNewButton_3.setBounds(682, 10, 85, 21);
		contentPane.add(btnNewButton_3);
		
		JLabel lblNewLabel = new JLabel("Product ID");
		lblNewLabel.setBounds(547, 43, 63, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Product Name");
		lblNewLabel_1.setBounds(547, 127, 85, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Product Brand");
		lblNewLabel_2.setBounds(547, 87, 85, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Quantity To Increase");
		lblNewLabel_3.setBounds(547, 179, 123, 13);
		contentPane.add(lblNewLabel_3);
		
		productIdField = new JTextField();
		productIdField.setBounds(546, 58, 96, 19);
		contentPane.add(productIdField);
		productIdField.setColumns(10);
		
		productNameField = new JTextField();
		productNameField.setEditable(false);
		productNameField.setBounds(547, 150, 96, 19);
		contentPane.add(productNameField);
		productNameField.setColumns(10);
		
		productBrandField = new JTextField();
		productBrandField.setEditable(false);
		productBrandField.setBounds(546, 98, 96, 19);
		contentPane.add(productBrandField);
		productBrandField.setColumns(10);
		
		quantityField = new JTextField();
		quantityField.setBounds(547, 202, 96, 19);
		contentPane.add(quantityField);
		quantityField.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Add/Edit ");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					AddNewProduct addProductPage = new AddNewProduct(connection);
					dispose();
				} catch (SQLException ex){

				}
			}
		});
		btnNewButton_4.setBounds(547, 230, 85, 21);
		contentPane.add(btnNewButton_4);
		
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
						//mouseClicked(null);
						break;
					case "Train Set":
						DisplayFilteredProductList(connection, "TRAINSET");
						//mouseClicked(null);
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
		category.setBounds(100, 10, 201, 21);
		contentPane.add(category);
		
		JButton btnNewButton_5 = new JButton("Log Out");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Login_Page loginPage = new Login_Page(connection);
					dispose();
				} catch (SQLException ex){

				}
				
			}
		});
		btnNewButton_5.setBounds(5, 10, 85, 21);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Delete");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char productFirstChar = productIdField.getText().charAt(0);
				productId = productIdField.getText();
				System.out.println(productId);
				ProductsDAO databaseOperations = new ProductsDAO();
				switch (productFirstChar) {
					case 'M':
						databaseOperations.deleteProduct(connection, "Products", "trainSet", productId);
						DisplayProductList(connection);
						break;
					case 'P':
						databaseOperations.deleteProduct(connection, "Products", "TrackPack", productId);
						DisplayProductList(connection);
						break;
					case 'S':
						databaseOperations.deleteProduct(connection, "Products", "RollingStock", productId);
						DisplayProductList(connection);
						
						break;
					case 'L':
						databaseOperations.deleteProduct(connection, "Products", "Locomotive", productId);
						DisplayProductList(connection);
							
						break;
					case 'C':
						databaseOperations.deleteProduct(connection, "Products", "Controller", productId);
						DisplayProductList(connection);
							
						break;
					case 'R':
						databaseOperations.deleteProduct(connection, "Products", "Tracks", productId);
						DisplayProductList(connection);
		
						break;
					default:
					break;
				}

			}
		});
		btnNewButton_6.setBounds(644, 233, 85, 21);
		contentPane.add(btnNewButton_6);
		if (userRole.equals(Role.MANAGER)){
			JButton btnNewButton_3_1 = new JButton("Manager");
			btnNewButton_3_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Manager_View managerPage = new Manager_View(connection);
						dispose();
					} catch (SQLException ex){

					}
				}
			});
			btnNewButton_3_1.setBounds(362, 12, 85, 21);
			contentPane.add(btnNewButton_3_1);
		}
		

	}


}

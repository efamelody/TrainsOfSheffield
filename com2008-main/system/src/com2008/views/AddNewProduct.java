package com2008.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.sql.Connection;
import com2008.model.Gauges;
import com2008.model.ProductsDAO;
import com2008.model.ProductsE;
import com2008.model.DCC;
import com2008.model.DatabaseOperations;


public class AddNewProduct extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField brandNameField;
	private JTextField productNameField;
	private JTextField productCodeField;
	private JTextField quantityField;
	private JTextField retailPriceField;
	private JTextField eraCodeField;
	private ProductsE productEnum;
	private DCC dcc;
	private boolean codeCheck;
	private boolean quantityCheck;
	private boolean retailPrice;
	private boolean eraCheck;

	public AddNewProduct(Connection connection) throws SQLException {
		setVisible(true);
		setTitle("Add/Edit Products");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel eraCodeLabel = new JLabel("Era Code");
		eraCodeLabel.setBounds(30, 211, 45, 13);
		eraCodeLabel.setVisible(false);
		contentPane.add(eraCodeLabel);
		
		eraCodeField = new JTextField();
		eraCodeField.setBounds(112, 207, 167, 19);
		eraCodeField.setVisible(false);
		contentPane.add(eraCodeField);
		eraCodeField.setColumns(10);

		JComboBox dCCList = new JComboBox();
		dCCList.setBounds(112, 236, 167, 21);
		contentPane.add(dCCList);
		
		JLabel dCCLabel = new JLabel("DCC");
		dCCLabel.setBounds(30, 240, 45, 13);
		contentPane.add(dCCLabel);

		dCCList.addItem("Please select DCC");
		dCCList.addItem("Analogue");
		dCCList.addItem("Ready");
		dCCList.addItem("Fitted");
		dCCList.addItem("Sound");
		dCCList.setVisible(false);
		dCCLabel.setVisible(false);

		JComboBox productTypeList = new JComboBox();

		productTypeList.addItem("Train Set");
		productTypeList.addItem("Track Pack");
		productTypeList.addItem("Locomotive");
		productTypeList.addItem("Rolling Stock");
		productTypeList.addItem("Track");
		productTypeList.addItem("Controller");

		productTypeList.setSelectedIndex(0);

		productEnum = ProductsE.fromString(productTypeList.getSelectedItem().toString());

		productTypeList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productEnum = ProductsE.fromString(productTypeList.getSelectedItem().toString());
				if (productEnum.equals(ProductsE.LOCOMOTIVE)){
					eraCodeLabel.setVisible(true);
					eraCodeField.setVisible(true);
					dCCLabel.setVisible(true);
					dCCList.setVisible(true);
					contentPane.revalidate();
					contentPane.repaint();
				}
				if (productEnum.equals(ProductsE.TRAINSET)){
					eraCodeLabel.setVisible(true);
					eraCodeField.setVisible(true);
					dCCLabel.setVisible(false);
					dCCList.setVisible(false);
					contentPane.revalidate();
					contentPane.repaint();
				}
				if (productEnum.equals(ProductsE.ROLLINGSTOCK)){
					eraCodeLabel.setVisible(true);
					eraCodeField.setVisible(true);
					dCCLabel.setVisible(false);
					dCCList.setVisible(false);
					contentPane.revalidate();
					contentPane.repaint();
				}
				if (productEnum.equals(ProductsE.TRACKPACK)){
					eraCodeLabel.setVisible(false);
					eraCodeField.setVisible(false);
					dCCLabel.setVisible(false);
					dCCList.setVisible(false);
					contentPane.revalidate();
					contentPane.repaint();
				}
				if (productEnum.equals(ProductsE.CONTROLLER)){
					eraCodeLabel.setVisible(false);
					eraCodeField.setVisible(false);
					dCCLabel.setVisible(false);
					dCCList.setVisible(false);
					contentPane.revalidate();
					contentPane.repaint();
				}
				if (productEnum.equals(ProductsE.TRACK)){
					eraCodeLabel.setVisible(false);
					eraCodeField.setVisible(false);
					dCCLabel.setVisible(false);
					dCCList.setVisible(false);
					contentPane.revalidate();
					contentPane.repaint();
				}
			}
		});
		productTypeList.setBounds(10, 10, 426, 21);
		contentPane.add(productTypeList);
		
		JLabel lblNewLabel = new JLabel("Brand Name");
		lblNewLabel.setBounds(30, 41, 72, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Product Name");
		lblNewLabel_1.setBounds(30, 80, 82, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Product Code");
		lblNewLabel_2.setBounds(30, 106, 82, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Quantity");
		lblNewLabel_3.setBounds(30, 129, 45, 13);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Retail Price");
		lblNewLabel_4.setBounds(30, 152, 45, 13);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Modelling Gauge");
		lblNewLabel_5.setBounds(30, 180, 45, 13);
		contentPane.add(lblNewLabel_5);
		
		brandNameField = new JTextField();
		brandNameField.setBounds(112, 41, 167, 19);
		contentPane.add(brandNameField);
		brandNameField.setColumns(10);
		
		productNameField = new JTextField();
		productNameField.setBounds(112, 77, 167, 19);
		contentPane.add(productNameField);
		productNameField.setColumns(10);
		
		productCodeField = new JTextField();
		productCodeField.setBounds(112, 103, 167, 19);
		contentPane.add(productCodeField);
		productCodeField.setColumns(10);
		
		quantityField = new JTextField();
		quantityField.setBounds(112, 129, 167, 19);
		contentPane.add(quantityField);
		quantityField.setColumns(10);
		
		retailPriceField = new JTextField();
		retailPriceField.setBounds(112, 152, 167, 19);
		contentPane.add(retailPriceField);
		retailPriceField.setColumns(10);
		
		JComboBox gaugeTypeCombo = new JComboBox();
		gaugeTypeCombo.setBounds(112, 176, 96, 21);
		contentPane.add(gaugeTypeCombo);
		gaugeTypeCombo.addItem("Please select gauge type");
		gaugeTypeCombo.addItem("OO");
		gaugeTypeCombo.addItem("TT");
		gaugeTypeCombo.addItem("N");

		JButton btnNewButton = new JButton("Add/Edit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(brandNameField.getText().isEmpty()  || productNameField.getText().isEmpty() ||  productCodeField.getText().isEmpty() ||
						quantityField.getText().isEmpty()	|| retailPriceField.getText().isEmpty() || gaugeTypeCombo.getSelectedItem().toString() == "Please select gauge type") {
							JOptionPane.showMessageDialog(null, " Please fill all fields");				
						}
				else{
					String brandName = brandNameField.getText();
					String productName = productNameField.getText();
					String productCode = productCodeField.getText();
					
					ProductsDAO databaseOperations = new ProductsDAO();

					boolean productExists = databaseOperations.checkIfProductExists(connection, productCode);

					if (productExists) {
						JOptionPane.showMessageDialog(null, "Product already existed, increase quantity instead");
					} else{
						char firstChar = productCodeField.getText().charAt(0);
						if (isInteger(quantityField.getText()) && (isInteger(retailPriceField.getText()) || isDouble(retailPriceField.getText()))) {
							int quantity = Integer.parseInt(quantityField.getText());
							double retailPrice = Double.parseDouble(retailPriceField.getText());
							Gauges gauge = Gauges.fromString(gaugeTypeCombo.getSelectedItem().toString());
							String eraCode = eraCodeField.getText();		

							switch (productEnum) {
								case TRAINSET:
									if (firstChar == 'M'){
										boolean addProductStatus = databaseOperations.addNewTrainSets(connection, brandName, productName, productCode, quantity, retailPrice, gauge, productEnum, eraCode);
										JOptionPane.showMessageDialog(null, "Product Created");
										new Staff_Page(connection);
										dispose();
										break; 
									} else {
										JOptionPane.showMessageDialog(null, "Product code for train set should start with capital M");
										break;
									}
								case TRACKPACK:
									if (firstChar == 'P'){
										boolean addProductStatus = databaseOperations.addNewTrackPack(connection, brandName, productName, productCode, quantity, retailPrice, gauge, productEnum);
										JOptionPane.showMessageDialog(null, "Product Created");
										new Staff_Page(connection);
										dispose();
										break; 
									} else {
										JOptionPane.showMessageDialog(null, "Product code for train set should start with capital M");
										break;
									}
								case LOCOMOTIVE:
									if (firstChar == 'L'){
										dcc = DCC.fromString(dCCList.getSelectedItem().toString());
										boolean addProductStatus = databaseOperations.addNewLocomotive(connection, brandName, productName, productCode, quantity, retailPrice, gauge, productEnum, eraCode, dcc);
										JOptionPane.showMessageDialog(null, "Product Created");
										new Staff_Page(connection);
										dispose();
										break; 
									} else {
										JOptionPane.showMessageDialog(null, "Product code for train set should start with capital M");
										break;
									} 
								case CONTROLLER:
									if (firstChar == 'C'){
										boolean addProductStatus = databaseOperations.addNewController(connection, brandName, productName, productCode, quantity, retailPrice, gauge, productEnum);
										JOptionPane.showMessageDialog(null, "Product Created");
										new Staff_Page(connection);
										dispose();
										break; 
									} else {
										JOptionPane.showMessageDialog(null, "Product code for train set should start with capital M");
										break;
									} 
								case ROLLINGSTOCK:
									if (firstChar == 'S'){
										boolean addProductStatus = databaseOperations.addNewRollingStock(connection, brandName, productName, productCode, quantity, retailPrice, gauge, productEnum, eraCode);
										JOptionPane.showMessageDialog(null, "Product Created");
										new Staff_Page(connection);
										dispose();
										break; 
									} else {
										JOptionPane.showMessageDialog(null, "Product code for train set should start with capital M");
										break;
									} 
								case TRACK:
									if (firstChar == 'R'){
										boolean addProductStatus = databaseOperations.addNewTrack(connection, brandName, productName, productCode, quantity, retailPrice, gauge, productEnum);
										JOptionPane.showMessageDialog(null, "Product Created");
										new Staff_Page(connection);
										dispose();
										break; 
									} else {
										JOptionPane.showMessageDialog(null, "Product code for train set should start with capital M");
										break;
									} 				
								default:
									if (firstChar == 'M'){
										boolean addProductStatus = databaseOperations.addNewTrainSets(connection, brandName, productName, productCode, quantity, retailPrice, gauge, productEnum, eraCode);
										JOptionPane.showMessageDialog(null, "Product Created");
										new Staff_Page(connection);
										dispose();
										break; 
									} else {
										JOptionPane.showMessageDialog(null, "Product code for train set should start with capital M");
										break;
									}
							}				
						} 
						else {
							JOptionPane.showMessageDialog(null, " Please enter an integer for Retail Price and Quantity");
						}
					}
				}
			}
		});

		btnNewButton.setBounds(267, 257, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Staff_Page(connection);
				dispose();
			}
		});
		btnNewButton_1.setBounds(362, 257, 85, 21);
		contentPane.add(btnNewButton_1);
		
		
	}

	public static boolean isInteger(String value){
		return value.matches("-?\\d+");
	}

	public static boolean isDouble(String value){
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	
}

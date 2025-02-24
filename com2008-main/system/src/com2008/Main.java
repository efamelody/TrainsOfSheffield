package com2008;

import com2008.model.DatabaseConnectionHandler;
import com2008.views.Login_Page;
import javax.swing.SwingUtilities;


public class Main {
	public static void main(String[] args) {
		// Create an instance of DatabaseConnectionHandler for managing database connections
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();

		//Execute the Swing GUI application on the Event Dispatch Thread
		SwingUtilities.invokeLater(() -> {
			Login_Page login_page = null;
			try{
				// Open a database connection
				databaseConnectionHandler.openConnection();

				//Create and initialize the view using database connection
				login_page = new Login_Page(databaseConnectionHandler.getConnection());
				login_page.setVisible(true);
			}catch (Throwable e){
				//Close connection if database crashes
				databaseConnectionHandler.closeConnection();
				throw new RuntimeException(e);
			}
		});
//		new Login_Page();
//		Mon 20 Nov 11.46am
	}
}

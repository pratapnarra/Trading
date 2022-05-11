import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class PortfolioScreen extends JFrame {
private JLabel sampleField;
private String username;
private Connection connection;
public String fname;
public String lname;
	
	
	public PortfolioScreen(String uname, String f, String l) {
		
		
		
		
		try {
			  connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tradingapp","root","");//Establishing connection
			  System.out.println("Connected With the database successfully");
			  
			  } catch (SQLException e) {
			  	
			  System.out.println(e);
			  }
		this.username = uname;
		this.fname = f;
		this.lname = l;
		sampleField = new JLabel(this.fname +  "'s Portfolio");
	    add(sampleField, BorderLayout.CENTER);
	    JPanel p =new JPanel();
		p.setLayout(new GridLayout(3, 1));
		
		 ResultSet resultSet = null;
         Statement statement = null;
         try {
         	//statement = connection.createStatement();
         	PreparedStatement stmt=connection.prepareStatement("select * from TradedStocks where FirstName = ? and LastName = ?"); 
         	stmt.setString(1,this.fname);
         	stmt.setString(2, this.lname);
			  
			resultSet = stmt.executeQuery();
			while (resultSet.next())
		    p.add(companyCard(resultSet.getString(3),resultSet.getInt(4), resultSet.getInt(5), resultSet.getInt(6)));
			
         }
         catch(SQLException ex) {
         	System.out.println(ex);
         }
		
//		p.add(companyCard("Tesla", 1000, 867));
//		p.add(companyCard("Meta", 450, 380));
//		p.add(companyCard("Microsoft", 400, 450));
		
		add(p,BorderLayout.SOUTH);
		
	}
	
	public JPanel companyCard(String c,int nstocks, int buying_price, int current_price) {
		JPanel panel =new JPanel();
		JLabel company = new JLabel(c);
		JLabel boughtat = new JLabel("Bought at: "+buying_price+"$");
		JLabel current = new JLabel("Current: "+ current_price +"$");
		JLabel stocks = new JLabel("Number of Stocks: "+ nstocks );
		JLabel net = new JLabel("Net: "+ ( ((float)current_price/buying_price) - 1.0)*100 +" %");
		JButton sell = new JButton("SELL");
		
		panel.add(company,BorderLayout.CENTER);
		panel.add(boughtat);
		panel.add(current);
		panel.add(stocks);
		panel.add(net);
		panel.add(sell);
		
		
	    return panel;
	}

}

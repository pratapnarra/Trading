import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		    p.add(companyCard(resultSet.getString(3),resultSet.getInt(4), resultSet.getFloat(5), resultSet.getFloat(6)));
			
         }
         catch(SQLException ex) {
         	System.out.println(ex);
         }
		
//		p.add(companyCard("Tesla", 1000, 867));
//		p.add(companyCard("Meta", 450, 380));
//		p.add(companyCard("Microsoft", 400, 450));
		
		add(p,BorderLayout.SOUTH);
		
	}
	
	public JPanel companyCard(String c,int nstocks, float buying_price, float current_price) {
		JPanel panel =new JPanel();
		JLabel company = new JLabel(c);
		JLabel boughtat = new JLabel("Bought at: "+buying_price+"$");
		JLabel current = new JLabel("Current: "+ current_price +"$");
		JLabel stocks = new JLabel("Number of Stocks: "+ nstocks );
		JLabel net = new JLabel("Net: "+ ( ((float)current_price/buying_price) - 1.0)*100 +" %");
		JButton sell = new JButton("SELL");
		
		sell.addActionListener(new SellListener(c,nstocks,buying_price, current_price));
		
		panel.add(company,BorderLayout.CENTER);
		panel.add(boughtat);
		panel.add(current);
		panel.add(stocks);
		panel.add(net);
		panel.add(sell);
		
		
	    return panel;
	}
	
	
	public class SellListener implements ActionListener {
		String company;
		
		int nstocks;
		float bprice;
		float curprice;
		
		
		SellListener(String c,  int st, float by, float cur){
			this.company = c;
			
			this.nstocks = st;
			this.bprice = by;
			this.curprice = cur;
		}
		
		public void actionPerformed(ActionEvent event) {
			
			
			
			
			try {
				PreparedStatement stmt=connection.prepareStatement("update Users set wallet = wallet + ? where Username = ?");
            	stmt.setFloat(1, (curprice*nstocks));
            	stmt.setString(2,PortfolioScreen.this.username);  
  			  
  			  stmt.executeUpdate();
			
  			
  			PreparedStatement stmt3 = connection.prepareStatement("SET SQL_SAFE_UPDATES=0;");
  			stmt3.executeUpdate();
  			
			PreparedStatement stmt2=connection.prepareStatement("delete from TradedStocks where "
						+ " FirstName =? and LastName = ? and Company = ? and numofstocks = ? "
						+ " and BoughtAt = ? and CurrentAt =?  ;");
				stmt2.setString(1,PortfolioScreen.this.fname);
				stmt2.setString(2,PortfolioScreen.this.lname);
				stmt2.setString(3,this.company);
				stmt2.setInt(4, nstocks);
				stmt2.setFloat(5, this.bprice);
				stmt2.setFloat(6, this.curprice);
				stmt2.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Sold Successfully");
			
			
			
			
			
			}
			catch (SQLException ex) {
				System.out.println(ex);
			}
			setVisible(false); //you can't see me!
			dispose(); //Destroy the JFrame object
			
			
		}
		
	}

}

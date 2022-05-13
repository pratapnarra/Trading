import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
public JButton cancelButton = new JButton("RETURN TO HOMESCREEN");
	
	
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
		sampleField.setFont(new Font("Serif", Font.BOLD, 18));
	    
		cancelButton.addActionListener(new CancelButtonListener());
		cancelButton.setBounds(100,0,100,30);
		 JPanel p1 = new JPanel();
		 p1.add(sampleField, BorderLayout.NORTH );
	    
	    
	    JPanel p =new JPanel();
		p.setLayout(new GridLayout(20, 1));
		
		
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
		
        add(p1,BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(p,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setPreferredSize(new Dimension(700, 650));
 		add(scrollPane,BorderLayout.CENTER);
 		add(cancelButton,BorderLayout.SOUTH );
		
	}
	
	public JPanel companyCard(String c,int nstocks, float buying_price, float current_price) {
		
		float n = ((current_price/buying_price) - 1)*100;
		JPanel panel =new JPanel();
		JLabel company = new JLabel(c);
		company.setForeground(Color.blue);
		JLabel boughtat = new JLabel("<html>" + "<B>" + "Bought at: " + "</B>" +buying_price+"INR"+ "</html>");
		JLabel current = new JLabel("<html>"+ "<B>" +"Current: " + "</B>"+ current_price +"INR"+ "</html>");
		JLabel stocks = new JLabel("<html>"+ "<B>" +"Number of Stocks: " + "</B>"+ nstocks + "</html>");
		JLabel net = new JLabel("<html>"+ "<B>" +"Net: " + "</B>"+ String.format("%.2f", n) +" %"+ "</html>");
		JButton sell = new JButton("SELL");
		
		if (n> 0){
			net.setForeground(Color.GREEN.darker());
		}
		if (n<0) {
			net.setForeground(Color.red);
		}
		
		
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
	
	
	public class CancelButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			setVisible(false); //you can't see me!
			dispose(); //Destroy the JFrame object
		}
		
	}

}

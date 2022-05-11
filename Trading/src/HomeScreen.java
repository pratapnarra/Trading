import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;



public class HomeScreen extends JFrame {
	private JLabel sampleField;
	private Connection connection;
	public String username;
	public String fname;
	public String lname;
	private JButton myport;
	private JButton logout;
	private JButton wallet;
	  
	
	public HomeScreen(String uname, String f, String l) {
		
		ResultSet resultSet = null;
		try {
			  connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tradingapp","root","");//Establishing connection
			  System.out.println("Connected With the database successfully");
			  Statement statement = null;
			  statement = connection.createStatement();
			  
			  resultSet = statement.executeQuery
					  ("select * from Market");
			  } catch (SQLException e) {
			  	
			  System.out.println(e);
			  }
		
		
		this.username = uname;
		this.fname = f;
		this.lname = l;
		myport = new JButton("My Portfolio");
		
		myport.addActionListener(new PortfolioListener());
		
		
		logout = new JButton("Logout");
		logout.addActionListener(new LogoutListener());
		
		wallet = new JButton("My Wallet");
		wallet.addActionListener(new WalletListener());
		
		
		sampleField = new JLabel("Welcome to the Stock Market " + this.fname);
		JPanel m = new JPanel();
		m.setLayout(new GridLayout(1, 3));
		m.add(myport);
		m.add(logout);
		m.add(wallet);
		
	    add(sampleField, BorderLayout.CENTER);
	    JPanel p =new JPanel();
		p.setLayout(new GridLayout(3, 1));
		
		 try {
				while (resultSet.next())
					p.add(stockCard(resultSet.getString(1) ,resultSet.getLong(2),resultSet.getInt(3)));
					
					
				  
			} catch (SQLException e) {
				e.printStackTrace();
				
			}
		
		
		//p.add(stockCard("Meta", 1000000, 380));
		//p.add(stockCard("Microsoft", 900000, 450));
		add(m,BorderLayout.NORTH);
		add(p,BorderLayout.SOUTH);
		
	}
	
	class PortfolioListener implements ActionListener
    {  
       public void actionPerformed(ActionEvent event)
       {  
    	   JFrame frame1 = new PortfolioScreen(HomeScreen.this.username,HomeScreen.this.fname, HomeScreen.this.lname );
	 	      frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 	      frame1.setVisible(true);   
	 	      frame1.setBounds(10,10,500,500);
       }
    }
	
	
	class LogoutListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			setVisible(false); //you can't see me!
			dispose(); //Destroy the JFrame object
			
		}
		
	}
	
	class WalletListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			JFrame f1 = new Wallet(HomeScreen.this.username);
			f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 	      f1.setVisible(true);   
	 	      f1.setBounds(10,10,300,300);
			
			
		}
	}
	
	public JPanel stockCard(String c, long rev, int pri) {
		JPanel panel =new JPanel();
		JLabel company = new JLabel(c);
		JLabel revenue = new JLabel("Last Year Revenue: "+rev);
		JLabel price = new JLabel("Price: "+ pri +" $");
		JButton buy = new JButton("BUY");
		
		panel.add(company,BorderLayout.CENTER);
		panel.add(revenue);
		panel.add(price);
		panel.add(buy);
		
		
	    return panel;
		
		
	}
	
	
	

}

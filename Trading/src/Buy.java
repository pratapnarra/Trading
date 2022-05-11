import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Buy extends JFrame implements ActionListener {
	Container container=getContentPane();
    JLabel companyLabel;
    String username;
    
    
    JButton buyButton=new JButton("BUY");
    SpinnerModel value =  new SpinnerNumberModel(1, //initial value  
               1, //minimum value  
               25, //maximum value  
               1); //step  
   JSpinner nstocks = new JSpinner(value);  
   String company;
   String firstname;
   String lastname;
   int stockprice;
   long revenue;
    
    
    private Connection connection;
 
 
    Buy(String uname,String fname, String lname, String comp, int sprice, long rev)
    { 
    	this.username = uname;
    	this.firstname = fname;
    	this.lastname = lname;
    	this.company = comp;
    	this.stockprice = sprice;
    	this.revenue = rev;
    	companyLabel = new JLabel(company);
       //Calling methods inside constructor.
    	
		try {
			  connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tradingapp","root","");//Establishing connection
			  System.out.println("Connected With the database successfully");
			  
			  } catch (SQLException e) {
			  	
			  System.out.println(e);
			  }
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
 
    }
   public void setLayoutManager()
   {
       container.setLayout(null);
   }
   public void setLocationAndSize()
   {
       //Setting location and Size of each components using setBounds() method.
       companyLabel.setBounds(50,150,100,30);
       nstocks.setBounds(100,100,50,30);    
       
       
       buyButton.setBounds(200,300,100,30);
 
 
   }
   public void addComponentsToContainer()
   {
      //Adding each components to the Container
       container.add(companyLabel);
       container.add(nstocks);
       container.add(buyButton);
   }
   public void addActionEvent() {
       buyButton.addActionListener(this);
       
   }
 
 
    @Override
    public void actionPerformed(ActionEvent e) {
    	//Coding Part of LOGIN button
    	
    	    
            int numberofstocks = (int) nstocks.getValue();
            int wallet =0;
            
            
            ResultSet resultSet = null;
           
            try {
            	//statement = connection.createStatement();
            	PreparedStatement stmt=connection.prepareStatement("select wallet from Users where Username = ?"); 
            	stmt.setString(1,this.username);  
  			  
  			  resultSet = stmt.executeQuery();
  			while (resultSet.next())
  			wallet = resultSet.getInt(1);
  			
  			if (wallet >= numberofstocks * stockprice ) {
  				
  				PreparedStatement stmt2=connection.prepareStatement("insert into TradedStocks(FirstName,LastName,"
  						+ "Company, numofstocks, BoughtAt, CurrentAt) "
  						+ "values(?, ?,?,?,?,?);");
  				stmt2.setString(1,this.firstname);
  				stmt2.setString(2,this.lastname);
  				stmt2.setString(3,this.company);
  				stmt2.setInt(4, numberofstocks);
  				stmt2.setInt(5, this.stockprice);
  				stmt2.setInt(6, this.stockprice);
  				stmt2.executeUpdate();
  				
  				PreparedStatement stmt3=connection.prepareStatement("update Users set wallet = wallet - ? where Username = ?");
            	stmt3.setInt(1, (numberofstocks * stockprice));
            	stmt3.setString(2,this.username);  
    			stmt3.executeUpdate();
    			
    			JOptionPane.showMessageDialog(null, "Purchase Successful");
  				
  			}
  			
  			else {
  				JOptionPane.showMessageDialog(null, "Insufficient Funds");
  			}
  			
  				
  			
            }
            catch(SQLException ex) {
            	System.out.println(ex);
            }
            setVisible(false); //you can't see me!
			dispose(); //Destroy the JFrame object
			  
            
           
 
        
        
      
 
    }
    

}

import java.awt.Color;
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
    JButton cancelButton = new JButton("CANCEL");
    SpinnerModel value =  new SpinnerNumberModel(1, //initial value  
               1, //minimum value  
               25, //maximum value  
               1); //step  
   JSpinner nstocks = new JSpinner(value); 
   JLabel changeMonthLabel;
   JLabel changeYearLabel;
   String company;
   String firstname;
   String lastname;
   float stockprice;
   float changemonth;
   float changeyear;
   
    
    
    private Connection connection;
 
 
    Buy(String uname,String fname, String lname, String comp, float sprice, float c30, float cyear)
    { 
    	this.username = uname;
    	this.firstname = fname;
    	this.lastname = lname;
    	this.company = comp;
    	this.stockprice = sprice;
    	this.changemonth = c30;
    	this.changeyear = cyear;
    	companyLabel = new JLabel(company);
    	changeMonthLabel = new JLabel("<html>" + "<B>" + "30 Day Change: " + "</B>" +this.changemonth+" %"+ "</html>");
    	changeYearLabel = new JLabel("<html>" + "<B>" + "Year Change: " + "</B>" +this.changeyear+" %"+ "</html>");
       //Calling methods inside constructor.
    	
    	if (this.changemonth> 0){
			changeMonthLabel.setForeground(Color.GREEN.darker());
		}
    	else {
    		changeMonthLabel.setForeground(Color.red);
		}
		if (this.changeyear> 0){
			changeYearLabel.setForeground(Color.GREEN.darker());
		}
		else {
			changeYearLabel.setForeground(Color.red);
		}
    	
    	
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
       companyLabel.setBounds(120,50,200,30);
       changeYearLabel.setBounds(70,100,200,30);
       changeMonthLabel.setBounds(70,150,200,30);
       nstocks.setBounds(30,200,50,30); 
       cancelButton.setBounds(100,200,80,30);
//       changeMonthLabel.setBounds();
//       changeYearLabel.setBounds(, ABORT, WIDTH, HEIGHT);
       buyButton.setBounds(200,200,80,30);
 
 
   }
   public void addComponentsToContainer()
   {
      //Adding each components to the Container
       container.add(companyLabel);
       container.add(nstocks);
       container.add(buyButton);
       container.add(changeYearLabel);
       container.add(changeMonthLabel);
       container.add(cancelButton);
   }
   public void addActionEvent() {
       buyButton.addActionListener(this);
       cancelButton.addActionListener(this);
       
   }
 
 
    @Override
    public void actionPerformed(ActionEvent e) {
    	//Coding Part of LOGIN button
    	
    	    if (e.getSource() == buyButton) {
            int numberofstocks = (int) nstocks.getValue();
            float wallet =0;
            
            
            ResultSet resultSet = null;
           
            try {
            	//statement = connection.createStatement();
            	PreparedStatement stmt=connection.prepareStatement("select wallet from Users where Username = ?"); 
            	stmt.setString(1,this.username);  
  			  
  			  resultSet = stmt.executeQuery();
  			while (resultSet.next())
  			wallet = resultSet.getFloat(1);
  			
  			if (wallet >= numberofstocks * stockprice ) {
  				
  				PreparedStatement stmt2=connection.prepareStatement("insert into TradedStocks(FirstName,LastName,"
  						+ "Company, numofstocks, BoughtAt, CurrentAt) "
  						+ "values(?, ?,?,?,?,?);");
  				stmt2.setString(1,this.firstname);
  				stmt2.setString(2,this.lastname);
  				stmt2.setString(3,this.company);
  				stmt2.setInt(4, numberofstocks);
  				stmt2.setFloat(5, this.stockprice);
  				stmt2.setFloat(6, this.stockprice);
  				stmt2.executeUpdate();
  				
  				PreparedStatement stmt3=connection.prepareStatement("update Users set wallet = wallet - ? where Username = ?");
            	stmt3.setFloat(1, (numberofstocks * stockprice));
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
    	    
    	    if (e.getSource() == cancelButton) {
    	    	 setVisible(false); //you can't see me!
    				dispose(); //Destroy the JFrame object
    	    }
            
           
 
        
        
      
 
    }
    

}

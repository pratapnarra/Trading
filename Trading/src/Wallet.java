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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Wallet extends JFrame implements ActionListener  {

	Container container=getContentPane();
	JLabel balanceLabel;
    JLabel amtLabel=new JLabel("Enter Amount");
    JTextField amtTextField=new JTextField();
    JButton addButton=new JButton("ADD");
    JButton cancelButton = new JButton("CANCEL");
    String username;
    
    private Connection connection;
 
 
    Wallet(String uname)
    {
       //Calling methods inside constructor.
    	this.username = uname;
    	ResultSet resultSet = null;
    	int wallet = 0;
		try {
			  connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tradingapp","root","");//Establishing connection
			  System.out.println("Connected With the database successfully");
			  
			  
			  PreparedStatement stmt=connection.prepareStatement("select wallet from Users where Username = ?"); 
          	stmt.setString(1,this.username);  
			  
			  resultSet = stmt.executeQuery();
			while (resultSet.next())
			wallet = resultSet.getInt(1);
			  
			  } catch (SQLException e) {
			  	
			  System.out.println(e);
			  }
		balanceLabel = new JLabel("Current Balance: " + String.valueOf(wallet)+ " INR");
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
       amtLabel.setBounds(30,150,100,30);
       
       amtTextField.setBounds(130,150,150,30);
       balanceLabel.setBounds(30,100,180,30);
       cancelButton.setBounds(80,200,100,30);
       addButton.setBounds(180,200,100,30);
 
 
   }
   public void addComponentsToContainer()
   {
      //Adding each components to the Container
       container.add(amtLabel);
       container.add(amtTextField);
       container.add(balanceLabel);
       container.add(cancelButton);
       container.add(addButton);
       
   }
   public void addActionEvent() {
       addButton.addActionListener(this);
       cancelButton.addActionListener(this);
       
   }
 
 
    @Override
    public void actionPerformed(ActionEvent e) {
    	//Coding Part of LOGIN button
    	
    	
    	
    	
        if (e.getSource() == addButton) {
        	
            float amt;
            
            amt= Float.parseFloat(amtTextField.getText()) ;
            
            
            
            ResultSet resultSet = null;
            Statement statement = null;
            try {
            	//statement = connection.createStatement();
            	PreparedStatement stmt=connection.prepareStatement("update Users set wallet = wallet + ? where Username = ?");
            	stmt.setFloat(1, amt);
            	stmt.setString(2,this.username);  
  			  
  			  stmt.executeUpdate();
  			//while (resultSet.next());
  			  
  			JOptionPane.showMessageDialog(null, "Successfully Added");
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

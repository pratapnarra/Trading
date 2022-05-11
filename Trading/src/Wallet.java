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
    JLabel amtLabel=new JLabel("Enter Amount");
    JTextField amtTextField=new JTextField();
    JButton addButton=new JButton("ADD");
    String username;
    
    private Connection connection;
 
 
    Wallet(String uname)
    {
       //Calling methods inside constructor.
    	this.username = uname;
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
       amtLabel.setBounds(30,150,100,30);
       amtTextField.setBounds(130,150,150,30);
       addButton.setBounds(180,200,100,30);
 
 
   }
   public void addComponentsToContainer()
   {
      //Adding each components to the Container
       container.add(amtLabel);
       container.add(amtTextField);
       container.add(addButton);
       
   }
   public void addActionEvent() {
       addButton.addActionListener(this);
       
   }
 
 
    @Override
    public void actionPerformed(ActionEvent e) {
    	//Coding Part of LOGIN button
    	
    	
    	
    	
        if (e.getSource() == addButton) {
        	
            int amt;
            
            amt= Integer.parseInt(amtTextField.getText()) ;
            System.out.println(amt);
            
            
            ResultSet resultSet = null;
            Statement statement = null;
            try {
            	//statement = connection.createStatement();
            	PreparedStatement stmt=connection.prepareStatement("update Users set wallet = wallet + ? where Username = ?");
            	stmt.setInt(1, amt);
            	stmt.setString(2,this.username);  
  			  
  			  stmt.executeUpdate();
  			//while (resultSet.next());
  			}
            catch(SQLException ex) {
            	System.out.println(ex);
            }
            setVisible(false); //you can't see me!
			dispose(); //Destroy the JFrame object
            
            
           
 
        }
        
       
 
    }
}

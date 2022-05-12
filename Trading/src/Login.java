import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import javax.swing.*;




import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class Login extends JFrame implements ActionListener {
	
    Container container=getContentPane();
    JLabel userLabel=new JLabel("USERNAME");
    JLabel passwordLabel=new JLabel("PASSWORD");
    JTextField userTextField=new JTextField();
    JPasswordField passwordField=new JPasswordField();
    JButton loginButton=new JButton("LOGIN");
    JButton resetButton=new JButton("RESET");
    JCheckBox showPassword=new JCheckBox("Show Password");
    JButton signupButton = new JButton("SIGNUP");
    
    private Connection connection;
 
 
    Login()
    {
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
       userLabel.setBounds(50,150,100,30);
       passwordLabel.setBounds(50,220,100,30);
       userTextField.setBounds(150,150,150,30);
       passwordField.setBounds(150,220,150,30);
       showPassword.setBounds(150,250,150,30);
       loginButton.setBounds(50,300,100,30);
       resetButton.setBounds(200,300,100,30);
       signupButton.setBounds(120,300,60,30);
 
 
   }
   public void addComponentsToContainer()
   {
      //Adding each components to the Container
       container.add(userLabel);
       container.add(passwordLabel);
       container.add(userTextField);
       container.add(passwordField);
       container.add(showPassword);
       container.add(loginButton);
       container.add(resetButton);
       container.add(signupButton);
   }
   public void addActionEvent() {
       loginButton.addActionListener(this);
       resetButton.addActionListener(this);
       showPassword.addActionListener(this);
       signupButton.addActionListener(this);
   }
 
 
    @Override
    public void actionPerformed(ActionEvent e) {
    	//Coding Part of LOGIN button
    	
    	
    	
    	
        if (e.getSource() == loginButton) {
        	
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = String.valueOf(passwordField.getPassword());
            
            ResultSet resultSet = null;
            Statement statement = null;
            try {
            	//statement = connection.createStatement();
            	PreparedStatement stmt=connection.prepareStatement("select * from Users where Username = ?"); 
            	stmt.setString(1,userText);  
  			  
  			  resultSet = stmt.executeQuery();
  			while (resultSet.next())
  			if (pwdText.equals(resultSet.getString(5))) {
  				JFrame frame1 = new HomeScreen(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
  	 	      frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	 	      frame1.setVisible(true);   
  	 	      frame1.setBounds(10,10,500,500);
  			}
  				
  			else
  				JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
            catch(SQLException ex) {
            	System.out.println(ex);
            }
			  
            
           
 
        }
        //Coding Part of RESET button
        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
       //Coding Part of showPassword JCheckBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }  
        
        if (e.getSource() == signupButton) {
        	Signup frame = new Signup();
            //set NewUser frame visible
             frame.setVisible(true);
        }
        
        
 
    }
    
  
    
    public static void main(String[] args)
	   {  
    Login frame=new Login();
    frame.setTitle("Login Form");
    frame.setVisible(true);
    frame.setBounds(10,10,370,600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);


	   }
}
 


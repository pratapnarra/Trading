import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Wrapper;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Signup extends JFrame implements ActionListener//create class NewUser
{
	private JPanel contentPane; //declare variable
	private JTextField txtUser;
	private JTextField txtSSN;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JButton btnSignup;
	private JTextField txtPassword;
	private Connection connection;
	protected java.lang.String Spassword;
	
	// database URL
	static final String DB_URL = "jdbc:mysql://localhost/demo";
	
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	static final String FIRSTNAME = "root";
	static final String LASTNAME = "root";
	static final String SSN = "root";
	protected static final String String = null;
	
	
	public Signup() //create constructor
	{
		
		
		//set title
		try {
			  connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tradingapp","root","");//Establishing connection
			  System.out.println("Connected With the database successfully");
			  
			  } catch (SQLException e) {
			  	
			  System.out.println(e);
			  }
				setTitle("New User Login");
				//set close operation
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//set bounds of frame
				setBounds(100, 100, 450, 300);
				//create object of JPanel
				contentPane = new JPanel();
		                //set contentPane border
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				//set ContentPane with new object
				setContentPane(contentPane);
				//set contentPane layout is null
				contentPane.setLayout(null);
				
		                // create text field for user
				txtUser = new JTextField();
				//set bounds for text fields
				txtUser.setBounds(188, 50, 99, 20);
				//in contentPane add text field
				contentPane.add(txtUser);
				//set column for text field
				txtUser.setColumns(10);
				
				//lable the text field
				JLabel lblUserName = new JLabel("User Name");
				//set bounds for label
				lblUserName.setBounds(70, 50, 86, 14);
				//add into contentPane
				contentPane.add(lblUserName);
				
				JLabel lblFirstName = new JLabel("First Name");
				//set bounds for label
				lblFirstName.setBounds(70, 100, 86, 14);
				//add into contentPane
				contentPane.add(lblFirstName);
				
				JLabel lblLastName = new JLabel("Last Name");
				//set bounds for label
				lblLastName.setBounds(70, 150, 86, 14);
				//add into contentPane
				contentPane.add(lblLastName);
				
				JLabel lblSSN = new JLabel("SSN");
				//set bounds for label
				lblSSN.setBounds(70, 200, 86, 14);
				//add into contentPane
				contentPane.add(lblSSN);
				
				
				//lable the text field
				JLabel lblPassword = new JLabel("Password");
				//set bounds for label
				lblPassword.setBounds(70, 250, 86, 14);
				//add into contentPane
				contentPane.add(lblPassword);
				
		                //create button signup
				btnSignup = new JButton("SignUp");
				//add event handler on SignUp button
		
	
		
		
		//set bound for SignUp button
		btnSignup.setBounds(131, 300, 89, 23);
		//add button into contentPane
		contentPane.add(btnSignup);
		
		//create text field for password
		txtPassword = new JTextField();
		//set bound for password field
		txtPassword.setBounds(188, 250, 99, 20);
		//add text field on contentPane
		contentPane.add(txtPassword);
		//set column for password text field
		txtPassword.setColumns(10);	
		
		//create text field for password
		txtSSN = new JTextField();
		//set bound for password field
		txtSSN.setBounds(188, 200, 99, 20);
		//add text field on contentPane
		contentPane.add(txtSSN);
		//set column for password text field
		txtSSN.setColumns(10);	
			
		//create text field for password
		txtFirstName = new JTextField();
		//set bound for password field
		txtFirstName.setBounds(188, 100, 99, 20);
		//add text field on contentPane
		contentPane.add(txtFirstName);
		//set column for password text field
		txtFirstName.setColumns(10);	
				
		//create text field for password
		txtLastName = new JTextField();
		//set bound for password field
		txtLastName.setBounds(188, 150, 99, 20);
		//add text field on contentPane
		contentPane.add(txtLastName);
		//set column for password text field
		txtLastName.setColumns(10);	
						
		btnSignup.addActionListener(this);		
		}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String usernameText;
        String pwdText;
        String fnameText;
        String lnameText;
        String ssnText;
        usernameText = txtUser.getText();
        pwdText = txtPassword.getText();
        fnameText = txtFirstName.getText();
        lnameText = txtLastName.getText();
        ssnText = txtSSN.getText();
        System.out.println(usernameText);
        System.out.println(pwdText);
        System.out.println(fnameText);
        System.out.println(lnameText);
        System.out.println(ssnText);
        
        try {
        PreparedStatement stmt2=connection.prepareStatement("insert into Users(Username ,FirstName,LastName,"
					+ "ssn,Password, wallet) "
					+ "values(?,?, ?,?,?,?);");
            stmt2.setString(1, usernameText);
			stmt2.setString(2,fnameText);
			stmt2.setString(3,lnameText);
			stmt2.setString(4,ssnText);
			stmt2.setString(5,pwdText );
			stmt2.setFloat(6, 0);
			
			stmt2.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Try logging in");
			
	}catch(SQLException ex) {
		System.out.println(ex);
	}
        setVisible(false); //you can't see me!
		dispose(); //Destroy the JFrame object
        
        
//        ResultSet resultSet = null;
//        Statement statement = null;
//        try {
//        	//statement = connection.createStatement();
//        	PreparedStatement stmt=connection.prepareStatement("select * from Users where Username = ? and  "); 
//        	stmt.setString(1,this.getText);  
//			  
//			  resultSet = stmt.executeQuery();
//			while (resultSet.next())
//			if (pwdText.equals(resultSet.getString(5))) {
//				JFrame frame1 = new HomeScreen(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
//	 	      frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	 	      frame1.setVisible(true);   
//	 	      frame1.setBounds(10,10,500,500);
//			}
//				
//			else
//				JOptionPane.showMessageDialog(this, "Invalid Username or Password");
//        }
//        catch(SQLException ex) {
//        	System.out.println(ex);
//        }
        
		
	}
}
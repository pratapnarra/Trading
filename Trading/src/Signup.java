import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Wrapper;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Signup extends JFrame //create class NewUser
{
	private JPanel contentPane; //declare variable
	private JTextField txtUser;
	private JButton btnSignup;
	private JTextField txtPassword;
	protected java.lang.String Spassword;
	
	// database URL
	static final String DB_URL = "jdbc:mysql://localhost/demo";
	
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	protected static final String String = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) // main method
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() //define run method
			{
				try  //try block
				{
					//create NewUser frame object
					Signup frame = new Signup();
                                        //set NewUser frame visible
					frame.setVisible(true);
				} 
				catch (Exception e) //catch block
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Signup() //create constructor
	{
		
		//set title
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
				txtUser.setBounds(188, 51, 99, 20);
				//in contentPane add text field
				contentPane.add(txtUser);
				//set column for text field
				txtUser.setColumns(10);
				
				//lable the text field
				JLabel lblUserName = new JLabel("User Name");
				//set bounds for label
				lblUserName.setBounds(70, 54, 86, 14);
				//add into contentPane
				contentPane.add(lblUserName);
				
				//lable the text field
				JLabel lblPassword = new JLabel("Password");
				//set bounds for label
				lblPassword.setBounds(70, 109, 86, 14);
				//add into contentPane
				contentPane.add(lblPassword);
				
		                //create button signup
				btnSignup = new JButton("SignUp");
				//add event handler on SignUp button
		
	
		
		
		//set bound for SignUp button
		btnSignup.setBounds(131, 165, 89, 23);
		//add button into contentPane
		contentPane.add(btnSignup);
		
		//create text field for password
		txtPassword = new JTextField();
		//set bound for password field
		txtPassword.setBounds(188, 106, 99, 20);
		//add text field on contentPane
		contentPane.add(txtPassword);
		//set column for password text field
		txtPassword.setColumns(10);				
		}
}
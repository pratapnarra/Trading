
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
 
public class testing {
 
public static void main(String[] args) {
   try {
Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tradingapp","root","");//Establishing connection
System.out.println("Connected With the database successfully");
} catch (SQLException e) {
	
System.out.println(e);
}
}
 
}

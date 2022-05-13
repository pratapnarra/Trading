
import java.io.IOException;
import java.net.URI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.net.MalformedURL.Exception;
import java.net.URL;
import java.net. URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.*;
import java.io.FileReader;
import java.io.FileWriter;
//import org.apache.http.*;h
import java.util.Iterator;
import java.util.Map;

  
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


import java.sql.*;

 
public class webscrap {
	private Connection connection;
	
	public webscrap() {
		
		try {
			  connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tradingapp","root","");//Establishing connection
			  System.out.println("Connected With the database successfully");
			  
			  PreparedStatement stmt=connection.prepareStatement("truncate table  Market");
				
				stmt.executeUpdate();
			  
			  } catch (SQLException e) {
			  	
			  System.out.println(e);
			  }
		
		try{
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://latest-stock-price.p.rapidapi.com/any"))
					.header("X-RapidAPI-Host", "latest-stock-price.p.rapidapi.com")
					.header("X-RapidAPI-Key", "f549209ac6msh0ecfa32ed6b5664p1eebbdjsnda43503037cc")
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			
		    try (PrintWriter out = new PrintWriter(new FileWriter("test.json"))) {
		        out.write(response.body());
		        out.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		    Object obj = new JSONParser().parse(new FileReader("test.json"));
		    JSONArray ja = (JSONArray) obj;
		    Iterator<JSONObject> iterator = ja.iterator();
		    int i=0; 
		    
		    while(iterator.hasNext() && i< 40 ) {
		    	
		    	JSONObject j = new JSONObject(iterator.next());
		    	i +=1;
		    	
		    	
		    	PreparedStatement stmt2=connection.prepareStatement("insert into Market(Company, last30,"
		    			+ "lastyear,StockPrice ) "
  						+ "values(?, ?,?,?);");
  				stmt2.setString(1,j.get("identifier").toString());
  				stmt2.setFloat(2, Float.parseFloat(j.get("perChange30d").toString()) );
  				stmt2.setFloat(3, Float.parseFloat(j.get("perChange365d").toString()) );
  				stmt2.setFloat(4, Float.parseFloat(j.get("lastPrice").toString()) );
  				
  				stmt2.executeUpdate();
		    	
  				PreparedStatement stmt3=connection.prepareStatement("update TradedStocks set  "
  						+ "CurrentAt = ?  where Company = ?;");
  				stmt3.setFloat(1, Float.parseFloat(j.get("lastPrice").toString()) );
  				stmt3.setString(2,j.get("identifier").toString());
  				stmt3.executeUpdate();
  				
		        
		     }
		    
		    
			} catch(Exception ex) {
				System.out.println(ex);
			}
		
		
		
		
	}
 


}

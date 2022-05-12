
import java.sql.Connection;
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
	
	public webscrap() {
		
	}
 
public static void main(String[] args) {
	
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
    
    while(iterator.hasNext() && i<1 ) {
    	i +=1;
    	
        System.out.println(iterator.next());
     }
    
    
	} catch(Exception ex) {
		System.out.println(ex);
	}
	
}


}

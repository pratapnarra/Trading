
import java.sql.Connection;
import java.io.IOException;
import java.net.URI;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.sql.*;
 
public class testing {
 
public static void main(String[] args) {
	
	OkHttpClient client = new OkHttpClient();

	Request request = new Request.Builder()
		.url("https://alpha-vantage.p.rapidapi.com/query?interval=5min&function=TIME_SERIES_INTRADAY&symbol=MSFT&datatype=json&output_size=compact")
		.get()
		.addHeader("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
		.addHeader("X-RapidAPI-Key", "d4258678bbmsh2473abe176be803p1b9bafjsncdddd9dd4a93")
		.build();

	try {
		Response response = client.newCall(request).execute();
		System.out.println(response);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


}

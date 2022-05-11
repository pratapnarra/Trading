import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.net.MalformedURL.Exception;
import java.net.URL;
import java.net. URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.*;

public class webscrap {
	public static void main(String[] args) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.polygon.io/v2/aggs/ticker/AAPL/range/1/day/2020-06-01/2020-06-17?apiKey=iTOpTOHpb5iq_sp_wMfX0Z382znW1ZUP"))
					//.header("X-RapidAPI-Host", "stock-market-data.p.rapidapi.com")
					//.header("X-RapidAPI-Key", "f3e1bf9960mshc91350b6c0c262bp14461djsndcfccd54b7d1")
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());
		} catch(Exception e) {
			System.out.println("");
		}
	}
}
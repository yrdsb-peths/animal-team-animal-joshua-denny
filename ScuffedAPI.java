/**
 * Write a description of class ScuffedAPI here.
 * 
 * @author Denny Ung
 * @version Version 1.0.0 ()
 */

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ScuffedAPI  
{
    // instance variables - replace the example below with your own
    private int x;
    private String API_URL = "https://api_ddc.scuffed.dev/";
    private String API_URI = "";
    HttpClient client;

    /**
     * Constructor for objects of class ScuffedAPI
     */
    public ScuffedAPI()
    {
        client = HttpClient.newHttpClient();
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int getLeaderboard()
    {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.example.com/data"))
            .header("Accept", "application/json")
            .GET()
            .build();
            
        return 0;
    }
}

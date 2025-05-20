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
import java.util.ArrayList;

public class ScuffedAPI  
{
    private String API_URL = "https://api_ddc.scuffed.dev/";
    private String API_URI = "v1/";
    HttpClient client;

    public ScuffedAPI()
    {
        client = HttpClient.newHttpClient();
    }

    
    public void getLeaderboard()
    {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL + API_URI + "getLeaderboard"))
            .header("Accept", "application/json")
            .GET()
            .build();
            
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenAccept(resp -> {
            System.out.println("Async status: " + resp.statusCode());
            System.out.println("Async body:   " + resp.body());
        });
        
        return;
    }
}

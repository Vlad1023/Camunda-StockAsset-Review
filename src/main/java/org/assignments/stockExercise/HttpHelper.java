package org.assignments.stockExercise;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpHelper {
    public static JSONObject SendRequest(String uri){
        HttpClient vClient=HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20)).followRedirects(HttpClient.Redirect.NORMAL).version(HttpClient.Version.HTTP_1_1).build();
        HttpRequest vRequest= HttpRequest.newBuilder()
                .headers("Content-Type", "application/json").timeout(Duration.ofSeconds(20))
                .uri(URI.create(uri)).build();

        try {
            HttpResponse<String> vResponse = vClient.send(vRequest, HttpResponse.BodyHandlers.ofString());
            if(vResponse.statusCode()== HttpServletResponse.SC_NOT_FOUND) {
                System.out.println("Error during http request. Not found");
                return null;
            }
            else {
                JSONObject jsonResult = new JSONObject(vResponse.body());
                return jsonResult;
            }
        } catch (IOException | InterruptedException | JSONException e) {
            System.out.println("Error during http request");
            return null;
        }
    }
}

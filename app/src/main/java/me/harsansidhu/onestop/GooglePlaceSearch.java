package me.harsansidhu.onestop;
/*
    Using Google Places API for Web Services, given latitude and longitude this class
    has method that return a data type (Container) which contains the name, latitude,
    and longitude of the closest business.
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GooglePlaceSearch{
    private static final String TAG = GooglePlaceSearch.class.getSimpleName();
	// URL for nearby search for Google Places
	private static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	
	// API Key for OneStop
	private static final String API_KEY = "AIzaSyDjZnWgy8a68jBEu4hccDpa3A3yjKElLj4";
	
	// Given a URL, return the response from API service as a string
	private static String sendGetRequest(URL url) {
		String response = null;
		
		try {
			// Open the connection and send an HTTP GET request
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			// Read the response and append it to a StringBuffer 
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine;
			StringBuffer jsonString = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				jsonString.append(inputLine);
			}			
			
			in.close();
			response = jsonString.toString();		
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		return response; 
	}
	
	// Given a latitude and longitude, return the closest open business as a String
	// If no business is open, return null
    public static ArrayList<Container> returnClosestOpen(double latitude, double longitude) {
        ArrayList<Container> resultList = new ArrayList<Container>();

        // Create the URL to send to Google Places API
        // See https://developers.google.com/places/webservice/search for a description of the parameters
        try {
            URL searchURL = new URL(PLACES_API_URL
                    + "location="
                    + String.valueOf(latitude) + "," + String.valueOf(longitude)
                    + "&rankby=distance"
                    + "&types=food"
                    + "&opennow"
                    + "&key="
                    + API_KEY);

            // Make a Http GET request to the Google Places API and retrieve the response
            String response = GooglePlaceSearch.sendGetRequest(searchURL);

            try {
                // Convert the String into a JSON Object and create a JSON array of the businesses returned
                JSONObject jsonresponse = new JSONObject(response);
                JSONArray  closest = jsonresponse.getJSONArray("results");

                for (int i = 0; i < closest.length(); i++) {
                    // Create a container object for each business and store in the arraylist
                    String businessName      = closest.getJSONObject(i).getString("name");
                    double businessLatitude  = (double) closest.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").get("lat");
                    double businessLongitude = (double) closest.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").get("lng");

                    // Create a container object to store the business name latitude and longitude
                    Container result = new Container(businessName, businessLatitude, businessLongitude);

                    resultList.add(result);
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return resultList;
    }
}
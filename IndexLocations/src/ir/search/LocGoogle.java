/**
 * 
 */
package ir.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author roide
 *
 */
public class LocGoogle {
	
	public static void main(String[] args) {
		getLatLon("New Delhi");
	}

	/**
	 * @param args
	 */
	public static synchronized String getLatLon(String address) {
		
		if(address.trim().length() <=0 ) {
			System.out.println("LocGoogle:Address empty");
			return "";
		}
		
		address = address.trim().replaceAll("\\s+", "+");
		String gurl = "http://maps.googleapis.com/maps/api/geocode/json?address="
				+ address + "&sensor=false";
		try {
			URL url = new URL(gurl);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);
			if(responseCode != 200) {
				System.out.println("Error in Http Requst for geocodes.");
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			//System.out.println("result:" + response);
			
			JSONObject json = (JSONObject)JSONValue.parse(response.toString());
			//JSONArray res = ((JSONArray)json.get("results")).get(0);
			JSONObject loc = (JSONObject) ((JSONObject)( (JSONObject) ((JSONArray)json.get("results")).get(0)).get("geometry") ).get("location");
			String lng = Double.toString( ( Double)loc.get("lng") );
			String lat = Double.toString( ( Double)loc.get("lat") );
			//System.out.println( lat + "," + lng  );
			//System.out.println(json);
			in.close();
			return lat + "," + lng ;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}

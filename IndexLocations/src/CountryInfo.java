import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.solr.common.SolrInputDocument;

/**
 * 
 */

/**
 * @author roide
 *
 */
public class CountryInfo {
	
	static HashMap<String, String> mapCountryCode = new HashMap<>();

	/**
	 * 
	 */
	public CountryInfo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static HashMap<String, String> load() {
		try {
			String file = "/home/roide/Fall2013/IR/3_project/OSM_data/countryInfo.txt" ;
			FileInputStream fstream = new FileInputStream(file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line ;
			while((line=br.readLine()) != null ) {
				if(line.startsWith("#"))
					continue;
				//System.out.println("" + line);
				String[] values = line.split("\t");	
				mapCountryCode.put(values[0].trim(), values[4].trim());
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e);
		}
		return mapCountryCode;
	}

}

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
			//String prevLine="";
			String line ;
			
			/*while((line=br.readLine()) != null) {
				if(!line.startsWith("#"))
					break;
				//prevLine = line;
			}*/
			//String headers[] = prevLine.split("\t");
			//for(int i=0;i<headers.length; i++) {
				//System.out.print("" + headers[i] + "\t");
			//}
			//System.out.println("\n");
			while((line=br.readLine()) != null ) {
				if(line.startsWith("#"))
					continue;
				//System.out.println("" + line);
				String[] values = line.split("\t");	
				//System.out.println("XX:" + values[0]);
				//System.out.println("" + values[1]);
				//System.out.println("" + values[2]);
				//System.out.println("" + values[3]);
				//System.out.println("Country:" + values[4]);
				mapCountryCode.put(values[0].trim(), values[4].trim());
				//System.out.println("capital:" + values[5]);
				//System.out.println("" + values[6]);
				//System.out.println("" + values[7]);
				//System.out.println("continent:" + values[8]);
				//System.out.println("" + values[9]);
				//System.out.println("" + values[10]);
				//System.out.println("" + values[11]);
				//System.out.println("" + values[12]);
				//System.out.println("" + values[13]);
				//System.out.println("" + values[14]);
				//System.out.println("" + values[15]);
				//System.out.println("" + values[16]);
				//System.out.println("" + values[17]);
				//System.out.println("" + values[18]);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e);
		}
		return mapCountryCode;
	}

}

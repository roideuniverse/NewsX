import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;


/**
 * 
 */

/**
 * @author roide
 *
 */
public class IndexLocations {

	/**
	 * 
	 */
	public IndexLocations() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String core = "geolocations";
			String urlString = "http://localhost:8983/solr/" + core;
			SolrServer server = new HttpSolrServer(urlString);
			
			// Open the file that is the first
			// command line parameter
			HashMap<String, String> cCode = CountryInfo.load();
			String file = "/home/roide/Fall2013/IR/3_project/OSM_data/allCountries.txt" ;
			//String file = "/home/roide/Fall2013/IR/3_project/OSM_data/cities1000.txt" ;
			FileInputStream fstream = new FileInputStream(file);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			long id=1;
			ArrayList<SolrInputDocument> docBuffer = new ArrayList<SolrInputDocument>();
			while((line=br.readLine()) != null) {
				//System.out.println("" + line);
				String[] values = line.split("\t");
				String featureClass = values[6];
				//System.out.println(featureClass);
				if(featureClass.equals("A") ) {
					//do nothing;
				} else {
					continue;
				}
				//System.out.println("Indexing..");
				SolrInputDocument doc = new SolrInputDocument();
				doc.addField("id", id++);
				doc.addField("name", values[1].trim() );
				if(featureClass.equals("A") ) {
					doc.addField("bigregion", values[1].trim() );
				} else if( featureClass.equals("P") ) {
					doc.addField("smallregion", values[1].trim() );
				}
				doc.addField("text", values[2].trim());
				
				String alternate = values[3].trim();
				if(alternate != null && alternate.length() != 0) {
					String alternates [] = alternate.split(",");
					for(int iter=0; iter<alternates.length; iter++) {
						//System.out.println("-->" + alternates[iter]);
						doc.addField("text", alternates[iter].trim());
					}
				}
				doc.addField("lat", values[4].trim());
				doc.addField("lon", values[5].trim());
				
				String code = values[8].trim();
				doc.addField("countrycode", code);
				
				//System.out.println(cCode);
				if(cCode.containsKey(code)) {
					//System.out.println(cCode.get(code));
					doc.addField("country", cCode.get(code));
				} else
					System.out.println("no ");

				docBuffer.add(doc);
				if( id%70000 == 0) {
					server.optimize();
					UpdateResponse response = server.add(docBuffer);
					System.out.println("" + (id*100 / 4000000) );
					docBuffer.clear();
				}
			}
			// Close the input stream
			in.close();
			server.commit();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}

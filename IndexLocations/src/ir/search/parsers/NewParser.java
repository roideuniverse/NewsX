/**
 * 
 */
package ir.search.parsers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;

import java.util.Collection;

import java.util.List;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

import ir.search.LocationFinder;
import ir.search.QueryParser;
import ir.search.wikipedia.WikipediaDocument;

/**
 * @author rahul
 *
 */
public class NewParser {
	/* */
	
	static long startTime =0;
	
	/* TODO: Implement this method */
	/**
	 * 
	 * @param filename
	 * @param docs
	 */
	public void parse(String filename, Collection<WikipediaDocument> docs) {
		//System.out.println("::Parser::parse\nFilename=" + filename+ " Docs_size=" + docs.size());
		if(filename == null || filename.isEmpty()) {
			return;
		}
		try {

			InputStreamReader inReader = new InputStreamReader(new FileInputStream(filename), "UTF-8");		
			BufferedReader reader = new BufferedReader(inReader);
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser saxParser = spf.newSAXParser();
			DefaultHandler handler = new XMLParser(docs);

			saxParser.parse(is, handler);

		} catch (IOException x) {
			System.err.println(x);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println("Parsing Terminated");
			e.printStackTrace();
		} 
	}
	
	/**
	 * Method to add the given document to the collection.
	 * PLEASE USE THIS METHOD TO POPULATE THE COLLECTION AS YOU PARSE DOCUMENTS
	 * For better performance, add the document to the collection only after
	 * you have completely populated it, i.e., parsing is complete for that document.
	 * @param doc: The WikipediaDocument to be added
	 * @param documents: The collection of WikipediaDocuments to be added to
	 */
	private void add(WikipediaDocument doc, Collection<WikipediaDocument> documents) {
		documents.add(doc);
	}
	
	public List<String> findLocation(String text)
    {
    	List<String> lstLocation=new ArrayList<>();
    	LocationFinder objLocationFinder=new LocationFinder();
    	try
    	{
    		lstLocation=objLocationFinder.getLocation(text);
    		
    	}
    	catch(Exception e)
    	{
    		System.err.println("Error in Class:method->WikiPediaParser:findLocation" + e.getMessage());
    	}
    	return lstLocation;
    }
    
    public List<String> findLatLong(List<String> lstLocation)
    {
    	List<String> lstLatLong=new ArrayList<>();
    	QueryParser objQueryParser=new QueryParser();
    	try
    	{
    		lstLatLong=objQueryParser.queryGeoLocationIndex(lstLocation);
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error in Class:method->WikiPediaParser:findLatLong" + e.getMessage());
    	}
    	return lstLatLong;
    }
	
	/**
	 * @author kaushiks
	 * 
	 */
	public class XMLParser extends DefaultHandler {

		private static final String TAG_DOC = "doc";
		private Collection<WikipediaDocument> mCollWikiDoc;
		
		private WikipediaDocument mWikiDoc;
		boolean mDoc = true;
		private String mText="";

		XMLParser(Collection<WikipediaDocument> collWikiDoc) {
			mCollWikiDoc = collWikiDoc;
		}

		public void startElement(String namespaceURI, String localName,
				String qName, Attributes atts) throws SAXException {
			List<String> lstLocation = new ArrayList<>();
			List<String> lstLatLong= new ArrayList<>();
			//System.out.println("::XMLParser::startElement::" + qName + ":" + atts );
			if(qName.equalsIgnoreCase(TAG_DOC)) {
				
				mDoc = true;
				mWikiDoc = new WikipediaDocument();
				
				String id = atts.getValue("id");
				if(id != null && id.trim().length() >0 ) {
					mWikiDoc.setId(Integer.parseInt(id));
				}
				String url = atts.getValue("url");
				if(url != null && url.trim().length() >0 ) {
					mWikiDoc.setWikilink(url);
				}
				String title = atts.getValue("title");
				if(title != null && title.trim().length() >0 ) {
					mWikiDoc.setTitle(title);
					//find location and set to Wiki document
					lstLocation=findLocation(title);
					mWikiDoc.setLocation(lstLocation);
					
					//find LatLong and set to Wiki document
					lstLatLong=findLatLong(lstLocation);
					mWikiDoc.setLatLong(lstLatLong);
					
				}
				String timestamp = atts.getValue("timestamp");
				if(timestamp != null && timestamp.trim().length() >0 ) {
					mWikiDoc.setTimestamp(timestamp);
				}
			}
		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			List<String> lstLocation = new ArrayList<>();
			List<String> lstLatLong= new ArrayList<>();
			//System.out.println("::XMLParser::EndElement::" + qName + "\n");
			if(qName.equalsIgnoreCase(TAG_DOC)) {
				//System.out.println(mText);
				mWikiDoc.setText(mText);
				
				//find location and set to Wiki document
				//lstLocation=findLocation(mText);
				//mWikiDoc.setLocation(lstLocation);
				
				//find LatLong and set to Wiki document
				//lstLatLong=findLatLong(lstLocation);
				//mWikiDoc.setLatLong(lstLatLong);
				
				// Add Wiki docs to collection
				add(mWikiDoc, mCollWikiDoc);
				
				
				// Set all objects to null
				mDoc = false;
				mWikiDoc = null;
				mText = "";
				
			}
		}

		public void characters(char ch[], int start, int length)
				throws SAXException {
			//System.out.println("::XMLParser::characters->" + new String(ch, start, length));
			if(mDoc) {
				mText = mText + new String(ch, start, length);
			}

		}
		
		
		
	}
	
}

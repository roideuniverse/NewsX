/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.parsers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;

/**
 * @author nikhillo
 *
 */
public class NewParser {
	/* */
	private static int count = 0;
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

			InputStreamReader inReader = new InputStreamReader(
					new FileInputStream(filename), "UTF-8");
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
		} finally {
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
	private synchronized void add(WikipediaDocument doc, Collection<WikipediaDocument> documents) {
		documents.add(doc);
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
		private String mText;

		XMLParser(Collection<WikipediaDocument> collWikiDoc) {
			mCollWikiDoc = collWikiDoc;
		}

		public void startElement(String namespaceURI, String localName,
				String qName, Attributes atts) throws SAXException {
			System.out.println("::XMLParser::startElement::" + qName + ":" + atts );
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
				}
				String timestamp = atts.getValue("timestamp");
				if(timestamp != null && timestamp.trim().length() >0 ) {
					mWikiDoc.setTimestamp(timestamp);
				}
			}
		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			System.out.println("::XMLParser::EndElement::" + qName + "\n");
			if(qName.equalsIgnoreCase(TAG_DOC)) {
				//System.out.println(mText);
				mWikiDoc.setText(mText);
				mCollWikiDoc.add(mWikiDoc);
				mDoc = false;
				mWikiDoc = null;
				mText = "";
				//throw new SAXException();
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
	private static int xxxyy=0;
}

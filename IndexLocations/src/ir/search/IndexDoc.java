package ir.search;

import ir.search.wikipedia.WikipediaDocument;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;


public class IndexDoc {
	
	public void implementIndexing(List<WikipediaDocument> doc) throws SAXException,ParserConfigurationException,IOException,SolrServerException
	{
		try{
				System.out.println("Index Started");
		
				  HttpSolrServer server = new HttpSolrServer(FileUtil.getUrl());
				  server.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
				  server.setConnectionTimeout(5000); // 5 seconds to establish TCP
				  
				  server.setParser(new XMLResponseParser()); // binary parser is used by default
				 
				  server.setSoTimeout(1000);  // socket read timeout
				  server.setDefaultMaxConnectionsPerHost(100);
				  server.setMaxTotalConnections(100);
				  server.setFollowRedirects(false);  // defaults to false
				 
				  server.setAllowCompression(true);
				  server.addBeans(doc);
				  server.commit();
		}
		catch(Exception e)
		{
			
			System.out.println("Message:"+e.getMessage());
		}
	}

}

package ir.search;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;


public class QueryParser {
	
	
	public  List<String> queryGeoLocationIndex(List<String> location) 
	{
		  
		  List<String> lstLatLong=new ArrayList<>();
		  try
		  {
			  Iterator itr=location.iterator();
			  while(itr.hasNext())
			  {
				  SolrDocumentList list = getLatLonIndex(itr.next().toString());
				  for(int i=0;i<list.size();i++)
				  {
					  lstLatLong.add(list.get(i).get(FileUtil.getLatitudeField())+","+list.get(i).get(FileUtil.getLongitudeField()));
				  }
			  }
		  }
		  catch (Exception e) {
			  System.out.println("Error Message:"+e.getMessage());
			}
		 return lstLatLong;
	}
	
	private SolrDocumentList getLatLonIndex(String location)
	{
		// String url = "http://192.168.2.4:8983/solr/geolocations";
		 SolrServer server = new HttpSolrServer(FileUtil.getLocationUrl());
		 SolrDocumentList list = new SolrDocumentList();
		 
		  ModifiableSolrParams params = new ModifiableSolrParams();
		try
		{
			  params.set(CommonParams.Q, location);
			  params.set(CommonParams.START, "0");
			  params.set(CommonParams.ROWS, "10");
			  QueryResponse rsp=server.query(params);
			  list=rsp.getResults();
		}
		catch(SolrServerException e)
		  {
			  
			  System.out.println("Error Message:"+e.getMessage());
		  }
		return list;
	}

}

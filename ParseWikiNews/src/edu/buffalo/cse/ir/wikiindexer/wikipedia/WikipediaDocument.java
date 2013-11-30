/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author nikhillo
 * This class acts as a container for a single wikipedia page.
 * It has a structure analogous to how a page looks in a browser
 * The class has various getters and setters for the different fields
 * You would call the setters from within your parser code to 
 * populate the fields as you parse them.
 * 
 * The getters would be used by your indexer(s) as and when you implement them
 *
 */
public class WikipediaDocument {

	private String timestamp;
	private int id;
	private String title;
	private String wikilink;
	private String text;
	
	public WikipediaDocument() {
	}
	
	public WikipediaDocument(int id, String url, String title, String timestamp, String body) {
		this.id = id;
		this.wikilink = url;
		this.title = title;
		this.timestamp = timestamp;
		this.text = body;	
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWikilink() {
		return wikilink;
	}

	public void setWikilink(String wikilink) {
		this.wikilink = wikilink;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
/**
 * 
 */
package ir.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author rahul This class provides utility file lookup methods that can be
 *         safely used without worrying about OS dependencies.
 */
public class FileUtil {
	
	private static final String strPath = "lib/";
	private static final String strUrl = "http://localhost:8983/solr/newsx";
	private static final String strLocationUrl = "http://192.168.2.4:8983/solr/geolocations";
	private static final String strLatitudeField = "lat";
	private static final String strLongitudeField= "lon";
			
	public static String getPath()
	{
		return strPath;
	}
	
	public static String getUrl()
	{
		return strUrl;
	}
	public static String getLocationUrl()
	{
		return strLocationUrl;
	}
	
	public static String getLatitudeField()
	{
		return strLatitudeField;
	}
	
	public static String getLongitudeField()
	{
		return strLongitudeField;
	}
}

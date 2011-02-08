package com.followme;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.location.Location;
import android.util.Log;

public class Server {
	
	
	private String m_host = "http://suismoi.heroku.com"; //"http://suismoi.heroku.com";// "http://10.0.2.2:3000";

	String getHost(){
		return m_host;
	}
	
	 String getCode()
	  {
	    HttpClient httpClient = new DefaultHttpClient();
	    try
	    {
	      String url = m_host+"/balises/activate.xml";
	      Log.d( "followme", "performing get " + url );

	      HttpGet method = new HttpGet( new URI(url) );
	      HttpResponse response = httpClient.execute(method);
	      if ( response != null )
	      {
	        //Log.i( "followme", "received " + getResponse(response.getEntity()) );
	        return getResponse(response.getEntity());
	      
	      }
	      else
	      {
	        Log.i( "followme", "got a null response" );
	      }
	    } catch (IOException e) {
	      Log.e( "ouch", "!!! IOException " + e.getMessage() );
	    } catch (URISyntaxException e) {
	      Log.e( "ouch", "!!! URISyntaxException " + e.getMessage() );
	    }
	    catch (Exception e) { Log.d("getCode","Exception"); }
		return null;
	  }
	 
	  private String getResponse( HttpEntity entity )
	  {
	    String response = null;

	    try
	    {
	    	Document doc = null;
	     	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	     	DocumentBuilder db = dbf.newDocumentBuilder();
	     	doc = db.parse(entity.getContent());
	     	Node code = doc.getElementsByTagName("code").item(0);
	     	response = code.getChildNodes().item(0).getNodeValue();
	     	
	    }
	    catch (IOException ioe) {
	     		Log.d("Server",ioe.getLocalizedMessage());
	     	} 
	    catch (ParserConfigurationException pce) {
	     		Log.d("Server","Parse Error");    	
	     	} 
	    catch (SAXException se) {
	     		Log.d("Server","SAXException");
	     	}
	    catch (Exception e) { Log.d("getResponse","Exception"); }

	     	
	    return response;
	  }
	
	  /** Send GPS position with code regulary to the server*/
	
		
		void updateLocation(String code,Location location)
		  {
		    HttpClient httpClient = new DefaultHttpClient();
		    try
		    {
		      String url = m_host+"/balises/signal?code="+code;
		      String lat = "&lat="+location.getLatitude();
		      String lng = "&lng="+location.getLongitude();
		      Log.d( "followme", "performing get " + url );

		      HttpGet method = new HttpGet( new URI(url+lat+lng) );
		      httpClient.execute(method);
		     
		    } catch (IOException e) {
		      Log.e( "ouch", "!!! IOException " + e.getMessage() );
		    } catch (URISyntaxException e) {
		      Log.e( "ouch", "!!! URISyntaxException " + e.getMessage() );
		    }
		    catch (Exception e) { Log.d("updateLocation","Exception"); }

		  }
		
	
	
}

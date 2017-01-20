package rest.helpers.strategy;
/**
 * provide a class for interacting with jersey request
 */
// example using jersey : http://blogs.sun.com/enterprisetechtips/entry/consuming_restful_web_services_with
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.io.FileInputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import javax.ws.rs.core.*;

public abstract class HttpRequestUtil {
	
	protected final Client client = Client.create(); // one time deal to create and use this.
	protected String requesterr = null;
	protected Logger logger = Logger.getLogger(HttpRequestUtil.class);
	
	final static HttpHostnameVerifier hv = new HttpHostnameVerifier();
	
	//final static HostnameVerifier HV = AbstractVerifier.DEFAULT;

	protected ClientConfig getSSLConfig() throws Exception
    {
    	
    	TrustManager[] trustAllCerts = new TrustManager[] { 
    			new X509TrustManager() {         
    				public X509Certificate[] getAcceptedIssuers() {   
    					logger.debug("called issuers");
    					return null;
    				}          
    				public void checkClientTrusted(X509Certificate[] certs, String authType) {   
    					logger.debug("called client trusted");
    					return;         
    				}          
    				public void checkServerTrusted(X509Certificate[] certs, String authType) {     
    					logger.debug("called server trusted");
    					return;         
    				}
					public boolean isClientTrusted(X509Certificate[] arg0) {
						logger.debug("called is client trusted");
						return true;
					}
					public boolean isServerTrusted(X509Certificate[] arg0) {
						logger.debug("called is server trusted");
						return false;
					}     
    			}
    	}; 
    	
    	ClientConfig config = new DefaultClientConfig();
    	
//    	KeyStore trustStore;    
//    	trustStore = KeyStore.getInstance("JKS");     
//    	logger.debug("using ssl trust store: "  + System.getProperty("javax.net.ssl.trustStore"));
//    	trustStore.load(new FileInputStream(System.getProperty("javax.net.ssl.trustStore")),"changeit".toCharArray());     
//    	TrustManagerFactory tmf=TrustManagerFactory.getInstance("SunX509");
    	//KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    	//char[]pwd= null;
    	//kmf.init(trustStore, pwd);
    	//tmf.init(trustStore); 

    	SSLContext ctx = SSLContext.getInstance("SSL");
//    	SSLContext ctx = SSLContext.getInstance("TLS");
    	//ctx.init(null, tmf.getTrustManagers(), null);
    	ctx.init(null,trustAllCerts, null);
    	//ctx.init((KeyManager)kmf, trustAllCerts, null);
    	
    	config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(hv, ctx));
    	return config;
    	 

    }
    /**
     * configure the client to have a proxy and port
     * @param proxy
     * @param port
     */
   public static void configureProxyClient(String proxy, String port)
   {
	   if((proxy.length() != 0) && (port.length() != 0))
       {
		   //TODO, need to find where the HTTP client is, only worth doing if we have to traverse a proxy
		   /*
		   DefaultApacheHttpClientConfig config = new DefaultApacheHttpClientConfig();
		   **
		   * config.getProperties().put(DefaultApacheHttpClientConfig.PROPERTY_PROXY_URL, 
		   * 			"http://"+proxy+":"+port);
		       //password would be
		        * config.getState().setProxyCredentials(AuthScope.ANY_REALM, proxy, parseInt(port),
		        * 					username, password);
		   *  client = Client.create(config);
		   *
		   */
	   }
   }
/**
 * make an http based request to interact with webservice.
 * All POST request are expected to be of type JSON, we will serialize the JSON object from a string.
 * Please only provide integers, strings, or string arrays.  String arrays should be ; seperated.
 * @param url - base url of the destination web service
 * @param Data - can be query string or json string for sending data
 * @param method - see HttpRequestType , used for specifying the type of http request
 * @param httpRequestCallback - call back function that implements json/gson wrapper, such as a request object.
 */
    public abstract void makeRequest(String url, String Data, HttpRequestType method, final HttpRequestCallback httpRequestCallback); 

}

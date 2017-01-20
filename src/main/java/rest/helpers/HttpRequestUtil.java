package rest.helpers;
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

public class HttpRequestUtil {
	
	final static Client client = Client.create(); // one time deal to create and use this.
	static private String requesterr = null;
	private static Logger logger = Logger.getLogger(HttpRequestUtil.class);
	
	final static HttpHostnameVerifier hv = new HttpHostnameVerifier();
	
	//final static HostnameVerifier HV = AbstractVerifier.DEFAULT;

	private static ClientConfig getSSLConfig() throws Exception
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
    public static void makeRequest(String url, String Data, HttpRequestType method, final HttpRequestCallback httpRequestCallback) {
    	logger.debug("calling makeRequest for url : " + url);
        try {
        	Client reqClient = null;
        	if(url.indexOf("https") >= 0)
        	{
        		reqClient = Client.create(getSSLConfig());
        	}
        	else
        		reqClient = client;
        	// RequestBuilder requestBuilder = new RequestBuilder(method, url);
        	URI ur = new URI(url);
        	
        	WebResource webResource = reqClient.resource(ur);
        	
        	if(method == HttpRequestType.GET)
        	{
        		
        		if(Data != null )
        		{
        			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        			if(Data.startsWith("?"))
        			{
        				String [] query  = Data.split("\\?");
        				for(String part : query)
        				{
        					if(! part.equals(""))
        					{
        						String [] querypart = part.split("&");
        						for(String qparam : querypart)
        						{
        							String param = qparam.split("=")[0];
        							String value = qparam.split("=")[1];
        							queryParams.add(param, value);
        							logger.debug(String.format("query parameter added param = %s value = %s", param, value));
        							
        						}
        					}
        					
        				}
        			}
        			logger.debug("webResource query");
        			httpRequestCallback.ResponseRecieved(webResource.queryParams(queryParams).get(String.class));
        		}
        		else
        		{
        			httpRequestCallback.ResponseRecieved(webResource.get(String.class));
        		}
        	} 
        	else if (method == HttpRequestType.PUT)
        	{
             	if(Data != null )
        		{
        			//MultivaluedMap<String, String> formData = new MultivaluedMapImpl();  works with raw types
        			//Form formData = new Form();   works with html data
             		
             		//JSONObject formData = new JSONObject();
        			//formData.getJSONObject(Data);
        			
    			
//        			logger.debug("webResource query: " + Data);
//        			ClientResponse resp = webResource.type("application/json").put(ClientResponse.class, Data);
        			
    				String [] query  = Data.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\\n", "").split("\\,");
             		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
             		for (int i = 0; i < query.length; i++) {
             			String key = query[i].replaceAll("\"", "").split("\\:")[0].trim();
             			String value = query[i].replaceAll("\"", "").split("\\:")[1].trim();
             			formData.add(key, value);
					}
             		
        			logger.debug("webResource query: " + Data);
        			ClientResponse resp = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, formData);

        			if(resp.getStatus() != 200)
        			{
        				requesterr = "Problem with POST response (" + resp.getStatus()+ ") : " + resp.getEntity(String.class);
        				httpRequestCallback.Error(requesterr, new Exception("invalid response : " + resp.getStatus()));
        			}
        			else
        			{
        				httpRequestCallback.ResponseRecieved(resp.getEntity(String.class));
        			}
        			
        		}
        		else
        		{
        			String errmsg = "No post data was provided, this must be provided as comma seperated list of name value paris sperated by = signs";
        			logger.error(errmsg);
        			requesterr = errmsg;
        			
        		}
        	}
        	else if (method == HttpRequestType.POST)
            {
        		
        		/*
        		 * 
        		 * POST Request: A POST request is a syntactic combination of a GET request and a PUT request, that is, you can use a POST request to send an entity to a web resource and receive another entity. Use the post() method in the WebResource class to submit an HTTP POST request to the web resource. For example, the following code submits a POST request with query parameters and URL-encoded form data:
        		 * 
        		 * 
        		 *   MultivaluedMap formData = new MultivaluedMapImpl();
					  formData.add("name1", "val1");
					  formData.add("name2", "val2");
					  ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class, formData);
        		 */
        		/*
        		 * The data can be provided as a comma seperated list of name value pairs seperated by = signs
        		 */
             	if(Data != null )
        		{
        			//MultivaluedMap<String, String> formData = new MultivaluedMapImpl();  works with raw types
        			//Form formData = new Form();   works with html data
             		
    				String [] query  = Data.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\\n", "").split("\\,");
             		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
             		for (int i = 0; i < query.length; i++) {
             			String key = query[i].replaceAll("\"", "").split("\\:")[0].trim();
             			String value = query[i].replaceAll("\"", "").split("\\:")[1].trim();
             			formData.add(key, value);
					}
             		
        			logger.debug("webResource query: " + Data);
//        			ClientResponse resp = webResource.type("application/json").post(ClientResponse.class, Data);
        			ClientResponse resp = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, formData);
//        			ClientResponse resp = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class, Data);
        			
        			if(resp.getStatus() != 200 && resp.getStatus() != 201)
        			{
        				requesterr = "Problem with POST response (" + resp.getStatus()+ ") : " + resp.getEntity(String.class);
        				httpRequestCallback.Error(requesterr, new Exception("invalid response : " + resp.getStatus()));
        			}
        			else
        			{
        				httpRequestCallback.ResponseRecieved(resp.getEntity(String.class));
        			}
        			
        		}
        		else
        		{
        			String errmsg = "No post data was provided, this must be provided as comma seperated list of name value paris sperated by = signs";
        			logger.error(errmsg);
        			requesterr = errmsg;
        			
        		}
        		
        	}
        	else if(method == HttpRequestType.DELETE)
        	{
             	if(Data != null )
        		{
        			logger.debug("webResource query: " + Data);
        			ClientResponse resp = webResource.type("application/json").delete(ClientResponse.class, Data);

        			if(resp.getStatus() != 200)
        			{
        				requesterr = "Problem with POST response (" + resp.getStatus()+ ") : " + resp.getEntity(String.class);
        				httpRequestCallback.Error(requesterr, new Exception("invalid response : " + resp.getStatus()));
        			}
        			else
        			{
        				httpRequestCallback.ResponseRecieved(resp.getEntity(String.class));
        			}
        		}
        	}
        	else
        	{
        		logger.error("not supported");
        		requesterr = "havn't implemented";
        	}
      
        } catch (Exception e) {
        	logger.debug("Exception in makeRequest");
        	logger.debug(e);
        	httpRequestCallback.Error(requesterr,e);

        }

    }

}

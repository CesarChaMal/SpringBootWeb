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

public class HttpRequestUtilPost extends HttpRequestUtil {
	
	/**
	 * make an http based request to interact with webservice. All POST request
	 * are expected to be of type JSON, we will serialize the JSON object from a
	 * string. Please only provide integers, strings, or string arrays. String
	 * arrays should be ; seperated.
	 * 
	 * @param url
	 *            - base url of the destination web service
	 * @param Data
	 *            - can be query string or json string for sending data
	 * @param method
	 *            - see HttpRequestType , used for specifying the type of http
	 *            request
	 * @param httpRequestCallback
	 *            - call back function that implements json/gson wrapper, such
	 *            as a request object.
	 */
	public void makeRequest(String url, String Data, HttpRequestType method,
			final HttpRequestCallback httpRequestCallback) {
		logger.debug("calling makeRequest for url : " + url);
		try {
			Client reqClient = null;
			if (url.indexOf("https") >= 0) {
				reqClient = Client.create(getSSLConfig());
			} else
				reqClient = client;
			// RequestBuilder requestBuilder = new RequestBuilder(method, url);
			URI ur = new URI(url);

			WebResource webResource = reqClient.resource(ur);

			if (method == HttpRequestType.POST) {

				/*
				 * 
				 * POST Request: A POST request is a syntactic combination of a
				 * GET request and a PUT request, that is, you can use a POST
				 * request to send an entity to a web resource and receive
				 * another entity. Use the post() method in the WebResource
				 * class to submit an HTTP POST request to the web resource. For
				 * example, the following code submits a POST request with query
				 * parameters and URL-encoded form data:
				 * 
				 * 
				 * MultivaluedMap formData = new MultivaluedMapImpl();
				 * formData.add("name1", "val1"); formData.add("name2", "val2");
				 * ClientResponse response =
				 * webResource.type("application/x-www-form-urlencoded").post(
				 * ClientResponse.class, formData);
				 */
				/*
				 * The data can be provided as a comma seperated list of name
				 * value pairs seperated by = signs
				 */
				if (Data != null) {
					// MultivaluedMap<String, String> formData = new
					// MultivaluedMapImpl(); works with raw types
					// Form formData = new Form(); works with html data

					String[] query = Data.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\\n", "")
							.split("\\,");
					MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
					for (int i = 0; i < query.length; i++) {
						String key = query[i].replaceAll("\"", "").split("\\:")[0].trim();
						String value = query[i].replaceAll("\"", "").split("\\:")[1].trim();
						formData.add(key, value);
					}

					logger.debug("webResource query: " + Data);
					// ClientResponse resp =
					// webResource.type("application/json").post(ClientResponse.class,
					// Data);
					ClientResponse resp = webResource.type(MediaType.APPLICATION_FORM_URLENCODED)
							.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, formData);
					// ClientResponse resp =
					// webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class,
					// Data);

					if (resp.getStatus() != 200 && resp.getStatus() != 201) {
						requesterr = "Problem with POST response (" + resp.getStatus() + ") : "
								+ resp.getEntity(String.class);
						httpRequestCallback.Error(requesterr, new Exception("invalid response : " + resp.getStatus()));
					} else {
						httpRequestCallback.ResponseRecieved(resp.getEntity(String.class));
					}

				} else {
					String errmsg = "No post data was provided, this must be provided as comma seperated list of name value paris sperated by = signs";
					logger.error(errmsg);
					requesterr = errmsg;

				}

			} else {
				logger.error("not supported");
				requesterr = "havn't implemented";
			}

		} catch (Exception e) {
			logger.debug("Exception in makeRequest");
			logger.debug(e);
			httpRequestCallback.Error(requesterr, e);

		}

	}

}

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

public class HttpRequestUtilGet extends HttpRequestUtil {

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

			if (method == HttpRequestType.GET) {

				if (Data != null) {
					MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
					if (Data.startsWith("?")) {
						String[] query = Data.split("\\?");
						for (String part : query) {
							if (!part.equals("")) {
								String[] querypart = part.split("&");
								for (String qparam : querypart) {
									String param = qparam.split("=")[0];
									String value = qparam.split("=")[1];
									queryParams.add(param, value);
									logger.debug(
											String.format("query parameter added param = %s value = %s", param, value));

								}
							}

						}
					}
					logger.debug("webResource query");
					httpRequestCallback.ResponseRecieved(webResource.queryParams(queryParams).get(String.class));
				} else {
					httpRequestCallback.ResponseRecieved(webResource.get(String.class));
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

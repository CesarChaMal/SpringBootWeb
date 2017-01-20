package rest.helpers.strategy;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class HttpRequestGet implements HttpRequestCallback, HttpRequestMethod {
	
	private boolean haserrors;
	private String response;
	private Throwable respexcept;
	private RequestData wrdata = null;

	private static Logger logger = Logger.getLogger(HttpRequestUtil.class.getName());
	private Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

	private String urlpath = "";
	private String querystring = "";
	private String data = "";
	private HttpRequestCallback httpRequestCallback = this;

	public HttpRequestCallback getHttpRequestCallback() {
		return httpRequestCallback;
	}

	public void setHttpRequestCallback(HttpRequestCallback httpRequestCallback) {
		this.httpRequestCallback = httpRequestCallback;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUrlpath() {
		return urlpath;
	}

	public void setUrlpath(String urlpath) {
		this.urlpath = urlpath;
	}

	public String getQuerystring() {
		return querystring;
	}

	public void setQuerystring(String querystring) {
		this.querystring = querystring;
	}

	public void setData(String data) {
		this.data = data;
	}

	public HttpRequestGet(String url, String data)
	{
		this.urlpath = String.format(url);
		this.data = data;
//		this.makeRequest(urlPath, json, this);	
	}
	
	public void makeRequest()
	{
		HttpRequestUtilGet request = new HttpRequestUtilGet();
		request.makeRequest(urlpath, querystring, HttpRequestType.GET, httpRequestCallback);

		if(!this.hasError())
		{
			// no errors, lets do some work and populate this object
			wrdata = new RequestData(this.response);
		}
		else
		{
			logger.error("unable to get work response : " + urlpath);
		}
	}
	
	/**
	 * @return - the value for workrequest data
	 */
	public RequestData getData()
	{
		return wrdata;
	}
	
	/**
	 * return the response string
	 */
	public String toString()
	{
		return response;
	}
	
	/**
	 * used by call back to populate response strings
	 */
	public void ResponseRecieved(String resp)
	{
		response = resp;
		// parse the string into gson object
		// perform any parse or generation of the object
	}

	/**
	 * store the error received from the http request.
	 * 
	 * @param request - request error 
	 * @param exception - exception from http request
	 */
	public void Error(String request, Throwable exception)
	{
		//special handling?
		logger.debug(request);
		logger.debug(exception.toString());
		haserrors  = (exception!=null);
		respexcept = exception;

	}
	
	/**
	 * respond with status of request object if it has errors.
	 * @return true or false, true means there are errors.
	 */
	public boolean hasError()
	{
		return haserrors;
	}
	
	/**
	 * @return - return exception
	 */
	public Throwable getException()
	{
		return respexcept;
	}

	/**
	 * use gson object to serialize one of these to life
	 * @author hp-employee
	 *
	 */
	public class RequestData {

		private Map<String, Map<String, String>> response = null;

		private boolean isInitialized = false;
		
		public RequestData(String stream)
		{
			// for simple responses, not for xl spaceship complex responses 
//			WorkRequestPrimative wrp = new WorkRequestPrimative();
//			Gson gson = new Gson();
//			wrp = gson.fromJson(stream, WorkRequestPrimative.class);
			response = new HashMap<>();
			Type type = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
			gson.toJson(response, type);
//			init(wrp);
		}
		
		public Map<String, Map<String, String>> getResponse() {
			return response;
		}

		public void setResponse(Map<String, Map<String, String>> response) {
			this.response = response;
		}

		public boolean isInitialized() {
			return isInitialized;
		}

		public void setInitialized(boolean isInitialized) {
			this.isInitialized = isInitialized;
		}

	}

}

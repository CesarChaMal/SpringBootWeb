package rest.helpers.strategy;

public class HttpRequestManager implements HttpRequestMethod {

	private HttpRequestMethod request;
	
	public void setMethod(HttpRequestMethod  method){
		this.request = method;
	}

	@Override
	public void makeRequest() {
		this.request.makeRequest();
	}
}

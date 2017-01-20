package rest.helpers;

/**
 * Used to figure out what kind of web request to make, used in rest.helpers
 */
public enum HttpRequestType {
	PUT ("put"),
    GET ("get"),
    POST ("post"),
    DELETE ("delete");
	
	HttpRequestType(String methodname) {
        this.method = methodname;
   }
    private final String method;
    /**
     * return the method string value for the entity type.
     * @return
     */
    public String method()   { return method; }
    
}

package rest.helpers.strategy;

/**
 * Use the interface to provide methods for interacting with jersey classes
 * @author Cesar Chavez
 *
 */
public interface HttpRequestCallback{

    void ResponseRecieved(String response);
    void Error(String requesterr, Throwable e);
    boolean hasError();
}


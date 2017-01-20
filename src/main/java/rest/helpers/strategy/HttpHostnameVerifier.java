package rest.helpers.strategy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;
import org.apache.log4j.Logger;

public class HttpHostnameVerifier implements HostnameVerifier {
	private static Logger logger = Logger.getLogger(HttpHostnameVerifier.class);
	public boolean verify(String hostname, SSLSession session)
	{
		logger.debug("verifying : " + hostname);
		return true;
	}

}


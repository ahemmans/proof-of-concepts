package us.states.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import us.states.report.ReportGenerator;

public class BaseSoapClient extends ReportGenerator {

	protected SOAPConnection createConnection() throws UnsupportedOperationException, SOAPException {
		return SOAPConnectionFactory.newInstance().createConnection();
	}
 
	protected SOAPMessage createMessage() throws SOAPException {
		return MessageFactory.newInstance().createMessage();
	}
	
	protected SOAPMessage sendMessage(String endPoint, SOAPMessage msg) throws SOAPException, MalformedURLException {
		SOAPConnection con = createConnection();
		URL urlEndpoint = new URL(endPoint);
		return con.call(msg, urlEndpoint);
	}

}

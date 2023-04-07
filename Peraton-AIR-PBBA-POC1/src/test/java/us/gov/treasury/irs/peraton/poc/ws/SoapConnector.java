package us.gov.treasury.irs.peraton.poc.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

//import com.tier3llc.countyservice.GetCountiesResponse;


public class SoapConnector extends WebServiceGatewaySupport {

	@Value("${ws.url}")
	private String wsUrl;
	
	@Autowired
	WebServiceTemplate webServiceTemplate;
	
	public Object callWebService(String url, Object request){
	    return webServiceTemplate.marshalSendAndReceive(url, request);
	}
	
	public Object callWebService(Object request){
	    return webServiceTemplate.marshalSendAndReceive(wsUrl, request);
	}
	
	//public GetCountiesResponse sendRequest(Object request) {
	//	return (GetCountiesResponse) webServiceTemplate.marshalSendAndReceive(request, new SoapActionCallback(wsUrl));
	//}
}

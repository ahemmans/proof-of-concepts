package us.states.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.tier3llc.countyservice.County;
import com.tier3llc.countyservice.GetCountiesRequest;
import com.tier3llc.countyservice.GetCountiesResponse;

public class CountyClient extends WebServiceGatewaySupport {

	GetCountiesRequest request;
	GetCountiesResponse response;
	County county;
	List<County> counties;
	
	@Value("${ws.url}")
	private String wsUrl;
	
	@Autowired 
	WebServiceTemplate webServiceTemplate;
	
	public GetCountiesResponse getCountyResponseById(int id) {
		request = new GetCountiesRequest();
		request.setId(id);
		request.setFilename("fileattach.txt");
		request.setMimetype("text/plain");
		response = (GetCountiesResponse) webServiceTemplate
				.marshalSendAndReceive(request, new SoapActionCallback(wsUrl));
		
		return response;
	}
	
	public GetCountiesResponse getCountyResponseByState(String state) {
		request = new GetCountiesRequest();
		request.setState(state.toUpperCase());
		response = (GetCountiesResponse) webServiceTemplate
				.marshalSendAndReceive(request, new SoapActionCallback(wsUrl));
		
		return response;
	}
	
	public County getCountyById(int id) {
		response = getCountyResponseById(id);
		county = response.getCounty().get(0);
		return county;
	}
	
	public List<County> getCountiesByState(String state) {
		response = getCountyResponseByState(state);
		counties = response.getCounty();
		return counties;
	}
	
	public void testResponse() {
		int id=0;
		request = new GetCountiesRequest();
		request.setId(id);
		Object resp = webServiceTemplate.marshalSendAndReceive(request, new SoapActionCallback(wsUrl));
	}
}

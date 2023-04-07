package us.states.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tier3llc.countyservice.GetCountiesRequest;
import com.tier3llc.countyservice.GetCountiesResponse;

import us.states.ws.SoapConnector;

@Service
public class XmlUtils {

	@Autowired
	SoapConnector soapConnector;
	
	public GetCountiesRequest convertRequestFile(String fileName) throws JAXBException {		
		return convertRequestFile(new File(fileName));
	}
	
	public GetCountiesResponse convertResponseFile(String fileName) throws JAXBException {		
		return convertResponseFile(new File(fileName));
	}
	
	public GetCountiesRequest convertRequestFile(File file) throws JAXBException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(GetCountiesRequest.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		
		GetCountiesRequest request = (GetCountiesRequest) jaxbUnmarshaller.unmarshal(file);
		return request;
	}
	
	public GetCountiesResponse convertResponseFile(File file) throws JAXBException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(GetCountiesResponse.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		
		GetCountiesResponse response = (GetCountiesResponse) jaxbUnmarshaller.unmarshal(file);
		return response;
	}
	
	public void getObject() {
		GetCountiesRequest request = new GetCountiesRequest();
		request.setId(1);
		Object response = soapConnector.callWebService(request);
		if (response instanceof JAXBElement<?>) {
			JAXBElement<?> jaxbElement = (JAXBElement<?>) response;
 			if (jaxbElement.getValue() instanceof GetCountiesResponse) {
 				response = (GetCountiesResponse) jaxbElement.getValue();
 			}
		}
	}

}

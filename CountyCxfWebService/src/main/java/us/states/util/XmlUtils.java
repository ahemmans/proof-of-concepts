package us.states.util;

import java.io.File;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;

import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;

import com.tier3llc.countyservice.GetCountiesRequest;
import com.tier3llc.countyservice.GetCountiesResponse;

import us.states.config.AppConstants;

@Service
public class XmlUtils {

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
	
	public static String getElementValue(SOAPBody bdy, String parent, String element) {
		//log.info("==== getElementValue ====");
		String retValue = null;
		NodeList parentNodeList;
		Iterator<Node> nit = bdy.getChildElements();
		while (nit.hasNext()) {
			Node el = (Node) nit.next();			
			switch (parent) {
				case AppConstants.REQUEST_ELEMENT:
					if (el.getLocalName()!=null && el.getLocalName().equalsIgnoreCase(parent)) {							
						parentNodeList = getNodeList(el, parent);									//getCountiesRequest
						retValue = getNodeValue(parentNodeList, element);							//element		
					}					
					break;
				case AppConstants.RESPONSE_ELEMENT:
					parentNodeList = getNodeList(el, parent);										//getCountiesResponse
					Node countyNode = (Node) parentNodeList.item(0);								
					NodeList countyNodeList = getNodeList(countyNode, "county");					//county
					retValue = getNodeValue(countyNodeList, element);								//element
					break;			
			}	//switch			
		
		}	//while
		
		return retValue;
	}
	
	private static NodeList getNodeList(Node element, String parent) {
		//log.info("==== getNodeList ====");
		NodeList nodeList = null;
		if (element.getLocalName()!=null && element.getLocalName().equalsIgnoreCase(parent)) {			
			nodeList = element.getChildNodes();
		}
		return nodeList;
	}
	
	private static String getNodeValue(NodeList nodeList, String element) {
		//log.info("==== getNodeValue ====");
		String retValue = null;
		for(int i=0; i<nodeList.getLength(); i++) {
			Node el = (Node) nodeList.item(i);
			if (el.getLocalName()!=null && el.getLocalName().equalsIgnoreCase(element)) {	//element
				retValue = el.getTextContent();
				break;
			}
		}
		return retValue;
	}
	
}

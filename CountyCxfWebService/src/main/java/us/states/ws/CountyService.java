package us.states.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import com.tier3llc.countyservice.GetCountiesRequest;
import com.tier3llc.countyservice.GetCountiesResponse;
import com.tier3llc.countyservice.ObjectFactory;

@WebService(targetNamespace="http://www.tier3llc.com/countyservice", name="CountiesPort")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE) //, style=SOAPBinding.Style.RPC
public interface CountyService {

	static final String NAMESPACE_URI = "http://www.tier3llc.com/countyservice";
	
	@WebMethod(operationName="getCounties", action="")
	@WebResult(name="getCountiesResponse", targetNamespace=NAMESPACE_URI)
	public GetCountiesResponse getCountiesResponse(@WebParam(partName="getCountiesRequest", name="getCountiesRequest", targetNamespace=NAMESPACE_URI) GetCountiesRequest request); 
	//public GetCountiesResponse getCountiesResponse(@WebParam(partName="getCountiesRequest", name="getCountiesRequest", targetNamespace=NAMESPACE_URI) SOAPMessage request) throws SOAPException;
	
}

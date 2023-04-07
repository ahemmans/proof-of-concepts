package us.states.exception;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import java.util.List;
import javax.xml.namespace.QName;

import lombok.extern.slf4j.Slf4j;

import us.states.domain.ServiceFault;

@Slf4j
public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

	private static final QName CODE = new QName("code");
	private static final QName DESCRIPTION = new QName("description");
	private static final QName DETAILS = new QName("details");

	@Override
	protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
		log.warn("Exception processed ", ex);
		if (ex instanceof ServiceFaultException) {		
			SoapFaultDetail detail = fault.addFaultDetail();
			List<ServiceFault> serviceFaults = ((ServiceFaultException) ex).getServiceFaults();			
			for(int i=0;i<serviceFaults.size();i++) {				
				detail.addFaultDetailElement(CODE).addText(serviceFaults.get(i).getCode());
				detail.addFaultDetailElement(DESCRIPTION).addText(serviceFaults.get(i).getDescription());
			}
		}
	}
	
}

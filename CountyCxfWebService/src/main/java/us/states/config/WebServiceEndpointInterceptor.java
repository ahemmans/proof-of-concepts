package us.states.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import lombok.extern.slf4j.Slf4j;
import us.states.service.FileAttachmentProcessor;
import us.states.util.XmlUtils;

@Slf4j
@Service
public class WebServiceEndpointInterceptor implements EndpointInterceptor {

	@Autowired
	FileAttachmentProcessor attchmntProcessor;
	
	@Autowired
	XmlUtils xmlUtil;
	
	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
		log.info("==== WebServiceEndpointInterceptor handleRequest ====");
		WebServiceMessage msg = messageContext.getRequest();
		SaajSoapMessage saajMsg = (SaajSoapMessage) msg;
		SOAPMessage soapMsg = saajMsg.getSaajMessage();
		
		log.info("# attachments : {}", soapMsg.countAttachments());
		//logMessage("request", msg);
		
		String fileName = xmlUtil.getElementValue(soapMsg.getSOAPBody(), AppConstants.REQUEST_ELEMENT, "filename") == null ? "" : xmlUtil.getElementValue(soapMsg.getSOAPBody(), AppConstants.REQUEST_ELEMENT, "filename");
		if (soapMsg.countAttachments() > 0 && !StringUtils.isEmpty(fileName)) {
			boolean hasAttachment = attchmntProcessor.processRequestAttachment(soapMsg);
		}		
		
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
		log.info("==== WebServiceEndpointInterceptor handleResponse  ====");
		WebServiceMessage msg = messageContext.getResponse();
		SaajSoapMessage saajMsg = (SaajSoapMessage) msg;
		SOAPMessage soapMsg = saajMsg.getSaajMessage();
		//logMessage("response", msg);
		
		log.info("# attachments : {}", soapMsg.countAttachments());
		boolean hasAttachment = attchmntProcessor.processResponseAttachment(soapMsg);
		log.info("post hasAttachment : {}", hasAttachment);
		log.info("# attachments : {}", soapMsg.countAttachments());

		return true;
	}

	private void logMessage(String id, WebServiceMessage msg) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			msg.writeTo(out);		
			String msgStr = new String(out.toByteArray());
			log.info(id + " msg : {}", msgStr);
		} catch (IOException e) {
			log.error("WebServiceEndpointInterceptor logMessage IOException : ", e.getMessage());
		}
	}

	@Override
	public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
		log.info("==== WebServiceEndpointInterceptor handleFault ====");
		WebServiceMessage msg = messageContext.getResponse();
		SaajSoapMessage saajMsg = (SaajSoapMessage) msg;
		SOAPMessage soapMsg = saajMsg.getSaajMessage();
		
		log.error("message : {} ", soapMsg.getSOAPBody().getFault().getTextContent());
		//throw new RuntimeException(String.format(soapMsg.getSOAPBody().getFault().getFaultString()));
		
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
		//log.info("==== WebServiceEndpointInterceptor afterCompletion ====");		
	}

}

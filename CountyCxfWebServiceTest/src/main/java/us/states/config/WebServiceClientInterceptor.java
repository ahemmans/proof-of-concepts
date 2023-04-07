package us.states.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebServiceClientInterceptor implements ClientInterceptor {

	@Autowired
	RequestContext reqCtx;
	
	@Override
	public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		log.info("==== WebServiceClientInterceptor handleRequest ====");
		WebServiceMessage msg = messageContext.getRequest();
		SaajSoapMessage saajMsg = (SaajSoapMessage) msg;
		SOAPMessage soapMsg = saajMsg.getSaajMessage();	
		log.info("# attachments : {}", soapMsg.countAttachments());	
		//logMessage("resquest", msg);
		
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		log.info("==== WebServiceClientInterceptor handleResponse ====");
		WebServiceMessage msg = messageContext.getResponse();
		SaajSoapMessage saajMsg = (SaajSoapMessage) msg;
		SOAPMessage soapMsg = saajMsg.getSaajMessage();	
		log.info("# attachments : {}", soapMsg.countAttachments());	
		//logMessage("response", msg);				
		
		return true;
	}
	
	private void logMessage(String id, WebServiceMessage msg) {
		reqCtx = new RequestContext();
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			msg.writeTo(out);
			String msgStr = new String(out.toByteArray());
			reqCtx.setResponseStr(msgStr);
			log.info(id + " msg : {}", msgStr);
		} catch (IOException e) {
			log.error("WebServiceClientInterceptor logMessage IOException : ", e.getMessage());
		}
	}
	
	@Override
	public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		log.info("==== WebServiceClientInterceptor handleFault ====");
		WebServiceMessage msg = messageContext.getResponse();
		SaajSoapMessage saajMsg = (SaajSoapMessage) msg;
		SOAPMessage soapMsg = saajMsg.getSaajMessage();	
		
		try {
			SOAPFault soapFault = soapMsg.getSOAPBody().getFault();
			log.error("message : {}", soapFault.getTextContent());
			throw new RuntimeException(String.format(soapFault.getTextContent()));
		} catch (SOAPException e) {
			log.error("WebServiceClientInterceptor handleFault error : {}", e.getMessage());
		}
		
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {
		//log.info("==== WebServiceClientInterceptor afterCompletion ====");		
	}

	public RequestContext getReqCtx() {
		return reqCtx;
	}

	public void setReqCtx(RequestContext reqCtx) {
		this.reqCtx = reqCtx;
	}
	
	
}

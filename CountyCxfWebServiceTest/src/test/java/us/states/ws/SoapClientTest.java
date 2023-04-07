package us.states.ws;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.IOException;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.cxf.helpers.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import lombok.extern.slf4j.Slf4j;
import us.states.config.AppConfig;
import us.states.config.AppConstants;
import us.states.util.DataUtils;

@Slf4j
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { AppConfig.class })
@SpringJUnitConfig(AppConfig.class)
@TestPropertySource(value="/application.yaml")
public class SoapClientTest extends BaseSoapClient {

	private static final String TEST_ATTACHMENT = "src/test/resources/attachments/";
	
	SOAPMessage msg, reply;
	SOAPPart sp;
	SOAPEnvelope env;
	SOAPHeader hdr;
	SOAPBody bdy;
	AttachmentPart ap;	
	SOAPBodyElement countyRequest;
	SOAPElement idElement, stateElement, filenameElement, mimetypeElement, filedataElement;
	Name getCountiesRequestName, idName, stateName, filenameName, mimetypeName, filedataName;
	
	String inputParam;
	String expResp;
	String filePath;
	String fileName;
	String mimeType;
	
	//@Value("${ws.namespace.uri}")
	String namespaceUri; 
	
	//@Value("${ws.target.namespace}")
	String targetNamespace; 
	
	//@Value("${ws.url}")
	String endPoint; 
	
	DataUtils dataUtil;

	@BeforeEach
	public void setup() throws SOAPException {
		msg = createMessage();
		sp = msg.getSOAPPart();		
		env = sp.getEnvelope();
		hdr = env.getHeader();
		bdy = env.getBody();
		//ap = msg.createAttachmentPart();
		
		namespaceUri = AppConstants.NAMESPACE_URI;
		targetNamespace = AppConstants.TARGET_NAMESPACE;
		endPoint = AppConstants.WS_URL;
		createElements();		
	}
	
	@Disabled
	@Test
	public void createMsgWithoutAttachmentTest() throws SOAPException, IOException {
		log.info("==== createMsgWITHOUTAttachmentTest() ====");
		//TEST VALUES
		//inputParam = "MD";
		inputParam = "24033";
		expResp = "Prince George's County";		
		
		countyRequest = bdy.addBodyElement(getCountiesRequestName);
		idElement = countyRequest.addChildElement(idName).addTextNode(inputParam);;
		//stateElement = countyRequest.addChildElement(stateName).addTextNode(inputParam);
		filenameElement = countyRequest.addChildElement(filenameName);
		mimetypeElement = countyRequest.addChildElement(mimetypeName);
		
		reply = sendMessage(endPoint, msg);
				
		log.info("**** reply : {}", reply.getSOAPBody().getTextContent());
		assertNotNull(reply);
		assertTrue(reply.getSOAPBody().getTextContent().contains(expResp));
	}
	

	@Test
	public void createMsgWithAttachmentTest() throws SOAPException, IOException {
		log.info("==== createMsgWITHAttachmentTest() ====");
		//TEST VALUES
		//inputParam = "MD";
		inputParam = "24005";
		expResp = "Baltimore County";
		fileName = "unit-test.txt";
		mimeType = "text/plain";
		filePath = TEST_ATTACHMENT + fileName;	
		
		countyRequest = bdy.addBodyElement(getCountiesRequestName);
		idElement = countyRequest.addChildElement(idName).addTextNode(inputParam);;
		//stateElement = countyRequest.addChildElement(stateName).addTextNode(inputParam);
		filenameElement = countyRequest.addChildElement(filenameName).addTextNode(fileName);
		mimetypeElement = countyRequest.addChildElement(mimetypeName).addTextNode(mimeType);
		
		DataHandler dh = dataUtil.mapFileData(filePath);
		ap = msg.createAttachmentPart(dh);
		ap.setContentId(fileName);
		msg.addAttachmentPart(ap);
	
		reply = sendMessage(endPoint, msg);
				
		log.info("**** reply : {}", reply.getSOAPBody().getTextContent());
		assertNotNull(reply);
		//assertTrue(reply.getSOAPBody().getTextContent().contains(expResp));
		
		Iterator<AttachmentPart> it = reply.getAttachments();
		while (it.hasNext()) {
			AttachmentPart attachment = (AttachmentPart) it.next();
			Object content = attachment.getContent();
			String id = attachment.getContentId();
			log.info("**** Attachment : {} - contains : {} - contentType : {}", id, content, attachment.getContentType());
			log.info("**** Contents : {} ", IOUtils.toString(attachment.getRawContent(),"UTF-8"));
		}
	}
	
	private void createElements() throws SOAPException {
		getCountiesRequestName = env.createName("getCountiesRequest", targetNamespace, namespaceUri);
		idName = env.createName("id", targetNamespace, namespaceUri);
		stateName= env.createName("state", targetNamespace, namespaceUri);
		filenameName = env.createName("filename", targetNamespace, namespaceUri);
		mimetypeName = env.createName("mimetype", targetNamespace, namespaceUri);
		filedataName = env.createName("filedata", targetNamespace, namespaceUri);
	}
	
	//get the message/reply
	public SOAPMessage onMessage(SOAPMessage msg) throws SOAPException {

		SOAPEnvelope env = msg.getSOAPPart().getEnvelope();	
		env.getBody()
		  .addChildElement(env.createName("Response"))
		  .addTextNode("This is a Response");
	
		Iterator<AttachmentPart> it = msg.getAttachments();
		while (it.hasNext()) {
			AttachmentPart attachment = (AttachmentPart) it.next();
			Object content = attachment.getContent();
			String id = attachment.getContentId();
			System.out.print("Attachment " + id + " contains: " + content);
			System.out.println("");
		}
		
		return msg;
	}



}

package us.states.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;

import lombok.extern.slf4j.Slf4j;
import us.states.config.AppConstants;
import us.states.dao.CountyRepository;
import us.states.domain.County;
import us.states.util.DataUtils;
import us.states.util.XmlUtils;

@Slf4j
@Service
public class FileAttachmentProcessor {
	
	static CountyRepository repo;	
	static DataUtils dataUtil;
	static XmlUtils xmlUtil;
	static String  workingDir;		
	
	public FileAttachmentProcessor(CountyRepository repo, @Value("${workingdir.path}") String  workingDir) {
		this.repo = repo;
		this.workingDir = workingDir;
	}
	
	public static boolean processRequestAttachment(SOAPMessage msg) throws SOAPException, IOException {
		log.info("==== FileAttachmentProcessor processRequestAttachment ====");
		SOAPBody bdy = msg.getSOAPBody();		
		boolean hasAttachment = false;
		Iterator<AttachmentPart> it = msg.getAttachments();
		while (it.hasNext()) {
			hasAttachment = true;
			AttachmentPart attachment = (AttachmentPart) it.next();			
			//Object content = attachment.getContent();
			//String _id = attachment.getContentId();
			String id = xmlUtil.getElementValue(bdy, AppConstants.REQUEST_ELEMENT, "id") == null ? "0" : xmlUtil.getElementValue(bdy, AppConstants.REQUEST_ELEMENT, "id");			
			String fileName = xmlUtil.getElementValue(bdy, AppConstants.REQUEST_ELEMENT, "filename") == null ? "" : xmlUtil.getElementValue(bdy, AppConstants.REQUEST_ELEMENT, "filename");
			String mimeType = xmlUtil.getElementValue(bdy, AppConstants.REQUEST_ELEMENT, "mimetype") == null ? "" : xmlUtil.getElementValue(bdy, AppConstants.REQUEST_ELEMENT, "mimetype");
			//log.info("**** Contents : {} ", IOUtils.toString(attachment.getRawContent(),"UTF-8"));
			log.info("**** id : {} \r\n fileName : {} \r\n mimeType : {} \r\n ", id, fileName, mimeType);
			writeFile(attachment, fileName);
			repo.updateRecord(fileName, mimeType, attachment.getRawContentBytes(), Integer.parseInt(id));
		}
		
		if (!hasAttachment) {
			log.info("NO FILE ATTACHMENT");
		}
		
		return hasAttachment;
	}

	public static boolean processResponseAttachment(SOAPMessage msg) throws SOAPException, IOException {
		log.info("==== FileAttachmentProcessor processResponseAttachment ====");
		SOAPBody bdy = msg.getSOAPBody();		
		boolean hasAttachment = false;
		String id = xmlUtil.getElementValue(bdy, AppConstants.RESPONSE_ELEMENT, "id") == null ? "0" : xmlUtil.getElementValue(bdy, AppConstants.RESPONSE_ELEMENT, "id");
		County county = repo.findById(Integer.parseInt(id)).get();
		if (!StringUtils.isEmpty(county.getFilename()) && county.getFiledata()!=null) {
			hasAttachment = true;
			log.info("FILE ATTACHMENT : {}", county.getFilename());
			DataHandler dh = dataUtil.mapFileData(county.getFiledata());
			AttachmentPart attachment = msg.createAttachmentPart(dh);
			attachment.setContentId(county.getFilename());
			msg.addAttachmentPart(attachment);	
			//writeFile(attPart, county.getFilename());
		} else {
			log.info("NO FILE ATTACHMENT");
		}
		
		return hasAttachment;
	}

	
/*
	
	private static String getElementValue(SOAPBody bdy, String parent, String element) {
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

*/
	
	private static void writeFile(AttachmentPart attachment, String fileName) throws IOException, SOAPException  {
		try ( FileOutputStream out = new FileOutputStream(new File(workingDir + fileName)) ) {
			log.info("**** writing file : {}", workingDir + fileName);
			out.write(attachment.getRawContentBytes());
		}
	}


	
}

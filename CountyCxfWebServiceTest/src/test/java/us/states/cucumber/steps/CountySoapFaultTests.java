package us.states.cucumber.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.activation.DataHandler;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.Detail;
import javax.xml.soap.Name;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.tier3llc.countyservice.GetCountiesRequest;
import com.tier3llc.countyservice.GetCountiesResponse;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import lombok.extern.slf4j.Slf4j;

import us.states.cucumber.Context;
import us.states.cucumber.ScenarioContext;
import us.states.domain.ServiceFault;
import us.states.report.ReportGenerator;
import us.states.util.DataUtils;
import us.states.util.XmlUtils;
import us.states.ws.BaseSoapClient;

@Slf4j
public class CountySoapFaultTests extends BaseSoapClient {
	
	@Autowired BaseSteps baseSteps;	
	@Autowired XmlUtils xmlUtil;
	@Autowired DataUtils dataUtil;	

	@Value("${ws.url}")
	String endPoint;
	
	@Value("${ws.namespace.uri}")
	String namespaceUri;
	
	@Value("${ws.target.namespace}")
	String targetNamespace;
	
	private GetCountiesRequest request;
	private GetCountiesResponse response;
	private String expectedResponse;
	private String filePath;
	private List<ServiceFault> serviceFaults;
	
	SOAPMessage msg, reply;
	SOAPPart sp;
	SOAPEnvelope env;
	SOAPHeader hdr;
	SOAPBody bdy;
	SOAPFault fault;
	AttachmentPart ap;
	
	SOAPBodyElement countyRequest;
	SOAPElement idElement, stateElement, filenameElement, mimetypeElement, filedataElement;
	Name getCountiesRequestName, idName, stateName, filenameName, mimetypeName, filedataName;
	
	private static ScenarioContext faultScenarioContext;
	private static List<ScenarioContext> listOfFaultScenarios;
	
	private static final String TEST_DATA = "src/test/resources/testdata/";
	private static final String TEST_ATTACHMENT = "src/test/resources/attachments/";
	private static final String TAG = "@CountySoapFault";
	
	@When("^the SOAP test case \"(.*)\" is initiated with request \"(.*)\"$")
	public void getCoutiesWS_isCalled_with_file(String testId, String filename) throws Throwable {
		filePath = TEST_DATA + filename;
		request = xmlUtil.convertRequestFile(filePath);

		assertNotNull(request);
		faultScenarioContext.setContext(Context.TESTCASE_ID, testId);
		faultScenarioContext.setContext(Context.TESTCASE_STATUS, true);
		faultScenarioContext.setContext(Context.REQUEST_FILE, filename);
	}
	
	@And("^the SOAP message is created$")
	public void create_soap_msg() throws Throwable {				
		countyRequest = bdy.addBodyElement(getCountiesRequestName);
		idElement = countyRequest.addChildElement(idName).addTextNode(request.getId().toString());;
		//stateElement = countyRequest.addChildElement(stateName).addTextNode(request.getState());
		filenameElement = countyRequest.addChildElement(filenameName).addTextNode(request.getFilename());
		mimetypeElement = countyRequest.addChildElement(mimetypeName).addTextNode(request.getMimetype());
				
		assertNotNull(msg);
		assertNotNull(bdy);
		assertNotNull(countyRequest);
	}
	
	@Then("^the \"(.*)\" element is \"(.*)\"$")
	public void remove_element(String el, String remove_empty) {
		switch (remove_empty) {
			case "remove" :
				if (el.contains("id"))       countyRequest.removeChild(idElement);
				if (el.contains("mimetype")) countyRequest.removeChild(mimetypeElement);
				if (el.contains("filename")) countyRequest.removeChild(filenameElement);
				break;
			case "empty" :	
				if (el.contains("id"))       idElement.setTextContent("");
				if (el.contains("mimetype")) mimetypeElement.setTextContent("");
				if (el.contains("filename")) filenameElement.setTextContent("");
				break;
		}
		faultScenarioContext.setContext(Context.ELEMENTS, el);
		faultScenarioContext.setContext(Context.REMOVE_EMPTY, remove_empty);
	}
	/*
	@Then("^the \"(.*)\" element is empty$")
	public void update_element(String el) {
		if (el.contains("id"))       idElement.setTextContent("");
		if (el.contains("mimetype")) mimetypeElement.setTextContent("");
		if (el.contains("filename")) filenameElement.setTextContent("");
	}
	*/
	@And("^the file attachment is inserted into the SOAP message : \"(.*)\"$")
	public void attach_file_to_soap_message(boolean attachFile) throws Throwable {
		filePath = TEST_ATTACHMENT + request.getFilename();
		if (attachFile) {
			log.info("File Attachment : {}", request.getFilename());
			filedataElement = countyRequest.addChildElement(filedataName).addTextNode(new String(dataUtil.mapFileBytes(filePath)));
			
			DataHandler dh = dataUtil.mapFileData(filePath);
			ap = msg.createAttachmentPart(dh);
			ap.setContentId(request.getFilename());
			msg.addAttachmentPart(ap);
			
			assertNotNull(ap);
			log.info("# attachments : {}", msg.countAttachments());
			faultScenarioContext.setContext(Context.FILE_ATTACHMENT, request.getFilename());	
		} else {
			faultScenarioContext.setContext(Context.FILE_ATTACHMENT, "");
		}
	}
	
	@Then("^the SOAP message is sent$")
	public void send_soap_msg() throws Throwable {
		reply = sendMessage(endPoint, msg);
		assertNotNull(reply);
		//log.info("reply : {}", reply.getSOAPBody().getTextContent());		
		
		//log.info("# attachments : {}" ,reply.countAttachments());
		//scenarioContext.setContext(Context.RESPONSE, response.toString());
		//scenarioContext.setContext(Context.RESPONSE, reply.getSOAPBody().getTextContent());
	}
	
	@Then("^verify the SOAP response is a SOAPFault$")
	public void verify_soap_fault() throws Throwable {
		fault = reply.getSOAPBody().getFault();
		assertNotNull(fault);
		log.info("reply : {}", fault.getTextContent());
		
		//Detail detail = fault.getDetail();		
		serviceFaults = getServiceFaults(fault.getDetail());
		assertTrue(serviceFaults.size()>0); 
	}
	
	@And("^verify that errors \"(.*)\" are generated$")
	public void verify_errors(String errCode) throws Throwable {
		String[] errCodes = errCode.split(",");
		
		//final String errCode1 = errCodes[0];
		//assertTrue(serviceFaults.stream().anyMatch(fault -> errCode1.equals(fault.getCode())));
				
		List<String> listOfErrCodes = serviceFaults.stream()
			.map(ec -> ec.getCode())
			.collect(Collectors.toList());
		List<String> expectedList = new ArrayList<String>();
		CollectionUtils.addAll(expectedList, errCodes);
		faultScenarioContext.setContext(Context.EXPECTED_RESPONSE, expectedList.toString());
		faultScenarioContext.setContext(Context.ACTUAL_RESPONSE, listOfErrCodes.toString());
		for(int i=0;i<errCodes.length;i++) {
			if(!listOfErrCodes.contains(errCodes[i])) {
				faultScenarioContext.setContext(Context.TESTCASE_STATUS, false);
			}
		}
		
		//assertTrue(listOfErrCodes.stream().anyMatch(item -> errCode.equals(item.toString())));
		//assertThat(listOfErrCodes).contains(errCodes);
		assertThat(serviceFaults.stream()
			.map(ec -> ec.getCode())
			.collect(Collectors.toList())
			)
			.contains(errCodes);		
	}
	
	@Then("^verify the SOAP response is valid$")
	public void validate_response() throws Throwable {		
		assertTrue(isResponseValid(reply.getSOAPBody()));
		assertTrue(reply.getSOAPBody().getTextContent().contains(request.getFilename()));
		assertTrue(reply.getSOAPBody().getTextContent().contains(request.getId().toString()));
		//assertNotNull(getReplyString(reply));
	}
	
	private List<ServiceFault> getServiceFaults(Detail detail) {
		List<ServiceFault> faults = new ArrayList<ServiceFault>();	
				
		Iterator<Node> it = detail.getChildElements();
		while (it.hasNext()) {
			Node elCode =  (Node) it.next();	//code
			Node elDesc = (Node) it.next();		//description
			faults.add(new ServiceFault(elCode.getTextContent(), elDesc.getTextContent()));
			log.info("node : {{} : {}}", elCode.getNodeName(),  elCode.getTextContent());
			log.info("node : {{} : {}}", elDesc.getNodeName(), elDesc.getTextContent());			
		}
		
		return faults;
	}
	
	private String getReplyString(SOAPMessage reply) throws IOException, SOAPException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        reply.writeTo(out);
        String strMsg = new String(out.toByteArray());
        log.info("**** reply : {}", strMsg);
        out.close();
        //faultScenarioContext.setContext(Context.EXPECTED_RESPONSE, strMsg);
        
        return strMsg;
	}
	
	private boolean isResponseValid(SOAPBody bdy) {
		boolean isValid=false;
		Iterator<Node> nit = bdy.getChildElements();
		while (nit.hasNext()) {
			Node el = (Node) nit.next();	
			if (el.getLocalName()!=null && el.getLocalName().equalsIgnoreCase("getCountiesResponse")) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}
	
	private void createElements() throws SOAPException {
		getCountiesRequestName = env.createName("getCountiesRequest", targetNamespace, namespaceUri);
		idName = env.createName("id", targetNamespace, namespaceUri);
		stateName= env.createName("state", targetNamespace, namespaceUri);
		filenameName = env.createName("filename", targetNamespace, namespaceUri);
		mimetypeName = env.createName("mimetype", targetNamespace, namespaceUri);
		filedataName = env.createName("filedata", targetNamespace, namespaceUri);
	}
	
	
	@BeforeAll
	public static void beforeAll() throws Exception {
		//log.info("Executing beforeAll()...");
		//initiate list of scenarioContexts
		listOfFaultScenarios = new ArrayList<ScenarioContext>();
	}
	
	@Before(TAG)
	public void init() throws Exception {
		//isEndpointSecure = false;
		baseSteps.initializeTestRestTemplate();
		this.faultScenarioContext = new ScenarioContext();
		
		msg = createMessage();
		sp = msg.getSOAPPart();		
		env = sp.getEnvelope();
		hdr = env.getHeader();
		bdy = env.getBody();
		createElements();
	}
	
	@After(TAG)
	public void afterEach() throws Exception {
		//log.info("Executing afterEach()...");
		//increment list
		if(ObjectUtils.isEmpty(faultScenarioContext.getContext(Context.ELEMENTS))) {
			faultScenarioContext.setContext(Context.ELEMENTS, "");
			faultScenarioContext.setContext(Context.REMOVE_EMPTY, "");
		}		
		listOfFaultScenarios.add(faultScenarioContext);
	}
	
	@AfterAll
	public static void afterAll() throws Exception {
		//log.info("Executing afterAll()...");
		log.info("# of fault scenarios : {}", listOfFaultScenarios.size());
		String rptFileName = "CountySoapFaultTests.xlsx";
		if (listOfFaultScenarios.size()>0) ReportGenerator.createReport(listOfFaultScenarios, rptFileName);
	}
	
}

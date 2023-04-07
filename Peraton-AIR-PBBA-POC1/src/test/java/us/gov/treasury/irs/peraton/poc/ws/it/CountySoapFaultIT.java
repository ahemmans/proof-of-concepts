package us.gov.treasury.irs.peraton.poc.ws.it;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.AttachmentPart;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

//import com.tier3llc.countyservice.GetCountiesRequest;
//import com.tier3llc.countyservice.GetCountiesResponse;

import us.gov.treasury.irs.peraton.poc.cucumber.Context;
import us.gov.treasury.irs.peraton.poc.cucumber.ScenarioContext;
import us.gov.treasury.irs.peraton.poc.cucumber.steps.BaseSteps;
import us.gov.treasury.irs.peraton.poc.report.ReportGenerator;
import us.gov.treasury.irs.peraton.poc.util.DataUtils;
import us.gov.treasury.irs.peraton.poc.ws.BaseSoapClient;
import us.gov.treasury.irs.peraton.poc.ws.SoapConnector;

//import us.states.domain.County;
//import us.states.repo.CountyRepository;


@Tag("IT") @Tag("CountySoapFault")
@SpringBootTest
@ActiveProfiles("it")
public class CountySoapFaultIT extends BaseSoapClient {
	private final static Log log = LogFactory.getLog(CountySoapFaultIT.class);
	
	@Autowired SoapConnector soapConnector;
//	@Autowired CountyRepository repo;
//	@Autowired County county;	
	@Autowired BaseSteps baseSteps;
	@Autowired DataUtils dataUtil;
	
	@Value("${workingdir.path}")
	String workingDir;
	
	@Value("${ws.url}")
	String endPoint;
	
	@Value("${ws.namespace.uri}")
	String namespaceUri;
	
	@Value("${ws.target.namespace}")
	String targetNamespace;
	
//	private GetCountiesRequest request;
//	private GetCountiesResponse response;
	private ResponseEntity<String> actualResponse;
	//private String filePath;
	
	private static ScenarioContext faultScenarioContext;
	private static List<ScenarioContext> listOfFaultScenarios;
	private boolean isTestcaseDisabled;
	
	private static final String TEST_DATA = "src/test/resources/testdata/";
	private static final String TEST_ATTACHMENT = "src/test/resources/attachments/";
	//private static final String TAG = "@CountySoapFault";
	
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
		listOfFaultScenarios = new ArrayList<ScenarioContext>();
	}
	
	@BeforeEach
	public void init() throws Exception {
		//isEndpointSecure = false;
		baseSteps.initializeTestRestTemplate();
		this.faultScenarioContext = new ScenarioContext();
	}
	
	@AfterEach 
	public void afterEach() throws Exception {
		//log.info("Executing afterEach()...");
		faultScenarioContext.setContext(Context.ELEMENTS, "");
		faultScenarioContext.setContext(Context.REMOVE_EMPTY, "");
		if (!isTestcaseDisabled) listOfFaultScenarios.add(faultScenarioContext);
	}
	
	@AfterAll
	public static void afterAll() throws Exception {
		//log.info("Executing afterAll()...");
		log.info("# of scenarios : " + listOfFaultScenarios.size());
		String rptFileName = "CountyWsIT.xlsx";
		if (listOfFaultScenarios.size()>0) ReportGenerator.createReport(listOfFaultScenarios, rptFileName);
	}
	
}

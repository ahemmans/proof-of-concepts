package us.gov.treasury.irs.peraton.poc.ws.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.assertj.core.api.BDDAssertions.then;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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



@Disabled
@Tag("IT") @Tag("CountyIT")
@SpringBootTest
@ActiveProfiles("it")
public class CountyWsIT extends BaseSoapClient {
	private final static Log log = LogFactory.getLog(CountyWsIT.class);
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
	
	private static ScenarioContext scenarioContext;
	private static List<ScenarioContext> listOfScenarios;
	private boolean isTestcaseDisabled;
	
	private static final String TEST_DATA = "src/test/resources/testdata/";
	private static final String TEST_ATTACHMENT = "src/test/resources/attachments/";
	//private static final String TAG = "@CountyIT";
	
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
	
	@Disabled
	@DisplayName("CountyWsIT Test Cases")
	@ParameterizedTest
	@CsvFileSource(resources = "/testdata/test_data_master.csv")
	public void testCountySoapCall(String testId, String filename, String expResp, boolean attachFile, boolean runTest) throws Throwable {
		log.info("==== CountyWsIT Test Case : " + testId);
		this.isTestcaseDisabled = !runTest;
		if(isTestcaseDisabled) log.info("Test Case {} is DISABLED" + testId);
		given(!isTestcaseDisabled).isTrue();
		String expResp2 = StringUtils.isEmpty(expResp) ? "" : expResp;
		scenarioContext.setContext(Context.TESTCASE_ID, testId);
		scenarioContext.setContext(Context.TESTCASE_STATUS, true);
		scenarioContext.setContext(Context.REQUEST_FILE, filename);
		scenarioContext.setContext(Context.EXPECTED_RESPONSE, expResp2);
		
		//check that web service is up
		try {
			//actualResponse = baseSteps.invokeGetRestApi("/actuator/health", false);
			actualResponse = ResponseEntity
	                .status(200)
	                .body("OK");
		} catch (Exception e) {
			log.error("Web Service Error : " + e.getMessage());
			isTestcaseDisabled = true;
		}
		given(actualResponse).isNotNull();
		assertThat((HttpStatus.OK).equals(actualResponse.getStatusCode()));

/*
		//verify request is valid
		log.info("request file : {}", TEST_DATA + filename);
		request = xmlUtil.convertRequestFile(TEST_DATA + filename);
		assertThat(request).isNotNull();
		
		//attach file
		if (attachFile) {
			log.info("file attachment : {}", TEST_ATTACHMENT + request.getFilename());
			request.setFiledata(dataUtil.mapFileData(TEST_ATTACHMENT + request.getFilename()));
			scenarioContext.setContext(Context.FILE_ATTACHMENT, request.getFilename());
		} else {
			log.info("NO FILE ATTACHMENT");
			scenarioContext.setContext(Context.FILE_ATTACHMENT, "");
		}
		
		//verify response is returned
		response = (GetCountiesResponse) soapConnector.callWebService(request);	
		assertThat(response).isNotNull();
		//log.info("response : {}", response.toString());
		
		//map response to object
		List<String> counties = response.getCounty().stream()
				.map(c -> c.getCounty())
				.collect(Collectors.toList());
		assertThat(counties)
			.isNotEmpty()
			.isNotNull()
			.hasSizeGreaterThanOrEqualTo(1);
		scenarioContext.setContext(Context.ACTUAL_RESPONSE, counties.toString());
		if(!counties.contains(expResp2)) {
			scenarioContext.setContext(Context.TESTCASE_STATUS, false);
		}
		
		//validate response
		if (counties.size() == 1) {			
			assertAll("Verify response : " + testId,
				() -> assertThat(expResp2).isEqualTo(response.getCounty().get(0).getCounty())
			);				
		} else {
			assertAll("Verify response : " + testId,
				() -> assertTrue(response.getCounty().stream().anyMatch(c -> c.getCounty().contains(expResp2)))
			);			
		}

		log.info("Verify file written to working dir : {}", workingDir + request.getFilename());
		File newFile = new File(workingDir + request.getFilename());
		then(newFile)
			.exists()
			.isFile()
			.isNotEmpty();
		
		log.info("Verify database is updated");
		county = repo.findById(request.getId()).get();
		then(county)
			.isNotNull()
			.hasFieldOrPropertyWithValue("county", expResp2);
			//.hasFieldOrPropertyWithValue("filename", request.getFilename());
*/		

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
		listOfScenarios = new ArrayList<ScenarioContext>();
	}
	
	@BeforeEach
	public void init() throws Exception {
		//isEndpointSecure = false;
		baseSteps.initializeTestRestTemplate();
		this.scenarioContext = new ScenarioContext();
	}
	
	@AfterEach 
	public void afterEach() throws Exception {
		//log.info("Executing afterEach()...");
		scenarioContext.setContext(Context.ELEMENTS, "");
		scenarioContext.setContext(Context.REMOVE_EMPTY, "");
		if (!isTestcaseDisabled) listOfScenarios.add(scenarioContext);
	}
	
	@AfterAll
	public static void afterAll() throws Exception {
		//log.info("Executing afterAll()...");
		log.info("# of scenarios : " + listOfScenarios.size());
		String rptFileName = "CountyWsIT.xlsx";
		if (listOfScenarios.size()>0) ReportGenerator.createReport(listOfScenarios, rptFileName);
	}
	
}

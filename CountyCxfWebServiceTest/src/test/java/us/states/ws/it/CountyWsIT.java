package us.states.ws.it;

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

import com.tier3llc.countyservice.GetCountiesRequest;
import com.tier3llc.countyservice.GetCountiesResponse;

import lombok.extern.slf4j.Slf4j;

import us.states.cucumber.Context;
import us.states.cucumber.ScenarioContext;
import us.states.cucumber.steps.BaseSteps;

import us.states.domain.County;
import us.states.repo.CountyRepository;
import us.states.report.ReportGenerator;
import us.states.util.DataUtils;
import us.states.util.XmlUtils;
import us.states.ws.BaseSoapClient;
import us.states.ws.SoapConnector;

@Disabled
@Tag("IT") @Tag("CountyIT")
@Slf4j
@SpringBootTest
@ActiveProfiles("it")
public class CountyWsIT extends BaseSoapClient {

	@Autowired SoapConnector soapConnector;
	@Autowired CountyRepository repo;
	@Autowired County county;	
	@Autowired BaseSteps baseSteps;
	@Autowired XmlUtils xmlUtil;
	@Autowired DataUtils dataUtil;
	
	@Value("${workingdir.path}")
	String workingDir;
	
	@Value("${ws.url}")
	String endPoint;
	
	@Value("${ws.namespace.uri}")
	String namespaceUri;
	
	@Value("${ws.target.namespace}")
	String targetNamespace;
	
	private GetCountiesRequest request;
	private GetCountiesResponse response;
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
	
	@DisplayName("CountyWsIT Test Cases")
	@ParameterizedTest
	@CsvFileSource(resources = "/testdata/test_data_master.csv")
	public void testCountySoapCall(String testId, String filename, String expResp, boolean attachFile, boolean runTest) throws Throwable {
		log.info("==== CountyWsIT Test Case : {} ====", testId);
		this.isTestcaseDisabled = !runTest;
		if(isTestcaseDisabled) log.info("Test Case {} is DISABLED", testId);
		given(!isTestcaseDisabled).isTrue();
		String expResp2 = StringUtils.isEmpty(expResp) ? "" : expResp;
		scenarioContext.setContext(Context.TESTCASE_ID, testId);
		scenarioContext.setContext(Context.TESTCASE_STATUS, true);
		scenarioContext.setContext(Context.REQUEST_FILE, filename);
		scenarioContext.setContext(Context.EXPECTED_RESPONSE, expResp2);
		
		//check that web service is up
		try {
			actualResponse = baseSteps.invokeGetRestApi("/actuator/health", false);
		} catch (Exception e) {
			log.error("Web Service Error : {}", e.getMessage());
			isTestcaseDisabled = true;
		}
		given(actualResponse).isNotNull();
		assertThat((HttpStatus.OK).equals(actualResponse.getStatusCode()));

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
		/*
		if (counties.size() == 1) {			
			assertAll("Verify response : " + testId,
				() -> assertThat(expResp2).isEqualTo(response.getCounty().get(0).getCounty())
			);				
		} else {
			assertAll("Verify response : " + testId,
				() -> assertTrue(response.getCounty().stream().anyMatch(c -> c.getCounty().contains(expResp2)))
			);			
		}
		*/
		
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
		
		/*
		assertAll("CountyWsIT Test Case : " + testId,
			() -> assertEquals(HttpStatus.OK, actualResponse.getStatusCode()), 
			() -> assertNotNull(request),
			
			() -> assertNotNull(response),
			() -> assertTrue(response.getCounty().stream().anyMatch(c -> c.getCounty().contains(expResp2))),
			() -> assertTrue(response.getCounty().stream().anyMatch(c -> ((Integer) c.getId()).toString().contains(request.getId().toString()))),
			() -> assertTrue(response.getCounty().stream().anyMatch(c -> c.getFilename().contains(request.getFilename()))),
			//() -> assertEquals(expResp2, response.getCounty().get(0).getCounty()),			
			//() -> assertEquals(request.getId(), response.getCounty().get(0).getId()),
			//() -> assertEquals(request.getFilename(), response.getCounty().get(0).getFilename()),			
			() -> assertTrue(newFile.exists()),
			() -> assertTrue(!newFile.isDirectory()),
			() -> assertTrue(newFile.length()>0),
			
			() -> assertNotNull(county),
			() -> assertEquals(expResp2, county.getCounty()),
			() -> assertEquals(request.getFilename(), county.getFilename())
		);
		*/
		
		/*
		@Nested
		@DisplayName("")
		class RunningWebServer {
			@DisplayName("Given that the Web Service is UP")
			@Test
			public void givenWebServiceUp() throws Throwable {
				log.info("Given that the Web Service is UP");
				actualResponse = baseSteps.invokeGetRestApi("/actuator/health", false);
				assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
			}
		}
		
		@Nested
		@DisplayName("Request Validator")
		class RequestValidator {
			@DisplayName("Verify that the request is valid")
			@Test
			public void validateRequest() throws Throwable {
				log.info("Verify that the request is valid");
				request = xmlUtil.convertRequestFile(TEST_DATA + filename);
				if (attachFile) {
					request.setFiledata(dataUtil.mapFileData(TEST_ATTACHMENT + request.getFilename()));
					scenarioContext.setContext(Context.FILE_ATTACHMENT, request.getFilename());
				} else {
					scenarioContext.setContext(Context.FILE_ATTACHMENT, "");
				}
				assertNotNull(request);
			}			
		}
		
		@Nested
		@DisplayName("Response Validator")
		class ResponseValidator {
			@DisplayName("Verify that the response is valid")
			@Test
			public void validateResponse() throws Throwable {
				log.info("Verify that the response is valid");
				response = (GetCountiesResponse) soapConnector.callWebService(request);					
				scenarioContext.setContext(Context.ACTUAL_RESPONSE, response.getCounty().get(0).getCounty());
				if(!expResp.equals(response.getCounty().get(0).getCounty())) {
					scenarioContext.setContext(Context.TESTCASE_STATUS, false);
				}
				
				assertAll("CountyWsIT Test Case : " + testId,
						//() -> assertEquals(HttpStatus.OK, actualResponse.getStatusCode()),
						//() -> assertNotNull(request),
						() -> assertNotNull(response),
						() -> assertEquals(expResp, response.getCounty().get(0).getCounty()),	
						() -> assertEquals(request.getId(),response.getCounty().get(0).getId()),
						() -> assertEquals(request.getFilename(),response.getCounty().get(0).getFilename())						
				);
			}
		}
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
		log.info("# of scenarios : {}", listOfScenarios.size());
		String rptFileName = "CountyWsIT.xlsx";
		if (listOfScenarios.size()>0) ReportGenerator.createReport(listOfScenarios, rptFileName);
	}
	
}

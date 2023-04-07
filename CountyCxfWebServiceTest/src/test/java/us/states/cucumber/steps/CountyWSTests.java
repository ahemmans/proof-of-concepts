package us.states.cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.helpers.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tier3llc.countyservice.GetCountiesRequest;
import com.tier3llc.countyservice.GetCountiesResponse;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import lombok.extern.slf4j.Slf4j;

import us.states.config.RequestContext;
import us.states.cucumber.Context;
import us.states.cucumber.ScenarioContext;
import us.states.domain.County;
import us.states.domain.IFS_STATUS;
import us.states.domain.SUBMISSION_BC;
import us.states.domain.TRANSMITTER_BC;
import us.states.domain.query.SubmissionStatus_1095A;
import us.states.repo.CountyRepository;
import us.states.repo.IfsStatus;
import us.states.repo.SubmissionBc;
import us.states.repo.TransmitterBc;
import us.states.report.ReportGenerator;
import us.states.service.SubmissionBcService;
import us.states.service.TransmitterBcService;
import us.states.util.DataUtils;
import us.states.util.XmlUtils;
import us.states.ws.SoapConnector;

@Slf4j
public class CountyWSTests extends ReportGenerator {

	@Autowired CountyRepository repo;
	@Autowired County county;
	@Autowired SoapConnector soapConnector;
	@Autowired BaseSteps baseSteps;
	@Autowired XmlUtils xmlUtil;
	@Autowired DataUtils dataUtil;
	//@Autowired RequestContext reqCtx;

	@Autowired IfsStatus ifsStatus;
	@Autowired SubmissionBc submissionBc;
	@Autowired TransmitterBc transmitterBc;
	@Autowired TransmitterBcService transmitterBcService;
	
	@Value("${workingdir.path}")
	String workingDir;
	
	private GetCountiesRequest request;
	private GetCountiesResponse response;
		
	private ResponseEntity<String> actualResponse;
	private String expectedResponse;
	private int idParam;
	private String filePath, formType, gsoId;
	
	private static ScenarioContext scenarioContext;
	private static List<ScenarioContext> listOfScenarios;
	
	private static final String TEST_DATA = "src/test/resources/testdata/fs22/";
	private static final String TEST_ATTACHMENT = "src/test/resources/attachments/";
	private static final String TAG = "@County";
	
	@Given("^that the County WebService is \"(.*)\"$")
	public void givenWebServiceUp(String status) throws Throwable {
		actualResponse = baseSteps.invokeGetRestApi("/actuator/health", false);
		//log.info("**** actualResponse : {}",  actualResponse.getBody());
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
	}
	
	@When("^the getCounties WS is called with id \"(.*)\"$")
	public void getCoutiesWS_isCalled_with_id(String _id) throws Throwable {
		idParam = Integer.parseInt(_id);
		request = new GetCountiesRequest();
		request.setId(idParam);
		response = (GetCountiesResponse) soapConnector.callWebService(request);
		//log.info("**** county : {}", response.getCounty().get(0).getCounty());
		assertNotNull(response);		
		/*
		Object resp = soapConnector.callWebService(request);
		if (resp instanceof JAXBElement<?>) {
			JAXBElement<?> jaxbElement = (JAXBElement<?>) resp;
 			if (jaxbElement.getValue() instanceof GetCountiesResponse) {
 				response = (GetCountiesResponse) jaxbElement.getValue();
 			}
		}
		*/
	}
	
	@When("^the WS test case \"(.*)\" is initiated with request \"(.*)\"$")
	public void getCoutiesWS_isCalled_with_file(String testId, String filename) throws Throwable {
		filePath = TEST_DATA + filename;
		request = xmlUtil.convertRequestFile(filePath);
		//idParam = request.getId();
		//fileAttachParam = request.getFilename();
		//mimetypeParam = request.getMimetype();

		assertNotNull(request);
		scenarioContext.setContext(Context.TESTCASE_ID, testId);
		scenarioContext.setContext(Context.TESTCASE_STATUS, true);
		scenarioContext.setContext(Context.REQUEST_FILE, filename);
	}
	
	@And("^the file is attached to the request : \"(.*)\" \"(.*)\"$")
	public void attach_file_to_request(boolean attachFile, String _formType) throws Throwable {
		log.info("File Attachment : {}", request.getFilename());
		filePath = TEST_ATTACHMENT + request.getFilename();
		formType = _formType;
		//setting the filedata initializes the DataHandler. Attachment is created in the SoapMessage
		if (attachFile) {
			request.setFiledata(dataUtil.mapFileData(filePath));
			scenarioContext.setContext(Context.FILE_ATTACHMENT, request.getFilename());		
		} else {
			scenarioContext.setContext(Context.FILE_ATTACHMENT, "");	
		}
	}
	
	@Then("^the WS call is executed$")
	public void exeucte_webservice_call() throws Throwable {
		response = (GetCountiesResponse) soapConnector.callWebService(request);
		assertNotNull(response);
		
		//String gsoId = RandomStringUtils.random(8, "0123456789abcdef");
		gsoId = response.getCounty().get(0).getCounty();
		log.info("gsoId : {}", gsoId);
		log.info("formType : {}", formType);
		//ifsStatus.insertStatus(gsoId, request.getFilename(), formType, "ACCEPTED");		
		//submissionBc.insertSubmission(gsoId, formType, "ACCEPTED");
		//transmitterBc.insertTransmitter(gsoId, formType, request.getFilename());
		updateData();
		//log.info("**** response : {}", response.toString());			
		//scenarioContext.setContext(Context.RESPONSE, response.toString());
	}
	
	private void updateData() {
		IFS_STATUS ifs_Status = new IFS_STATUS();
		ifs_Status.setSTATUS_ID(ifsStatus.getId()+1);
		ifs_Status.setGSO_ID(gsoId);
		ifs_Status.setNAME(request.getFilename());
		ifs_Status.setSUBTYPE(formType);
		ifs_Status.setSTATUS("ACCEPTED");
		ifs_Status.setSUBMISSION_ID(1);
		ifs_Status.setCREATE_DT(Date.from(Instant.now()));
		ifs_Status.setUPDATE_DT(Date.from(Instant.now()));
		
		SUBMISSION_BC submission_Bc = new SUBMISSION_BC();
		submission_Bc.setSUBMISSION_BC_ID(submissionBc.getId()+1);
		submission_Bc.setGSO_ID(gsoId);
		submission_Bc.setFORM_TYPE(formType);
		submission_Bc.setSUBMISSION_STATUS_TXT("ACCEPTED");
		submission_Bc.setTRANSMITTER_BC_ID(1);
		submission_Bc.setTAX_YR("2023");
		submission_Bc.setTRANSMIT_MODE_IND("1");
		submission_Bc.setUNIQUE_SUBMISSION_ID("1");
		submission_Bc.setEIN("1234567");
		submission_Bc.setINSERTED_BY_TXT("int_test");
		submission_Bc.setINSERTED_DT(Timestamp.from(Instant.now()));
		char z = '-';
		submission_Bc.setCORRESPONDENCE_CD(z);
		submission_Bc.setAMENDED_DOC_CD(z);
		
		TRANSMITTER_BC transmitter_Bc = new TRANSMITTER_BC();
		transmitter_Bc.setTRANSMITTER_BC_ID(transmitterBc.getId()+1);
		transmitter_Bc.setGSO_ID(gsoId);
		transmitter_Bc.setFORM_TYPE(formType);
		transmitter_Bc.setDOC_SYSTEM_FILE_NAME_TXT(request.getFilename());
		transmitter_Bc.setTRANSMISSIONID_NUM(1);
		transmitter_Bc.setTAX_YR("2023");
		transmitter_Bc.setUNIQUE_TRANS_ID("1");
		transmitter_Bc.setTCC("tcc001");
		transmitter_Bc.setINSERTED_BY_TXT("int_test");
		transmitter_Bc.setINSERTED_DT(Timestamp.from(Instant.now()));
		
		ifsStatus.saveAndFlush(ifs_Status);
		submissionBc.saveAndFlush(submission_Bc);
		transmitterBc.saveAndFlush(transmitter_Bc);		
	}
	
	@Then("^verify the response is valid$")
	public void validate_response() throws Throwable {
		assertEquals(request.getId(),response.getCounty().get(0).getId());
		assertEquals(request.getFilename(),response.getCounty().get(0).getFilename());
		
		//String respStr = reqCtx.getResponseStr();
		//String respStr = clientInterceptor.getReqCtx().getResponseStr();
		//log.info("respStr : {}", respStr);
	}
	
	@Then("^verify the file attachment is written to the working directory$")
	public void verify_file_written_to_workingdir() throws Throwable  {
		//endpointinterceptor in the webservice writes the file to the workingdir
		filePath = workingDir + request.getFilename();
		log.info("verify file : {}", filePath);
		File newFile = new File(filePath);
		
		assertTrue(newFile.exists());
		assertTrue(!newFile.isDirectory());
		assertTrue(newFile.length()>0);
	}
	
	@Then("^get the ACA tables status$")
	public void getStatus() throws InterruptedException {
		//transmitterBcService.getStatus1095A(gsoId);
		//log.info("Waiting 5 seconds....");
		//Thread.sleep(5000);
		List<Object[]> results = transmitterBc.submissionStatus1095A(gsoId);
		log.info("Resultset Size : ", results.size());
	}
	
	@And("^the county name expectedValue should equal \"(.*)\"$")
	public void verify_countyName(String expResp) throws Throwable {
		scenarioContext.setContext(Context.EXPECTED_RESPONSE, expResp);
		scenarioContext.setContext(Context.ACTUAL_RESPONSE, response.getCounty().get(0).getCounty());
		if(!expResp.equals(response.getCounty().get(0).getCounty())) {
			scenarioContext.setContext(Context.TESTCASE_STATUS, false);
		}
		
		//assertEquals(expResp, response.getCounty().get(0).getCounty());
		
		//if (response.getCounty().get(0).getFiledata()!=null) {
		//	String txtData = IOUtils.toString(response.getCounty().get(0).getFiledata().getInputStream(),"UTF-8");
		//	log.info("textData : {}", txtData);		//}	
		
	}

	@And("^verify the database is updated with the expectedValue \"(.*)\"$")
	public void verify_db_value(String expResp) {
		county = repo.findById(request.getId()).get();

		assertEquals(expResp, county.getCounty());
		assertEquals(request.getFilename(), county.getFilename());
	}
	
	@When("^the getCounties WS is initiated with state \"(.*)\"$")
	public void getCoutiesWS_isCalled_with_state(String _state) throws Throwable {
		request = new GetCountiesRequest();
		request.setState(_state);
		//response = (GetCountiesResponse) soapConnector.callWebService(request);
		//assertNotNull(response);
		//log.info("**** county : {}", response.getCounty().get(0).getCounty());
	}
	
	@Then("^the county list should contain \"(.*)\"$")
	public void verify_countyList(String expResp) throws Throwable {
		List<String> counties = response.getCounty().stream()
				.map(c -> c.getCounty())
				.collect(Collectors.toList());
		scenarioContext.setContext(Context.EXPECTED_RESPONSE, expResp);
		scenarioContext.setContext(Context.ACTUAL_RESPONSE, counties.toString());
		if(!counties.contains(expResp)) {
			scenarioContext.setContext(Context.TESTCASE_STATUS, false);
		}
		//assertTrue(response.getCounty().stream().anyMatch(c -> c.getCounty().contains(expResp)));				
	}
	
	
	@BeforeAll
	public static void beforeAll() throws Exception {
		//log.info("Executing beforeAll()...");
		//initiate list of scenarioContexts
		listOfScenarios = new ArrayList<ScenarioContext>();
	}
	
	@Before(TAG)
	public void init() throws Exception {
		//isEndpointSecure = false;
		baseSteps.initializeTestRestTemplate();
		this.scenarioContext = new ScenarioContext();
	}
	
	@BeforeStep
	public void beforeStep() {
		//log.info("Executing beforeStep()...");
		
	}

	@AfterStep
	public void afterStep() {
		//log.info("Executing afterStep()...");
		
	}	
	
	@After(TAG)
	public void afterEach() throws Exception {
		//log.info("Executing afterEach()...");
		//increment list
		scenarioContext.setContext(Context.ELEMENTS, "");
		scenarioContext.setContext(Context.REMOVE_EMPTY, "");
		listOfScenarios.add(scenarioContext);
	}
	
	@AfterAll
	public static void afterAll() throws Exception {
		//log.info("Executing afterAll()...");
		log.info("# of scenarios : {}", listOfScenarios.size());
		String rptFileName = "CountyWSTests.xlsx";
		if (listOfScenarios.size()>0) ReportGenerator.createReport(listOfScenarios, rptFileName);
	}

}

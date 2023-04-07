package us.states.cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
/*
 * @author Anthony Hemmans
 * @date 11/18/2022
 *
 */
@Slf4j
public class HealthCheckTests { 

	@Value("${service.host.url}")
	protected String serviceUrl;
	
	@Autowired
	BaseSteps baseSteps; 
	
	ResponseEntity<String> actualResponse;
	String expectedResponse;
	
	private static final String TAG = "@Health";
	private static final String REST_API_URI = "/actuator/health";
	
	@When("^the health check api is called$")
	public void call_health_check_api() throws Throwable {
		actualResponse = baseSteps.invokeGetRestApi(REST_API_URI, false);
		//log.info("**** actualResponse : {}",  actualResponse.getBody());
	}
	
	@Then("^the health check api should return a httpstatus of 200$")
	public void health_check_response_ok() throws Throwable {
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
	}
	
	@And("^the health check api should return a status of \"(.*)\"$")
	public void health_check_should_be_up(String status) throws Throwable {
		expectedResponse = "{\"status\":\"" + status + "\"}";
		JSONAssert.assertEquals(expectedResponse, actualResponse.getBody(), JSONCompareMode.LENIENT);
	}
	
	@Before(TAG)
	public void init() throws Exception {
		//isEndpointSecure = false;
		baseSteps.initializeTestRestTemplate();
	}

}

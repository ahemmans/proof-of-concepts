package us.gov.treasury.irs.peraton.poc.cucumber.steps;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/*
 * @author Anthony Hemmans
 * @date 07/14/2022
 * 
 */
@Component
public class BaseSteps  {
	private final Logger log = LogManager.getLogger(getClass());		
    protected static RestTemplate restTemplate;	 
	protected String expectedResponse = null;
	protected ResponseEntity<String> actualResponse = null;
	protected ResponseEntity<String> responseEntity = null;
	protected boolean isEndpointSecure;	
	
	@Value("${service.host.url}")
	protected String serviceUrl;
	
	@Value("${ess.test.role}")
	private String testRole;	
	
	public void initializeTestRestTemplate() throws Exception {
		restTemplate = new RestTemplateBuilder().rootUri(serviceUrl).build();   
    }
	
	public ResponseEntity<String> invokePostRestApi(String restUri, String requestJson, boolean isSecure) throws Exception {
		log.info("uri : {} \r\n method : POST \r\n  request : {}", serviceUrl + restUri, requestJson);
		try {
			responseEntity =  restTemplate
					.postForEntity(URI.create(serviceUrl + restUri), 
							new HttpEntity<>(requestJson, getHeaders(ApiMethod.POST_APPOINTMENT, isSecure, testRole)), String.class);
			
		} catch (RestClientResponseException e) {
			
			 responseEntity = ResponseEntity
	                .status(e.getRawStatusCode())
	                .body(e.getResponseBodyAsString());
		}
		
		return responseEntity;
	}	
	
	public ResponseEntity<String> invokePostRestApi(String restUri, String requestJson, ApiMethod method, boolean isSecure) throws Exception {
		String uri = method.equals(ApiMethod.POST_TIMESLOT)  ? restUri : serviceUrl + restUri;		
		log.info("uri : {} \r\n method : POST \r\n  request : {}", uri, requestJson);
		try {
			responseEntity =  restTemplate
					.postForEntity(URI.create(uri), 
							new HttpEntity<>(requestJson, getHeaders(method, isSecure, testRole)), String.class);
			
		} catch (RestClientResponseException e) {
			
			 responseEntity = ResponseEntity
	                .status(e.getRawStatusCode())
	                .body(e.getResponseBodyAsString());
		}
		
		return responseEntity;
	}
	
	public ResponseEntity<String> invokePutRestApi(String restUri, String requestJson, boolean isSecure) throws Exception {
		log.info("uri : {} \r\n  method : PUT \r\n request : {}", serviceUrl + restUri, requestJson);
		try {
			responseEntity = restTemplate
					.exchange(URI.create(serviceUrl + restUri), HttpMethod.PUT,
							new HttpEntity<>(requestJson, getHeaders(ApiMethod.PUT_APPOINTMENT, isSecure, testRole)), String.class);
		} catch (RestClientResponseException e) {
			
			 responseEntity = ResponseEntity
	                .status(e.getRawStatusCode())
	                .body(e.getResponseBodyAsString());
		}
		
		return responseEntity;
	}

	public ResponseEntity<String> invokePutRestApi(String restUri, String requestJson, String param, boolean isSecure) throws Exception {
		log.info("uri : {} \r\n  method : PUT \r\n request : {}", serviceUrl + restUri, requestJson);
		if (param.isEmpty()) {
			responseEntity = invokePutRestApi(restUri, requestJson, isSecure);
		} else {
			try {
				responseEntity = restTemplate
						.exchange(URI.create(serviceUrl + restUri), HttpMethod.PUT,
								new HttpEntity<>(requestJson, getHeaders(ApiMethod.PUT_APPOINTMENT, param, isSecure, testRole)), String.class);
			} catch (RestClientResponseException e) {
				
				 responseEntity = ResponseEntity
		                .status(e.getRawStatusCode())
		                .body(e.getResponseBodyAsString());
			}
		}
		
		return responseEntity;
	}
	
	public ResponseEntity<String> invokeGetRestApi(String restUri, boolean isSecure) throws Exception {
		log.info("uri : {} \r\n method : GET", serviceUrl + restUri);
		try {
			responseEntity = restTemplate
					.exchange(URI.create(serviceUrl + restUri), HttpMethod.GET,
							new HttpEntity<>(getHeaders(ApiMethod.GET_APPOINTMENT, isSecure, testRole)), String.class);

		} catch (RestClientResponseException e) {
			
			 responseEntity = ResponseEntity
	                .status(e.getRawStatusCode())
	                .body(e.getResponseBodyAsString());
		}
		
		return responseEntity;
	}
	
	public ResponseEntity<String> invokeGetRestApi(String restUri, ApiMethod method, boolean isSecure) throws Exception {
		String uri = (method.equals(ApiMethod.GET_AVAILABLE_APPOINTMENTS) || method.equals(ApiMethod.GET_TIMESLOTS)) ? restUri : serviceUrl + restUri;		
		log.info("uri : {} \r\n method : GET", uri);
		try {
			responseEntity = restTemplate
					.exchange(URI.create(uri), HttpMethod.GET,
							new HttpEntity<>(getHeaders(method, isSecure, testRole)), String.class);

		} catch (RestClientResponseException e) {
			
			 responseEntity = ResponseEntity
	                .status(e.getRawStatusCode())
	                .body(e.getResponseBodyAsString());
		}
		
		return responseEntity;
	}
	
	public ResponseEntity<String> invokeGetRestApi(String restUri, String param, ApiMethod method, boolean isSecure) throws Exception {
		log.info("uri : {} \r\n method : GET", serviceUrl + restUri);
		if (param.isEmpty()) {
			responseEntity = invokeGetRestApi(restUri, method, isSecure);
		} else {
			try {
				responseEntity = restTemplate
						.exchange(URI.create(serviceUrl + restUri), HttpMethod.GET,
								new HttpEntity<>(getHeaders(method, param, isSecure, testRole)), String.class);
	
			} catch (RestClientResponseException e) {
				
				 responseEntity = ResponseEntity
		                .status(e.getRawStatusCode())
		                .body(e.getResponseBodyAsString());
			}
		}
		
		return responseEntity;
	}
	
	public ResponseEntity<String> invokeDeleteRestApi(String restUri, String requestJson, boolean isSecure) throws Exception {
		log.info("uri : {} \r\n method : DELETE \r\n  request : {}", serviceUrl + restUri, requestJson);
		try {
			responseEntity = restTemplate
					.exchange(URI.create(serviceUrl + restUri), HttpMethod.DELETE,
							new HttpEntity<>(getHeaders(ApiMethod.DELETE_APPOINTMENT, isSecure, testRole)), String.class);

		} catch (RestClientResponseException e) {
			
			 responseEntity = ResponseEntity
	                .status(e.getRawStatusCode())
	                .body(e.getResponseBodyAsString());
		}
		
		return responseEntity;
		
	}	

	protected HttpHeaders getHeaders(ApiMethod method) throws Exception {
		return getHeaders(method, false, null);
	}

	protected HttpHeaders getHeaders(ApiMethod method, String param) throws Exception {
		return getHeaders(method, param, false, null); 
	}
	
	protected HttpHeaders getHeaders(ApiMethod method, boolean isSecure, String role) throws Exception {
		log.info("ROLE : {}", role);
		HttpHeaders httpHeaders = new HttpHeaders();
		//if (isSecure) httpHeaders.setBearerAuth(jwt.getToken(role));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //httpHeaders.add("ContextID", AppointmentServiceConstants.CONTEXT_ID);
        //httpHeaders.add("ConsumerID", AppointmentServiceConstants.CONSUMER_ID);
        //if(method.equals(ApiMethod.GET_APPOINTMENT)) {
        //	httpHeaders.set("RequestId", UUID.randomUUID().toString());
        //}

        return httpHeaders;
	}

	protected HttpHeaders getHeaders(ApiMethod method, String param, boolean isSecure, String role) throws Exception {
		HttpHeaders httpHeaders = getHeaders(method, isSecure, role);
        if(method.equals(ApiMethod.GET_APPOINTMENT_BY_CONFIRMATION_NUMBER)) {
        	httpHeaders.set("loginUsername", param);
        }
        if(method.equals(ApiMethod.PUT_APPOINTMENT)) {
        	httpHeaders.set("callDownstreamApi", param);
        }
        
        return httpHeaders;
	}
	
	protected ObjectMapper getObj() {
		return JsonMapper.builder()
				   .addModule(new Jdk8Module())
				   .addModule(new JavaTimeModule())
				   .build();
	}
}

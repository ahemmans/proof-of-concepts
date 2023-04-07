package us.states.ws;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.AutoConfigureMockWebServiceClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.ResponseActions;
import org.springframework.xml.transform.StringSource;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.ws.test.server.RequestCreators.*;
import static org.springframework.ws.test.server.ResponseMatchers.*;

import us.states.dao.CountyRepository;
import us.states.domain.County;
import us.states.util.DataUtils;
import us.states.util.StringUtils;

@Slf4j
@SpringBootTest 
@AutoConfigureMockWebServiceClient
public class CountyServiceEndpointTest {

	private static final Map<String, String> NAMESPACE_MAPPING = createMapping();
	static final String FILE_PATH = "src/test/resources/testdata/";
	
	@Mock
	CountyServiceEndpoint countyServiceEndpoint;
	
	@Autowired
    private MockWebServiceClient client;
	
	@Mock
	private CountyRepository countyRepository;
	
	@Mock
	private DataUtils dataUtils;
	
	@BeforeAll
	static void setup() {
		
	}
	
	//TODO: find way to execute with either response_001 or response_002, w/o having to execute "@Clear" test
	@Disabled
	@Test
    void givenXmlRequest_whenServiceInvoked_thenValidResponse() throws IOException {
		County county = createCounty();		
		when(countyRepository.getOne(24033)).thenReturn(county);
		
		StringSource request = new StringSource(StringUtils.readFileAsString(FILE_PATH + "test_data_request_001.xml"));

		StringSource expectedResponse = new StringSource(StringUtils.readFileAsString(FILE_PATH + "test_data_response_002.xml")
																						.replace("\n", "")
																						.replace("\r", "")
																						.replace("\t", "")
																						.trim() 
		);
		
		ResponseActions response = client.sendRequest(withPayload(request))
			.andExpect(noFault())
			.andExpect(validPayload(new ClassPathResource("schema/county.xsd")))
			//.andExpect(xpath("/ns2:getCountiesResponse/ns2:county[1]/ns2:state", NAMESPACE_MAPPING)
		    //        .evaluatesTo("Maryland"));
			.andExpect(payload(expectedResponse));		
		
		//log.info("*** response : {}", response.toString());
		//response.andExpect(payload(expectedResponse));
	}
	
    private static Map<String, String> createMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("ns2", "http://www.tier3llc.com/countyservice");
        return mapping;
    }

    private County createCounty() {
    	County county = new County();
		county.setId(24033);
		county.setCounty("Prince George's County");
		county.setPopulation((long) 890081);
		county.setStAbbr("MD");
		county.setState("Maryland");
		county.setFilename("fileattach.txt");
		county.setMimetype("text/plain");
		county.setFiledata(new byte[0]);
		
		return county;
    }

}

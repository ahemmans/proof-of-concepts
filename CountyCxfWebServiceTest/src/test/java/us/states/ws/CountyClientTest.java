package us.states.ws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.tier3llc.countyservice.CountiesPort;
import com.tier3llc.countyservice.CountiesPortService;
import com.tier3llc.countyservice.County;
import com.tier3llc.countyservice.GetCountiesRequest;
import com.tier3llc.countyservice.GetCountiesResponse;

import lombok.extern.slf4j.Slf4j;
import us.states.config.AppConfig;

@Slf4j
@SpringJUnitConfig(AppConfig.class)
@TestPropertySource(value="/application.yaml")
public class CountyClientTest {

	@Autowired
	CountyClient client;
	
	CountiesPortService service;
	CountiesPort countyService;
	GetCountiesRequest request;
	GetCountiesResponse response;
	County county;
	
	@BeforeEach
	public void setup() {
		service = new CountiesPortService();
		countyService = service.getCountiesPortSoap11();
	}
	
	@Disabled
	@Test
	public void givenCountyById() {
		request = new GetCountiesRequest();
		request.setId(24033);
		request.setFilename("fileattach.txt");
		request.setMimetype("text/plain");
		response = countyService.getCounties(request);
		//log.info("response : {}", response.toString());
		assertNotNull(response);
		assertEquals("Prince George's County", response.getCounty().get(0).getCounty());
		//log.info("**** county : " + response.getCounty().get(0).getCounty());
	}
	
	@Disabled
	@Test
	public void getCountyFromClient() {
		county = client.getCountyById(24033);
		//log.info("county : {}", county.toString());
		assertNotNull(county);
		assertEquals("Prince George's County", county.getCounty());		
	}
}

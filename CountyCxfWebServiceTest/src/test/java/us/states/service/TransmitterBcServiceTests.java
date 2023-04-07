package us.states.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;

@Disabled
@Slf4j
@SpringBootTest
@ActiveProfiles("it")
public class TransmitterBcServiceTests {

	@Autowired TransmitterBcService transmitterBcService;
	
	@Disabled
	@Test
	public void TestGetStatus1095A() {
		String gsoId = "9f61909d";
		transmitterBcService.getStatus1095A(gsoId);
		assertTrue(true);
		
	}
	
}

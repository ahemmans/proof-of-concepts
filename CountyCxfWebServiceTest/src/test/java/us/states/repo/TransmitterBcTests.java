package us.states.repo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import lombok.extern.slf4j.Slf4j;
import us.states.ws.it.CountyWsIT;

@Disabled
@Slf4j
@SpringBootTest
@ActiveProfiles("it")
public class TransmitterBcTests {

	@Autowired TransmitterBc transmitterBc;
	
	@Disabled
	@Test
	public void TestResultSet() {
		String gsoId = "c2e63047";
		List<Object[]> results = transmitterBc.submissionStatus1095A(gsoId);
		log.info("results size : {}", results.size());
		assertTrue(results.size()>0);
	}
}

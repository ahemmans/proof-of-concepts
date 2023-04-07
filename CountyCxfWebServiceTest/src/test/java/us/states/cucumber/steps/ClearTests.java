package us.states.cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.tier3llc.countyservice.GetCountiesRequest;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import lombok.extern.slf4j.Slf4j;

import us.states.domain.County;
import us.states.repo.CountyRepository;
import us.states.util.XmlUtils;

@Tag("Clear")
@Slf4j
public class ClearTests {

	@Autowired CountyRepository repo;	
	@Autowired County county;	
	@Autowired BaseSteps baseSteps;
	@Autowired XmlUtils xmlUtil;
	
	@Value("${workingdir.path}") 
	String workingDir;
	
	@Value("${workingdir.reports}") 
	String rptWorkingDir;
	
	private GetCountiesRequest request;
	private String filePath;	
	
	private static final String TEST_DATA = "src/test/resources/testdata/";
	private static final String TAG = "@Clear";
	
	@When("^the getCounties WS is cleared with request \"(.*)\"$")
	public void getCoutiesWS_isCleared_with_file(String filename) throws Throwable {
		filePath = TEST_DATA + filename;
		request = xmlUtil.convertRequestFile(filePath);
		
		assertNotNull(request);
	}	

	@Then("^clear the working directory$")
	public void clear_workingdir() throws Throwable  {
		File directory = new File(workingDir);
		FileUtils.cleanDirectory(directory);
		assertTrue(directory.listFiles().length == 0);
	}
	
	@And("^clear the database record$")
	public void update_database_record() throws Throwable {
		repo.updateRecord("", "", request.getId());
		county = repo.findById(request.getId()).get();
		assertEquals("",county.getFilename());
	}
	
	@Before(TAG)
	public void init() throws Exception {
		baseSteps.initializeTestRestTemplate();
	}
	
}

package us.states.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import us.states.dao.CountyRepository;
import us.states.domain.County;

@Slf4j
@SpringBootTest
public class CountyServiceTest {

	static final String FILE_PATH = "src/main/resources/attachments/";
	
	@Autowired
	CountyService service;
	
	@Autowired
	CountyRepository repo;
	
	@Autowired
	County county;
	
	@Disabled
	@Test
	public void insertImage() throws Exception {
		int id = 24033;
		insertImage(id);
		assertTrue(true);
	}
	
	private void insertImage(int id) throws IOException {
		log.info("About to execute insert insert id : {}", id);
		//get current data
		county = repo.getById(id);
		county.setFiledata(getFileData(county.getFilename()));
		repo.save(county);
	}

	private byte[] getFileData(String fileName) throws IOException  {

		  //File file = new File(FILE_PATH + fileName);
		  //byte[] bytes = Files.readAllBytes(file.toPath());
		  log.info("getting file : {}",  FILE_PATH + fileName);
		  byte[] bytes = Files.readAllBytes(Paths.get(FILE_PATH + fileName));
		  log.info("file size ", bytes.length);
		  return bytes;
	}
}

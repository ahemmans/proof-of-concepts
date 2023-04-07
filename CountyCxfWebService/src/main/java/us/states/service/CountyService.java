package us.states.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import us.states.dao.CountyRepository;
import us.states.domain.County;

@Slf4j
@Service
public class CountyService {

	static final String FILE_PATH = "src/main/resources/attachments/";
	
	@Autowired
	CountyRepository repo;
	
	@Autowired
	County county;
	
	public void insertImage(int id) throws IOException {
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

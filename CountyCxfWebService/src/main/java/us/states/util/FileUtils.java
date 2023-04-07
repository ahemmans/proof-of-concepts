package us.states.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileUtils {

	@Value("${workingdir.path}") 
	String filePath;
	
	public void writeBytesToFile(String path, String fileName, byte[] bytearray) {
		
	}
	
	public void writeBytesToFile(String fileName, byte[] bytearray) throws IOException {
		File outputFile = new File(filePath+fileName);
		try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
		    outputStream.write(bytearray);
		}
	}
	
}

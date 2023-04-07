package us.states.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.activation.DataHandler;

import org.springframework.stereotype.Service;

import com.sun.istack.ByteArrayDataSource;

@Service
public class DataUtils {

	public static DataHandler mapFileData(byte[] filedata) {
		
		ByteArrayDataSource ds = new ByteArrayDataSource(filedata, filedata.length, "application/octet-stream");
		return new DataHandler(ds);		
	}
	
	public static DataHandler mapFileData() {
		
		ByteArrayDataSource ds = new ByteArrayDataSource(new byte[0], 0, "application/octet-stream");
		return new DataHandler(ds);		
	}
	
	public static DataHandler mapFileData(String filePath) throws IOException {
		
		ByteArrayDataSource ds = new ByteArrayDataSource(mapFileBytes(filePath), mapFileBytes(filePath).length, "application/octet-stream");
		return new DataHandler(ds);		
	}
	
	
	public static byte[] mapFileBytes(File file) throws IOException {
		return Files.readAllBytes(file.toPath());
	}
	
	public static byte[] mapFileBytes(String filePath) throws IOException {
		//return Files.readAllBytes(Paths.get(filePath));		
		return mapFileBytes(new File(filePath));
	}
	
}

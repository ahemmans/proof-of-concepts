package us.states.util;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.activation.DataHandler;

import org.apache.commons.io.IOUtils;
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
	
	public static byte[] mapFileBytes(DataHandler dataHandler) throws IOException {
		//InputStream in = dataHandler.getInputStream();
		//OutputStream out = dataHandler.getOutputStream();
		return IOUtils.toByteArray(dataHandler.getInputStream());	
	}
	
	private static final int INITIAL_SIZE = 1024 * 1024;
	private static final int BUFFER_SIZE = 1024;

	public static byte[] toBytes(DataHandler dh) throws IOException {
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(INITIAL_SIZE);
	    DataFlavor flavor = new DataFlavor(InputStream.class,"text/plain");
	    try (InputStream in = (InputStream) dh.getTransferData(flavor)) {
	    	//InputStream in = dh.getInputStream();
	 	    byte[] buffer = new byte[BUFFER_SIZE];
	 	    int bytesRead;
	 	    while ( (bytesRead = in.read(buffer)) >= 0 ) {
	 	        bos.write(buffer, 0, bytesRead);
	 	    }	    
	 	
	    } catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return bos.toByteArray();
	}   	   
	
}

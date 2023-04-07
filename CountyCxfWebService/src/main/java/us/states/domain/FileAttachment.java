package us.states.domain;

import lombok.Data;

@Data
public class FileAttachment {

	private String fileName;
	
	private String contentId;
	
	private String mimeType;
	
	private int fileLength;
	
	private byte[] fileData;
	
}

package us.gov.treasury.irs.peraton.poc.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

@Service
public class DocumentWriter {

	private OutputFormat format = OutputFormat.createCompactFormat();
	private FileWriter fileWriter;
	private XMLWriter writer;
	
	public void 
	write(Document doc, File file, String fmt) 
			throws IOException {
		
		if (fmt.equalsIgnoreCase("pretty") ) {
			format = OutputFormat.createPrettyPrint();
		}
		fileWriter = new FileWriter(file);
		writer = new XMLWriter(fileWriter, format);			
		writer.write(doc);
		writer.close();
	}
	
	public void 
	write(Document doc, File file) 
			throws IOException {
		
		write(doc, file, "compact");
	}
	
}

package us.gov.treasury.irs.peraton.poc.util;

import java.io.File;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

@Service
public class DocumentReader {

	public Document 
	parse(File file) 
			throws DocumentException {
        
		SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }
	
	public Document 
	parse(InputStream in) 
			throws DocumentException {
        
		SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        return document;
    }
	
	public Document 
	parse(InputSource ins) 
			throws DocumentException {
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(ins);
		return document;
	}


	
}

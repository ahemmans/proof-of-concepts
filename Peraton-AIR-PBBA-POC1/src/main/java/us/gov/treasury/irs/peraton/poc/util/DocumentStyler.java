package us.gov.treasury.irs.peraton.poc.util;

import java.io.File;
import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.springframework.stereotype.Service;

import net.sf.saxon.BasicTransformerFactory;

@Service
public class DocumentStyler {

	private TransformerFactory factory = BasicTransformerFactory.newInstance();
    private Transformer transformer;
    private DocumentSource source;
    private DocumentResult result;
    private Document transformedDoc;
    
	public Document 
	styleDocument(Document document, File stylesheet) 
			throws Exception {

        // load the transformer using JAXP
        //TransformerFactory factory = TransformerFactory.newInstance();
		transformer = factory.newTransformer(new StreamSource(stylesheet));

        // now lets style the given document
        source = new DocumentSource(document);
        result = new DocumentResult();
        transformer.transform(source, result);

        // return the transformed document
        transformedDoc = result.getDocument();
        return transformedDoc;
    }
	
	public Document 
	styleDocument(Document document, InputStream stylesheet) 
			throws Exception {

        // load the transformer using JAXP
        //TransformerFactory factory = TransformerFactory.newInstance();
		transformer = factory.newTransformer(new StreamSource(stylesheet));

        // now lets style the given document
		source = new DocumentSource(document);
		result = new DocumentResult();
        transformer.transform(source, result);
        
        // return the transformed document
        transformedDoc = result.getDocument();
        return transformedDoc;
    }

	public Document 
	styleDocument(Document document, InputStream stylesheet, String xsd) 
			throws Exception {

        // load the transformer using JAXP
        //TransformerFactory factory = TransformerFactory.newInstance();
		transformer = factory.newTransformer(new StreamSource(stylesheet));
		transformer.setParameter("xsd-file", xsd);
		
        // now lets style the given document
		source = new DocumentSource(document);
		result = new DocumentResult();
        transformer.transform(source, result);
                
        // return the transformed document
        transformedDoc = result.getDocument();
        return transformedDoc;
    }
	
	public String 
	styleDocumentAsString(Document document, File stylesheet) 
			throws Exception {
		
		Document styleDoc = styleDocument(document, stylesheet);
		
		return styleDoc.asXML();
	}
 
}

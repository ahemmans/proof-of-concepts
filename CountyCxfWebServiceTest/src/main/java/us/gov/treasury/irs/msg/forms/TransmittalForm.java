package us.gov.treasury.irs.msg.forms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;
import us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.Form109495BTrnsmtUpstreamType;
import us.gov.treasury.irs.msg.form1094_1095ctransmitterupstreammessage.Form109495CTransmittalUpstreamType;
import us.gov.treasury.irs.msg.form1094_1095bcscripsintakemessage.Form109495BTransmittalSCRIPSType;
import us.gov.treasury.irs.msg.form1094_1095bcscripsintakemessage.Form109495CTransmittalSCRIPSType;

@Slf4j
public abstract class TransmittalForm {

	protected Object requestObj;
	
	public abstract Object unmarshall(String _file) throws FileNotFoundException, JAXBException;
	
	public abstract Form109495BTrnsmtUpstreamType getFormType109495B(String _file) throws FileNotFoundException, JAXBException;
	public abstract Form109495CTransmittalUpstreamType getFormType109495C(String _file) throws FileNotFoundException, JAXBException; 
	public abstract Form109495BTransmittalSCRIPSType getFromType109495BScrips(String _file) throws FileNotFoundException, JAXBException;
	public abstract Form109495CTransmittalSCRIPSType getFromType109495CScrips(String _file) throws FileNotFoundException, JAXBException;
		
	private Validator initValidator(String _xsd) throws SAXException {
	    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	    Source schemaFile = new StreamSource(new File(_xsd));
	    Schema schema = factory.newSchema(schemaFile);
	    return schema.newValidator();
	}
	
	protected boolean isValid(String _xsd, String _file) throws SAXException, IOException {
		Validator validator = initValidator(_xsd);
	    try {
	        validator.validate(new StreamSource(new File(_file)));
	        return true;
	    } catch (SAXException e) {
	    	log.error("XML Validation error : {}", e.getMessage());
	        return false;
	    }
	}
	
	public abstract boolean isXmlValid(String _file) throws SAXException, IOException;
	
}

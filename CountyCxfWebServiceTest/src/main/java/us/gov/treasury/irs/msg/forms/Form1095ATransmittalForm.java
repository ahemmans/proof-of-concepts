package us.gov.treasury.irs.msg.forms;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import us.gov.treasury.irs.msg.form1094_1095bcscripsintakemessage.Form109495BTransmittalSCRIPSType;
import us.gov.treasury.irs.msg.form1094_1095bcscripsintakemessage.Form109495CTransmittalSCRIPSType;
import us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.Form109495BTrnsmtUpstreamType;
import us.gov.treasury.irs.msg.form1094_1095ctransmitterupstreammessage.Form109495CTransmittalUpstreamType;

@Service
public class Form109495ATransmittalForm extends TransmittalForm {

	public static final String XSD_DOC = "src/main/resources/2022/MSG/IRS-Form1095ATransmissionUpstreamMessage.xsd";
	private JAXBContext jaxbContext;
	private Marshaller jaxbMarshaller;
	private Unmarshaller jaxbUnmarshaller;
	
	@Override
	public Object unmarshall(String _file) throws FileNotFoundException, JAXBException {
		return null;
	}

	@Override
	public boolean isXmlValid(String _file) throws SAXException, IOException {
		return isValid(XSD_DOC, _file);
	}
	
	@Override
	public Form109495BTrnsmtUpstreamType getFormType109495B(String _file) throws FileNotFoundException, JAXBException {
		return null;
	}

	@Override
	public Form109495CTransmittalUpstreamType getFormType109495C(String _file) throws FileNotFoundException, JAXBException {
		return null;
	}

	@Override
	public Form109495BTransmittalSCRIPSType getFromType109495BScrips(String _file) throws FileNotFoundException, JAXBException {
		return null;
	}

	@Override
	public Form109495CTransmittalSCRIPSType getFromType109495CScrips(String _file) throws FileNotFoundException, JAXBException {
		return null;
	}



}

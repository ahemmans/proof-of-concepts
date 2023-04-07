package us.gov.treasury.irs.msg.forms;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.Form109495BTrnsmtUpstreamType;
import us.gov.treasury.irs.msg.form1094_1095ctransmitterupstreammessage.Form109495CTransmittalUpstreamType;
import us.gov.treasury.irs.msg.form1094_1095bcscripsintakemessage.Form109495BTransmittalSCRIPSType;
import us.gov.treasury.irs.msg.form1094_1095bcscripsintakemessage.Form109495CTransmittalSCRIPSType;
import us.gov.treasury.irs.msg.form1094_1095bcscripsintakemessage.ObjectFactory;

@Service
public class Form109495CScripsTransmittalForm extends TransmittalForm {

	public static final String XSD_DOC = "src/main/resources/2022/MSG/IRS-Form1094-1095CTransmissionSCRIPSIntakeMessage.xsd";
	private JAXBContext jaxbContext;
	private Marshaller jaxbMarshaller;
	private Unmarshaller jaxbUnmarshaller;
	
	private Unmarshaller getJaxbUnmarshaller() throws JAXBException {
		jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		jaxbUnmarshaller =  jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller;
	}
	
	@Override
	public Object unmarshall(String _file) throws FileNotFoundException, JAXBException {
		requestObj = getJaxbUnmarshaller()
			.unmarshal(new StreamSource(_file), Form109495CTransmittalSCRIPSType.class).getValue();
		return requestObj;
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
	public Form109495CTransmittalSCRIPSType getFromType109495CScrips(String _file) throws FileNotFoundException, JAXBException {
		return (Form109495CTransmittalSCRIPSType) unmarshall(_file);
	}

	@Override
	public Form109495BTransmittalSCRIPSType getFromType109495BScrips(String _file) throws FileNotFoundException, JAXBException {
		return null;
	}
	



}

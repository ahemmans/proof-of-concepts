package us.states.cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import lombok.extern.slf4j.Slf4j;
import us.gov.treasury.irs.msg.forms.Form109495BScripsTransmittalForm;
import us.gov.treasury.irs.msg.forms.Form109495BTransmittalForm;
import us.gov.treasury.irs.msg.forms.Form109495CScripsTransmittalForm;
import us.gov.treasury.irs.msg.forms.Form109495CTransmittalForm;
import us.gov.treasury.irs.msg.forms.TransmittalForm;
import us.gov.treasury.irs.msg.form1094_1095bcscripsintakemessage.Form109495BTransmittalSCRIPSType;
import us.gov.treasury.irs.msg.form1094_1095bcscripsintakemessage.Form109495CTransmittalSCRIPSType;
import us.gov.treasury.irs.msg.form1094_1095btransmitterupstreammessage.Form109495BTrnsmtUpstreamType;
import us.gov.treasury.irs.msg.form1094_1095ctransmitterupstreammessage.Form109495CTransmittalUpstreamType;

import us.states.config.AppConstants;
import us.states.domain.County;
import us.states.repo.CountyRepository;
import us.states.repo.IfsStatus;
import us.states.repo.SubmissionBc;
import us.states.repo.TransmitterBc;
import us.states.report.ReportGenerator;
import us.states.service.TransmitterBcService;
import us.states.util.DataUtils;
import us.states.util.XmlUtils;
import us.states.ws.SoapConnector;

@Slf4j
public class TransmittalFormTests extends ReportGenerator {

	@Autowired CountyRepository repo;
	@Autowired County county;
	@Autowired SoapConnector soapConnector;
	@Autowired BaseSteps baseSteps;
	@Autowired XmlUtils xmlUtil;
	@Autowired DataUtils dataUtil;
	//@Autowired RequestContext reqCtx;

	//repos
	@Autowired IfsStatus ifsStatus;
	@Autowired SubmissionBc submissionBc;
	@Autowired TransmitterBc transmitterBc;
	@Autowired TransmitterBcService transmitterBcService;
	
	@Autowired Form109495BTransmittalForm form109495BTransmittalForm;
	@Autowired Form109495CTransmittalForm form109495CTransmittalForm;
	@Autowired Form109495BScripsTransmittalForm form109495BScripsTransmittalForm;
	@Autowired Form109495CScripsTransmittalForm form109495CScripsTransmittalForm;
		
	@Value("${workingdir.path}")
	String workingDir;
	
	private static final String TEST_DATA = "src/test/resources/testdata/fs22/";
	private static final String TEST_ATTACHMENT = "src/test/resources/attachments/";
	private static final String TAG = "@TransmittalForms";
	
	private String filePath, formType, gsoId;
	private Form109495BTrnsmtUpstreamType request1094B;
	private Form109495CTransmittalUpstreamType request1094C;
	private Form109495BTransmittalSCRIPSType request1094BScrips;
	private Form109495CTransmittalSCRIPSType request1094CScrips;
	
	
	/*
	private void marshall(String _file) throws JAXBException {
		//request
		jaxbContext = JAXBContext.newInstance(Form109495BTrnsmtUpstreamType.class);
		jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		jaxbMarshaller.marshal(request, new File(_file));
	}
	*/	
	
	private TransmittalForm getTransmittalForm(String _formType) {
		switch (_formType) {
			case "1094B" :
			case "1095B" :
				return (TransmittalForm) form109495BTransmittalForm;
			case "1094C" :
			case "1095C" :
				return (TransmittalForm) form109495CTransmittalForm;
			case "1094BScrips" :
			case "1095BScrips" :
				return (TransmittalForm) form109495BScripsTransmittalForm;	
			case "1094CScrips" :
			case "1095CScrips" :
				return (TransmittalForm) form109495CScripsTransmittalForm;
		}
		return null;		
	}	
	
	@Given("^the request file \"(.*)\" of formType \"(.*)\"$")
	public void given_test_file_of_formtype(String _file, String _formType) throws Throwable {
		filePath = TEST_DATA + _file;
		formType = _formType;
		assertTrue(new File(filePath).exists());
	}
	
	@Then("^validate the request \"(.*)\"$")
	public void validate_request(boolean _validateXML) throws Throwable {
		switch (formType) {
			case "1094B" :
			case "1095B" :
				if (_validateXML) assertTrue(getTransmittalForm(formType).isXmlValid(filePath));
				request1094B = getTransmittalForm(formType).getFormType109495B(filePath);
				assertNotNull(request1094B);				
				break;
			case "1094C" :
			case "1095C" :
				if (_validateXML) assertTrue(getTransmittalForm(formType).isXmlValid(filePath));
				request1094C = getTransmittalForm(formType).getFormType109495C(filePath);
				assertNotNull(request1094C);
				break;
			case "1094BScrips" :
			case "1095BScrips" :
				//TBD
				break;
			case "1094CScrips" :
			case "1095CScrips" :
				if (_validateXML) assertTrue(getTransmittalForm(formType).isXmlValid(filePath));
				request1094CScrips = getTransmittalForm(formType).getFromType109495CScrips(filePath);
				assertNotNull(request1094CScrips);
				break;
		}
	}
	
	@And("^verify that each taxyr element is \"(.*)\"$")
	public void verify_taxyr(String _taxyr) throws Throwable {
		switch (formType) {
			case "1094B" :
			case "1095B" :
				assertTrue(_taxyr.equals(request1094B.getForm1094BUpstreamDetail().get(0).getTaxYr()));
				request1094B.getForm1094BUpstreamDetail().get(0).getForm1095BUpstreamDetail()
					.stream()
					.forEach(t -> assertTrue(_taxyr.equals(t.getTaxYr())));
				break;
			case "1094C" :
			case "1095C" :
				assertTrue(_taxyr.equals(request1094C.getForm1094CUpstreamDetail().get(0).getTaxYr()));
				request1094C.getForm1094CUpstreamDetail().get(0).getForm1095CUpstreamDetail()
					.stream()
					.forEach(t -> assertTrue(_taxyr.equals(t.getTaxYr())));
				break;
			case "1094BScrips" :
			case "1095BScrips" :
				//TBD
				break;
			case "1094CScrips" :
			case "1095CScrips" :
				assertTrue(_taxyr.equals(request1094CScrips.getForm1094CSCRIPSsubmissionDetail().get(0).getTaxYrP().toString()));
				request1094CScrips.getForm1094CSCRIPSsubmissionDetail().get(0).getForm1095CSCRIPSDetail()
					.stream()
					.forEach(t -> assertTrue(_taxyr.equals(t.getTaxYrP().toString())));
				break;
		}
	}
	
}

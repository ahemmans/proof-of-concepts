package us.gov.treasury.irs.peraton.poc.fs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import us.gov.treasury.irs.peraton.poc.config.AppConstants;
import us.gov.treasury.irs.peraton.poc.config.DataFileProps;
import us.gov.treasury.irs.peraton.poc.util.DocumentReader;
import us.gov.treasury.irs.peraton.poc.util.DocumentStyler;
import us.gov.treasury.irs.peraton.poc.util.DocumentWriter;
import us.gov.treasury.irs.peraton.poc.util.Refactor;
import us.gov.treasury.irs.peraton.poc.util.FormType;

@Service
public class FilingSeasonUpdate {
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired DocumentReader readXml;
	@Autowired DocumentStyler styleXml;
	@Autowired DocumentWriter writeXml;
	@Autowired DataFileProps dataFileProps;
	
	@Value("${testdatadir}")  	   private String testdataDir;
	@Value("${formatxslt}")   	   private String formatXslt;
	@Value("${namespaceold}") 	   private String oldNamespace;
	@Value("${namespacenew}") 	   private String newNamespace;
	@Value("${refactor}")		   private String refactor;
	
	String oldFS, newFS, formType, testFile;
	String oldFileName, newFileName;	
	String testCaseId, testCaseIdNew, testCaseIdRef, docSysFileNm;
	
	public void retire_filing_season(String _oldFS) {
		oldFS = _oldFS;
	}

	//1 - Refactor1 - pre-merge  - only change filename 
	//2 - Refactor2 - post-merge - change filename, ty, namespace
	public void update_filing_season(String... args) throws TransformerException {
		String[] commandLineArgs = setFilingSeasons(args);
		if (!ObjectUtils.isEmpty(commandLineArgs) && commandLineArgs.length > 0) {
			try {
				Reader in = new FileReader(testFile);
				CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
						.setHeader(dataFileProps.getHeaders())
						.setSkipHeaderRecord(true)
						.build();
				Iterable<CSVRecord> records = csvFormat.parse(in);
				for (CSVRecord record : records) {
					testCaseId = record.get(0);
					testCaseIdRef = record.get(1);
					testCaseIdNew = record.get(2);					
					docSysFileNm = record.get(3);
					//log.info("==== " + testCaseId + " : " + testCaseIdNew + " : " + testCaseIdRef  + " : " + docSysFileNm);
					oldFileName = docSysFileNm;
					if(formType.equals(FormType.ELECTRONIC.getDesc()) || formType.equals(FormType.TEN95A.getDesc())) {
						newFileName = oldFileName
							.replace(testCaseId, testCaseIdNew)
							.replace(oldFS.toUpperCase(), newFS.toUpperCase());	
					} else if(formType.equals(FormType.REFACTOR.getDesc())) {
						newFileName = oldFileName
							.replace(testCaseId, testCaseIdRef)
							.replace(oldFS.toUpperCase(), newFS.toUpperCase());
					} else if(formType.equals(FormType.PAPER.getDesc())) {
						String oldTaxYr = String.valueOf(Integer.parseInt(oldFS.replace("fs", "20"))-1);
						String newTaxYr = String.valueOf(Integer.parseInt(newFS.replace("fs", "20"))-1);
						String currScrips = oldFS.replace("fs", "scrips");
						String newScrips = "scrips"+String.valueOf(Integer.parseInt(newFS.substring(2)));						
						newFileName = oldFileName
							.replace(oldTaxYr, newTaxYr)
							.replace(currScrips, newScrips)
							.replace(testCaseId, testCaseIdNew);
					}
					log.info("----------------");
					log.info("oldFileName : " + oldFileName);
					log.info("newFileName : " + newFileName);
					updateTestData(); 
				}
				
			} catch (IOException e) {
				log.error("Error reading csv file : " + e.getMessage());
			} finally {
				log.info("---- Execution Complete ----");
			}
			
		} else {
		  log.info("==== NO COMMAND LINE PARAMETERS SUMBITTED ====");
		}	
	}
	
	private String[] setFilingSeasons(String... args) {
		try {
		  String strArgs[] = Arrays.stream(args).collect(Collectors.joining("|")).split(",");
		  log.info("oldFS : " + strArgs[0].substring(strArgs[0].indexOf("=")+1));
		  log.info("newFS : " +  strArgs[1].substring(strArgs[1].indexOf("=")+1));
		  log.info("formType : " +  strArgs[2].substring(strArgs[2].indexOf("=")+1));
		  log.info("refactor stage: " +  refactor);
		  oldFS = strArgs[0].substring(strArgs[0].indexOf("=")+1);
		  newFS = strArgs[1].substring(strArgs[1].indexOf("=")+1);
		  formType = strArgs[2].substring(strArgs[2].indexOf("=")+1);
		  setTestFile(formType);
		  return strArgs;
		} catch (Exception e) {
		  return null;	
		}		
	}
	
	private String setTestFile(String formType) {
		List<String> dataFiles = dataFileProps.getDataFiles();
		for(int i=0;i<dataFiles.size();i++) {
			if (formType.equals(FormType.TEN95A.getDesc()) && dataFiles.get(i).contains(FormType.TEN95A.getDesc())) {
				testFile = dataFiles.get(i);
				break;
			} else if(formType.equals(FormType.PAPER.getDesc()) && dataFiles.get(i).contains(FormType.PAPER.getDesc().toUpperCase())) {
				testFile = dataFiles.get(i);
				break;
			} else if ( (formType.equals(FormType.ELECTRONIC.getDesc()) || formType.equals(FormType.REFACTOR.getDesc())) && dataFiles.get(i).contains(FormType.ELECTRONIC.getDesc().toUpperCase())) {
			//else if (formType.equals(FormType.ELECTRONIC.getDesc()) || formType.equals(FormType.REFACTOR.getDesc())) {
			//	testFile = dataFiles.stream()
			//		.filter(tf -> !tf.contains(FormType.TEN95A.getDesc()) && !tf.contains(FormType.PAPER.getDesc().toUpperCase()))
			//		.map(Object::toString)
			//		.collect(Collectors.joining());
				testFile = dataFiles.get(i);
				break;
			} 
		}
		log.info("sourceFile : " + testFile);
		return testFile;
	}
	
	private void updateTestData() throws TransformerException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		
		try {			
			String newDirectory = testdataDir.replace(oldFS, newFS);
			if(formType.equals(FormType.REFACTOR.getDesc())) {
				newDirectory = testdataDir.replace(oldFS, newFS+"/"+FormType.REFACTOR.getDesc());
			}
	        Files.createDirectories(Paths.get(newDirectory));
	       
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(testdataDir + oldFileName));
	        doc.getDocumentElement().normalize();
	    
	        if(formType.equals(FormType.ELECTRONIC.getDesc()) || refactor.equals(Refactor.POST_MERGE.getDesc())) {   
		        if (doc.getElementsByTagName(AppConstants.FORM_1094_1095B_ROOT_NODE).getLength() > 0) {
		        	updateFormDetailNode(doc.getElementsByTagName(AppConstants.FORM_1094_1095B_ROOT_NODE), AppConstants.FORM_1094B_DETAIL_NODE);
		        	updateFormDetailNode(doc.getElementsByTagName(AppConstants.FORM_1094B_DETAIL_NODE), AppConstants.FORM_1095B_DETAIL_NODE);
		        }
		        if (doc.getElementsByTagName(AppConstants.FORM_1094_1095C_ROOT_NODE).getLength() > 0) {
		        	updateFormDetailNode(doc.getElementsByTagName(AppConstants.FORM_1094_1095C_ROOT_NODE),  AppConstants.FORM_1094C_DETAIL_NODE);
		        	updateFormDetailNode(doc.getElementsByTagName(AppConstants.FORM_1094C_DETAIL_NODE),  AppConstants.FORM_1095C_DETAIL_NODE);
		        }
	        }
	      	if(formType.equals(FormType.TEN95A.getDesc())) {
	      		if (doc.getElementsByTagName(AppConstants.FORM_1095A_ROOT_NODE).getLength() > 0) {
	      			updateFormDetailNode(doc.getElementsByTagName(AppConstants.FORM_1095A_ROOT_NODE), AppConstants.FORM_1095A_DETAIL_NODE);
	      		}
	      	}
	      	if(formType.equals(FormType.PAPER.getDesc()) && !oldFileName.contains(".WO")) {
	      		if (doc.getElementsByTagName(AppConstants.FORM_1094_1095C_SCRIPS_ROOT_NODE).getLength() > 0) {
	      			updateFormDetailNode(doc.getElementsByTagName(AppConstants.FORM_1094_1095C_SCRIPS_ROOT_NODE), AppConstants.FORM_1094_1095C_SCRIPS_HEADER_NODE);
	      			updateFormDetailNode(doc.getElementsByTagName(AppConstants.FORM_1094_1095C_SCRIPS_ROOT_NODE), AppConstants.FORM_1094C_SCRIPS_SUBMISSION_DETAIL_NODE);
	      			updateFormDetailNode(doc.getElementsByTagName(AppConstants.FORM_1094C_SCRIPS_SUBMISSION_DETAIL_NODE), AppConstants.FORM_1095C_SCRIPS_DETAIL_NODE);	
	      		}
	      	}
	      	
	        FileOutputStream output = new FileOutputStream(newDirectory + newFileName);
	        log.info("new test file : " + newDirectory + newFileName);      
	        writeXml(doc, output);
	        
	        if(formType.equals(FormType.ELECTRONIC.getDesc()) || refactor.equals(Refactor.POST_MERGE.getDesc())) { 
	        	updateNamespace(new File(newDirectory + newFileName));
	        }
		} catch (ParserConfigurationException | SAXException | IOException e) {
			log.error("Exception parsing XML file : " + e.getMessage());
	    }
	}
	
	private void updateFormDetailNode(NodeList list, String detailNode) {
        for (int temp = 0; temp < list.getLength(); temp++) {
            Node node = list.item(temp);
            NodeList childNodes = node.getChildNodes();	             
            for (int i=0; i<childNodes.getLength(); i++) {
          	  	//log.info(childNodes.item(i).getNodeName() + " : " + childNodes.item(i).getTextContent());
          	  	if (childNodes.item(i).getNodeName().equals(detailNode)) {
          	  		NodeList childNodes2 = childNodes.item(i).getChildNodes();
          	  		for (int j=0; j<childNodes2.getLength(); j++) {
          	  			//log.info(childNodes2.item(j).getNodeName() + " : " + childNodes2.item(j).getTextContent());
          	  			if (childNodes2.item(j).getNodeName().equals(AppConstants.ELEMENT_TAX_YEAR) || childNodes2.item(j).getNodeName().equals(AppConstants.ELEMENT_TAX_YEARP)) {
          	  				String newTaxYr = String.valueOf(Integer.parseInt(newFS.replace("fs", "20"))-1);
          	  				childNodes2.item(j).setTextContent(newTaxYr);
          	  			}
          	  			if (childNodes2.item(j).getNodeName().equals(AppConstants.ELEMENT_PAYER_DLNP) || childNodes2.item(j).getNodeName().equals(AppConstants.ELEMENT_PAYEE_DLNP)) {
          	  				String currDLNP = childNodes2.item(j).getTextContent();
          	  				String updatedDLNP = currDLNP.replace(testCaseId, testCaseIdNew);
          	  				childNodes2.item(j).setTextContent(updatedDLNP);
          	  			}
	          	  		//if (childNodes2.item(j).getNodeName().equals(AppConstants.FORM_1095C_SCRIPS_DETAIL_NODE)) {
	          	  			//recursion
	          	  		//}
          	  		}
          	  	} 
            }
        }
	}	
	
	private void writeXml(Document doc, FileOutputStream output)
		throws TransformerException, UnsupportedEncodingException, FileNotFoundException {		
		
		TransformerFactory tF = TransformerFactory.newInstance();
		tF.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
	
		Transformer transformer = tF.newTransformer(new StreamSource(new File(formatXslt)));
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		//transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

		transformer.transform(new DOMSource(doc), new StreamResult(output));		
	}
	
	private void updateNamespace(File xmlFile)  {
		try {
			org.dom4j.Document xmlDoc = readXml.parse(xmlFile);
			log.debug("xmlDoc : " + xmlDoc.asXML());			
			String temp_str = xmlDoc.asXML().replace(oldNamespace, newNamespace);
			org.dom4j.Document updatedDoc = readXml.parse(new InputSource(new StringReader(temp_str)));
			writeXml.write(updatedDoc, xmlFile, "pretty");
			
		} catch (DocumentException | IOException e) {
			log.error("Exception parsing XML file : " + e.getMessage());
		}		
	}

	public void update_filing_season_xml(String... args) throws TransformerException {
		String[] commandLineArgs = setFilingSeasons(args);
		if (!ObjectUtils.isEmpty(commandLineArgs) && commandLineArgs.length > 0) {
		      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		      Document doc = null;
		      
		      try {
		          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		          DocumentBuilder db = dbf.newDocumentBuilder();
		          doc = db.parse(new File(testFile));
		          doc.getDocumentElement().normalize();
		
		          NodeList list = doc.getElementsByTagName("file");	          
		          for (int temp = 0; temp < list.getLength(); temp++) {
		              Node node = list.item(temp);
		              NodeList childNodes = node.getChildNodes();	              
		              for (int i=0; i< childNodes.getLength();i++) {	            	  
		            	  if (childNodes.item(i).getNodeName().equals("taxyear")) {
		            		  String taxyr = newFS.replace("fs", "20");
		            		  childNodes.item(i).setTextContent(taxyr);
		            	  }
		            	  if (childNodes.item(i).getNodeName().equals("filename")) {
		            		  log.info(childNodes.item(i).getNodeName() + " : " + childNodes.item(i).getTextContent());
		            		  //log.info(childNodes.item(i+2).getNodeName() + " : " + childNodes.item(i+2).getTextContent());
		            		  oldFileName = childNodes.item(i).getTextContent();	            		  
		            		  newFileName = childNodes.item(i+2).getTextContent().replace(oldFS.toUpperCase(), newFS.toUpperCase());
		            		  childNodes.item(i).setTextContent(newFileName);
		            		  updateTestData();	            		  
		            	  }
		            	  if (childNodes.item(i).getNodeName().equals("newfilename")) {
		            	  	  //log.info(childNodes.item(i).getNodeName() + " : " + childNodes.item(i).getTextContent());
		            		  newFileName = childNodes.item(i).getTextContent().replace(oldFS.toUpperCase(), newFS.toUpperCase());
		            	  	  childNodes.item(i).setTextContent(newFileName);
		            	  }
		              }
		          }	          
		          
		          String newFsFilename = testFile.replace(oldFS.toUpperCase(), newFS.toUpperCase());
		          log.info("newFsFilename = " + newFsFilename);
		          FileOutputStream output = new FileOutputStream(newFsFilename);
		          writeXml(doc, output);
		          
		      } catch (ParserConfigurationException | SAXException | IOException e) {
		          log.error("Exception parsing XML file : " + e.getMessage());		          
		      }	
		  } else {
			  log.info("==== NO COMMAND LINE PARAMETERS SUMBITTED ====");
		  }				
	}
	
}

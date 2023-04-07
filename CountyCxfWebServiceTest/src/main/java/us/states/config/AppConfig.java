package us.states.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import us.states.ws.CountyClient;
import us.states.ws.SoapConnector;

@Configuration
public class AppConfig {

	@Autowired
	private Environment env;
	
	@Value("${wsdl.url}")
	private String wsdlUrl;
	
	@Value("${context.path}")
	private String contextPath;
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(contextPath);
		marshaller.setMtomEnabled(true);
		return marshaller;
	}

	@Bean 
	public CountyClient countyClient(Jaxb2Marshaller marshaller) {
		CountyClient client = new CountyClient();
		client.setDefaultUri(wsdlUrl);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;		
	}
	
	@Bean
	public SoapConnector soapConnector(Jaxb2Marshaller marshaller) {
		SoapConnector client = new SoapConnector();
		client.setDefaultUri(wsdlUrl);		
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;	
	}
	
	@Bean
	public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
		ClientInterceptor[] interceptors = { new WebServiceClientInterceptor() };
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setDefaultUri(wsdlUrl);		
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		webServiceTemplate.setInterceptors(interceptors);
		webServiceTemplate.setCheckConnectionForFault(false);
		webServiceTemplate.setCheckConnectionForError(false);
		return webServiceTemplate;
	}
	
	public ClientInterceptor[] interceptors() {
		ClientInterceptor[] interceptors = { new WebServiceClientInterceptor() };
		return interceptors;
	}
}

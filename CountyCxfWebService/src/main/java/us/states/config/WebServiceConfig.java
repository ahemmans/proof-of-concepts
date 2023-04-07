package us.states.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurationSupport;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.adapter.DefaultMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import us.states.exception.DetailSoapFaultDefinitionExceptionResolver;
import us.states.exception.ServiceFaultException;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Value("${context.path}")
	private String contextPath;
	
	@Value("${ws.namespace.uri}")
	private String namespaceUri;
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(contextPath);
		marshaller.setMtomEnabled(true);		
		return marshaller;
	}
	
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(new WebServiceEndpointInterceptor());
	}

	@Bean
	public DefaultMethodEndpointAdapter defaultMethodEndpointAdapter() {
		List<MethodArgumentResolver> argumentResolvers = new ArrayList<>();
		argumentResolvers.add(methodProcessor());
		List<MethodReturnValueHandler> returnValueHandlers = new ArrayList<>();
		returnValueHandlers.add(methodProcessor());
		DefaultMethodEndpointAdapter adapter = new DefaultMethodEndpointAdapter();
		adapter.setCustomMethodArgumentResolvers(argumentResolvers);
		adapter.setCustomMethodReturnValueHandlers(returnValueHandlers);
		return adapter;
	}
	
	@Bean
	public MarshallingPayloadMethodProcessor methodProcessor() {
		return new MarshallingPayloadMethodProcessor(marshaller());
	}
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
	    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	    servlet.setApplicationContext(applicationContext);
	    servlet.setTransformWsdlLocations(true);
	    return new ServletRegistrationBean<MessageDispatcherServlet>(servlet, "/ws/*");
	}
	
	@Bean(name = "counties")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countiesSchema) {
	    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	    wsdl11Definition.setPortTypeName("CountiesPort");
	    wsdl11Definition.setLocationUri("/ws");
	    wsdl11Definition.setTargetNamespace(namespaceUri);
	    wsdl11Definition.setSchema(countiesSchema);
	    wsdl11Definition.setCreateSoap11Binding(true);
	    return wsdl11Definition;
	}
	
	@Bean
	public XsdSchema countiesSchema() {
	    return new SimpleXsdSchema(new ClassPathResource("schema/county.xsd"));
	}
	
	@Bean
	public SoapFaultMappingExceptionResolver exceptionResolver() {
		SoapFaultMappingExceptionResolver exceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();
	
		SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
		faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
		exceptionResolver.setDefaultFault(faultDefinition);
	
		Properties errorMappings = new Properties();
		errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
		errorMappings.setProperty(ServiceFaultException.class.getName(), SoapFaultDefinition.SERVER.toString());
		exceptionResolver.setExceptionMappings(errorMappings);
		exceptionResolver.setOrder(1);
		
		return exceptionResolver;
	}
	 
	@Bean
	public SpringBus springBus(LoggingFeature loggingFeature) {
		SpringBus cfxbus = new SpringBus();
		cfxbus.getFeatures().add(loggingFeature);
		
		return cfxbus;
	}
	
	@Bean
	public LoggingFeature loggingFeature() {
		LoggingFeature loggingFeature = new LoggingFeature();
		loggingFeature.setPrettyLogging(true);
		
		return loggingFeature;
	}

}

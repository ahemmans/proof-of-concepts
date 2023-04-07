package us.states.exception;

import java.util.List;

import org.springframework.stereotype.Component;

import us.states.domain.ServiceFault;

@Component
public class ServiceFaultException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private List<ServiceFault> serviceFaults;
	
	public ServiceFaultException() {
		
	}

	public ServiceFaultException(List<ServiceFault> serviceFaults) {
		super();
		this.serviceFaults = serviceFaults;
	}
	
	public ServiceFaultException(String message, List<ServiceFault> serviceFaults) {
		super(message);
	    this.serviceFaults = serviceFaults;
	}

	public ServiceFaultException(String message, Throwable e, List<ServiceFault> serviceFaults) {
		super(message, e);
		this.serviceFaults = serviceFaults;
	}

	public List<ServiceFault> getServiceFaults() {
		return serviceFaults;
	}

	public void setServiceFaults(List<ServiceFault> serviceFaults) {
		this.serviceFaults = serviceFaults;
	}	

}
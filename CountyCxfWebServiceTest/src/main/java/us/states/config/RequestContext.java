package us.states.config;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
//@RequestScope
public class RequestContext implements Serializable {

	private static final long serialVersionUID = 1L;

	private String requestStr;
	private String responseStr;
	
	public RequestContext() {
		
	}
	
	public RequestContext(String requestStr, String responseStr) {
		//super();
		this.requestStr = requestStr;
		this.responseStr = responseStr;
	}

	public String getRequestStr() {
		return requestStr;
	}

	public void setRequestStr(String requestStr) {
		this.requestStr = requestStr;
	}

	public String getResponseStr() {
		return responseStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requestStr == null) ? 0 : requestStr.hashCode());
		result = prime * result + ((responseStr == null) ? 0 : responseStr.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestContext other = (RequestContext) obj;
		if (requestStr == null) {
			if (other.requestStr != null)
				return false;
		} else if (!requestStr.equals(other.requestStr))
			return false;
		if (responseStr == null) {
			if (other.responseStr != null)
				return false;
		} else if (!responseStr.equals(other.responseStr))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestContext [requestStr=" + requestStr + ", responseStr=" + responseStr + "]";
	}
	
	
	
}

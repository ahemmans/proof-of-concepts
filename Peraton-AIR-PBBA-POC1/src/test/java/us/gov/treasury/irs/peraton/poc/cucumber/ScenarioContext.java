package us.gov.treasury.irs.peraton.poc.cucumber;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

	private Map<String, Object> scenarioContext;

	public ScenarioContext() {
		this.scenarioContext = new HashMap<>();
	}

	public Map<String, Object> getScenarioContext() {
		return scenarioContext;
	}

	public void setScenarioContext(Map<String, Object> scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	public void setContext(Context key, Object value) {
        scenarioContext.put(key.toString(), value);
    }

    public Object getContext(Context key){
        return scenarioContext.get(key.toString());
    }
	 
    public Boolean isContains(Context key){
        return scenarioContext.containsKey(key.toString());
    }
}

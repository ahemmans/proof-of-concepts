package us.states.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

/*
 * @author Anthony Hemmans
 * @date 11/18/2022
 * 
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("us/states/cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "us.states.cucumber.steps")
public class CucumberIT {

}

package us.states.cucumber.steps;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.cucumber.spring.CucumberContextConfiguration;

/*
 * @author Anthony Hemmans
 * @date 11/18/2022
 * 
 */
@SpringBootTest
@ActiveProfiles("it")
@CucumberContextConfiguration
public class CucumberContext {
	
}    
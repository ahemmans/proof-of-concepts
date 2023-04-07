@Health
Feature: Health Check Test
  As the County API is tested
  I want to test the Health of the application
    
  Scenario: Health Check Test    
    When the health check api is called
    Then the health check api should return a httpstatus of 200	
    And the health check api should return a status of "UP"		

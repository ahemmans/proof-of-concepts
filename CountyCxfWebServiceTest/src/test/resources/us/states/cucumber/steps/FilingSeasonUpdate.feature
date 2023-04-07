@FS
Feature: Filing Season Update

  Scenario: Filing Season Update Test    
    When filing season "fs22" is ready to be archived    
    Then update the filing season to "fs23"
	
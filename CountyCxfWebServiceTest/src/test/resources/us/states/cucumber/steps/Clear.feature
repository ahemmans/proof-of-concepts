@Clear
Feature: CountyWS Data Clearing Test
  As the County API is tested
  I want to clear the data and reset the tests
  
  Background:
  	Given that the County WebService is "UP"
  
	Scenario Outline:  Clear Test Scenarios : <test_id>
		When the getCounties WS is cleared with request "<request_test_file>"
		Then clear the working directory
		And clear the database record
		
	Examples:
	  | test_id       | request_test_file             |
	  | CLEAR_unit    | test_data_request_unit.xml    |
	  | CLEAR_001     | test_data_request_001.xml     |
	  | CLEAR_1196102 | test_data_request_1196102.xml |
	  | CLEAR_1196251 | test_data_request_1196251.xml |
	  | CLEAR_RUN_DMC | test_data_request_rundmc.xml  |
	  | CLEAR_PDF     | test_data_request_pdf.xml     |
	  | CLEAR_ZIP     | test_data_request_zip.xml     |
    

        
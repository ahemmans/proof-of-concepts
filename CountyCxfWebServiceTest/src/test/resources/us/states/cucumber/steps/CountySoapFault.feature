@CountySoapFault @Fault @Error
Feature: SOAP Fault Tests -  CountyWS API Test
  As the County API is tested
  I want to test the Fault errors of the API
  
  Background:
  	Given that the County WebService is "UP"
  
  
  Scenario Outline: getCounty Tests : TEST ID : <test_id>
  	When the SOAP test case "<test_id>" is initiated with request "<request_test_file>"
  	And the SOAP message is created
  	Then the "<element>" element is "<remove_empty>"
  	And the file attachment is inserted into the SOAP message : "<attach_file>"
  	Then the SOAP message is sent
  	Then verify the SOAP response is a SOAPFault
  	And verify that errors "<error_codes>" are generated  	

  Examples:
    | test_id | request_test_file                  | element              | remove_empty | error_codes    | expResp                   | description | attach_file |
    | ME-01   | test_data_request_error_master.xml | id                   | remove       | ME01           | Missing Element : id      |             | false       | 
    | RV-01   | test_data_request_error_master.xml | id                   | empty        | RV01           | Required Value : id       |             | false	      |
    | RV-02   | test_data_request_error_master.xml | filename             | remove       | RV02           | Required Value : filename |             | false	      |
    | RV-03   | test_data_request_error_master.xml | mimetype             | remove       | RV03           | Required Value : mimetype |             | false	      |
    | ME-05   | test_data_request_error_master.xml | id,filename          | remove       | ME01,RV02      |                           |             | true	      |
    | ME-06   | test_data_request_error_master.xml | filename,mimetype    | empty        | RV02,RV03      |                           |             | true	      |
    #| ME-06.1 | test_data_request_error_master.xml | filename,mimetype    | empty        | RV01,RV03      |                           | Error       | true	      |
    | ME-07   | test_data_request_error_master.xml | id,filename,mimetype | remove       | ME01,RV02,RV03 |                           |             | true	      |
    | ME-08   | test_data_request_error_master.xml | id,filename,mimetype | empty        | RV01,RV02,RV03 |                           |             | true	      |    

     
  @Mimetype   
  Scenario Outline: getCounty Tests : TEST ID : <test_id>
  	When the SOAP test case "<test_id>" is initiated with request "<request_test_file>"
  	And the SOAP message is created
  	#Then the "<element>" element is "<remove_empty>"
  	And the file attachment is inserted into the SOAP message : "<attach_file>"
  	Then the SOAP message is sent
  	Then verify the SOAP response is a SOAPFault
  	And verify that errors "<error_codes>" are generated  	

  Examples:
    | test_id | request_test_file                | element        | remove_empty | error_codes | expResp                                                                          | description | attach_file |
    | MT-01   | test_data_request_error_mt01.xml |                |              | MT01        | Invalid mime type : the file (extension) does not match the associated mime type |             | true	      |
    | MT-02   | test_data_request_error_mt02.xml |                |              | MT02        | Invalid mime type : %mt% is not an approved mime type                            |             | false	      | 
    #| MT-03   | test_data_request_error_mt03.xml |                |              | MT01        | Invalid mime type : the file (extension) does not match the associated mime type |             | false       |        

        
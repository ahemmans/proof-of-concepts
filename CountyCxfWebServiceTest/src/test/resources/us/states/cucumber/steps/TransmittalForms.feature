@TransmittalForms 
Feature: SOAP API -  AIR API Test
  As the ACA AIR/PBBA API is tested
  I want to test the responses of the API
  
  #Background:
  #	Given that the County WebService is "UP"  
  
  @1094B @1095B @1094C @1095C
  Scenario Outline: getCounty Tests : TEST ID : <test_id>
  	Given the request file "<request_test_file>" of formType "<formType>"
  	Then validate the request "<validateXML>"
  	And verify that each taxyr element is "<taxyr>"

  Examples:
    | test_id  | request_test_file                     | formType | validateXML | attach_file | filing_season | taxyr | status   | business_rules | expected_response | description |
    | 1101939  | 1101939_FS22_1094_1095B_TestData.xml  | 1094B    | true        | true        | fs22          | 2021  | ACCEPTED |                |                   |             |
    | 1234567  | 1234567_FS22_1094_1095C_TestData.xml  | 1094C    | true        | true        | fs22          | 2021  | ACCEPTED |                |                   |             |
    #| 1206598  | 1206598_1095A_AIRBR223_BirthDt.xml    | 1095A    | false       | true        | fs22          | 2021  | ACCEPTED |                |                   |             |
    #| 1206602  | 1206602_FS22_1095A_SmokeTest.xml      | 1095A    | false       | true        | fs22          | 2021  | ACCEPTED |                |                   |             |
    #| 1196105  | scrips23.OCR1010C.2022DSIT1196105.xml | 1094CScrips | true     | true        | fs22          | 2021  | ACCEPTED |                |                   |             |
    #| 0000000  | TBD.xml                               | 1094BScrips | true     | true        | fs22          | 2021  | ACCEPTED |                |                   |             | 
        
@County
Feature: SOAP API -  CountyWS API Test
  As the County API is tested
  I want to test the responses of the API
  
  Background:
  	Given that the County WebService is "UP"
  
  @GetCounties  @ById
  Scenario Outline: getCounty Tests : TEST ID : <test_id>
  	#When the getCounties WS is called with id "<id>"
  	When the WS test case "<test_id>" is initiated with request "<request_test_file>"
  	And the file is attached to the request : "<attach_file>" "<formType>"
  	Then the WS call is executed
  	Then verify the response is valid  	
  	Then verify the file attachment is written to the working directory
  	Then get the ACA tables status
  	And the county name expectedValue should equal "<expResp>"
  	And verify the database is updated with the expectedValue "<expResp>"  

  Examples:
    | test_id  | request_test_file                    | expResp                | business_rules | description | attach_file |  formType | status | expResp2 |
#    | TEST_001 | test_data_request_fs2022_001.xml     | Prince George's County | MNFT_001       | 24033       | true        |          |        |          |
#    | TEST_001.1 | test_data_request_fs2022_001.xml   | Prince George's County | MNFT_001       | 24033       | false       |          |        |          |
#    | 1196102  | test_data_request_fs2022_1196102.xml |                        | MNFT_001       | 75000       | true        |          |        |          |
#    | 1196251  | test_data_request_fs2022_1196251.xml |                        | MNFT_001       | 75001       | true        |          |        |          |  
#    | TEST_RUN_DMC | test_data_request_rundmc.xml     |                        | RUNDMC_001     | 75002       | true        |          |        |          |
#    | PDF_TEST | test_data_request_pdf.xml            |                        | PDF_001        | 75003       | true        |          |        |          |
#    | ZIP_TEST | test_data_request_zip.xml            |                        | ZIP_001        | 75004       | true        |          |        |          |
     | 1101939  | test_data_request_fs2022_1101939.xml | Prince George's County |                | 24033       | true        | electronic | ACCEPTED |          |          
     | 1234567  | test_data_request_fs2022_1234567.xml | Prince George's County |                | 24033       | true        | electronic | ACCEPTED |          |
     | 1206598  | test_data_request_fs2022_1206598.xml | Prince George's County |                | 24033       | true        | 1095A      | ACCEPTED |          |
     | 1206602  | test_data_request_fs2022_1206602.xml | Prince George's County |                | 24033       | true        | 1095A      | ACCEPTED |          |
     | 1196105  | test_data_request_fs2022_1196105.xml | Prince George's County |                | 24033       | true        | paper      | ACCEPTED |          |
     | 1196251  | test_data_request_fs2022_1196251.xml | Prince George's County |                | 24033       | true        | paper      | ACCEPTED |          |
     | 1196102  | test_data_request_fs2022_1196102.xml | Prince George's County |                | 24033       | true        | paper      | ACCEPTED |          |

#  @GetCounties @ByState
#  Scenario Outline: getCountyList Tests : TEST ID : <test_id>
#  	When the WS test case "<test_id>" is initiated with request "<request_test_file>"
#  	And the file is attached to the request : "<attach_file>"
#  	Then the WS call is executed 
#  	Then the county list should contain "<expResp>"   

#  Examples:
#    | test_id | request_test_file              | file_attachment | exp_resp_file | url | id    | state | expResp                | attach_file | description |
#    | TEST_MD | test_data_request_state_md.xml |                 |               |     | 24033 | MD    | Prince George's County | false       |             |
#    | TEST_NJ | test_data_request_state_nj.xml |                 |               |     | 34023 | NJ    | Middlesex County       | false       |             |  
    #| TEST_NJ2 | test_data_request_state_nj.xml |                 |               |     | 34023 | NJ    | Baltimore County       | false       | ERROR       |
   
        
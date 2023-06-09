SELECT  
       A.UNIQUE_TRANS_ID, A.GSO_ID, A.FORM_TYPE 
      ,B.STATUS, to_char(B.CREATE_DT, 'yyyy-mm-dd HH:mi:ss') as CREATE_DT
      ,C.errortype, C.errormsg
      ,D.submission_status_txt
      ,E.error_message_cd, E.error_message_txt
      --,F.ERROR_CD, F.ERROR_MSG_TXT
FROM TRANSMITTER_BC A 
	FULL OUTER JOIN IFS_STATUS B ON A.GSO_ID = B.GSO_ID 
	FULL OUTER JOIN IFS_ERROR C ON B.GSO_ID = C.GSO_ID 
	FULL OUTER JOIN SUBMISSION_BC D ON C.GSO_ID = D.GSO_ID
	FULL OUTER JOIN TIN_VALIDATION_ERROR E ON D.GSO_ID = E.GSO_ID
	--FULL OUTER JOIN AIR_NEW.transmission_error F ON E.GSO_ID =F.GSO_ID
---WHERE A.UNIQUE_TRANS_ID = 'SAM08857-2022-0720-0072-001401196179:SYS12::SCRIPS18:O'
WHERE A.GSO_ID ='SCRIPS1095C-23-00001449'
	AND C.errortype NOT LIKE '%E0%'
ORDER BY A.GSO_ID desc, C.errortype desc;
-- PostgreSQL table creations --
DROP TABLE IF EXISTS IFS_STATUS;
DROP TABLE IF EXISTS IFS_ERROR;
DROP TABLE IF EXISTS TRANSMITTER_BC;
DROP TABLE IF EXISTS TRANSMISSION_ERROR;
DROP TABLE IF EXISTS TRANSMISSION_1095A; 
DROP TABLE IF EXISTS TRANSMISSION_1095_EXT;
DROP TABLE IF EXISTS TIN_VALIDATION_ERROR;
DROP TABLE IF EXISTS BULK_TIN;
DROP TABLE IF EXISTS IR_FORM_ERROR;
DROP TABLE IF EXISTS DS_EOY_FORMS_TRANSMIT;
DROP TABLE IF EXISTS SUBMISSION_BC;

CREATE TABLE IFS_STATUS (
	STATUS_ID     SERIAL PRIMARY KEY,   
	GSO_ID        VARCHAR(240) NOT NULL, 
	SUBMISSION_ID INT NOT NULL,    
	CREATE_DT     DATE,          
	UPDATE_DT     DATE,          
	NAME          VARCHAR(240), 
	SUBTYPE       VARCHAR(240), 
	STATUS        VARCHAR(100), 
	INFO          VARCHAR(4000),
	OWNER         VARCHAR(50),  
	CONTACT       VARCHAR(100)
);

CREATE TABLE IFS_ERROR (
	ERROR_ID        SERIAL PRIMARY KEY,	    
	GSO_ID          VARCHAR(240) NOT NULL, 
	SUBMISSION_ID   INT NOT NULL,    
	ERRORTYPE       VARCHAR(1000),
	ERRORMSG        VARCHAR(4000),
	ERRORLEVEL      VARCHAR(50),  
	XPATH           VARCHAR(255), 
	LINE_INT        VARCHAR(255), 
	ADDITIONAL_INFO VARCHAR(2000)
);

CREATE TABLE TRANSMITTER_BC (
	TRANSMITTER_BC_ID         SERIAL PRIMARY KEY,	   
	TRANSMISSIONID_NUM        INT NOT NULL,   
	TAX_YR                    VARCHAR(4) NOT NULL,  
	UNIQUE_TRANS_ID           VARCHAR(100) NOT NULL,
	ORIGINAL_RECEIPT_ID       VARCHAR(100),
	GSO_ID                    VARCHAR(240) NOT NULL,
	TRANSMITTER_TIN           VARCHAR(10), 
	FORM_TYPE                 VARCHAR(20), 
	PRIOR_YEAR_DATA_IND       VARCHAR(5),  
	TRANSMISSION_TYPE_IND     VARCHAR(1),  
	CHECKSUM_TXT              VARCHAR(64), 
	TRANSMITTER_NM_TXT        VARCHAR(150),
	USER_CUST_TXT             VARCHAR(100),
	TCC                       VARCHAR(10) NOT NULL, 
	VENDOR_SOFTWARE_IND       VARCHAR(5),  
	REPORTED_PAYER_NUM        INT,   
	REPORTED_PAYEE_NUM        INT,   
	DOC_SYSTEM_FILE_NAME_TXT  VARCHAR(50) NOT NULL, 
	SOFTWARE_ID               VARCHAR(10), 
	INSERTED_BY_TXT           VARCHAR(15) NOT NULL, 
	INSERTED_DT               TIMESTAMP NOT NULL, 
	REPLACEMENT_RECEIPT_ID    VARCHAR(100),
	ADJUSTED_RECEIPT_DT       TIMESTAMP, 
	UPDATED_BY_TXT            VARCHAR(15), 
	UPDATED_DT                TIMESTAMP, 
	PROCESSING_YR             VARCHAR(4), 
	PARENT_GSO_ID             VARCHAR(240)
);

CREATE TABLE TRANSMISSION_ERROR (
	TRANSMISSION_ERROR_ID  SERIAL PRIMARY KEY,	   
	TRANSMISSIONID_NUM     INT NOT NULL,   
	GSO_ID                 VARCHAR(240) NOT NULL,
	BATCH_CATEGORY_CD      VARCHAR(80) NOT NULL, 
	ERROR_CD               VARCHAR(50) NOT NULL, 
	ERROR_MSG_TXT          VARCHAR(240),
	ERROR_LEVEL_TXT        VARCHAR(50), 
	INSERTED_BY_TXT        VARCHAR(15), 
	INSERTED_DT            TIMESTAMP, 
	UPDATED_BY_TXT         VARCHAR(15), 
	UPDATED_DT             TIMESTAMP
);

CREATE TABLE TRANSMISSION_1095A (
	TRANSMISSION_1095A_ID_NUM  SERIAL PRIMARY KEY, 	
	TRANSMISSIONID_NUM         INT NOT NULL,   
	GSO_ID                     VARCHAR(240) NOT NULL,
	CORRELATION_ID             VARCHAR(80), 
	EXCHANGEID_NUM             VARCHAR(28) NOT NULL, 
	PAYER_DLN                  VARCHAR(14), 
	PAYEE_DLN                  VARCHAR(14), 
	BATCH_ID                   VARCHAR(240) NOT NULL,
	CHECKSUM_TXT               VARCHAR(64), 
	ORIGINAL_BATCH_ID          VARCHAR(240),
	BATCH_ATTCH_TRANS_QTY      INT NOT NULL,   
	BATCH_CATEGORY_CD          VARCHAR(100) NOT NULL,
	BATCH_TRANS_QTY            INT,   
	TRANS_ATTCH_QTY            INT NOT NULL,   
	TRANS_SEQUENCE_NUM         INT NOT NULL,   
	REPORT_PERIOD_DT           VARCHAR(10) NOT NULL, 
	ATTCH_BYTE_SIZE_NUM        VARCHAR(10), 
	DOC_SYSTEM_FILE_NAME_TXT   VARCHAR(100) NOT NULL,
	DOC_SEQUENCE_NUM           VARCHAR(5) NOT NULL,  
	SYS_DOC_ID                 VARCHAR(10), 
	INSERTED_BY_TXT            VARCHAR(15) NOT NULL, 
	INSERTED_DT                TIMESTAMP NOT NULL, 
	UPDATED_BY_TXT             VARCHAR(15), 
	UPDATED_DT                 TIMESTAMP
);

CREATE TABLE TRANSMISSION_1095_EXT (
	TRANSMISSION_EXTID_NUM     SERIAL PRIMARY KEY,	  
	TRANSMISSIONID_NUM         INT NOT NULL,   
	GSO_ID                     VARCHAR(240) NOT NULL,
	BATCH_ID                   VARCHAR(240),
	ORIGINAL_BATCH_ID          VARCHAR(240),
	VERSION                    INT,       
	TAX_YR                     VARCHAR(4),  
	DUPLICATE_IND              VARCHAR(1),  
	ORIGL_RETURNS_CNT_NUM      INT,   
	REPL_RETURNS_CNT_NUM       INT,   
	COR_RETURNS_CNT_NUM        INT,   
	PROCESSED_RETURNS_CNT_NUM  INT,   
	INSERTED_BY_TXT            VARCHAR(15), 
	INSERTED_DT                TIMESTAMP NOT NULL, 
	UPDATED_BY_TXT             VARCHAR(15), 
	UPDATED_DT                 TIMESTAMP, 
	FORM_TYPE                  VARCHAR(20), 
	ACTUAL_PAYER_NUM           INT,   
	ACTUAL_PAYEE_NUM           INT,   
	SHIPMENT_REC_NUM           VARCHAR(8)
);

CREATE TABLE TIN_VALIDATION_ERROR (
	TIN_VAL_ERROR_ID     SERIAL PRIMARY KEY, 	   
	GSO_ID               VARCHAR(240) NOT NULL,
	BULK_ID              VARCHAR(70) NOT NULL, 
	TIN_TXT              VARCHAR(20), 
	TAXPAYER_NAME_TXT    VARCHAR(140),
	TIN_REQUEST_TYPE_CD  VARCHAR(20), 
	VAL_RESULT_CD        VARCHAR(20), 
	ERROR_MESSAGE_CD     VARCHAR(40), 
	ERROR_MESSAGE_TXT    VARCHAR(240),
	ELEMENT_NAME_TXT     VARCHAR(240),
	RECORD_SEQ_NUM       VARCHAR(100),
	POLICY_NUM           VARCHAR(100),
	INSERTED_DT          TIMESTAMP
);

CREATE TABLE BULK_TIN (
	BULK_ID             VARCHAR(70) NOT NULL,  
	GSO_ID              VARCHAR(240) NOT NULL, 
	CREATE_DT           DATE,          
	UPDATE_DT           DATE,          
	COMPLETED_IND       VARCHAR(5),   
	PROCESSED_IND       VARCHAR(5),   
	FORM_LIST          A,          
	SET_NUM             INT,    
	SUBSET_NUM          INT,    
	SUBMISSION_ID       VARCHAR(100), 
	HOST_NAME           VARCHAR(100), 
	IRID_SEQ_NBR        INT,    
	TIN_SERVICE_TYPE    VARCHAR(15),  
	TIN_REQ_STATUS      VARCHAR(4000),
	TIN_REQ_COUNT_NUM   INT,    
	TIN_RESP_COUNT_NUM  INT,
	PRIMARY KEY (BULK_ID) 
);

CREATE TABLE IR_FORM_ERROR (
	IR_FORM_ERROR_ID      SERIAL PRIMARY KEY,	
	ERROR_ID              INT NOT NULL,   
	IR_REC_SEQ_NUM        VARCHAR(100) NOT NULL,
	GSO_ID                VARCHAR(240) NOT NULL,
	FORMTYPE              VARCHAR(20), 
	ELEMENT_NAME_TXT      VARCHAR(240) NOT NULL,
	ELEMENT_INSTANCE_TXT  VARCHAR(50), 
	INSERTED_BY_TXT       VARCHAR(15) NOT NULL, 
	INSERTED_DT           TIMESTAMP NOT NULL
);

CREATE TABLE DS_EOY_FORMS_TRANSMIT (
	GSO_ID                     VARCHAR(240) NOT NULL, 
	FORM_TYPE                  VARCHAR(20) NOT NULL,  
	CREATE_DT                  DATE NOT NULL,          
	UPDATED_DT                 DATE NOT NULL,          
	TRANS_FILE_NAME_TXT        VARCHAR(256) NOT NULL, 
	TRANS_REC_COUNT            INT NOT NULL,    
	TRANS_STATUS_IND           CHAR(1) NOT NULL,       
	EOD_IND                    CHAR(1) NOT NULL,       
	REASON_TXT                 VARCHAR(1024),
	TRANS_PAYER_REC_COUNT_NUM  INT,  
	PRIMARY KEY (GSO_ID)
);

CREATE TABLE SUBMISSION_BC (
	SUBMISSION_BC_ID             	SERIAL PRIMARY KEY,                                       
	TRANSMITTER_BC_ID               INT NOT NULL,                                       
	TAX_YR                          VARCHAR(4) NOT NULL,                                       
	TRANSMIT_MODE_IND               VARCHAR(1) NOT NULL,                                       
	UNIQUE_SUBMISSION_ID            VARCHAR(100) NOT NULL,                                      
	ORIGINAL_UNIQUE_SUBMISSION_ID   VARCHAR(100),                                     
	REPLACEMENT_UNIQUE_SUB_ID       VARCHAR(100),                                     
	ADJUSTED_RECEIPT_DT             TIMESTAMP,                                      
	GSO_ID                          VARCHAR(240) NOT NULL,                                      
	FORM_TYPE                       VARCHAR(20),                                      
	EIN                             VARCHAR(9) NOT NULL,                                       
	SUBMISSION_STATUS_TXT           VARCHAR(50),                                     
	FILER_EMPLOYER_NM_TXT           VARCHAR(150),                                     
	DELINQUENT_IND                  VARCHAR(2),                                      
	DELINQUENT_DT                   TIMESTAMP,                                      
	CORRESPONDENCE_CD               CHAR(3),                                      
	REPORTED_PAYEE_NUM              INT,                                     
	ACTUAL_PAYEE_NUM                INT,                                    
	THRESHOLD_VAL_STATUS_CD         VARCHAR(10),                                    
	INSERTED_BY_TXT                 VARCHAR(15) NOT NULL,                                    
	INSERTED_DT                     TIMESTAMP NOT NULL,                                     
	UPDATED_BY_TXT                  VARCHAR(15),                                    
	UPDATED_DT                      TIMESTAMP,                                     
	REPORTED_ORIG_PAYEE_NUM         INT,                                      
	REPORTED_ORIG_SBP_PAYEE_NUM     INT,                                     
	REPORTED_AMEND_PAYEE_NUM        INT,                                      
	REPORTED_AMEND_SBP_PAYEE_NUM    INT,                                      
	AMENDED_DOC_CD                  CHAR(3),                                      
	ACTUAL_AMEND_PAYEE_NUM          INT,                                    
	ACTUAL_ORIG_PAYEE_NUM           INT 
);
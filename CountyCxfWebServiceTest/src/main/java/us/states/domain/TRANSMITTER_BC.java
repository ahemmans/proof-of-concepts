package us.states.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;
import us.states.domain.query.SubmissionStatus_1095A;

@NamedNativeQuery(name = "TransmitterBc.submissionStatus1095A", 
	query = "SELECT  \r\n" + 
			"	 A.UNIQUE_TRANS_ID, A.GSO_ID, A.FORM_TYPE \r\n" + 
			"    ,B.STATUS, to_char(B.CREATE_DT, 'yyyy-mm-dd HH:mi:ss') as CREATE_DT \r\n" + 
			"    ,D.submission_status_txt \r\n" +
			"FROM TRANSMITTER_BC A \r\n" +
			"    FULL OUTER JOIN IFS_STATUS B ON A.GSO_ID = B.GSO_ID \r\n" +
			"    FULL OUTER JOIN SUBMISSION_BC D ON B.GSO_ID = D.GSO_ID \r\n" +
			"WHERE A.GSO_ID = :gsoId"
	,
	resultSetMapping = "StatusResult1095A",
	resultClass = SubmissionStatus_1095A.class)
/*
@SqlResultSetMapping(name = "StatusResult1095A",
	entities = { 
		@EntityResult(entityClass = us.states.domain.TRANSMITTER_BC.class, fields = {
			@FieldResult(name = "UNIQUE_TRANS_ID", column = "UNIQUE_TRANS_ID"),
			@FieldResult(name = "GSO_ID", column = "GSO_ID"),
			@FieldResult(name = "FORM_TYPE", column = "FORM_TYPE")
		}),
		@EntityResult(entityClass = us.states.domain.IFS_STATUS.class, fields = {
			@FieldResult(name = "STATUS", column = "STATUS"),
			@FieldResult(name = "CREATE_DT", column = "CREATE_DT")				
		}),
		@EntityResult(entityClass = us.states.domain.SUBMISSION_BC.class, fields = {
			@FieldResult(name = "SUBMISSION_STATUS_TXT", column = "SUBMISSION_STATUS_TXT")	
		})
	}
)
*/
@SqlResultSetMapping(
        name="StatusResult1095A",
        classes = @ConstructorResult(
                targetClass = SubmissionStatus_1095A.class,
                columns = {
                        @ColumnResult(name = "UNIQUE_TRANS_ID", type = String.class),
                        @ColumnResult(name = "GSO_ID", type = String.class),
                        @ColumnResult(name = "FORM_TYPE", type = String.class),
                        @ColumnResult(name = "STATUS", type = String.class),
                        @ColumnResult(name = "CREATE_DT", type = Date.class),
                        @ColumnResult(name = "SUBMISSION_STATUS_TXT", type = String.class),
                }))
@Component
@Entity
@Table(name="TRANSMITTER_BC")
@Data
public class TRANSMITTER_BC {
/*
 * 	TRANSMITTER_BC_ID         INT NOT NULL,   
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
	PARENT_GSO_ID             VARCHAR(240),
	PRIMARY KEY (TRANSMITTER_BC_ID)
 */
	@Id
	private int TRANSMITTER_BC_ID;
	
	@Column @NotNull
	private int TRANSMISSIONID_NUM;
	
	@Column
	private String TAX_YR;
	
	@Column @NotNull
	private String UNIQUE_TRANS_ID; 
	
	@Column
	private String ORIGINAL_RECEIPT_ID; 
	
	@Column @NotNull
	private String GSO_ID; 
	
	@Column
	private String TRANSMITTER_TIN;  
	
	@Column
	private String FORM_TYPE;  
	
	@Column
	private String PRIOR_YEAR_DATA_IND;   
	
	@Column
	private String TRANSMISSION_TYPE_IND;   
	
	@Column
	private String CHECKSUM_TXT;  
	
	@Column
	private String TRANSMITTER_NM_TXT; 
	
	@Column
	private String USER_CUST_TXT; 
	
	@Column @NotNull
	private String TCC;  
	
	@Column
	private String VENDOR_SOFTWARE_IND;   
	
	@Column
	private int REPORTED_PAYER_NUM;    
	
	@Column
	private int REPORTED_PAYEE_NUM;    
	
	@Column @NotNull
	private String DOC_SYSTEM_FILE_NAME_TXT;  
	
	@Column
	private String SOFTWARE_ID;  
	
	@Column @NotNull
	private String INSERTED_BY_TXT;  
	
	@Column @NotNull
	private Timestamp INSERTED_DT;  
	
	@Column
	private String REPLACEMENT_RECEIPT_ID; 
	
	@Column
	private Timestamp ADJUSTED_RECEIPT_DT;  
	
	@Column
	private String UPDATED_BY_TXT;  
	
	@Column
	private Timestamp UPDATED_DT;  
	
	@Column
	private String PROCESSING_YR;  
	
	@Column
	private String PARENT_GSO_ID; 
}

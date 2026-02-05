package demo.ai.db.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import demo.ai.db.model.dto.TransmissionCountByYear;
import demo.ai.db.model.dto.TransmissionCountByYearAndMonth;
import demo.ai.db.model.dto.TransmissionCountDto;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@NamedNativeQuery(
	name = "TransmissionError.getAllErrorCounts", 
	query = "SELECT SUBSTRING(receipt_id, 1, POSITION('-' IN receipt_id) - 1) AS form_type, Count(*) as tot_num "
			+ "FROM TRANSMISSION_ERROR "
			+ "GROUP BY form_type "
			+ "ORDER BY form_type;", 
	resultSetMapping = "error_count_formType"
)

@NamedNativeQuery(
	name = "TransmissionError.getErrorCountByFormType", 
	query = "SELECT SUBSTRING(receipt_id, 1, POSITION('-' IN receipt_id) - 1) AS form_type, Count(*) as tot_num "
			+ "FROM TRANSMISSION_ERROR "
			+ "WHERE SUBSTRING(receipt_id, 1, POSITION('-' IN receipt_id) - 1) = ? "
			+ "GROUP BY form_type;", 
	resultSetMapping = "error_count_formType"
)

@SqlResultSetMapping(
	name = "error_count_formType", 
	classes = @ConstructorResult(
		targetClass = TransmissionCountDto.class, 
		columns = {
			@ColumnResult(name = "form_type", type = String.class),
			@ColumnResult(name = "tot_num", type = Integer.class) 
		}
	)
)

@NamedNativeQuery(
		name = "TransmissionError.getErrorCountByFormTypeByYear", 
		query = "SELECT ts.form_type, DATE_PART('year', ts.inserted_dt) AS year, Count(te.error_cd) as tot_num "
				+ "FROM TRANSMISSION_STATUS ts "
				+ "INNER JOIN TRANSMISSION_ERROR te ON te.receipt_id = ts.receipt_id "
				+ "WHERE ts.form_type = ? AND DATE_PART('year', ts.inserted_dt) = ? "
				+ "GROUP BY ts.form_type, DATE_PART('year', ts.inserted_dt) "
				+ "ORDER BY ts.form_type, year", 
		resultSetMapping = "error_count_formType_byYear"
	)

@SqlResultSetMapping(
	name = "error_count_formType_byYear", 
	classes = @ConstructorResult(
		targetClass = TransmissionCountByYear.class, 
		columns = {
			@ColumnResult(name = "form_type", type = String.class),
			@ColumnResult(name = "year", type = Double.class),
			@ColumnResult(name = "tot_num", type = Integer.class)
		}
	)
)

@NamedNativeQuery(
		name = "TransmissionError.getErrorCountByFormTypeByYearAndMonth", 
		query = "SELECT ts.form_type, DATE_PART('year', ts.inserted_dt) AS year, DATE_PART('month', ts.inserted_dt) AS month_num, TO_CHAR(ts.inserted_dt, 'FMMonth') AS month, Count(te.error_cd) as tot_num "
				+ "FROM TRANSMISSION_STATUS ts "
				+ "INNER JOIN TRANSMISSION_ERROR te ON te.receipt_id = ts.receipt_id "
				+ "WHERE ts.form_type = ? AND DATE_PART('year', ts.inserted_dt) = ? AND TO_CHAR(inserted_dt, 'FMMonth') = ? "
				+ "GROUP BY ts.form_type, DATE_PART('year', ts.inserted_dt), DATE_PART('month', ts.inserted_dt), TO_CHAR(ts.inserted_dt, 'FMMonth') "
				+ "ORDER BY ts.form_type, year, month_num", 
		resultSetMapping = "error_count_formType_byYear_byMonth"
	)

@SqlResultSetMapping(
	name = "error_count_formType_byYear_byMonth", 
	classes = @ConstructorResult(
		targetClass = TransmissionCountByYearAndMonth.class, 
		columns = {
			@ColumnResult(name = "form_type", type = String.class),
			@ColumnResult(name = "year", type = Double.class),
			@ColumnResult(name = "month_num", type = Double.class),
			@ColumnResult(name = "month", type = String.class),
			@ColumnResult(name = "tot_num", type = Integer.class)
		}
	)
)

@Data
@Component
@Entity 
@Table(name="TRANSMISSION_ERROR")
public class TransmissionError implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TRANSMISSION_ERROR_ID")
	@NotNull
	@JsonIgnore
    private int transmissionErrorId;
    
	@Column(name="RECEIPT_ID")
	@NotNull
	private String receiptId;
    
	@Column(name="ERROR_CD")
	@NotNull
	private String errorCd;
    
	@Column(name="ERROR_MSG")
	@NotNull
	private String errorMsg;
    
	@Column(name="ERROR_LEVEL")
	@NotNull
	private String errorLevel;
    
	@Column(name="LINE_NUM")
	private String lineNum;
    
	@Column(name="XPATH_MAPPING")
	private String xpathMapping;
    
	@Column(name="ADDITIONAL_INFO")
	private String additionalInfo;    

}

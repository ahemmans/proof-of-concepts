package demo.ai.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import demo.ai.db.model.dto.TransmissionCountByStatusDto;
import demo.ai.db.model.dto.TransmissionCountByYear;
import demo.ai.db.model.dto.TransmissionCountByYearAndMonth;
import demo.ai.db.model.dto.TransmissionCountDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@NamedNativeQuery(
	name = "TransmissionStatus.getAllStatusCounts", 
	query = "SELECT form_type, Count(*) as tot_num FROM TRANSMISSION_STATUS GROUP BY form_type ORDER BY form_type;", 
	resultSetMapping = "status_count_formType"
)

@NamedNativeQuery(
	name = "TransmissionStatus.getStatusCountByFormType", 
	query = "SELECT form_type, Count(*) as tot_num FROM TRANSMISSION_STATUS WHERE form_type = :formType GROUP BY form_type;", 
	resultSetMapping = "status_count_formType"
)

@NamedNativeQuery(
		name = "TransmissionStatus.getStatusCountByFormTypeByStatus", 
		query = "SELECT form_type, status, Count(*) as tot_num FROM TRANSMISSION_STATUS WHERE form_type = :formType AND status = :status GROUP BY form_type, status;", 
		resultSetMapping = "status_count_formType_status"
)

@SqlResultSetMapping(
	name = "status_count_formType", 
	classes = @ConstructorResult(
		targetClass = TransmissionCountDto.class, 
		columns = {
			@ColumnResult(name = "form_type", type = String.class),
			@ColumnResult(name = "tot_num", type = Integer.class) 
		} 
	)
)

@SqlResultSetMapping(
		name = "status_count_formType_status", 
		classes = @ConstructorResult(
			targetClass = TransmissionCountByStatusDto.class, 
			columns = {
				@ColumnResult(name = "form_type", type = String.class),
				@ColumnResult(name = "status", type = String.class),
				@ColumnResult(name = "tot_num", type = Integer.class) 
			} 
		)
)

@NamedNativeQuery(
		name = "TransmissionStatus.getStatusCountByFormTypeByYear", 
		query = "SELECT form_type, DATE_PART('year', inserted_dt) AS year, Count(*) as tot_num "
				+ "FROM TRANSMISSION_STATUS "
				+ "WHERE form_type = ? AND DATE_PART('year', inserted_dt) = ? "
				+ "GROUP BY form_type, DATE_PART('year', inserted_dt) "
				+ "ORDER BY form_type, year", 
		resultSetMapping = "status_count_formType_byYear"
	)

@SqlResultSetMapping(
	name = "status_count_formType_byYear", 
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
		name = "TransmissionStatus.getStatusCountByFormTypeByYearAndMonth", 
		query = "SELECT form_type, DATE_PART('year', inserted_dt) AS year, DATE_PART('month', inserted_dt) AS month_num, TO_CHAR(inserted_dt, 'FMMonth') AS month, Count(*) as tot_num "
				+ "FROM TRANSMISSION_STATUS "
				+ "WHERE form_type = ? AND DATE_PART('year', inserted_dt) = ? AND TO_CHAR(inserted_dt, 'FMMonth') = ? "
				+ "GROUP BY form_type, DATE_PART('year', inserted_dt), DATE_PART('month', inserted_dt), TO_CHAR(inserted_dt, 'FMMonth') "
				+ "ORDER BY form_type, year, month_num", 
		resultSetMapping = "status_count_formType_byYear_byMonth"
	)

@SqlResultSetMapping(
	name = "status_count_formType_byYear_byMonth", 
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
@ToString(exclude = "transmissionStatusHist")
@EqualsAndHashCode(exclude = "transmissionStatusHist")
@Entity 
@Table(name="TRANSMISSION_STATUS")
public class TransmissionStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TRANSMISSION_STATUS_ID")
	@NotNull
	@JsonIgnore
	private int transmissionStatusId;
	
	@Column(name="RECEIPT_ID")
	@NotNull
	private String receiptId;
	
	@Column(name="STATUS")
	@NotNull
	private String status;
	
	@Column(name="FORM_TYPE")
	@NotNull
	private String formType;
	
	@Column(name="SERVER")
	@NotNull
	private String server;
	
	@Column(name="INSERTED_DT")
	@NotNull
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm:ss a")
	private Date insertedDt;
	
	@Column(name="UPDATE_DT")
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm:ss a")
	private Date updateDt;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "transmissionStatusId", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<TransmissionStatusHist> transmissionStatusHist;

}

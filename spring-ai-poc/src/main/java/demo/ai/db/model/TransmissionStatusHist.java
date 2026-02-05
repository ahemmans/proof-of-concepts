package demo.ai.db.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Component
@Entity 
@Table(name="TRANSMISSION_STATUS_HIST")
public class TransmissionStatusHist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TRANSMISSION_STATUS_HIST_ID")
	@NotNull
	@JsonIgnore
	private int transmissionStatusHistId;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="TRANSMISSION_STATUS_ID")
	@NotNull
	private TransmissionStatus transmissionStatusId;
	
	@Column(name="RECEIPT_ID")
	@NotNull
	private String receiptId;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="INSERTED_DT")
	@NotNull
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss a")
	private Date insertedDt; 

}

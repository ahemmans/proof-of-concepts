package us.states.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="counties")
public class County implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	@Column
	private String county;
	@Column
	private String state;
	@Column(name="st_abbr")
	private String stAbbr;
	@Column
	private Long population;
	
	@Column
	private String filename;
	@Column
	private String mimetype;
	@Column
	private byte[] filedata;
	
	public County() {
		
	}

	public County(int id, String county, String state, String stAbbr, Long population, String filename, String mimetype) {
		//super();
		this.id = id;
		this.county = county;
		this.state = state;
		this.stAbbr = stAbbr;
		this.population = population;
		this.filename = filename;
		this.mimetype = mimetype;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStAbbr() {
		return stAbbr;
	}

	public void setStAbbr(String stAbbr) {
		this.stAbbr = stAbbr;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getFiledata() {
		return filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((county == null) ? 0 : county.hashCode());
		result = prime * result + id;
		result = prime * result + ((population == null) ? 0 : population.hashCode());
		result = prime * result + ((stAbbr == null) ? 0 : stAbbr.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
		result = prime * result + ((mimetype == null) ? 0 : mimetype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		County other = (County) obj;
		if (county == null) {
			if (other.county != null)
				return false;
		} else if (!county.equals(other.county))
			return false;
		if (id != other.id)
			return false;
		if (population == null) {
			if (other.population != null)
				return false;
		} else if (!population.equals(other.population))
			return false;
		if (stAbbr == null) {
			if (other.stAbbr != null)
				return false;
		} else if (!stAbbr.equals(other.stAbbr))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (mimetype == null) {
			if (other.mimetype != null)
				return false;
		} else if (!mimetype.equals(other.mimetype))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "County [id=" + id + ", county=" + county + ", state=" + state + ", stAbbr=" + stAbbr + ", population=" + population 
				+ ", filename=" + filename + ", mimetype=" + mimetype +
				"]";
	}
		
}

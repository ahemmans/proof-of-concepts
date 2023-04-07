package us.states.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.County;

@Transactional
@Repository
public interface CountyRepository extends JpaRepository<County, Integer> {

	public List<County> findByStAbbr(String st_abbr);
	
	@Modifying
	@Query("UPDATE County c SET c.filename = :filename, c.mimetype = :mimetype, c.filedata = :bit WHERE c.id = :id")
	public void updateRecord(
			@Param("filename") String filename, 
			@Param("mimetype") String mimetype, 
			@Param("bit") byte[] bit,
			@Param("id") int id
		);
}

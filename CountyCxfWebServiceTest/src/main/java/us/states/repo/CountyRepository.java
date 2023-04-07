package us.states.repo;

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
	
	//@Query("SELECT c FROM County c WHERE c.id = :id")
	//public County getCountyById(@Param("id") int id);
	
	@Modifying
	@Query("UPDATE County c SET c.filename = :filename, c.mimetype = :mime WHERE c.id = :id")
	public void updateRecord(@Param("filename") String filename, @Param("mime") String mime, @Param("id") int id);
	
	@Modifying
	@Query("UPDATE County c SET c.filedata = :txt WHERE c.id = :id")
	public void updateFileData(@Param("txt") byte[] txt, @Param("id") int id);
	
}

package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.IFS_STATUS;

@Transactional
@Repository
public interface IfsStatus extends JpaRepository<IFS_STATUS, Integer> {

	@Modifying
	@Query(nativeQuery = true, 
			value = "INSERT INTO IFS_STATUS (gso_id, submission_id, name, subtype, status, create_dt, update_dt) " + 
					"VALUES (:gsoId, 1, :testFile, :formType, :status, CURRENT_DATE, CURRENT_DATE)"
		)
	public void insertStatus(@Param("gsoId") String gsoId, @Param("testFile") String testFile, @Param("formType") String formType, @Param("status") String status);

	@Query(nativeQuery = true, value = "SELECT MAX(status_id) from IFS_STATUS")
	public int getId();
}

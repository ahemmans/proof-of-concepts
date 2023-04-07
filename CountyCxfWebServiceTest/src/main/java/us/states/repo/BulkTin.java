package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.BULK_TIN;

@Transactional
@Repository
public interface BulkTin extends JpaRepository<BULK_TIN, String> {

}

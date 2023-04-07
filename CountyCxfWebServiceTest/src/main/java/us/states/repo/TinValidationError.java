package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.TIN_VALIDATION_ERROR;

@Transactional
@Repository
public interface TinValidationError extends JpaRepository<TIN_VALIDATION_ERROR, Integer> {

}

package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.IR_FORM_ERROR;

@Transactional
@Repository
public interface IrFormError extends JpaRepository<IR_FORM_ERROR, Integer> {

}

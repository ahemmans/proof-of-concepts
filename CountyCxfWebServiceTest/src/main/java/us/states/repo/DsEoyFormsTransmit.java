package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.DS_EOY_FORMS_TRANSMIT;

@Transactional
@Repository
public interface DsEoyFormsTransmit extends JpaRepository<DS_EOY_FORMS_TRANSMIT, String> {

}

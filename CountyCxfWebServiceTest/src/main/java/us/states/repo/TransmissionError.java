package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.TRANSMISSION_ERROR;

@Transactional
@Repository
public interface TransmissionError extends JpaRepository<TRANSMISSION_ERROR, Integer> {

}

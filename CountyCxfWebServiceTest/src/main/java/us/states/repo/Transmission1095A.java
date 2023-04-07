package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.TRANSMISSION_1095A;

@Transactional
@Repository
public interface Transmission1095A extends JpaRepository<TRANSMISSION_1095A, Integer> {

}

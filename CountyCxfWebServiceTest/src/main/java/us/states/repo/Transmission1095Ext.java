package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.TRANSMISSION_1095_EXT;

@Transactional
@Repository
public interface Transmission1095Ext extends JpaRepository<TRANSMISSION_1095_EXT, Integer> {

}

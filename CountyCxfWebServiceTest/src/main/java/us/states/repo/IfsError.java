package us.states.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import us.states.domain.IFS_ERROR;

@Transactional
@Repository
public interface IfsError extends JpaRepository<IFS_ERROR, Integer> {

}

package www.exam.janvier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import www.exam.janvier.entity.FicheSecuriteEntity;

public interface FicheSecuriteRepository extends JpaRepository<FicheSecuriteEntity,Long> {

}

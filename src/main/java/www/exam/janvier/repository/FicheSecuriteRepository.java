package www.exam.janvier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import www.exam.janvier.entity.FicheSecuriteEntity;

import java.util.List;

public interface FicheSecuriteRepository extends JpaRepository<FicheSecuriteEntity,Long> {
    List<FicheSecuriteEntity> findByStatut(String statut);
}

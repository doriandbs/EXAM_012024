package www.exam.janvier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import www.exam.janvier.entity.ProduitEntity;

@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity,Long> {
}

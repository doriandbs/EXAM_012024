package www.exam.janvier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import www.exam.janvier.entity.ProduitEntity;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity,Long> {
    @Query("SELECT p, f FROM ProduitEntity p JOIN p.fichesSecurite f")
    List<ProduitEntity> findProduitsWithFiches();

    @Query("SELECT p FROM ProduitEntity p JOIN p.fichesSecurite f WHERE f.id = :ficheId")
    List<ProduitEntity> findByFicheId(Long ficheId);
}

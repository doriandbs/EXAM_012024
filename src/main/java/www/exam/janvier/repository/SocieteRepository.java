package www.exam.janvier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import www.exam.janvier.entity.SocieteEntity;

import java.util.List;

@Repository
public interface SocieteRepository extends JpaRepository<SocieteEntity,Long> {
   SocieteEntity findByNomSociete(String nomSociete);

    boolean existsByNomSociete(String nomSociete);

    @Query("SELECT u FROM SocieteEntity u JOIN u.roles r WHERE r.name = :roleName")
    List<SocieteEntity> findByRole(String roleName);

    @Query("SELECT u FROM SocieteEntity u JOIN u.produits p WHERE p.id = :id")
    List<SocieteEntity> findByProductId(Long id);



}

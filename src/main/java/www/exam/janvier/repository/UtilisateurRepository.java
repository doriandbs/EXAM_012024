package www.exam.janvier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import www.exam.janvier.entity.UtilisateurEntity;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity,Long> {
   UtilisateurEntity findByUsername(String userName);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM UtilisateurEntity u JOIN u.roles r WHERE r.name = :roleName")
    List<UtilisateurEntity> findByRole(String roleName);

    @Query("SELECT u FROM UtilisateurEntity u JOIN u.produits p WHERE p.id = :id")
    List<UtilisateurEntity> findByProductId(Long id);



}

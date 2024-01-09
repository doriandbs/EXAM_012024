package www.exam.janvier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import www.exam.janvier.entity.UtilisateurEntity;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity,Long> {
   UtilisateurEntity findByUsername(String userName);

    boolean existsByUsername(String username);

}

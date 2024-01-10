package www.exam.janvier.service;

import www.exam.janvier.entity.UtilisateurEntity;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    boolean existByUsername(String username);
    UtilisateurEntity save(UtilisateurEntity entity);
    UtilisateurEntity findByUsername(String username);
    Optional<UtilisateurEntity> findById(Long id);
    List<UtilisateurEntity> findAllByRole(String roleName);

    List<UtilisateurEntity> findByProductId(Long id);


}

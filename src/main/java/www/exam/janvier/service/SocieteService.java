package www.exam.janvier.service;

import www.exam.janvier.entity.SocieteEntity;

import java.util.List;
import java.util.Optional;

public interface SocieteService {
    boolean existByNomSociete(String nomSociete);
    SocieteEntity save(SocieteEntity entity);
    SocieteEntity findByNomSociete(String nomSociete);
    Optional<SocieteEntity> findById(Long id);
    List<SocieteEntity> findAllWithProductsByRole(String roleName);

    List<SocieteEntity> findByProductId(Long id);


}

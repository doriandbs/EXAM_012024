package www.exam.janvier.service;

import www.exam.janvier.entity.ProduitEntity;

import java.util.List;
import java.util.Optional;

public interface ProduitService {

    List<ProduitEntity> getAll();
    Optional<ProduitEntity> getById(Long id);

    ProduitEntity save(ProduitEntity produit);

}

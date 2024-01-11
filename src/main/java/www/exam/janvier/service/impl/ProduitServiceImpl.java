package www.exam.janvier.service.impl;

import org.springframework.stereotype.Service;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.repository.ProduitRepository;
import www.exam.janvier.service.ProduitService;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitServiceImpl implements ProduitService {
    private final ProduitRepository produitRepo;

    public ProduitServiceImpl(ProduitRepository produitRepo){
        this.produitRepo=produitRepo;
    }
    @Override
    public List<ProduitEntity> getAll() {
        return produitRepo.findAll();
    }

    @Override
    public Optional<ProduitEntity> getById(Long id) {
        return produitRepo.findById(id);
    }

    @Override
    public ProduitEntity ajouterProduit(ProduitEntity produit) {
        return produitRepo.save(produit);
    }

}

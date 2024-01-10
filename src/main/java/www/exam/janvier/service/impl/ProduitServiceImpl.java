package www.exam.janvier.service.impl;

import org.springframework.stereotype.Service;
import www.exam.janvier.dto.FicheSecuriteProduitDTO;
import www.exam.janvier.entity.FicheSecuriteEntity;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.repository.ProduitRepository;
import www.exam.janvier.service.ProduitService;

import java.util.List;
import java.util.Set;

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
    public ProduitEntity ajouterProduit(FicheSecuriteProduitDTO produitDTO) {
        ProduitEntity entity = new ProduitEntity();
        entity.setNom(produitDTO.getProduitNom());
        entity.setFichesSecurite((Set<FicheSecuriteEntity>) produitDTO.getFicheSecurite());
        return produitRepo.save(entity);
    }

}

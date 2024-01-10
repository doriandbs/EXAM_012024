package www.exam.janvier.service;

import www.exam.janvier.dto.FicheSecuriteProduitDTO;
import www.exam.janvier.entity.ProduitEntity;

import java.util.List;

public interface ProduitService {

    List<ProduitEntity> getAll();

    ProduitEntity ajouterProduit(FicheSecuriteProduitDTO produitDTO);
}

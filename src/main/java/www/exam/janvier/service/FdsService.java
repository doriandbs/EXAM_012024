package www.exam.janvier.service;

import www.exam.janvier.dto.FicheSecuriteProduitDTO;
import www.exam.janvier.entity.FicheSecuriteEntity;

import java.io.IOException;
import java.util.List;

public interface FdsService {

    public byte[] convertPdf(String filePath) throws IOException;
    List<FicheSecuriteProduitDTO> findAllByStatut(String statut) throws IOException;

    void updateStatut(Long ficheId, String nouveauStatut);

    FicheSecuriteEntity findById(Long ficheId) throws IOException;

    FicheSecuriteEntity saveFicheSecurite(FicheSecuriteEntity fiche);

    List<FicheSecuriteEntity> findAllWithoutProduct();

}

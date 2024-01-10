package www.exam.janvier.service;

import www.exam.janvier.dto.FicheSecuriteProduitDTO;

import java.io.IOException;
import java.util.List;

public interface FdsService {

    public byte[] convertPdf(String filePath) throws IOException;
    List<FicheSecuriteProduitDTO> findAll() throws IOException;

    void updateStatut(Long ficheId, String nouveauStatut);
}

package www.exam.janvier.service;

import www.exam.janvier.entity.FicheSecuriteEntity;

import java.io.IOException;
import java.util.List;

public interface FdsService {

    public byte[] convertPdf(String filePath) throws IOException;
    List<FicheSecuriteEntity> findAll();

    void updateStatut(Long ficheId, String nouveauStatut);
}

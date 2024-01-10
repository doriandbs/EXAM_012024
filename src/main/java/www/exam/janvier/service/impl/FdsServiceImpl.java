package www.exam.janvier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.exam.janvier.entity.FicheSecuriteEntity;
import www.exam.janvier.repository.FicheSecuriteRepository;
import www.exam.janvier.service.FdsService;
import www.exam.janvier.service.UtilisateurService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class FdsServiceImpl implements FdsService {
    @Autowired
    private FicheSecuriteRepository ficheSecuriteRepo;

    @Autowired
    private UtilisateurService utilisateurService;


    public byte[] convertPdf(String path) throws IOException {
        File fichier = new File(path);
        if (!fichier.exists()) {
            throw new IOException("Fichier non trouvé dans " + path);
        }

        try (FileInputStream fileInputStream = new FileInputStream(fichier);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    @Override
    public List<FicheSecuriteEntity> findAll() {
        List<FicheSecuriteEntity> list = ficheSecuriteRepo.findAll();
        return list;
    }

    @Override
    public void updateStatut(Long ficheId, String nouveauStatut) {
        FicheSecuriteEntity fiche = ficheSecuriteRepo.findById(ficheId)
                .orElseThrow(() -> new RuntimeException("Fiche non trouvée"));
        fiche.setStatut(nouveauStatut);
        fiche.setDateMaj(LocalDate.now());
        ficheSecuriteRepo.save(fiche);
    }

}

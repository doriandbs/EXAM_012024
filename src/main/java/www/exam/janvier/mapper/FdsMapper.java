package www.exam.janvier.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.exam.janvier.DTO.FdsDTO;
import www.exam.janvier.entity.FicheSecuriteEntity;
import www.exam.janvier.service.FdsService;

import java.io.IOException;

@Component
public class FdsMapper {
    @Autowired
    private FdsService fdsService;
    public FdsDTO convertToDTO(FicheSecuriteEntity fiche) {
        FdsDTO fdsDTO = new FdsDTO();
        fdsDTO.setNom(fiche.getName());
        fdsDTO.setId(fiche.getId());
        fdsDTO.setCheminPdf(fiche.getCheminPdf());
        fdsDTO.setStatut(fiche.getStatut());
        fdsDTO.setDateCreation(fiche.getDateCreation());
        fdsDTO.setDateMaj(fiche.getDateMaj());

        try {
            byte[] pdfContent = fdsService.convertPdf(fiche.getCheminPdf());
            fdsDTO.setPdfContent(pdfContent);
        } catch (IOException e) {
            System.out.println("erreur : " + e.getMessage());
        }

        return fdsDTO;
    }
}

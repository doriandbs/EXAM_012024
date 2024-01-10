package www.exam.janvier.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.exam.janvier.dto.UtilisateurDTO;
import www.exam.janvier.entity.UtilisateurEntity;

@Component
public class UtilisateurMapper {
    private final ProduitMapper produitMapper;
    @Autowired
    public UtilisateurMapper(ProduitMapper produitMapper) {
        this.produitMapper=produitMapper;
    }
    public UtilisateurDTO convertToDTO(UtilisateurEntity utilisateur) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setNomsociete(utilisateur.getNomSociete());
        utilisateurDTO.setProduits(utilisateur.getProduits().stream()
                .map(produitMapper::convertToDTO).toList());
        return utilisateurDTO;

    }
}

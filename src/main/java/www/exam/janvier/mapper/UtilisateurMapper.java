package www.exam.janvier.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.exam.janvier.DTO.UtilisateurDTO;
import www.exam.janvier.entity.UtilisateurEntity;

import java.util.stream.Collectors;

@Component
public class UtilisateurMapper {
    @Autowired
    private ProduitMapper produitMapper;
    public UtilisateurDTO convertToDTO(UtilisateurEntity utilisateur) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setUsername(utilisateur.getUsername());
        utilisateurDTO.setProduits(utilisateur.getProduits().stream()
                .map(produit -> produitMapper.convertToDTO(produit))
                .collect(Collectors.toList()));
        return utilisateurDTO;

    }
}

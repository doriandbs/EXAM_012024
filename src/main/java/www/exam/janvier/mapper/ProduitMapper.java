package www.exam.janvier.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.exam.janvier.DTO.FdsDTO;
import www.exam.janvier.DTO.ProduitDTO;
import www.exam.janvier.entity.ProduitEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProduitMapper {

    @Autowired
    private FdsMapper fdsMapper;
    public ProduitDTO convertToDTO(ProduitEntity produit) {
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setId(produit.getId());
        produitDTO.setNom(produit.getNom());

        List<FdsDTO> fdsDTOs = produit.getFichesSecurite()
                .stream()
                .map(fiche -> fdsMapper.convertToDTO(fiche))
                .collect(Collectors.toList());
        produitDTO.setFiches(fdsDTOs);

        return produitDTO;
    }
}

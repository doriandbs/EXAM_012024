package www.exam.janvier.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.exam.janvier.dto.FdsDTO;
import www.exam.janvier.dto.ProduitDTO;
import www.exam.janvier.entity.ProduitEntity;

import java.util.List;

@Component
public class ProduitMapper {

    private final FdsMapper fdsMapper;
    @Autowired
    public ProduitMapper(FdsMapper fdsMapper) {
        this.fdsMapper=fdsMapper;
    }
    public ProduitDTO convertToDTO(ProduitEntity produit) {
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setId(produit.getId());
        produitDTO.setNom(produit.getNom());

        List<FdsDTO> fdsDTOs = produit.getFichesSecurite()
                .stream()
                .map(fdsMapper::convertToDTO).toList();
        produitDTO.setFiches(fdsDTOs);

        return produitDTO;
    }
}

package www.exam.janvier.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.exam.janvier.dto.SocieteDTO;
import www.exam.janvier.entity.SocieteEntity;

@Component
public class SocieteMapper {
    private final ProduitMapper produitMapper;
    @Autowired
    public SocieteMapper(ProduitMapper produitMapper) {
        this.produitMapper=produitMapper;
    }
    public SocieteDTO convertToDTO(SocieteEntity societe) {
        SocieteDTO societeDTO = new SocieteDTO();
        societeDTO.setNomsociete(societe.getNomSociete());
        societeDTO.setProduits(societe.getProduits().stream()
                .map(produitMapper::convertToDTO).toList());
        return societeDTO;

    }
}

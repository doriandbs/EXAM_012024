package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.exam.janvier.DTO.FdsDTO;
import www.exam.janvier.DTO.ProduitDTO;
import www.exam.janvier.entity.FicheSecuriteEntity;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.service.ProduitService;
import www.exam.janvier.service.UtilisateurService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/user/{userId}/produits")
    public ResponseEntity<List<ProduitDTO>> getProduitsForUser(@PathVariable Long userId) {
        Optional<UtilisateurEntity> userOpt = utilisateurService.findById(userId);
        if (!userOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UtilisateurEntity user = userOpt.get();
        Set<ProduitEntity> produits = user.getProduits();

        List<ProduitDTO> produitDTOs = new ArrayList<>();
        for (ProduitEntity produit : produits) {
            ProduitDTO produitDTO = new ProduitDTO();
            produitDTO.setId(produit.getId());
            produitDTO.setNom(produit.getNom());

            List<FdsDTO> fdsDTOs = produit.getFichesSecurite()
                    .stream()
                    .map(this::convertToFdsDTO)
                    .collect(Collectors.toList());
            produitDTO.setFiches(fdsDTOs);
            produitDTOs.add(produitDTO);
        }

        return ResponseEntity.ok(produitDTOs);
    }

    private FdsDTO convertToFdsDTO(FicheSecuriteEntity fiche) {
        FdsDTO fdsDTO = new FdsDTO();
        fdsDTO.setCheminPdf(fiche.getCheminPdf());
        fdsDTO.setStatut(fiche.getStatut());
        fdsDTO.setDateCreation(fiche.getDateCreation());
        fdsDTO.setDateMaj(fiche.getDateMaj());
        return fdsDTO;
    }


}

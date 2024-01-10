package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.exam.janvier.DTO.FdsDTO;
import www.exam.janvier.DTO.ProduitDTO;
import www.exam.janvier.entity.FicheSecuriteEntity;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.service.FdsService;
import www.exam.janvier.service.ProduitService;
import www.exam.janvier.service.UtilisateurService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private FdsService fdsService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/user/produits")
    public ResponseEntity<List<ProduitDTO>> getProduitsForUser() {
        String userActif = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UtilisateurEntity> userOpt = Optional.ofNullable(utilisateurService.findByUsername(userActif));
        if (!userOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UtilisateurEntity user = userOpt.get();
        Set<ProduitEntity> produits = user.getProduits();

        List<ProduitDTO> produitDTOs = produits.stream().map(this::convertToProduitDTO).collect(Collectors.toList());

        return ResponseEntity.ok(produitDTOs);
    }

    private ProduitDTO convertToProduitDTO(ProduitEntity produit) {
        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setId(produit.getId());
        produitDTO.setNom(produit.getNom());

        List<FdsDTO> fdsDTOs = produit.getFichesSecurite()
                .stream()
                .map(this::convertToFdsDTO)
                .collect(Collectors.toList());
        produitDTO.setFiches(fdsDTOs);

        return produitDTO;
    }

    private FdsDTO convertToFdsDTO(FicheSecuriteEntity fiche) {
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

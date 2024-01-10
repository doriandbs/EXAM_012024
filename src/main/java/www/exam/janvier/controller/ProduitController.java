package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import www.exam.janvier.dto.FicheSecuriteProduitDTO;
import www.exam.janvier.dto.ProduitDTO;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.mapper.ProduitMapper;
import www.exam.janvier.service.ProduitService;
import www.exam.janvier.service.UtilisateurService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService produitService;
    private final UtilisateurService utilisateurService;
    private final ProduitMapper produitMapper;

    @Autowired
    public ProduitController(ProduitService produitService, UtilisateurService utilisateurService, ProduitMapper produitMapper) {
        this.produitService = produitService;
        this.utilisateurService= utilisateurService;
        this.produitMapper=produitMapper;
    }
    @GetMapping("/user/produits")
    public ResponseEntity<List<ProduitDTO>> getProduitsForUser() {
        String societeActive = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UtilisateurEntity> userOpt = Optional.ofNullable(utilisateurService.findByNomSociete(societeActive));
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UtilisateurEntity user = userOpt.get();
        Set<ProduitEntity> produits = user.getProduits();

        List<ProduitDTO> produitDTOs = produits.stream().map(produitMapper::convertToDTO).toList();

        return ResponseEntity.ok(produitDTOs);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ProduitDTO> getAll(){
        List<ProduitEntity> listeProduits = produitService.getAll();
        return listeProduits.stream().map(produitMapper::convertToDTO).toList();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> ajouterProduit(@RequestBody FicheSecuriteProduitDTO newProduit ){
        return ResponseEntity.notFound().build();
    }



}

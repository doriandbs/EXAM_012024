package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import www.exam.janvier.dto.ProduitDTO;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.entity.SocieteEntity;
import www.exam.janvier.mapper.ProduitMapper;
import www.exam.janvier.service.ProduitService;
import www.exam.janvier.service.SocieteService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService produitService;
    private final SocieteService societeService;
    private final ProduitMapper produitMapper;

    @Autowired
    public ProduitController(ProduitService produitService, SocieteService societeService, ProduitMapper produitMapper) {
        this.produitService = produitService;
        this.societeService= societeService;
        this.produitMapper=produitMapper;
    }
    @GetMapping("/societe/produits")
    public ResponseEntity<List<ProduitDTO>> getProduitsForSociete() {
        String societeActive = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SocieteEntity> societeOpt = Optional.ofNullable(societeService.findByNomSociete(societeActive));
        if (societeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SocieteEntity societe = societeOpt.get();
        Set<ProduitEntity> produits = societe.getProduits();

        List<ProduitDTO> produitDTOs = produits.stream()
                .filter(produit -> produit.getFichesSecurite() != null && !produit.getFichesSecurite().isEmpty())
                .map(produitMapper::convertToDTO)
                .toList();
        return ResponseEntity.ok(produitDTOs);
    }

    @GetMapping("/fds")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ProduitDTO> getAllProductsWithFds(){
        List<ProduitEntity> listeProduits = produitService.getAll();
        return listeProduits.stream()
                .filter(produit -> produit.getFichesSecurite() != null && !produit.getFichesSecurite().isEmpty())
                .map(produitMapper::convertToDTO)
                .toList();
    }


    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ProduitDTO> getAllProducts(){
        List<ProduitEntity> listeProduits = produitService.getAll();
        return listeProduits.stream()
                .map(produitMapper::convertToDTO)
                .toList();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProduitDTO> ajouterProduit(@RequestBody ProduitDTO newProduct ){
        ProduitEntity produit = new ProduitEntity();
        produit.setNom(newProduct.getNom());

        ProduitEntity savedProduit = produitService.ajouterProduit(produit);

        ProduitDTO nouveauProduit = new ProduitDTO(savedProduit.getId(), savedProduit.getNom(),null);
        return ResponseEntity.ok(nouveauProduit);
    }



}

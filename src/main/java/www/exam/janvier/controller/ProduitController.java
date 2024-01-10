package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import www.exam.janvier.DTO.FicheSecuriteProduitDTO;
import www.exam.janvier.DTO.ProduitDTO;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.mapper.ProduitMapper;
import www.exam.janvier.service.FdsService;
import www.exam.janvier.service.ProduitService;
import www.exam.janvier.service.UtilisateurService;

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

    @Autowired
    private ProduitMapper produitMapper;
    @GetMapping("/user/produits")
    public ResponseEntity<List<ProduitDTO>> getProduitsForUser() {
        String userActif = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UtilisateurEntity> userOpt = Optional.ofNullable(utilisateurService.findByUsername(userActif));
        if (!userOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        UtilisateurEntity user = userOpt.get();
        Set<ProduitEntity> produits = user.getProduits();

        List<ProduitDTO> produitDTOs = produits.stream().map(produit -> produitMapper.convertToDTO(produit)).collect(Collectors.toList());

        return ResponseEntity.ok(produitDTOs);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<ProduitDTO> getAll(){
        List<ProduitEntity> listeProduits = produitService.getAll();
        List<ProduitDTO> produitDTOs = listeProduits.stream().map(produit -> produitMapper.convertToDTO(produit)).collect(Collectors.toList());
        return produitDTOs;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> ajouterProduit(@RequestBody FicheSecuriteProduitDTO newProduit ){
        return ResponseEntity.notFound().build();
    }



}

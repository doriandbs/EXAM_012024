package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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



}

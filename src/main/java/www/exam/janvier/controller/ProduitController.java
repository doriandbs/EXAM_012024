package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import www.exam.janvier.dto.ProduitDTO;
import www.exam.janvier.entity.FicheSecuriteEntity;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.entity.SocieteEntity;
import www.exam.janvier.mapper.ProduitMapper;
import www.exam.janvier.service.FdsService;
import www.exam.janvier.service.ProduitService;
import www.exam.janvier.service.SocieteService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService produitService;
    private final SocieteService societeService;
    private final ProduitMapper produitMapper;

    private final FdsService fdsService;

    @Value("${pdf.download.path}")
    private String pdfPath;

    @Autowired
    public ProduitController(ProduitService produitService, SocieteService societeService, ProduitMapper produitMapper,FdsService fdsService) {
        this.produitService = produitService;
        this.societeService= societeService;
        this.produitMapper=produitMapper;
        this.fdsService=fdsService;
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
    public ResponseEntity<?> ajouterProduit( @RequestParam("nom") String nomProduit,
                                                      @RequestParam(name = "ficheId", required = false) Long ficheId,
                                                      @RequestParam(name = "fichier", required = false) MultipartFile fichier) throws IOException {

        try {
            ProduitEntity produit = new ProduitEntity();
            produit.setNom(nomProduit);
            produit.setFichesSecurite(new HashSet<>());
            if (fichier != null) {
                String cheminFichier = pdfPath + fichier.getOriginalFilename();
                Path path = Paths.get(cheminFichier);
                Files.write(path, fichier.getBytes());

                FicheSecuriteEntity fiche = new FicheSecuriteEntity();
                fiche.setName(fichier.getName());
                fiche.setCheminPdf(cheminFichier);
                fiche.setDateCreation(LocalDate.now());
                fiche.setDateMaj(LocalDate.now());
                fiche.setStatut("active");
                FicheSecuriteEntity savedFiche = fdsService.saveFicheSecurite(fiche);
                produit.getFichesSecurite().add(savedFiche);
            }
            if (ficheId != null) {
                FicheSecuriteEntity fiche = fdsService.findById(ficheId);
                produit.getFichesSecurite().add(fiche);
            }
            produitService.save(produit);
            return ResponseEntity.ok(Map.of("OK", "Ajout bien effectué :)"));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of("NotOk", "Echec"));

        }
    }



}

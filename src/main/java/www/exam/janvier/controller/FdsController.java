package www.exam.janvier.controller;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import www.exam.janvier.dto.FicheSecuriteProduitDTO;
import www.exam.janvier.entity.FicheSecuriteEntity;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.mapper.FdsMapper;
import www.exam.janvier.service.FdsService;
import www.exam.janvier.service.ProduitService;
import www.exam.janvier.service.SocieteService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/fds")
public class FdsController {

    private final FdsService fdsService;

    private final ProduitService produitService;

    @Value("${pdf.download.path}")
    private String pdfPath;


    @Autowired
    public FdsController(FdsService fdsService, SocieteService societeService, FdsMapper fdsMapper,ProduitService produitService) {
        this.fdsService = fdsService;
        this.produitService=produitService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<FicheSecuriteProduitDTO> getFds() throws IOException {
       return fdsService.findAll();
    }

    @PutMapping("/{ficheId}/updatestatut")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateStatutFiche(@PathVariable Long ficheId, @RequestBody Map<String, String> statut) {
        try {
            fdsService.updateStatut(ficheId, statut.get("statut"));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du statut");
        }
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> uploadFichierPDF(@RequestParam("nomFichier") String nomFichier,
                                                   @Nullable @RequestParam("produitId") Long produitId,
                                                   @RequestParam("fichier") MultipartFile fichier) {
        try {
            String cheminFichier = pdfPath + fichier.getOriginalFilename();
            Path path = Paths.get(cheminFichier);
            Files.write(path, fichier.getBytes());

            FicheSecuriteEntity fiche = new FicheSecuriteEntity();
            fiche.setName(nomFichier);
            fiche.setCheminPdf(cheminFichier);
            fiche.setDateCreation(LocalDate.now());
            fiche.setDateMaj(LocalDate.now());
            fiche.setStatut("active");
            FicheSecuriteEntity savedFiche = fdsService.saveFicheSecurite(fiche);
            if(produitId!=null) {
                Optional<ProduitEntity> produit = produitService.getById(produitId);
                produit.get().getFichesSecurite().add(savedFiche);
                produitService.save(produit.get());
            }

            return ResponseEntity.ok(Map.of("message", "Fichier téléchargé et fiche créée avec succès"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur lors du téléchargement du fichier"));
        }
    }

}


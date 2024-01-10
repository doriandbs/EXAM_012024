package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import www.exam.janvier.dto.FicheSecuriteProduitDTO;
import www.exam.janvier.mapper.FdsMapper;
import www.exam.janvier.service.FdsService;
import www.exam.janvier.service.UtilisateurService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/fds")
public class FdsController {

    private final FdsService fdsService;

    @Autowired
    public FdsController(FdsService fdsService, UtilisateurService utilisateurService, FdsMapper fdsMapper) {
        this.fdsService = fdsService;
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

}


package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.exam.janvier.DTO.FdsDTO;
import www.exam.janvier.entity.FicheSecuriteEntity;
import www.exam.janvier.service.FdsService;
import www.exam.janvier.service.UtilisateurService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/fds")
public class FdsController {

    @Autowired
    private FdsService fdsService;
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<FdsDTO> getFds() {
       List<FicheSecuriteEntity> fds = fdsService.findAll();
        List<FdsDTO> fdsDTOs = fds.stream().map(this::convertToFdsDTO).collect(Collectors.toList());
        return fdsDTOs;
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


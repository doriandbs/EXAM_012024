package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import www.exam.janvier.dto.RegisterDTO;
import www.exam.janvier.dto.UtilisateurDTO;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.mapper.UtilisateurMapper;
import www.exam.janvier.service.RoleService;
import www.exam.janvier.service.UtilisateurService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class UtilisateurController {

    private UtilisateurService utilisateurService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private UtilisateurMapper utilisateurMapper;

    @Autowired
    public UtilisateurController(RoleService roleService, UtilisateurService utilisateurService, PasswordEncoder passwordEncoder,UtilisateurMapper utilisateurMapper) {
        this.roleService = roleService;
        this.utilisateurService= utilisateurService;
        this.passwordEncoder=passwordEncoder;
        this.utilisateurMapper=utilisateurMapper;
    }

    @PostMapping("/addClient")
    public ResponseEntity<String> addClient(@RequestBody RegisterDTO registerDTO) {
        if(utilisateurService.existByNomSociete(registerDTO.getNomsociete())) {
            return ResponseEntity.badRequest().body("Utilisateur déjà enregistré");
        }

        UtilisateurEntity newUtilisateur = new UtilisateurEntity();
        newUtilisateur.setNomSociete(registerDTO.getNomsociete());
        RoleEntity clientRole = roleService.findByName("ROLE_CLIENT");

        newUtilisateur.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUtilisateur.setRoles(Collections.singleton(clientRole));
        newUtilisateur.setMail(registerDTO.getMail());
        utilisateurService.save(newUtilisateur);
        return ResponseEntity.ok("Utilisateur enregistré");
    }

    @GetMapping("/clients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UtilisateurDTO>> getAll() {
        List<UtilisateurEntity> clients = utilisateurService.findAllByRole("ROLE_CLIENT");
        List<UtilisateurDTO> clientDTOs = clients.stream()
                .map(utilisateur -> utilisateurMapper.convertToDTO(utilisateur)).toList();
        return ResponseEntity.ok(clientDTOs);
    }


}
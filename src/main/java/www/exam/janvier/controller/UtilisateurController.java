package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.exam.janvier.DTO.RegisterDTO;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.service.RoleService;
import www.exam.janvier.service.UtilisateurService;

import java.util.Collections;

@RestController
@RequestMapping("/admin/user")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/addClient")
    public ResponseEntity<?> addClient(@RequestBody RegisterDTO registerDTO) {
        if(utilisateurService.existByUsername(registerDTO.getUsername())) {
            return ResponseEntity.badRequest().body("User already registered");
        }

        UtilisateurEntity newUtilisateur = new UtilisateurEntity();
        newUtilisateur.setUsername(registerDTO.getUsername());
        RoleEntity clientRole = roleService.findByName("ROLE_CLIENT");

        newUtilisateur.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newUtilisateur.setRoles(Collections.singleton(clientRole));
        UtilisateurEntity userSaved = utilisateurService.save(newUtilisateur);

        return ResponseEntity.ok(userSaved);
    }
}
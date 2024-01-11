package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import www.exam.janvier.dto.RegisterDTO;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.entity.SocieteEntity;
import www.exam.janvier.service.RoleService;
import www.exam.janvier.service.SocieteService;

import java.util.Collections;

@RestController
public class RegisterController {

    private final SocieteService societeService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(RoleService roleService, SocieteService societeService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.societeService= societeService;
        this.passwordEncoder=passwordEncoder;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if(societeService.existByNomSociete(registerDTO.getNomsociete())) {
            return ResponseEntity.badRequest().body("Société déjà enregistrée");
        }

        SocieteEntity newSociete = new SocieteEntity();
        newSociete.setNomSociete(registerDTO.getNomsociete());
        RoleEntity clientRole = roleService.findByName("ROLE_ADMIN");

        newSociete.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newSociete.setRoles(Collections.singleton(clientRole));
        newSociete.setMail(registerDTO.getMail());
        societeService.save(newSociete);
        return ResponseEntity.ok().body("Société enregistrée");
    }
}

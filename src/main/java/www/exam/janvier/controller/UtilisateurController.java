package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import www.exam.janvier.DTO.RegisterDTO;
import www.exam.janvier.DTO.UtilisateurDTO;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.mapper.UtilisateurMapper;
import www.exam.janvier.service.RoleService;
import www.exam.janvier.service.UtilisateurService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtilisateurMapper utilisateurMapper;

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

    @GetMapping("/clients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UtilisateurDTO>> getAll() {
        List<UtilisateurEntity> clients = utilisateurService.findAllByRole("ROLE_CLIENT");
        List<UtilisateurDTO> clientDTOs = clients.stream()
                .map(utilisateur -> utilisateurMapper.convertToDTO(utilisateur))
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientDTOs);
    }


}
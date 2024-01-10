package www.exam.janvier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import www.exam.janvier.dto.RegisterDTO;
import www.exam.janvier.dto.SocieteDTO;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.entity.SocieteEntity;
import www.exam.janvier.mapper.SocieteMapper;
import www.exam.janvier.service.RoleService;
import www.exam.janvier.service.SocieteService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin/societes")
public class SocieteController {

    private final SocieteService societeService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final SocieteMapper societeMapper;

    @Autowired
    public SocieteController(RoleService roleService, SocieteService societeService, PasswordEncoder passwordEncoder,SocieteMapper societeMapper) {
        this.roleService = roleService;
        this.societeService= societeService;
        this.passwordEncoder=passwordEncoder;
        this.societeMapper=societeMapper;
    }

    @PostMapping("/addClient")
    public ResponseEntity<String> addClient(@RequestBody RegisterDTO registerDTO) {
        if(societeService.existByNomSociete(registerDTO.getNomsociete())) {
            return ResponseEntity.badRequest().body("Société déjà enregistrée");
        }

        SocieteEntity newSociete = new SocieteEntity();
        newSociete.setNomSociete(registerDTO.getNomsociete());
        RoleEntity clientRole = roleService.findByName("ROLE_CLIENT");

        newSociete.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newSociete.setRoles(Collections.singleton(clientRole));
        newSociete.setMail(registerDTO.getMail());
        societeService.save(newSociete);
        return ResponseEntity.ok("Société enregistrée");
    }

    @GetMapping("/clients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<SocieteDTO>> getAllClientWithProduct() {
        List<SocieteEntity> clientsWithProduct = societeService.findAllWithProductsByRole("ROLE_CLIENT");
        List<SocieteDTO> clientDTOs = clientsWithProduct.stream()
                .map(societeMapper::convertToDTO).toList();
        return ResponseEntity.ok(clientDTOs);
    }


}
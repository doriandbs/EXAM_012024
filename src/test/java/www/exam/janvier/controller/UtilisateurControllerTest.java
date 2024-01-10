package www.exam.janvier.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import www.exam.janvier.dto.RegisterDTO;
import www.exam.janvier.dto.UtilisateurDTO;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.mapper.UtilisateurMapper;
import www.exam.janvier.service.RoleService;
import www.exam.janvier.service.UtilisateurService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UtilisateurControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UtilisateurMapper utilisateurMapper;

    @InjectMocks
    private UtilisateurController utilisateurController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();
    }

    @Test
    void testAddClient_Success() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("societe", "password", "email@example.com");
        when(utilisateurService.existByNomSociete(registerDTO.getNomsociete())).thenReturn(false);
        when(roleService.findByName("ROLE_CLIENT")).thenReturn(new RoleEntity());
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encodedPassword");

        mockMvc.perform(post("/admin/users/addClient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Utilisateur enregistré"));
    }

    @Test
    void testAddClient_UserAlreadyExists() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("societe", "password", "email@example.com");
        when(utilisateurService.existByNomSociete(registerDTO.getNomsociete())).thenReturn(true);

        mockMvc.perform(post("/admin/users/addClient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Utilisateur déjà enregistré"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAll_Clients() throws Exception {
        List<UtilisateurEntity> clients = List.of(new UtilisateurEntity());
        when(utilisateurService.findAllByRole("ROLE_CLIENT")).thenReturn(clients);
        when(utilisateurMapper.convertToDTO(any(UtilisateurEntity.class))).thenReturn(new UtilisateurDTO());

        mockMvc.perform(get("/admin/users/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}

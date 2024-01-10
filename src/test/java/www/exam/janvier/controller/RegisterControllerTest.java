package www.exam.janvier.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import www.exam.janvier.dto.RegisterDTO;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.service.RoleService;
import www.exam.janvier.service.UtilisateurService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegisterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    void testRegister_Success() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("societe", "password", "email@example.com");
        when(utilisateurService.existByNomSociete(registerDTO.getNomsociete())).thenReturn(false);
        when(roleService.findByName("ROLE_CLIENT")).thenReturn(new RoleEntity());
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encodedPassword");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Utilisateur enregistré"));
    }

    @Test
    void testRegister_UserAlreadyExists() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("societe", "password", "email@example.com");
        when(utilisateurService.existByNomSociete(registerDTO.getNomsociete())).thenReturn(true);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Utilisateur déjà enregistré"));
    }
}

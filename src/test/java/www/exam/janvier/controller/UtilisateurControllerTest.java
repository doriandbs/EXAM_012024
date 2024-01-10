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
import www.exam.janvier.dto.SocieteDTO;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.entity.SocieteEntity;
import www.exam.janvier.mapper.SocieteMapper;
import www.exam.janvier.service.RoleService;
import www.exam.janvier.service.SocieteService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SocieteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SocieteService societeService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SocieteMapper societeMapper;

    @InjectMocks
    private SocieteController societeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(societeController).build();
    }

    @Test
    void testAddClient_Success() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("societe", "password", "email@example.com");
        when(societeService.existByNomSociete(registerDTO.getNomsociete())).thenReturn(false);
        when(roleService.findByName("ROLE_CLIENT")).thenReturn(new RoleEntity());
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encodedPassword");

        mockMvc.perform(post("/admin/societes/addClient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Société enregistrée"));
    }

    @Test
    void testAddClient_SocieteAlreadyExists() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("societe", "password", "email@example.com");
        when(societeService.existByNomSociete(registerDTO.getNomsociete())).thenReturn(true);

        mockMvc.perform(post("/admin/societes/addClient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Société déjà enregistré"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAll_Clients() throws Exception {
        List<SocieteEntity> clients = List.of(new SocieteEntity());
        when(societeService.findAllByRole("ROLE_CLIENT")).thenReturn(clients);
        when(societeMapper.convertToDTO(any(SocieteEntity.class))).thenReturn(new SocieteDTO());

        mockMvc.perform(get("/admin/societes/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}

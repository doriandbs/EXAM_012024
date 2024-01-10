package www.exam.janvier.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import www.exam.janvier.dto.FicheSecuriteProduitDTO;
import www.exam.janvier.dto.ProduitDTO;
import www.exam.janvier.entity.ProduitEntity;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.mapper.ProduitMapper;
import www.exam.janvier.service.ProduitService;
import www.exam.janvier.service.UtilisateurService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProduitControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProduitService produitService;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private ProduitMapper produitMapper;

    @InjectMocks
    private ProduitController produitController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produitController).build();
    }

    @Test
    void testGetProduitsForUser_Success() throws Exception {
        String societeActive = "societe1";
        UtilisateurEntity user = new UtilisateurEntity();
        user.setProduits(Set.of(new ProduitEntity()));

        when(utilisateurService.findByNomSociete(societeActive)).thenReturn(user);
        when(produitMapper.convertToDTO(any(ProduitEntity.class))).thenReturn(new ProduitDTO());

        Authentication auth = new UsernamePasswordAuthenticationToken(societeActive, "password");
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(auth);

        mockMvc.perform(get("/produits/user/produits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetProduitsForUser_UserNotFound() throws Exception {
        String societeActive = "nonExistingUser";
        when(utilisateurService.findByNomSociete(societeActive)).thenReturn(null);

        Authentication auth = new UsernamePasswordAuthenticationToken(societeActive, "password");
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(auth);

        mockMvc.perform(get("/produits/user/produits"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAll() throws Exception {
        List<ProduitEntity> produits = Arrays.asList(new ProduitEntity(), new ProduitEntity());
        when(produitService.getAll()).thenReturn(produits);
        when(produitMapper.convertToDTO(any(ProduitEntity.class))).thenReturn(new ProduitDTO());

        mockMvc.perform(get("/produits/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testAjouterProduit() throws Exception {
        FicheSecuriteProduitDTO newProduit = new FicheSecuriteProduitDTO();

        mockMvc.perform(post("/produits/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newProduit)))
                .andExpect(status().isNotFound());
    }
}

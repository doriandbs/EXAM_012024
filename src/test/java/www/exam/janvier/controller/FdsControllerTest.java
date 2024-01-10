package www.exam.janvier.controller;

// path/filename: src/test/java/www/exam/janvier/controller/FdsControllerTest.java

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import www.exam.janvier.dto.FicheSecuriteProduitDTO;
import www.exam.janvier.service.FdsService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FdsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FdsService fdsService;

    @InjectMocks
    private FdsController fdsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fdsController).build();
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetFds_Success() throws Exception {
        List<FicheSecuriteProduitDTO> mockFicheList = Arrays.asList(
                new FicheSecuriteProduitDTO(),
                new FicheSecuriteProduitDTO()
        );
        when(fdsService.findAll()).thenReturn(mockFicheList);

        mockMvc.perform(get("/admin/fds/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(mockFicheList.size())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateStatutFicheOK() throws Exception {
        Long ficheId = 1L;
        String statut = "Inactive";
        doNothing().when(fdsService).updateStatut(ficheId, statut);

        mockMvc.perform(put("/admin/fds/" + ficheId + "/updatestatut")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"statut\":\"" + statut + "\"}"))
                .andExpect(status().isOk());

        verify(fdsService, times(1)).updateStatut(ficheId, statut);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateStatutFicheFail() throws Exception {
        Long ficheId = 1L;
        String statut = "Inactive";
        doThrow(new RuntimeException("Fiche non trouvée")).when(fdsService).updateStatut(ficheId, statut);

        mockMvc.perform(put("/admin/fds/" + ficheId + "/updatestatut")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"statut\":\"" + statut + "\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erreur lors de la mise à jour du statut"));

        verify(fdsService, times(1)).updateStatut(ficheId, statut);
    }

}

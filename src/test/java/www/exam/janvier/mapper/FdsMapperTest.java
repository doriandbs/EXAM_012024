package www.exam.janvier.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import www.exam.janvier.dto.FdsDTO;
import www.exam.janvier.entity.FicheSecuriteEntity;
import www.exam.janvier.service.FdsService;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FdsMapperTest {
    @Mock
    private FdsService fdsService;

    private FdsMapper fdsMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fdsMapper = new FdsMapper(fdsService);
    }

    @Test
    void testConvertToDTO() throws IOException {
        FicheSecuriteEntity fiche = new FicheSecuriteEntity();
        fiche.setName("Test");
        fiche.setId(1L);
        fiche.setCheminPdf("cheminPdf");
        fiche.setStatut("Active");
        fiche.setDateCreation(LocalDate.now());
        fiche.setDateMaj(LocalDate.now());

        byte[] expectedPdfContent = new byte[] {1, 2, 3};
        when(fdsService.convertPdf(anyString())).thenReturn(expectedPdfContent);

        FdsDTO result = fdsMapper.convertToDTO(fiche);

        assertEquals(fiche.getName(), result.getNom());
        assertEquals(fiche.getId(), result.getId());
        assertEquals(fiche.getCheminPdf(), result.getCheminPdf());
        assertEquals(fiche.getStatut(), result.getStatut());
        assertEquals(fiche.getDateCreation(), result.getDateCreation());
        assertEquals(fiche.getDateMaj(), result.getDateMaj());
        assertArrayEquals(expectedPdfContent, result.getPdfContent());
    }
}
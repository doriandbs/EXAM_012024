package www.exam.janvier.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import www.exam.janvier.entity.FicheSecuriteEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FicheSecuriteProduitDTO {
    private FicheSecuriteEntity ficheSecurite;
    private String produitNom;
    private byte[] pdfContent;

}

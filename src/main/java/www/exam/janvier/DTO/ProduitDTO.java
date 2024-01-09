package www.exam.janvier.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProduitDTO {
    private Long id;
    private String nom;
    private List<FdsDTO> fiches;
}

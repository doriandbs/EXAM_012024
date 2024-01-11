package www.exam.janvier.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProduitDTO {
    private Long id;
    private String nom;
    private List<FdsDTO> fiches;
}

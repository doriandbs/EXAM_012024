package www.exam.janvier.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FdsDTO {
    private Long id;
    private String cheminPdf;
    private String statut;
    private LocalDate dateCreation;
    private LocalDate dateMaj;
}

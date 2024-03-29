package www.exam.janvier.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FdsDTO {
    private Long id;
    private String nom;
    private String cheminPdf;
    private String statut;
    private LocalDate dateCreation;
    private LocalDate dateMaj;
    private byte[] pdfContent;
}

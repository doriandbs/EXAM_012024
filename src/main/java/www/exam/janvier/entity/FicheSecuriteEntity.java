package www.exam.janvier.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "fiches_securite")
@Data
public class FicheSecuriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cheminPdf;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @Column(nullable = false)
    private LocalDate dateMaj;

    @OneToOne(mappedBy = "ficheSecurite")
    private ProduitFicheSecuriteEntity produitFicheSecurite;

}
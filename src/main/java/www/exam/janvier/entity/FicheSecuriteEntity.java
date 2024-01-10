package www.exam.janvier.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "fiches_securite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FicheSecuriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    private String cheminPdf;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @Column(nullable = false)
    private LocalDate dateMaj;

}
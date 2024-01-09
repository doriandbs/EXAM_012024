package www.exam.janvier.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name="produits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProduitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "produit_fiche",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "fiche_id")
    )
    @JsonIgnore
    private Set<FicheSecuriteEntity> fichesSecurite;

}

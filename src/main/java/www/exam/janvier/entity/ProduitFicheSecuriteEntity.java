package www.exam.janvier.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "produits_fiches_securite")
@Data
public class ProduitFicheSecuriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    private ProduitEntity produit;

    @OneToOne
    @JoinColumn(name = "fiche_securite_id", referencedColumnName = "id")
    private FicheSecuriteEntity ficheSecurite;

}

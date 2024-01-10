package www.exam.janvier.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "societes")
@Getter
@Setter
public class SocieteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nomSociete;

    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String mail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "societe_role",
            joinColumns = @JoinColumn(name = "societe_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "societe_produit",
            joinColumns = @JoinColumn(name = "societe_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private Set<ProduitEntity> produits;


}
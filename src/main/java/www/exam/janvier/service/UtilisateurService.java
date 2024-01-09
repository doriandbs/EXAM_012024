package www.exam.janvier.service;

import www.exam.janvier.entity.UtilisateurEntity;

public interface UtilisateurService {
    boolean existByUsername(String username);
    UtilisateurEntity save(UtilisateurEntity entity);

}

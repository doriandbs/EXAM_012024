package www.exam.janvier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.repository.UtilisateurRepository;
import www.exam.janvier.service.UtilisateurService;

import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepo;

    public boolean existByUsername(String username){
        return utilisateurRepo.existsByUsername(username);
    }

    public UtilisateurEntity save(UtilisateurEntity utilisateurEntity){
        return utilisateurRepo.save(utilisateurEntity);
    }

    public UtilisateurEntity findByUsername(String username){
        return utilisateurRepo.findByUsername(username);
    }

    @Override
    public Optional<UtilisateurEntity> findById(Long id) {
        return utilisateurRepo.findById(id);
    }
}

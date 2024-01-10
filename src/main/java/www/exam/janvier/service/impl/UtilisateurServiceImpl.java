package www.exam.janvier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.exam.janvier.entity.UtilisateurEntity;
import www.exam.janvier.repository.UtilisateurRepository;
import www.exam.janvier.service.UtilisateurService;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    UtilisateurRepository utilisateurRepo;
    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepo){
        this.utilisateurRepo = utilisateurRepo;
    }

    public boolean existByNomSociete(String nomSociete){
        return utilisateurRepo.existsByNomSociete(nomSociete);
    }

    public UtilisateurEntity save(UtilisateurEntity utilisateurEntity){
        return utilisateurRepo.save(utilisateurEntity);
    }

    public UtilisateurEntity findByNomSociete(String nomSociete){
        return utilisateurRepo.findByNomSociete(nomSociete);
    }

    @Override
    public Optional<UtilisateurEntity> findById(Long id) {
        return utilisateurRepo.findById(id);
    }

    public List<UtilisateurEntity> findAllByRole(String roleName) {
        return utilisateurRepo.findByRole(roleName);
    }

    @Override
    public List<UtilisateurEntity> findByProductId(Long id) {
        return utilisateurRepo.findByProductId(id);
    }
}

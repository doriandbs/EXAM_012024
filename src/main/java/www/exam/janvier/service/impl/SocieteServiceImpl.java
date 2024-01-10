package www.exam.janvier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.exam.janvier.entity.SocieteEntity;
import www.exam.janvier.repository.SocieteRepository;
import www.exam.janvier.service.SocieteService;

import java.util.List;
import java.util.Optional;

@Service
public class SocieteServiceImpl implements SocieteService {

    SocieteRepository societeRepo;
    @Autowired
    public SocieteServiceImpl(SocieteRepository societeRepo){
        this.societeRepo = societeRepo;
    }

    public boolean existByNomSociete(String nomSociete){
        return societeRepo.existsByNomSociete(nomSociete);
    }

    public SocieteEntity save(SocieteEntity societeEntity){
        return societeRepo.save(societeEntity);
    }

    public SocieteEntity findByNomSociete(String nomSociete){
        return societeRepo.findByNomSociete(nomSociete);
    }

    @Override
    public Optional<SocieteEntity> findById(Long id) {
        return societeRepo.findById(id);
    }

    public List<SocieteEntity> findAllWithProductsByRole(String roleName) {
        List<SocieteEntity> listSociete= societeRepo.findByRole(roleName);
        return listSociete.stream()
                .filter(societe -> societe.getProduits() != null
                        && !societe.getProduits().isEmpty()).toList();
    }

    @Override
    public List<SocieteEntity> findByProductId(Long id) {
        return societeRepo.findByProductId(id);
    }
}

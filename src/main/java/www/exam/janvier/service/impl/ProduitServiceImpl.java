package www.exam.janvier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.exam.janvier.repository.ProduitRepository;
import www.exam.janvier.service.ProduitService;

@Service
public class ProduitServiceImpl implements ProduitService {
    @Autowired
    private ProduitRepository produitRepo;

}

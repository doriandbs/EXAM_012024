package www.exam.janvier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.exam.janvier.entity.RoleEntity;
import www.exam.janvier.repository.RoleRepository;
import www.exam.janvier.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepo;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepo){
        this.roleRepo=roleRepo;
    }
    @Override
    public RoleEntity findByName(String name) {
        return roleRepo.findByName(name);
    }
}

package www.exam.janvier.service;

import www.exam.janvier.entity.RoleEntity;

public interface RoleService {
    RoleEntity findByName(String name);
}

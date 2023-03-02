package com.cg.service.role;

import com.cg.domain.dto.RoleDTO;
import com.cg.domain.entity.Role;
import com.cg.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public List<RoleDTO> findAllRoleDTO() {
        return roleRepository.findAllRoleDTO();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role getById(Long id) {
        return null;
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}

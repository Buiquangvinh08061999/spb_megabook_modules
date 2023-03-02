package com.cg.service.role;

import com.cg.domain.dto.RoleDTO;
import com.cg.domain.entity.Role;
import com.cg.service.IGeneralService;

import java.util.List;


public interface IRoleService extends IGeneralService<Role> {

    List<RoleDTO> findAllRoleDTO();
}

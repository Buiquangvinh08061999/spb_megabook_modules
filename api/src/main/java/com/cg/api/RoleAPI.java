package com.cg.api;

import com.cg.domain.dto.CategoryDTO;
import com.cg.domain.dto.RoleDTO;
import com.cg.exception.DataInputException;
import com.cg.service.category.ICategoryService;
import com.cg.service.role.IRoleService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IRoleService roleService;

    //Lấy tất cả list role ra;
    @GetMapping
    public ResponseEntity<?> findAllRoleDTO() {
        List<RoleDTO> roleDTOList = roleService.findAllRoleDTO();

        if (roleDTOList.isEmpty()) {
            throw new DataInputException("Danh sách role rỗng, vui lòng kiểm tra lại!");
        }

        return new ResponseEntity<>(roleDTOList, HttpStatus.OK);

    }
}

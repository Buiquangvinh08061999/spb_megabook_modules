package com.cg.repository;

import com.cg.domain.dto.CategoryDTO;
import com.cg.domain.dto.RoleDTO;
import com.cg.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //1.Hiển thị tất cả danh sách role
    @Query("SELECT NEW com.cg.domain.dto.RoleDTO (" +
                "r.id, " +
                "r.code, " +
                "r.name" +
            ") " +
            "FROM Role AS r"
    )
    List<RoleDTO> findAllRoleDTO();

}

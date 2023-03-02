package com.cg.service.user;

import com.cg.domain.dto.UserDTO;
import com.cg.domain.entity.User;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;


public interface IUserService extends IGeneralService<User> , UserDetailsService {

    //1.Hiển thị tất cả danh sách User
    List<UserDTO> findAllUserDTOByDeletedIsFalse();


    Optional<User> findById(String id);

    //2.Kiểm tra create
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);


    //1.Hiển thị tất cả danh sách admin
    List<UserDTO> findAllUserDTOByDeletedIsFalseRoleAdmin(String admin);

    //1.Hiển thị tất cả danh sách staff
    List<UserDTO> findAllUserDTOByDeletedIsFalseRoleStaff(String staff);


    //security
    User getByUsername(String username);
    Optional<User> findByUsername(String username);
}

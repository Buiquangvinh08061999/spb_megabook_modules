package com.cg.repository;

import com.cg.domain.dto.CustomerDTO;
import com.cg.domain.dto.UserDTO;
import com.cg.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //1.Hiển thị tất cả danh sách User
    @Query("SELECT NEW com.cg.domain.dto.UserDTO(" +
                "u.id, " +
                "u.username, " +
                "u.password, " +
                "u.fullName, " +
                "u.phone, " +
                "u.urlImage, " +
                "u.createAt, " +
                "u.updateAt, " +
                "u.role" +
                ") " +
            "FROM User AS u " +
            "WHERE u.deleted = false " +
            "ORDER BY u.id DESC"
    )
    List<UserDTO> findAllUserDTOByDeletedIsFalse();

    //Kiểm tra tạo mới
    Boolean existsByUsername(String username);
    Boolean existsByPhone(String phone);



    //1.Hiển thị tất cả danh sách admin
    @Query("SELECT NEW com.cg.domain.dto.UserDTO(" +
                "u.id, " +
                "u.username, " +
                "u.password, " +
                "u.fullName, " +
                "u.phone, " +
                "u.urlImage, " +
                "u.createAt, " +
                "u.updateAt, " +
                "u.role" +
            ") " +
            "FROM User AS u " +
            "WHERE u.deleted = false " +
            "AND u.role.name = ?1"
    )
    List<UserDTO> findAllUserDTOByDeletedIsFalseRoleAdmin(String admin);

    //1.Hiển thị tất cả danh sách staff
    @Query("SELECT NEW com.cg.domain.dto.UserDTO(" +
                "u.id, " +
                "u.username, " +
                "u.password, " +
                "u.fullName, " +
                "u.phone, " +
                "u.urlImage, " +
                "u.createAt, " +
                "u.updateAt, " +
                "u.role" +
            ") " +
            "FROM User AS u " +
            "WHERE u.deleted = false " +
            "AND u.role.name = ?1"
    )
    List<UserDTO> findAllUserDTOByDeletedIsFalseRoleStaff(String staff);


    //kiểm tra ở security
    User getByUsername(String username);
    Optional<User> findByUsername(String username);
}

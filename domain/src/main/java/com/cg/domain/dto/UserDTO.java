package com.cg.domain.dto;

import com.cg.domain.entity.Role;
import com.cg.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.Valid;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String id;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String urlImage;

    @Valid
    private RoleDTO role;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createAt;

    private String createBy;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;

    private String updateBy;


    public User toUser() {
        return (User) new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setFullName(fullName)
                .setPhone(phone)
                .setUrlImage(urlImage)
                .setRole(role.toRole())
                .setCreateAt(createAt)
                .setCreateBy(createBy)
                .setUpdateAt(updateAt)
                .setUpdateBy(updateBy);
    }

    public UserDTO(String id, String username, String password, String fullName, String phone, String urlImage,  Date createAt, Date updateAt, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.urlImage = urlImage;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.role = role.roleDTO();
    }
}
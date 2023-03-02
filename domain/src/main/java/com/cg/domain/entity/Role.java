package com.cg.domain.entity;

import com.cg.domain.dto.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @OneToMany(targetEntity = User.class , mappedBy = "role", fetch = FetchType.EAGER)
    private Set<User> users;


    public RoleDTO roleDTO() {
        return new RoleDTO()
                .setId(id)
                .setCode(code)
                .setName(name);
    }
}

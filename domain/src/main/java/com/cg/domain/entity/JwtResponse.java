package com.cg.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
    private String id;
    private String token;
    private String type = "Bearer";
    private String email;
    private String fullName;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String accessToken, String id, String fullName, String email, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                    "id=" + id +
                    ", token='" + token + '\'' +
                    ", type='" + type + '\'' +
                    ", name='" + fullName + '\'' +
                    ", email='" + email + '\'' +
                    ", roles=" + roles +
                '}';
    }
}

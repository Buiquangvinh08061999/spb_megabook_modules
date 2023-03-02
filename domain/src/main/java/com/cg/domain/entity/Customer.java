package com.cg.domain.entity;

import com.cg.domain.dto.CustomerDTO;
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
@Table(name = "customers")
public class Customer extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String phone;

    @Column(name = "url_image")
    private String urlImage;

    private String note;//thông tin ghi chú

    @OneToOne
    @JoinColumn(name = "location_region_id")
    private LocationRegion locationRegion;


    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;


    public CustomerDTO toCustomerDTO() {
        return new CustomerDTO()
                .setId(id)
                .setEmail(email)
                .setFullName(fullName)
                .setPhone(phone)
                .setUrlImage(urlImage)
                .setNote(note)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setCreateAt(getCreateAt())
                .setCreateBy(getCreateBy())
                .setUpdateAt(getUpdateAt())
                .setUpdateBy(getUpdateBy());
    }
}

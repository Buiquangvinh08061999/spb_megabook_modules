package com.cg.domain.entity;

import com.cg.domain.dto.SupplierDTO;
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
@Table(name = "suppliers")
public class Supplier extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // tên nhà cung cấp
    private String title;

    @Column(name="tax_code",nullable = false)
    private String taxCode; //Mã số thuế

    private String note; // ghi chú

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @OneToOne
    @JoinColumn(name ="location_region_id" )
    private LocationRegion locationRegion;


    @OneToMany(mappedBy = "supplier")
    private Set<ImportOrder> importOrders;


    public SupplierDTO toSupplierDTO() {
        return new SupplierDTO()
                .setId(id)
                .setTitle(title)
                .setTaxCode(taxCode)
                .setNote(note)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setCreateAt(getCreateAt())
                .setCreateBy(getCreateBy())
                .setUpdateAt(getUpdateAt())
                .setUpdateBy(getUpdateBy());
    }
}

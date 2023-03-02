package com.cg.domain.entity;

import com.cg.domain.dto.SupplierDTO;
import com.cg.domain.dto.WarehouseDTO;
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
@Table(name = "warehouses")
public class Warehouse extends BaseEntities{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // tên kho hàng
    private String title;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;


    @OneToOne
    @JoinColumn(name ="location_region_id" )
    private LocationRegion locationRegion;

    @OneToMany(mappedBy = "warehouse")
    private Set<ImportOrder> importOrders;


    public WarehouseDTO toWarehouseDTO() {
        return new WarehouseDTO()
                .setId(id)
                .setTitle(title)
                .setEmail(email)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setCreateAt(getCreateAt())
                .setCreateBy(getCreateBy())
                .setUpdateAt(getUpdateAt())
                .setUpdateBy(getUpdateBy());

    }
}

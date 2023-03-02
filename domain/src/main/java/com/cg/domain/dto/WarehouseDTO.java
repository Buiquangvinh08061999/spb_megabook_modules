package com.cg.domain.dto;

import com.cg.domain.entity.LocationRegion;
import com.cg.domain.entity.Warehouse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WarehouseDTO {

    private Long id;
    private String title;
    private String email;
    private String phone;

    private LocationRegionDTO locationRegion;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createAt;

    private String createBy;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;

    private String updateBy;

    public Warehouse toWarehouse() {
        return (Warehouse) new Warehouse()
                .setId(id)
                .setTitle(title)
                .setEmail(email)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setCreateAt(createAt)
                .setCreateBy(createBy)
                .setUpdateAt(updateAt)
                .setUpdateBy(updateBy);
    }


    public WarehouseDTO(Long id, String title, String email, String phone, Date createAt, Date updateAt, LocationRegion locationRegion) {
        this.id = id;
        this.title = title;
        this.email = email;
        this.phone = phone;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }
}

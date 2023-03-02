package com.cg.domain.dto;

import com.cg.domain.entity.LocationRegion;
import com.cg.domain.entity.Supplier;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.Valid;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SupplierDTO {

    private Long id;
    private String title;
    private String taxCode; //Mã số thuế
    private String note; // ghi chú
    private String fullName;
    private String email;
    private String phone;

    @Valid
    private LocationRegionDTO locationRegion;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createAt;

    private String createBy;

    @JsonFormat(pattern = "HH:mm - dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date updateAt;

    private String updateBy;

    public Supplier toSupplier() {
        return (Supplier) new Supplier()
                .setId(id)
                .setTitle(title)
                .setTaxCode(taxCode)
                .setNote(note)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setCreateAt(createAt)
                .setCreateBy(createBy)
                .setUpdateAt(updateAt)
                .setUpdateBy(updateBy);
    }

    public SupplierDTO(Long id, String title, String taxCode, String note, String fullName, String email, String phone, Date createAt, Date updateAt, LocationRegion locationRegion) {
        this.id = id;
        this.title = title;
        this.taxCode = taxCode;
        this.note = note;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }
}

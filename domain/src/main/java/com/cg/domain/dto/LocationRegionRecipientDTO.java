package com.cg.domain.dto;

import com.cg.domain.entity.LocationRegionRecipient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.Valid;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LocationRegionRecipientDTO {

    private Long id;
    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardId;
    private String wardName;
    private String address;
    private String fullName;
    private String phone;

    @Valid
    private OrderDTO order;


    public LocationRegionRecipient toLocationRegionRecipient() {
        return new LocationRegionRecipient()
                .setId(id)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address)
                .setFullName(fullName)
                .setPhone(phone)
                .setOrder(order.toOrder());
    }
}

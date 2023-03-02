package com.cg.domain.entity;

import com.cg.domain.dto.LocationRegionRecipientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="location_region_recipient")
public class LocationRegionRecipient extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="province_id")
    private String provinceId;

    @Column(name ="province_name")
    private String provinceName;

    @Column(name ="district_id")
    private String districtId;

    @Column(name ="district_name")
    private String districtName;

    @Column(name ="ward_id")
    private String wardId;

    @Column(name ="ward_name")
    private String wardName;

    private String address;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String phone;

    @OneToOne
    @JoinColumn(name ="order_id", nullable = false)
    private Order order;


    public LocationRegionRecipientDTO toLocationRegionRecipientDTO() {
        return new LocationRegionRecipientDTO()
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
                .setOrder(order.toOrderDTO());
    }
}

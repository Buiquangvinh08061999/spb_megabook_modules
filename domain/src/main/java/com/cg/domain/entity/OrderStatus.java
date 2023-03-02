package com.cg.domain.entity;

import com.cg.domain.dto.OrderStatusDTO;
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
@Table(name = "order_status")
public class OrderStatus extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "title_vi")
    private String titleVi;


    @OneToMany(targetEntity = Order.class, mappedBy = "orderStatus" , fetch = FetchType.EAGER)
    private Set<Order> orders;


    public OrderStatusDTO toOrderStatusDTO() {
        return new OrderStatusDTO()
                .setId(id)
                .setTitleEn(titleEn)
                .setTitleVi(titleVi);
    }
}

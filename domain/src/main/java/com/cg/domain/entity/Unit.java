package com.cg.domain.entity;

import com.cg.domain.dto.UnitDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "units")
public class Unit extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(targetEntity = Product.class, mappedBy = "unit", fetch = FetchType.EAGER)
    private Set<Product> products;

    public UnitDTO toUnitDTO(){
        return new UnitDTO()
                .setId(id)
                .setTitle(title);
    }
}

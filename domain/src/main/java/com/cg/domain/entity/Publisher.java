package com.cg.domain.entity;

import com.cg.domain.dto.PublisherDTO;
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
@Table(name = "publishers")
public class Publisher extends BaseEntities{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(targetEntity = Product.class, mappedBy = "publisher", fetch = FetchType.EAGER)
    private Set<Product> products;

    public PublisherDTO toPublisherDTO() {
        return new PublisherDTO()
                .setId(id)
                .setTitle(title);
    }

}

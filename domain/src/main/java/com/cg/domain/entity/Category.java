package com.cg.domain.entity;

import com.cg.domain.dto.CategoryDTO;
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
@Table(name = "categories")
public class Category extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;

    private String title;

    @OneToMany(targetEntity = Product.class, mappedBy = "category", fetch = FetchType.EAGER)
    private Set<Product> products;


    public CategoryDTO toCategoryDTO() {
        return new CategoryDTO()
                .setId(id)
                .setSlug(slug)
                .setTitle(title);
    }
}

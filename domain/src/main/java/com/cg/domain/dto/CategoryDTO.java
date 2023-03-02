package com.cg.domain.dto;

import com.cg.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String slug;
    private String title;


    public Category toCategory() {
        return new Category()
                .setId(id)
                .setSlug(slug)
                .setTitle(title);
    }
}

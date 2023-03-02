package com.cg.domain.dto;

import com.cg.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.Valid;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String title;
    private String sku;
    private String urlImage;
    private String describe;
    private String slug;
    private String author; // tác giả

    private BigDecimal price; // giá bán
    private BigDecimal costPrice; // giá vốn

    @Valid
    private CategoryDTO category;

    @Valid
    private UnitDTO unit;

    @Valid
    private PublisherDTO publisher; //Nhà xuất bản

    public Product toProduct() {
        return new Product()
                .setId(id)
                .setTitle(title)
                .setSku(sku)
                .setUrlImage(urlImage)
                .setDescribe(describe)
                .setSlug(slug)
                .setAuthor(author)
                .setPrice(price)
                .setCostPrice(costPrice)
                .setCategory(category.toCategory())
                .setUnit(unit.toUnit())
                .setPublisher(publisher.toPublisher());
    }


    //Tien
    public ProductDTO(Long id, String title, String slug, String sku, String urlImage, String author, String describe, Category category, Unit unit, Publisher publisher) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.sku = sku;
        this.urlImage = urlImage;
        this.author = author;
        this.describe = describe;
        this.category = category.toCategoryDTO();
        this.unit = unit.toUnitDTO();
        this.publisher = publisher.toPublisherDTO();
    }
}

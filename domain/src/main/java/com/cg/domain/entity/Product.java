package com.cg.domain.entity;

import com.cg.domain.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String sku;

    @Column(name = "url_image")
    private String urlImage;

    @Column(name = "describe_product")
    private String describe;

    private String slug;

    private String author; // tác giả

    //Bổ sung
    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price; // giá bán

    @Column(name = "cost_price", precision = 12, scale = 0, nullable = false)
    private BigDecimal costPrice; // giá vốn


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;


    @OneToOne(mappedBy = "product")
    private Item item;


    public ProductDTO toProductDTO() {
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setSku(sku)
                .setUrlImage(urlImage)
                .setDescribe(describe)
                .setSlug(slug)
                .setAuthor(author)
                .setPrice(price)
                .setCostPrice(costPrice)
                .setCategory(category.toCategoryDTO())
                .setUnit(unit.toUnitDTO())
                .setPublisher(publisher.toPublisherDTO());

    }

    public ProductDTO toProductInfoDTO() {
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setUrlImage(urlImage)
                .setSku(sku);
    }
}

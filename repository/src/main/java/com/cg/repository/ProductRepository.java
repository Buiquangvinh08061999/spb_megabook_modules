package com.cg.repository;

import com.cg.domain.dto.ProductDTO;
import com.cg.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    //Tien
    @Query("SELECT NEW com.cg.domain.dto.ProductDTO (" +
                "p.id, " +
                "p.title, " +
                "p.slug, " +
                "p.sku, " +
                "p.urlImage," +
                "p.author, " +
                "p.describe, " +
                "p.category, " +
                "p.unit, " +
                "p.publisher" +
            ") " +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "ORDER BY p.id DESC"
    )
    List<ProductDTO> findAllProductDTOSByDeletedIsFalse();


    @Query("SELECT NEW com.cg.domain.dto.ProductDTO (" +
                "p.id, " +
                "p.title, " +
                "p.slug, " +
                "p.sku, " +
                "p.urlImage," +
                "p.author, " +
                "p.describe, " +
                "p.category, " +
                "p.unit, " +
                "p.publisher" +
            ")" +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "AND CONCAT (" +
                "p.id, " +
                "p.title, " +
                "p.urlImage, " +
                "p.category.title " +
            ") LIKE ?1 "
    )
    List<ProductDTO> searchProduct(String keySearch);


    //1.Kiểm tra điều kiện khi create Product
    Boolean existsBySku(String sku);

}

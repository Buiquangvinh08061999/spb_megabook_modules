package com.cg.service.product;

import com.cg.domain.dto.ProductDTO;
import com.cg.domain.entity.Product;
import com.cg.service.IGeneralService;
import java.util.List;


public interface IProductService extends IGeneralService<Product> {


    //Tien
    List<ProductDTO> findAllProductDTOSByDeletedIsFalse();

    List<ProductDTO> searchProduct(String keySearch);

    //1.Kiểm tra điều kiện khi create Product
    Boolean existsBySku(String sku);

}

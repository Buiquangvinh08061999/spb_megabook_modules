package com.cg.service.product;

import com.cg.domain.dto.ProductDTO;
import com.cg.domain.entity.Item;
import com.cg.domain.entity.Product;
import com.cg.repository.ItemRepository;
import com.cg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product getById(Long id) {
        return null;
    }


    @Override
    public Product save(Product product) {

        Product newProduct = productRepository.save(product);

        Item item = new Item();
        item.setCostPrice(product.getCostPrice());// giá vốn
        item.setTotalCostPrice(BigDecimal.valueOf(0));
        item.setPrice(product.getPrice()); // giá bán
        item.setQuantity(0);
        item.setSold(0);
        item.setAvailable(0);
        item.setDefective(0);

        item.setProduct(newProduct);

        itemRepository.save(item); //iteam đã có đầy đủ dữ liệu, ta phải save lại

        return newProduct;
    }

    //1.Kiểm tra điều kiện không cho trùng lặp mã sku
    @Override
    public Boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }

    @Override
    public void remove(Long id) {

    }


    //Tien
    @Override
    public List<ProductDTO> findAllProductDTOSByDeletedIsFalse() {
        return productRepository.findAllProductDTOSByDeletedIsFalse();
    }

    @Override
    public List<ProductDTO> searchProduct(String keySearch) {
        return productRepository.searchProduct(keySearch);
    }

}

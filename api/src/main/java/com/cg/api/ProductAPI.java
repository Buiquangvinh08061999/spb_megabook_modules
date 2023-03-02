package com.cg.api;

import com.cg.domain.dto.*;
import com.cg.domain.entity.*;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;

import com.cg.service.publisher.IPublisherService;
import com.cg.service.unit.IUnitService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;


    @Autowired
    private IUnitService unitService;

    @Autowired
    private IPublisherService publisherService;

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<Category> categoryOptional = categoryService.findById(productDTO.getCategory().getId());
        if (!categoryOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy Id Catelory, vui lòng kiểm tra lại!");
        }


        Optional<Unit> unitOptional = unitService.findById(productDTO.getUnit().getId());
        if (!unitOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy Id Unit(đơn vị), vui lòng kiểm tra lại!");
        }

        Optional<Publisher> publisherOptional = publisherService.findById(productDTO.getPublisher().getId());
        if (!publisherOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy Id nhà xuất bản, vui lòng kiểm tra lại!");
        }


        Boolean existsBySku = productService.existsBySku(productDTO.getSku());
        if (existsBySku) {
            throw new DataInputException("Mã SKU sản phẩm đã tồn tại vui lòng kiểm tra lại");
        }

        productDTO.setId(0L);

        String slug = appUtils.replaceNonEnglishChar(productDTO.getTitle());
        slug = appUtils.removeNonAlphanumeric(slug);
        productDTO.setSlug(slug);

        try {
            Product product = productService.save(productDTO.toProduct());  //Save này là tên mình đặt, nó sẽ qua bên để xử lí tiếp phần save product, có đối tượng product rồi, thì Item mới setProduct(truyền vào đối tượng product);

            return new ResponseEntity<>(product.toProductDTO(), HttpStatus.CREATED);

        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }
    }


    //Tien
    @GetMapping
    public ResponseEntity<?> findAllProducts() {
        List<ProductDTO> productDTOList = productService.findAllProductDTOSByDeletedIsFalse();

        if (productDTOList.isEmpty()) {
            throw new DataInputException("Danh sách sản phẩm rỗng!");
        }

        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestBody SearchDTO searchDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        String keySearch = searchDTO.getKeySearch();
        keySearch = "%" + keySearch + "%";

        List<ProductDTO> productDTOList = productService.searchProduct(keySearch);

        if (productDTOList.isEmpty()) {
            throw new DataInputException("Không tìm thấy từ khóa tìm kiếm");
        }

        try {
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }
    }

}


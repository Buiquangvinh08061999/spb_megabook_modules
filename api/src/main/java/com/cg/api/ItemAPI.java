package com.cg.api;

import com.cg.domain.dto.ISumQuantityItemsDTO;
import com.cg.domain.dto.ItemDTO;
import com.cg.domain.dto.SearchDTO;
import com.cg.domain.entity.Item;
import com.cg.domain.entity.Product;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.item.IItemService;
import com.cg.service.product.IProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;


@RestController
@RequestMapping("/api/items")
public class ItemAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IItemService iItemService;

    @Autowired
    private IProductService productService;


    @GetMapping
    public ResponseEntity<?> findAllItemDTO() {
        List<ItemDTO> itemDTOList = iItemService.findAllItemDTOByDeletedIsFalse();

        if (itemDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của Item rỗng, không có dữ liệu hiển thị");
        }

        return new ResponseEntity<>(itemDTOList, HttpStatus.OK);
    }

    @GetMapping("/list/available-bigger")
    public ResponseEntity<?> findAllItemDTOBigger() {
        List<ItemDTO> itemDTOList = iItemService.findAllItemDTOByDeletedIsFalseBigger();

        if (itemDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của tồn kho lớn hơn 0 rỗng, không có dữ liệu hiển thị");
        }

        return new ResponseEntity<>(itemDTOList, HttpStatus.OK);
    }

    @GetMapping("/list/available-equal")
    public ResponseEntity<?> findAllItemDTOEqual() {
        List<ItemDTO> itemDTOList = iItemService.findAllItemDTOByDeletedIsFalseEqual();

        if (itemDTOList.isEmpty()) {
            throw new DataInputException("Danh sách của tồn kho bằng 0 rỗng, không có dữ liệu hiển thị");
        }

        return new ResponseEntity<>(itemDTOList, HttpStatus.OK);

    }


    @GetMapping("/{id}") //Hiển thị dữ liệu Item theo 1 id truyền vào
    public ResponseEntity<?> displayItemById(@PathVariable long id) {
        Optional<Item> itemOptional = iItemService.findById(id);

        if (!itemOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy ID Customer, vui lòng kiểm tra lại");
        }

        try {
            return new ResponseEntity<>(itemOptional.get().toItemDTO(), HttpStatus.OK);
        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> doUpdateItem(@RequestBody ItemDTO itemDTO, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<Item> itemOptional = iItemService.findById(itemDTO.getId());
        if (!itemOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy ID Item, vui lòng kiểm tra lại");
        }

        Optional<Product> productOptional = productService.findById(itemDTO.getId()); // Id item sẽ giống với Id product, ta dùng chung để tiết kiệm bộ nhớ
        if (!productOptional.isPresent()) {
            throw new ResourceNotFoundException("Không tìm thấy ID Product , dựa theo ID Item là cha của nó , vui lòng kiểm tra lại");
        }

        itemDTO.setProduct(productOptional.get().toProductDTO()); // Phần quan trọng nhất của update. Ta phải set lại đối tượng product tất cả các trường(có cả category và supplier) vào lại itemDTO.setProduct ( lấy từ Optional ra)

        itemDTO.setCreateAt(itemOptional.get().getCreateAt()); //Hiển thị lại ngày giờ khi update, ta lấy từ đối tượng itemOptional gán vào lại itemDTO khi update

        itemDTO.setAvailable(itemDTO.getQuantity()); //Sản phẩm còn lại trong kho là không cần nhập, nó được gán = quantity truyền vào

        BigDecimal costPrice = itemDTO.getCostPrice(); // giá nhập
        BigDecimal price = itemDTO.getPrice(); // giá bán

        if (costPrice.compareTo(price) >= 0) {
            throw new EmailExistsException("Giá nhập vượt quá giá bán, hoặc bằng giá bán, Vui lòng nhập lại giá nhập!");
        }

        BigDecimal totalCostPrice = costPrice.multiply(BigDecimal.valueOf(itemDTO.getQuantity()));

        itemDTO.setTotalCostPrice(totalCostPrice); //tổng tiền giá nhập

        try {
            Item item = iItemService.saveUpdate(itemDTO.toItem());

            return new ResponseEntity<>(item.toItemDTO(), HttpStatus.ACCEPTED);
        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại");
        }
    }


    //3.Tìm kiếm Item theo tên của sản phẩm ở bảng Product, và mã Sku theo từ khóa truyền vào
    @PostMapping("/search")
    public ResponseEntity<?> doSearchCustomerDTOByAll(@RequestBody SearchDTO searchDTO) {

        String keySearch = searchDTO.getKeySearch();
        keySearch = "%" + keySearch + "%";

        List<ItemDTO> itemDTOList = iItemService.searchItemByAll(keySearch);

        if (itemDTOList.isEmpty()) {
            throw new DataInputException("Không tìm thấy sản phẩm tìm kiếm, mã sku của sản phẩm!");
        }

        try {
            return new ResponseEntity<>(itemDTOList, HttpStatus.OK);  // Trả về danh sách vừa tìm được
        } catch (DataInputException e) {
            throw new DataInputException("Vui lòng kiểm tra lại!");
        }
    }

    @GetMapping("/order-products")
    public ResponseEntity<?> findAllItemProductOrderDTO() {
        List<ItemDTO> itemDTOList = iItemService.findAllItemInfoOrderDTO();

        if (itemDTOList.isEmpty()) {
            throw new DataInputException("Danh sách sản phẩm trống");
        }

        return new ResponseEntity<>(itemDTOList, HttpStatus.OK);
    }

    @GetMapping("/import-products")
    public ResponseEntity<?> findAllItemProductImportDTO() {
        List<ItemDTO> itemDTOList = iItemService.findAllItemInfoImportDTO();

        if (itemDTOList.isEmpty()) {
            throw new DataInputException("Danh sách sản phẩm trống");
        }

        return new ResponseEntity<>(itemDTOList, HttpStatus.OK);
    }



    //viết sp, tất cả dữ liệu quantity
    @GetMapping("/list-sum-quantity")
    public ResponseEntity<List<?>> findAllSumQuantity() {
        List<ISumQuantityItemsDTO>  sumQuantityDTOList = iItemService.findAllSumQuantity();

        return new ResponseEntity<>(sumQuantityDTOList, HttpStatus.OK);

    }
}

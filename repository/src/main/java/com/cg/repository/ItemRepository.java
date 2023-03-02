package com.cg.repository;

import com.cg.domain.dto.ISumQuantityItemsDTO;
import com.cg.domain.dto.ItemDTO;
import com.cg.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    //1.Hiển thị tất cả các trường Item, có khóa ngoại đến product, nên ta sẽ lấy được tất cả các trường ra, i.product.toProductDTO
    @Query("SELECT NEW com.cg.domain.dto.ItemDTO (" +
                "i.id, " +
                "i.costPrice, " +
                "i.totalCostPrice, " +
                "i.price, " +
                "i.quantity," +
                "i.sold, " +
                "i.available, " +
                "i.defective , " +
                "i.createAt, " +
                "i.updateAt, " +
                "i.product" +
            ") " +
            "FROM Item AS i " +
            "WHERE i.deleted = false " +
            "ORDER BY i.id DESC "
    )
    List<ItemDTO> findAllItemDTOByDeletedIsFalse(); //Hiển thị tất cả

    @Query("SELECT NEW com.cg.domain.dto.ItemDTO (" +
                "i.id, " +
                "i.costPrice, " +
                "i.totalCostPrice, " +
                "i.price, " +
                "i.quantity," +
                "i.sold, " +
                "i.available, " +
                "i.defective , " +
                "i.createAt, " +
                "i.updateAt, " +
                "i.product" +
            ") " +
            "FROM Item AS i " +
            "WHERE i.deleted = false AND i.available > 0 " +
            "ORDER BY i.id DESC "
    )
    List<ItemDTO> findAllItemDTOByDeletedIsFalseBigger(); //Hiển thị tất cả theo số lượng lớn hơn 0

    @Query("SELECT NEW com.cg.domain.dto.ItemDTO (" +
                "i.id, " +
                "i.costPrice, " +
                "i.totalCostPrice, " +
                "i.price, " +
                "i.quantity," +
                "i.sold, " +
                "i.available, " +
                "i.defective , " +
                "i.createAt, " +
                "i.updateAt, " +
                "i.product" +
            ") " +
            "FROM Item AS i " +
            "WHERE i.deleted = false AND i.available = 0 " +
            "ORDER BY i.id DESC "
    )
    List<ItemDTO> findAllItemDTOByDeletedIsFalseEqual(); //Hiển thị tất cả theo số lượng = 0


    //3.Tìm kiếm Item theo tên của sản phẩm ở bảng Product theo từ khóa truyền vào
    @Query("SELECT NEW com.cg.domain.dto.ItemDTO (" +
                "i.id, " +
                "i.costPrice, " +
                "i.totalCostPrice, " +
                "i.price, " +
                "i.quantity," +
                "i.sold, " +
                "i.available, " +
                "i.defective, " +
                "i.product " +
            ") " +
            "FROM Item AS i " +
            "WHERE i.deleted = false " +
            "AND CONCAT (" +
                "i.product.title ," +
                "i.product.sku " +
            ") LIKE ?1 " +
            "ORDER BY i.price ASC "
    )
    //Điều kiện giá từ thấp đến cao
    List<ItemDTO> searchItemByAll(String keySearch);

    @Query("SELECT NEW com.cg.domain.dto.ItemDTO(" +
                "i.id, " +
                "i.price, " +
                "i.available, " +
                "i.product" +
            ") " +
            "FROM Item AS i " +
            "WHERE i.deleted = false AND i.available > 0 " +
            "ORDER BY i.id DESC "
    )
    List<ItemDTO> findAllItemInfoOrderDTO();

    @Query("SELECT NEW com.cg.domain.dto.ItemDTO(" +
                "i.id, " +
                "i.costPrice, " +
                "i.product" +
            ") " +
            "FROM Item AS i " +
            "WHERE i.deleted = false " +
            "ORDER BY i.id DESC "
    )
    List<ItemDTO> findAllItemInfoImportDTO();


    //Vinh viết sp , tổng sl, tổng tồn kho, và tổng đã bán
    @Query(value = "CALL sp_sum_all_items();", nativeQuery = true)
    List<ISumQuantityItemsDTO> findAllSumQuantity();



}
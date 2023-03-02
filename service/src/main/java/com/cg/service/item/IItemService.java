package com.cg.service.item;

import com.cg.domain.dto.ISumQuantityItemsDTO;
import com.cg.domain.dto.ItemDTO;
import com.cg.domain.entity.Item;
import com.cg.service.IGeneralService;

import java.util.List;


public interface IItemService extends IGeneralService<Item> {

    //1.Hiển thị tất cả danh sách Item ra
    List<ItemDTO> findAllItemDTOByDeletedIsFalse();

    List<ItemDTO> findAllItemDTOByDeletedIsFalseBigger();

    List<ItemDTO> findAllItemDTOByDeletedIsFalseEqual();

    //2.Kiểm tra điều kiện khi Update
    Item saveUpdate (Item item);


    //3.Tìm kiếm Item theo tên của sản phẩm ở bảng Product theo từ khóa truyền vào
    List<ItemDTO> searchItemByAll(String keySearch);

    List<ItemDTO> findAllItemInfoOrderDTO();

    List<ItemDTO> findAllItemInfoImportDTO();


    //Vinh viết sp , tổng sl, tổng tồn kho, và tổng đã bán
    List<ISumQuantityItemsDTO> findAllSumQuantity();
}

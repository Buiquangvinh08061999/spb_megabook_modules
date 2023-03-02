package com.cg.service.item;

import com.cg.domain.dto.ISumQuantityItemsDTO;
import com.cg.domain.dto.ItemDTO;
import com.cg.domain.entity.Item;
import com.cg.repository.ItemRepository;
import com.cg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements IItemService{

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Item> findAll() {
        return null;
    }

    //1.Hiển thị tất cả các trường Item ra, có chứa thằng con product
    @Override
    public List<ItemDTO> findAllItemDTOByDeletedIsFalse() {

        return itemRepository.findAllItemDTOByDeletedIsFalse();

    }

    @Override
    public List<ItemDTO> findAllItemDTOByDeletedIsFalseBigger() {
        return itemRepository.findAllItemDTOByDeletedIsFalseBigger();
    }

    @Override
    public List<ItemDTO> findAllItemDTOByDeletedIsFalseEqual() {
        return itemRepository.findAllItemDTOByDeletedIsFalseEqual();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item getById(Long id) {
        return itemRepository.getById(id);
    }

    @Override
    public Item save(Item item) {


        return itemRepository.save(item);
    }

    //2.Tạo một phương thức lưu dữ liệu Item lại khi update , và kiểm tra dữ liệu mã sku
    @Override
    public Item saveUpdate(Item item) {

        return itemRepository.save(item);
    }


    @Override
    public void remove(Long id) {

    }

    //3.Tìm kết Item theo tên của product
    @Override
    public List<ItemDTO> searchItemByAll(String keySearch) {
        return itemRepository.searchItemByAll(keySearch);
    }

    @Override
    public List<ItemDTO> findAllItemInfoOrderDTO() {
        return itemRepository.findAllItemInfoOrderDTO();
    }

    @Override
    public List<ItemDTO> findAllItemInfoImportDTO() {
        return itemRepository.findAllItemInfoImportDTO();
    }


    @Override
    public List<ISumQuantityItemsDTO> findAllSumQuantity() {
        return itemRepository.findAllSumQuantity();
    }
}

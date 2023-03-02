package com.cg.service.importOrderItem;

import com.cg.domain.dto.ImportOrderItemDTO;
import com.cg.domain.entity.ImportOrderItem;
import com.cg.repository.ImportOrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class ImportOrderItemServiceImpl implements IImportOrderItemService{
    @Autowired
    private ImportOrderItemRepository importOrderItemRepository;

    @Override
    public List<ImportOrderItem> findAll() {
        return null;
    }

    //Tìm kiếm theo id
    @Override
    public Optional<ImportOrderItem> findById(Long id) {
        return importOrderItemRepository.findById(id);
    }

    @Override
    public ImportOrderItem getById(Long id) {
        return null;
    }

    //Lưu đơn nhập hàng
    @Override
    public ImportOrderItem save(ImportOrderItem importOrderItem) {
        return importOrderItemRepository.save(importOrderItem);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<ImportOrderItemDTO> findAllByOrderId(Long importOrderId) {
        return importOrderItemRepository.findAllByOrderId(importOrderId);
    }
}

package com.cg.service.importOrder;

import com.cg.domain.dto.ITotalAnalytics;
import com.cg.domain.dto.IImportOrderDTO;
import com.cg.domain.dto.ImportOrderDTO;
import com.cg.domain.dto.ImportOrderItemDTO;
import com.cg.domain.dto.ItemDTO;
import com.cg.domain.entity.*;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImportOrderServiceImpl implements IImportOrderService{

    @Autowired
    private ImportOderRepository importOderRepository;

    @Autowired
    private ImportOrderItemRepository importOrderItemRepository;

    @Autowired
    private ImportOrderStatusRepository importOrderStatusRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<ImportOrder> findAll() {
        return null;
    }

    //Hiển thị tất cả danh sách list ImportOrder
    @Override
    public List<ImportOrderDTO> findAllImportOrderDTO() {
        return importOderRepository.findAllImportOrderDTO();
    }

    @Override
    public List<ImportOrderDTO> findByImportOrderDTOByStatusPENDING(String pending) {
        return importOderRepository.findByImportOrderDTOByStatusPENDING(pending);
    }

    @Override
    public List<ImportOrderDTO> findByImportOrderDTOByStatusCOMPLETED(String completed) {
        return importOderRepository.findByImportOrderDTOByStatusCOMPLETED(completed);
    }

    @Override
    public List<ImportOrderDTO> findByImportOrderDTOByStatusCancel(String cancel) {
        return importOderRepository.findByImportOrderDTOByStatusCancel(cancel);
    }

    //Tìm kiếm theo id
    @Override
    public Optional<ImportOrder> findById(Long id) {
        return importOderRepository.findById(id);
    }

    @Override
    public ImportOrder getById(Long id) {
        return null;
    }

    @Override
    public ImportOrder save(ImportOrder importOrder) {

        return importOderRepository.save(importOrder);

    }

    @Override
    public void remove(Long id) {

    }
    //add ImportOrder, a Minh hướng dẫn
    @Override
    public void createImportOrder(List<ImportOrderItemDTO> importOrderItemDTOList, Supplier supplier, Warehouse warehouse, User user) {
        Optional<ImportOrderStatus> importOrderStatus = importOrderStatusRepository.findById(1L);

        ImportOrder order = new ImportOrder();
        order.setId(0L);
        order.setCreateBy(user.getFullName()); //lưu tên người tạo, từ user
        order.setSupplier(supplier);
        order.setWarehouse(warehouse);
        order.setUser(user);
        order.setImportOrderStatus(importOrderStatus.get());

        int grandQuantity = 0;
        BigDecimal grandTotal = new BigDecimal(0);

        order.setGrandQuantity(grandQuantity);
        order.setGrandTotal(grandTotal);

        ImportOrder newImportOrder = importOderRepository.save(order);


        for (ImportOrderItemDTO item : importOrderItemDTOList){
            String title = item.getProductTitle();
            String sku = item.getProductSku();
            BigDecimal price  = item.getItemPrice();

            int quantity = item.getItemQuantity();
            grandTotal = grandTotal.add(item.getTotalPrice());

            grandQuantity += item.getItemQuantity();

            ImportOrderItem importOrderItem = new ImportOrderItem();
            importOrderItem.setProductTitle(title);
            importOrderItem.setProductSku(sku);
            importOrderItem.setItemQuantity(quantity);
            importOrderItem.setItemPrice(price);
            importOrderItem.setTotalPrice(item.getTotalPrice());
            importOrderItem.setImportOrder(newImportOrder);
            importOrderItem.setItem(item.getItem().toItem()); // lưu lại đối tượng item


            importOrderItemRepository.save(importOrderItem);


        }
        newImportOrder.setGrandQuantity(grandQuantity); //lưu lại số lượng tổng SL
        newImportOrder.setGrandTotal(grandTotal); // sau mỗi lần lặp, lưu lại tổng tiền vào ImportOrderId lại

        importOderRepository.save(newImportOrder); //lưu lại

    }


    @Override
    public ImportOrder doChangeStatusOrder(ImportOrder importOrder, ImportOrderStatus importOrderStatus) {

        importOrder.setImportOrderStatus(importOrderStatus); //Lưu trạng thái được truyền vào

        ImportOrder newImportOrder = importOderRepository.save(importOrder); // lưu lại trạng thái

        if(importOrderStatus.getId() == 2){

            List<ImportOrderItemDTO> importOrderItemDTOList = importOrderItemRepository.findAllByOrderId(importOrder.getId()); // duyệt qua tất cả order theo order Id truyền vào

            for (ImportOrderItemDTO importOrderItemDTO : importOrderItemDTOList){

                ItemDTO itemDTO = importOrderItemDTO.getItem(); //lấy được item id theo orderItem ra

                itemDTO.setQuantity(itemDTO.getQuantity() + importOrderItemDTO.getItemQuantity()); //SL đã mua(mặc định 10 không thay đổi)
                itemDTO.setAvailable(itemDTO.getAvailable() + importOrderItemDTO.getItemQuantity()); //SL còn trong kho(có thay đổi nếu bán được, cộng SL khi ta nhập vào)


                itemDTO.setTotalCostPrice(itemDTO.getTotalCostPrice().add(importOrderItemDTO.getTotalPrice())); //Tổng tiền ở item theo id, + với  tổng số tiền mình vừa nhập hàng, ở bảng importOrder

                itemRepository.save(itemDTO.toItem()); // lưu lại item + vào số lượng, khi nhập hàng chuyển qua trạng thái hoàn thành

            }
        }



        return newImportOrder;
    }

    @Override
    public List<ITotalAnalytics> importTotalToday() {
        return importOderRepository.importTotalToday();
    }

    @Override
    public List<ITotalAnalytics> importTotalWeek() {
        return importOderRepository.importTotalWeek();
    }

    @Override
    public List<ITotalAnalytics> importTotalMonth() {
        return importOderRepository.importTotalMonth();
    }

    //Vinh viết sp để lấy Sl chờ nhập, và tổng đơn
    @Override
    public List<IImportOrderDTO> findAllImportOrderDTOCountAndSumPending() {
        return importOderRepository.findAllImportOrderDTOCountAndSumPending();
    }

    @Override
    public List<IImportOrderDTO> findAllImportOrderDTOCountAndSumCompleted() {
        return importOderRepository.findAllImportOrderDTOCountAndSumCompleted();
    }
}

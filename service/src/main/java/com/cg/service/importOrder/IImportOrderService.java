package com.cg.service.importOrder;

import com.cg.domain.dto.ITotalAnalytics;
import com.cg.domain.dto.IImportOrderDTO;
import com.cg.domain.dto.ImportOrderDTO;
import com.cg.domain.dto.ImportOrderItemDTO;
import com.cg.domain.entity.*;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IImportOrderService extends IGeneralService<ImportOrder> {

    //add ImportOrder, a Minh hướng dẫn
    void createImportOrder(List<ImportOrderItemDTO> orderItemDTOList, Supplier supplier, Warehouse warehouse, User user);

    //Hiển thị tất cả danh sách list ImportOrder
    List<ImportOrderDTO> findAllImportOrderDTO();

    //Hiển thị danh sách list ImportOrder theo trạng thái chờ nhập(pending)
    List<ImportOrderDTO> findByImportOrderDTOByStatusPENDING(String pending);

    //Hiển thị danh sách list ImportOrder theo trạng thái hoàn thành(completed)
    List<ImportOrderDTO> findByImportOrderDTOByStatusCOMPLETED(String completed);

    //Hiển thị danh sách list ImportOrder theo trạng thái bị hủy(cancel)
    List<ImportOrderDTO> findByImportOrderDTOByStatusCancel(String cancel);


    //4.Chuyển trạng thái order, truyền vào orderId và orderStatusId lấy từ value option;(api/orders/update-status/{orderId})
    ImportOrder doChangeStatusOrder(ImportOrder importOrder, ImportOrderStatus importOrderStatus);


    List<ITotalAnalytics> importTotalToday();

    List<ITotalAnalytics> importTotalWeek();

    List<ITotalAnalytics> importTotalMonth();

    //Vinh viết sp để lấy Sl chờ nhập, và tổng đơn
    List<IImportOrderDTO> findAllImportOrderDTOCountAndSumPending();

    List<IImportOrderDTO> findAllImportOrderDTOCountAndSumCompleted();
}

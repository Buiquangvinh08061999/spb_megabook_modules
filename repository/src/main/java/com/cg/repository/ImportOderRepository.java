package com.cg.repository;

import com.cg.domain.dto.ITotalAnalytics;
import com.cg.domain.dto.IImportOrderDTO;
import com.cg.domain.dto.ImportOrderDTO;
import com.cg.domain.entity.ImportOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImportOderRepository extends JpaRepository<ImportOrder, Long> {
    //1.Phần add ImportOrder:Hiển thị tất cả phần ImportOrder dựa theo user_id và trạng thái, Nếu khác trạng thái sẽ tạo đơn hàng mới
    @Query("SELECT NEW com.cg.domain.dto.ImportOrderDTO (" +
                "o.id, " +
                "o.grandQuantity, " +
                "o.note, " +
                "o.grandTotal, " +
                "o.createAt, " +
                "o.createBy, " +
                "o.updateAt, " +
                "o.updateBy, " +
                "o.user, " +
                "o.supplier, " +
                "o.warehouse, " +
                "o.importOrderStatus" +
            ") " +
            "FROM ImportOrder AS o " +
            "WHERE o.user.id = ?1 " +
            "AND o.importOrderStatus.titleEn = ?2 " +
            "ORDER BY o.id DESC"
    )
    Optional<ImportOrder> findImportOrderByUserIdAndTitleEn(String id, String titleEn);


    //Hiển thị tất cả danh sách list ImportOrder
    @Query("SELECT NEW com.cg.domain.dto.ImportOrderDTO (" +
                "o.id, " +
                "o.grandQuantity, " +
                "o.note, " +
                "o.grandTotal, " +
                "o.createAt, " +
                "o.createBy, " +
                "o.updateAt, " +
                "o.updateBy, " +
                "o.user, " +
                "o.supplier, " +
                "o.warehouse, " +
                "o.importOrderStatus" +
            ") " +
            "FROM ImportOrder AS o "
    )
    List<ImportOrderDTO> findAllImportOrderDTO();

    //Hiển thị danh sách list ImportOrder theo trạng thái chờ nhập(pending)
    @Query("SELECT NEW com.cg.domain.dto.ImportOrderDTO (" +
                "o.id, " +
                "o.grandQuantity, " +
                "o.note, " +
                "o.grandTotal, " +
                "o.createAt, " +
                "o.createBy, " +
                "o.updateAt, " +
                "o.updateBy, " +
                "o.user, " +
                "o.supplier, " +
                "o.warehouse, " +
                "o.importOrderStatus" +
            ") " +
            "FROM ImportOrder AS o " +
            "WHERE o.importOrderStatus.titleEn = ?1"
    )
    List<ImportOrderDTO> findByImportOrderDTOByStatusPENDING(String pending);


    //Hiển thị danh sách list ImportOrder theo trạng thái hoàn thành(completed)
    @Query("SELECT NEW com.cg.domain.dto.ImportOrderDTO (" +
                "o.id, " +
                "o.grandQuantity, " +
                "o.note, " +
                "o.grandTotal, " +
                "o.createAt, " +
                "o.createBy, " +
                "o.updateAt, " +
                "o.updateBy, " +
                "o.user, " +
                "o.supplier, " +
                "o.warehouse, " +
                "o.importOrderStatus" +
            ") " +
            "FROM ImportOrder AS o " +
            "WHERE o.importOrderStatus.titleEn = ?1"
    )
    List<ImportOrderDTO> findByImportOrderDTOByStatusCOMPLETED(String completed);

    //Hiển thị danh sách list ImportOrder theo trạng thái hoàn thành(cancel)
    @Query("SELECT NEW com.cg.domain.dto.ImportOrderDTO (" +
            "o.id, " +
            "o.grandQuantity, " +
            "o.note, " +
            "o.grandTotal, " +
            "o.createAt, " +
            "o.createBy, " +
            "o.updateAt, " +
            "o.updateBy, " +
            "o.user, " +
            "o.supplier, " +
            "o.warehouse, " +
            "o.importOrderStatus" +
            ") " +
            "FROM ImportOrder AS o " +
            "WHERE o.importOrderStatus.titleEn = ?1"
    )
    List<ImportOrderDTO> findByImportOrderDTOByStatusCancel(String cancel);


    @Query(value = "CALL sp_import_total_today();", nativeQuery = true)
    List<ITotalAnalytics> importTotalToday();

    @Query(value = "CALL sp_import_total_week();", nativeQuery = true)
    List<ITotalAnalytics> importTotalWeek();

    @Query(value = "CALL sp_import_total_month();", nativeQuery = true)
    List<ITotalAnalytics> importTotalMonth();

    //Vinh viết sp để lấy Sl chờ nhập, và tổng đơn- pending
    @Query(value = "CALL sp_count_and_sum_import_order_pending();", nativeQuery = true)
    List<IImportOrderDTO> findAllImportOrderDTOCountAndSumPending();


    //Vinh viết sp để lấy Sl chờ nhập, và tổng đơn, completed
    @Query(value = "CALL sp_count_and_sum_import_order_completed();", nativeQuery = true)
    List<IImportOrderDTO> findAllImportOrderDTOCountAndSumCompleted();


}

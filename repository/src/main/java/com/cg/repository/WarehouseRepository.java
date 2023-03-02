package com.cg.repository;

import com.cg.domain.dto.SupplierDTO;
import com.cg.domain.dto.WarehouseDTO;
import com.cg.domain.entity.Customer;
import com.cg.domain.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    //1.Hiển thị tất cả danh sách kho hàng
    @Query("SELECT NEW com.cg.domain.dto.WarehouseDTO(" +
                "w.id, " +
                "w.title, " +
                "w.email, " +
                "w.phone, " +
                "w.createAt, " +
                "w.updateAt, " +
                "w.locationRegion" +
            ") " +
            "FROM Warehouse AS w " +
            "WHERE w.deleted = false "
    )
    List<WarehouseDTO> findAllWarehouseDTOByDeletedIsFalse();

    //2.Kiểm tra điều kiện create

    Boolean existsByTitle(String title);

}

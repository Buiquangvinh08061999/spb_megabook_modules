package com.cg.repository;

import com.cg.domain.dto.CustomerDTO;
import com.cg.domain.dto.SupplierDTO;
import com.cg.domain.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
        //1.Hiển thị tất cả danh sách nhà cung cấp
        @Query("SELECT NEW com.cg.domain.dto.SupplierDTO(" +
                    "s.id, " +
                    "s.title, " +
                    "s.taxCode, " +
                    "s.note, " +
                    "s.fullName, " +
                    "s.email, " +
                    "s.phone, " +
                    "s.createAt, " +
                    "s.updateAt, " +
                    "s.locationRegion" +
                ") " +
                "FROM Supplier AS s " +
                "WHERE s.deleted = false " +
                "ORDER BY s.id DESC"
        )
        List<SupplierDTO> findAllSupplierDTOByDeletedIsFalse();

        //2.Kiểm tra điều kiện create

        Boolean existsByTitle(String title);
}

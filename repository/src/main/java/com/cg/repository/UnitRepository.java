package com.cg.repository;

import com.cg.domain.dto.UnitDTO;
import com.cg.domain.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    //1.Hiển thị tất cả danh sách Unit ra
    @Query("SELECT NEW com.cg.domain.dto.UnitDTO (" +
                "u.id, " +
                "u.title " +
            ") " +
            "FROM Unit AS u "
    )
    List<UnitDTO> findAllUnitDTO();


}

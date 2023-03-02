package com.cg.repository;

import com.cg.domain.dto.LocationRegionDTO;
import com.cg.domain.entity.LocationRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LocationRegionRepository extends JpaRepository<LocationRegion, Long> {

    @Query("SELECT NEW com.cg.domain.dto.LocationRegionDTO (" +
                "l.id, " +
                "l.provinceId, " +
                "l.provinceName, " +
                "l.districtId, " +
                "l.districtName, " +
                "l.wardId, " +
                "l.wardName, " +
                "l.address " +
            ")" +
            "FROM LocationRegion AS l " +
            "WHERE l.id = ?1 "
    )
    Optional<LocationRegionDTO> getLocationRegionDTOById(Long id);

}

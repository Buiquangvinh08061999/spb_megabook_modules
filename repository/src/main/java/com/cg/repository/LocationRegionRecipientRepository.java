package com.cg.repository;

import com.cg.domain.entity.LocationRegionRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRegionRecipientRepository extends JpaRepository<LocationRegionRecipient, Long> {
}

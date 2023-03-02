package com.cg.service.locationRegion;

import com.cg.domain.dto.LocationRegionDTO;
import com.cg.domain.entity.LocationRegion;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;


public interface ILocationRegionService extends IGeneralService<LocationRegion> {

    Optional<LocationRegionDTO> getLocationRegionDTOById(Long id);
}

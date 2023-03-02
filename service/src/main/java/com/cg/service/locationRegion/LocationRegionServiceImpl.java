package com.cg.service.locationRegion;

import com.cg.domain.dto.LocationRegionDTO;
import com.cg.domain.entity.LocationRegion;
import com.cg.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocationRegionServiceImpl implements ILocationRegionService {

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Override
    public List<LocationRegion> findAll() {
        return null;
    }

    @Override
    public Optional<LocationRegion> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public LocationRegion getById(Long id) {
        return null;
    }

    @Override
    public LocationRegion save(LocationRegion locationRegion) {
        return locationRegionRepository.save(locationRegion);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<LocationRegionDTO> getLocationRegionDTOById(Long id) {
        return locationRegionRepository.getLocationRegionDTOById(id);
    }
}

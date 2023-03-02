package com.cg.service.locationRegionRecipient;

import com.cg.domain.entity.LocationRegionRecipient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocationRegionRecipientServiceImpl implements ILocationRegionRecipientService {

    @Override
    public List<LocationRegionRecipient> findAll() {
        return null;
    }

    @Override
    public Optional<LocationRegionRecipient> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public LocationRegionRecipient getById(Long id) {
        return null;
    }

    @Override
    public LocationRegionRecipient save(LocationRegionRecipient locationRegionRecipient) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}

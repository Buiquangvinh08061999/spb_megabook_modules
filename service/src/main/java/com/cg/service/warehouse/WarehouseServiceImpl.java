package com.cg.service.warehouse;

import com.cg.domain.dto.WarehouseDTO;
import com.cg.domain.entity.LocationRegion;
import com.cg.domain.entity.Warehouse;
import com.cg.repository.LocationRegionRepository;
import com.cg.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WarehouseServiceImpl implements IWarehouseService{

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;


    @Override
    public List<Warehouse> findAll() {
        return null;
    }

    //1.Hiển thị tất cả danh sách kho hàng
    @Override
    public List<WarehouseDTO> findAllWarehouseDTOByDeletedIsFalse() {
        return warehouseRepository.findAllWarehouseDTOByDeletedIsFalse();
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public Warehouse getById(Long id) {
        return null;
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        LocationRegion locationRegion = locationRegionRepository.save(warehouse.getLocationRegion());

        warehouse.setLocationRegion(locationRegion);

        return warehouseRepository.save(warehouse);
    }

    @Override
    public Boolean existsByTitle(String title) {
        return warehouseRepository.existsByTitle(title);
    }

    @Override
    public void remove(Long id) {

    }
}

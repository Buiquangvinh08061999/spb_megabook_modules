package com.cg.service.supplier;

import com.cg.domain.dto.SupplierDTO;
import com.cg.domain.entity.LocationRegion;
import com.cg.domain.entity.Supplier;
import com.cg.domain.entity.User;
import com.cg.repository.LocationRegionRepository;
import com.cg.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class SupplierServiceImpl implements ISupplierService{

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }
    //Hiển thị tất cả nhà cung cấp
    @Override
    public List<SupplierDTO> findAllSupplierDTOByDeletedIsFalse() {
        return supplierRepository.findAllSupplierDTOByDeletedIsFalse();
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }


    @Override
    public Supplier getById(Long id) {
        return null;
    }

    @Override
    public Supplier save(Supplier supplier) {
        LocationRegion locationRegion = locationRegionRepository.save(supplier.getLocationRegion()); //Lưu tk location ở bảng cúa nó, rồi gán id của địa chỉ này vào thằng nhà cung cấp

        supplier.setLocationRegion(locationRegion);

        return supplierRepository.save(supplier);
    }

    @Override
    public Boolean existsByTitle(String title) {
        return supplierRepository.existsByTitle(title);
    }

    @Override
    public void remove(Long id) {
        supplierRepository.deleteById(id);
    }
}

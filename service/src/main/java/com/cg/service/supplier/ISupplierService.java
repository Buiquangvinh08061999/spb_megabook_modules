package com.cg.service.supplier;

import com.cg.domain.dto.SupplierDTO;
import com.cg.domain.entity.Supplier;
import com.cg.domain.entity.User;
import com.cg.service.IGeneralService;
import java.util.List;
import java.util.Optional;


public interface ISupplierService extends IGeneralService<Supplier> {
    List<SupplierDTO> findAllSupplierDTOByDeletedIsFalse();

    Boolean existsByTitle(String title);



}

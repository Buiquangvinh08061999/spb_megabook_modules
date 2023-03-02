package com.cg.service.importOrderStatus;

import com.cg.domain.dto.ImportOrderStatusDTO;
import com.cg.domain.entity.ImportOrderStatus;
import com.cg.repository.ImportOrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImportOrderStatusServiceImpl implements IImportOrderStatusService{

    @Autowired
    private ImportOrderStatusRepository importOrderStatusRepository;


    @Override
    public List<ImportOrderStatus> findAll() {
        return null;
    }

    //Tìm trạng thái status theo id truyền vào
    @Override
    public Optional<ImportOrderStatus> findById(Long id) {
        return importOrderStatusRepository.findById(id);
    }

    @Override
    public ImportOrderStatus getById(Long id) {
        return null;
    }

    @Override
    public ImportOrderStatus save(ImportOrderStatus importOrderStatus) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<ImportOrderStatusDTO> findAllImportOrderStatusDTO() {
        return importOrderStatusRepository.findAllImportOrderStatusDTO();
    }


    @Override
    public List<ImportOrderStatusDTO> findByTitleEnCOMPLETEDAndCANCEL(String pending) {
        return importOrderStatusRepository.findByTitleEnCOMPLETEDAndCANCEL(pending);
    }


}

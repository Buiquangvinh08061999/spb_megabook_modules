package com.cg.service.unit;

import com.cg.domain.dto.UnitDTO;
import com.cg.domain.entity.Unit;
import com.cg.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UnitServiceImpl implements IUnitService{


    @Autowired
    private UnitRepository unitRepository;

    @Override
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Override
    public List<UnitDTO> findAllUnitDTO() {
        return unitRepository.findAllUnitDTO();
    }

    @Override
    public Optional<Unit> findById(Long id) {
        return unitRepository.findById(id);
    }

    @Override
    public Unit getById(Long id) {
        return null;
    }

    @Override
    public Unit save(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public void remove(Long id) {

    }
}

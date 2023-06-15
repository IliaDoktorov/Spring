package com.HRM.services;

import com.HRM.dto.UnitDTO;
import com.HRM.models.Person;
import com.HRM.models.Unit;
import com.HRM.repositories.UnitsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UnitsService {
    @Autowired
    private UnitsRepository unitsRepository;

    public void add(Unit unit){

        if(unit.getParentUnit() != null && !unitsRepository.findByName(unit.getParentUnit()).isPresent())
            throw new EntityNotFoundException();

        unitsRepository.save(unit);

    }

    public UnitDTO get(String name){
        Optional<Unit> unitOptional = unitsRepository.findByName(name);

        if(unitOptional.isEmpty())
            throw new EntityNotFoundException();

        UnitDTO unitDTO = new UnitDTO();
        Unit unit = unitOptional.get();

        unitDTO.setName(unit.getName());

        UnitDTO temp = unitDTO;

        while (unit.getParentUnit() != null){
            Optional<Unit> unitFromDB = unitsRepository.findByName(unit.getParentUnit());

            if(unitFromDB.isPresent()){
                temp.setParentUnit(new UnitDTO(unitFromDB.get().getName(), null));
                temp = temp.getParentUnit();
                unit = unitFromDB.get();
            }
        }
        return unitDTO;
    }

    public Set<Person> getPeoplePerUnit(String name) {
        Optional<Unit> unitOptional = unitsRepository.findByName(name);

        if(unitOptional.isEmpty())
            throw new EntityNotFoundException("Unit with name (" + name + ") not found");

        return unitOptional.get().getPeople();
    }
}

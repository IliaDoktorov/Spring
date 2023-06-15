package com.HRM.services;

import com.HRM.models.Position;
import com.HRM.repositories.PeopleRepository;
import com.HRM.repositories.PositionRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PositionsService {
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PeopleRepository peopleRepository;

    public Position get(int id){
        Optional<Position> position = positionRepository.findById(id);

        if(position.isEmpty())
            throw new EntityNotFoundException("Position with id (" + id + ") not found");

        return position.get();
    }

    public void add(Position position){
        if(positionRepository.findByName(position.getName()).isPresent())
            throw new EntityExistsException("Position with name (" + position.getName() + ") already exist");

        positionRepository.save(position);
    }

    public void remove(int id){
        if(positionRepository.findById(id).isEmpty())
            throw new EntityNotFoundException("Position with id (" + id + ") not found");

        positionRepository.deleteById(id);
    }
}

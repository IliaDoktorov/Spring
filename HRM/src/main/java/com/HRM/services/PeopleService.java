package com.HRM.services;

import com.HRM.dto.PersonDTO;
import com.HRM.models.Person;
import com.HRM.models.Position;
import com.HRM.models.Unit;
import com.HRM.repositories.PeopleRepository;
import com.HRM.repositories.PositionRepository;
import com.HRM.repositories.UnitsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PeopleService {
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UnitsRepository unitsRepository;

    public void add(PersonDTO personDTO){
        List<Unit> units = getUnits(personDTO);
        Person person = convertToFullPerson(personDTO);

        units.forEach(unit -> {
            System.out.println("BEFORE");
            unit.getPeople().add(person);
            System.out.println("AFTER");
        });

        System.out.println("BEFORE set units");
        person.setUnits(new HashSet<>(units));
        System.out.println("AFTER set units");

        System.out.println("BEFORE save");
        peopleRepository.save(person);
        System.out.println("BEFORE save");
    }

    private Person convertToFullPerson(PersonDTO personDTO){
        Optional<Position> position = positionRepository.findByName(personDTO.getPosition());
        if (position.isEmpty())
            throw new EntityNotFoundException();

        Person person = new Person();

        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setEmail(personDTO.getEmail());
        person.setActive(personDTO.isActive());
        person.setPosition(position.get());

        return person;
    }

    private List<Unit> getUnits(PersonDTO personDTO) {
        List<Unit> units = new ArrayList<>();
        for(String unitName: personDTO.getUnits()){
            Optional<Unit> unit = unitsRepository.findByName(unitName);
            if(unit.isEmpty())
                throw new EntityNotFoundException();

            units.add(unit.get());
        }
        return units;
    }
}

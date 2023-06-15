package com.HRM.services;

import com.HRM.dto.PersonDTO;
import com.HRM.models.Person;
import com.HRM.models.Position;
import com.HRM.models.Unit;
import com.HRM.repositories.PeopleRepository;
import com.HRM.repositories.PositionRepository;
import com.HRM.repositories.UnitsRepository;
import jakarta.persistence.EntityExistsException;
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

        if(peopleRepository.findByemail(personDTO.getEmail()).isPresent())
            throw new EntityExistsException("Person with email (" + personDTO.getEmail() + ") already exist");

        // bidirectional relationship
        units.forEach(unit -> {
            unit.getPeople().add(person);
        });

        person.setUnits(new HashSet<>(units));

        peopleRepository.save(person);
    }

    private Person convertToFullPerson(PersonDTO personDTO){
        Person person = new Person();

        if(personDTO.getPosition() != null) {
            Optional<Position> position = positionRepository.findByName(personDTO.getPosition());
            if (position.isEmpty())
                throw new EntityNotFoundException("Position with name (" + personDTO.getPosition() + ") not found");

            person.setPosition(position.get());
        }

        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setEmail(personDTO.getEmail());
        person.setActive(personDTO.isActive());


        return person;
    }

    private List<Unit> getUnits(PersonDTO personDTO) {
        List<Unit> units = new ArrayList<>();
        if(personDTO.getUnits() != null) {
            for (String unitName : personDTO.getUnits()) {
                Optional<Unit> unit = unitsRepository.findByName(unitName);
                if (unit.isEmpty())
                    throw new EntityNotFoundException("Unit with name (" + unitName + ") not found");

                units.add(unit.get());
            }
        }
        return units;
    }
}

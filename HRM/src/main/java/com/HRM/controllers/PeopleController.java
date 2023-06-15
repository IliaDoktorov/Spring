package com.HRM.controllers;

import com.HRM.dto.PersonDTO;
import com.HRM.models.Person;
import com.HRM.services.PeopleService;
import com.HRM.utils.ResponseEntry;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    private PeopleService peopleService;

    // get person

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody PersonDTO personDTO){
        peopleService.add(personDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // update person

    // fire person

    // assign unit

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ResponseEntry> handleNotFoundException(EntityNotFoundException ex){
        return new ResponseEntity<>(new ResponseEntry(ex.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    private ResponseEntity<ResponseEntry> handleAlreadyExistException(EntityExistsException ex){
        return new ResponseEntity<>(new ResponseEntry(ex.getMessage(), System.currentTimeMillis()), HttpStatus.CONFLICT);
    }
}

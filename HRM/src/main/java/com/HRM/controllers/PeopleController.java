package com.HRM.controllers;

import com.HRM.dto.PersonDTO;
import com.HRM.services.PeopleService;
import com.HRM.utils.PersonValidationException;
import com.HRM.utils.PersonValidator;
import com.HRM.utils.ResponseEntry;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private PersonValidator personValidator;

    @GetMapping("/{id}")
    public PersonDTO get(@PathVariable("id") int id){
        return peopleService.get(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid PersonDTO personDTO,
                                          BindingResult bindingResult){

        personValidator.validate(personDTO, bindingResult);

        peopleService.add(personDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid PersonDTO personDTO,
                                             BindingResult bindingResult,
                                             @PathVariable("id") int id){

        personValidator.validate(personDTO, bindingResult);

        peopleService.update(id, personDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id){
        peopleService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

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

    @ExceptionHandler(PersonValidationException.class)
    private ResponseEntity<ResponseEntry> handlePersonValidationException(PersonValidationException ex){
        return new ResponseEntity<>(new ResponseEntry(ex.getMessage(), System.currentTimeMillis()), ex.getHttpStatus());
    }
}

package com.HRM.controllers;

import com.HRM.dto.PersonDTO;
import com.HRM.models.Person;
import com.HRM.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

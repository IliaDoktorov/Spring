package com.HRM.controllers;

import com.HRM.dto.UnitDTO;
import com.HRM.models.Person;
import com.HRM.models.Unit;
import com.HRM.services.UnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/units")
public class UnitsController {
    @Autowired
    private UnitsService unitsService;

    // get unit

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody Unit unit){
        unitsService.add(unit);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public UnitDTO get(@PathVariable("name") String name){
        return unitsService.get(name);
    }

    @GetMapping("/{name}/people")
    public Set<Person> getPeoplePerUnit(@PathVariable("name") String name){
        return unitsService.getPeoplePerUnit(name);
    }

    // update unit

    @ExceptionHandler
    private ResponseEntity<HttpStatus> handleException(Exception ex){
        System.out.println("NOT FOUND");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

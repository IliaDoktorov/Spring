package com.HRM.controllers;

import com.HRM.models.Position;
import com.HRM.services.PositionsService;
import com.HRM.utils.ResponseEntry;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
public class PositionsController {
    @Autowired
    private PositionsService positionsService;

    @GetMapping("/{id}")
    public Position get(@PathVariable("id") int id){
        return positionsService.get(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody Position position){
        positionsService.add(position);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<HttpStatus> remove(@PathVariable("id") int id){
        positionsService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // get all people with specified position

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ResponseEntry> handleNotFoundException(EntityNotFoundException ex){
        return new ResponseEntity<>(new ResponseEntry(ex.getMessage(), System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    private ResponseEntity<ResponseEntry> handleAlreadyExistException(EntityExistsException ex){
        return new ResponseEntity<>(new ResponseEntry(ex.getMessage(), System.currentTimeMillis()), HttpStatus.CONFLICT);
    }
}

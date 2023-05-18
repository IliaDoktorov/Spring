package org.library.controllers;

import jakarta.validation.Valid;
import org.library.dao.PersonDAO;
import org.library.models.Person;
import org.library.services.PeopleService;
import org.library.util.PersonSearchValidator;
import org.library.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
//    private final PersonDAO personDAO;

    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final PersonSearchValidator personSearchValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PeopleService peopleService, PersonValidator personValidator, PersonSearchValidator personSearchValidator) {
//        this.personDAO = personDAO;
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.personSearchValidator = personSearchValidator;
    }

    @GetMapping()
    public String index(Model model){
//        model.addAttribute("people", personDAO.index());
        model.addAttribute("people", peopleService.findAll(true));
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPersonPage(@PathVariable("id") int id,
                       Model model){
//        Person person = personDAO.getPersonById(id);
        Person person = peopleService.findById(id);
        model.addAttribute("person", person);
        model.addAttribute("books", person.getBooks());

        return "people/show";
    }

    @GetMapping("/new")
    public String newPersonPage(@ModelAttribute("person")Person person){
        return "people/new";
    }

    @PostMapping("/create")
    public String createNewPerson(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/new";
        }

//        personDAO.createNewPerson(person);
        peopleService.save(person);
        return "redirect:/people";
    }

    @PostMapping("/{id}/edit")
    public String editPersonPage(@PathVariable("id") int id, Model model){
//        model.addAttribute("person", personDAO.getPersonById(id));
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    @PostMapping("{id}/update")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id){
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/edit";
        }

//        personDAO.updatePerson(person, id);
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @PostMapping("{id}/delete")
    public String deletePerson(@PathVariable("id") int id){
//        personDAO.deletePersonById(id);
        peopleService.deleteById(id);
        return "redirect:/people";
    }

    @GetMapping("/search")
    public String searchPage(@ModelAttribute("person") Person person){
        return "people/search";
    }

    @GetMapping("/search-person")
    public String lookForPerson(@ModelAttribute("person") Person person,
                                      BindingResult bindingResult,
                                      Model model){

        personSearchValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "people/search";

        model.addAttribute("people", peopleService.findPerson(person));
        return "people/search";
    }
}

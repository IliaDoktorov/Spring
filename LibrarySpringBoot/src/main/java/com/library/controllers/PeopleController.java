package com.library.controllers;

import com.library.models.Person;
import com.library.services.PeopleService;
import com.library.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPersonPage(@PathVariable("id") int id,
                       Model model){
        Person person = peopleService.findById(id);
        model.addAttribute("person", person);
        model.addAttribute("books", person.getBooks());
        model.addAttribute("passport", person.getPassport());

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

        peopleService.save(person);
        return "redirect:/people";
    }

    @PostMapping("/{id}/edit")
    public String editPersonPage(@PathVariable("id") int id, Model model){
        Person personToEdit = peopleService.findById(id);
        model.addAttribute("person", personToEdit);
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

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @PostMapping("{id}/delete")
    public String deletePerson(@PathVariable("id") int id){
        peopleService.deleteById(id);
        return "redirect:/people";
    }

    @GetMapping("/search")
    public String searchPage(){
        return "people/search";
    }

    @GetMapping("/search-person")
    public String lookForPerson(@RequestParam("query")String query,
                                      Model model){
        if(query == null || query.isEmpty())
            return "books/search";

        model.addAttribute("people", peopleService.findByQuery(query));
        return "people/search";
    }
}

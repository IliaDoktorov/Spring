package com.library.controllers;

import com.library.models.Book;
import com.library.models.Person;
import com.library.services.BookService;
import com.library.services.PeopleService;
import com.library.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("/books")
//@Validated
public class BooksController {

    private final BookValidator bookValidator;
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookValidator bookValidator, BookService bookService, PeopleService peopleService) {
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                        @RequestParam(value = "items_per_page", required = false, defaultValue = "10") int itemsPerPage,
                        @RequestParam(value = "sort_by_year", required = false, defaultValue = "false") boolean sortByYear,
                        Model model){
        model.addAttribute("books", bookService.findAll(page, itemsPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showBookPage(@PathVariable("id") int bookId, Model model,
                               @ModelAttribute("person") Person person){
        Book book = bookService.findById(bookId);
        Person owner = book.getOwner();

        model.addAttribute("owner", owner);
        model.addAttribute("book", book);

        // if book is vacant, get and populate person list to display in drop-don list
        if(owner == null){
            model.addAttribute("people", peopleService.findAll());
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBookPage(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping("/create")
    public String createNewBook(@ModelAttribute("book") @Valid Book book,
                                BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()){
            return "books/new";
        }

        bookService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") int id,
                               Model model){
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PostMapping("/{id}/update")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @PathVariable("id") int id){
        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors()){
            return "books/edit";
        }

        bookService.update(id, book);

        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id){
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/vacate")
    public String vacate(@PathVariable("id") int bookId,
                         Model model,
                         @ModelAttribute("person") Person person){
        bookService.vacate(bookId);
        return showBookPage(bookId, model, person);
    }

    @PostMapping("/{id}/reserve")
    public String reserveBook(@PathVariable("id") int bookId, Model model,
                              @ModelAttribute("person") Person person){
        bookService.reserveBook(bookId,person);
        return showBookPage(bookId, model, person);
    }

    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }

    @GetMapping("/search-book")
    public String lookForBook(@RequestParam("query") String query,
                              Model model){
        if(query == null || query.isEmpty())
            return "books/search";

        model.addAttribute("books", bookService.findByQuery(query));
        return "books/search";
    }

}

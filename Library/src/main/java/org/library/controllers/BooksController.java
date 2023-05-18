package org.library.controllers;

import jakarta.validation.Valid;
import org.library.dao.BookDAO;
import org.library.dao.PersonDAO;
import org.library.models.Book;
import org.library.models.Person;
import org.library.services.BookService;
import org.library.services.PeopleService;
import org.library.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("/books")
public class BooksController {

    private final BookValidator bookValidator;
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator, BookService bookService, PeopleService peopleService) {
//        this.bookDAO = bookDAO;
//        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
//        model.addAttribute("books", bookDAO.index());
        model.addAttribute("books", bookService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showBookPage(@PathVariable("id") int bookId, Model model,
                               @ModelAttribute("person") Person person){
//        Book book = bookDAO.getBookById(bookId);
//        Person personByBook = personDAO.getPersonById(book.getOwner().getId());
        Book book = bookService.findById(bookId);
        Person personByBook = book.getOwner();

        model.addAttribute("personByBook", personByBook);
        model.addAttribute("book", book);

        // if book is vacant, get and populate person list to display in drop-don list
        if(personByBook == null){
            model.addAttribute("people", peopleService.findAll(false));
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

//        bookDAO.createNewBook(book);
        bookService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") int id,
                               Model model){
//        model.addAttribute("book", bookDAO.getBookById(id));
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

//        bookDAO.updateBook(book, id);
        bookService.update(id, book);

        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id){
//        bookDAO.deleteBookById(id);
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/vacate")
    public String vacate(@PathVariable("id") int bookId,
                         Model model,
                         @ModelAttribute("person") Person person){
//        bookDAO.vacate(bookId);
        bookService.vacate(bookId);
        return showBookPage(bookId, model, person);
    }

    @PostMapping("/{id}/reserve")
    public String reserveBook(@PathVariable("id") int bookId, Model model,
                              @ModelAttribute("person") Person person){

//        bookDAO.reserveBook(bookId, person.getId());
        bookService.reserveBook(bookId,person.getId());
        return showBookPage(bookId, model, person);
    }

    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }

    @GetMapping("/search-book")
    public String lookForBook(@RequestParam("query") String query,
                              Model model){
        List<Book> books = bookService.findByQuery(query);
        model.addAttribute("books", books);
        return "books/search";
    }

}

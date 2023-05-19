package com.library.services;

import com.library.models.Book;
import com.library.models.Person;
import com.library.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(int page, int itemsPerPage, boolean sortByYear){
        List<Book> books;
        if(sortByYear)
            books = booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("releaseYear"))).getContent();
        else
            books = booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
        return books;
    }

    public Book findById(int id){
        Book book = null;
        Optional<Book> foundBook = booksRepository.findById(id);
        if(foundBook.isPresent()) {
            book = foundBook.get();
            if(book.getOwner() != null)
                book.checkOverdue();
        }
        return book;
    }

    @Transactional(readOnly = false)
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional(readOnly = false)
    public void update(int id, Book book){
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional(readOnly = false)
    public void deleteById(int id){
        booksRepository.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void vacate(int bookId) {
        booksRepository.updateOwnerAndReservedAtById(null, null, bookId);
    }

    @Transactional(readOnly = false)
    public void reserveBook(int bookId, Person person) {
        booksRepository.updateOwnerAndReservedAtById(person, new Date() ,bookId);
    }

    public List<Book> findByQuery(String query) {
        // use single query to search in author and title
        // as an option, search could be implemented by each field separately
        return booksRepository.findByTitleContainsIgnoreCaseOrAuthorContainsIgnoreCase(query, query);
    }

    public long count(){
        return booksRepository.count();
    }
}

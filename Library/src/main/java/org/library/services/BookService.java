package org.library.services;

import org.library.models.Book;
import org.library.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public Book findById(int id){
        return booksRepository.findById(id).orElse(null);
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
    }

    @Transactional(readOnly = false)
    public void reserveBook(int bookId, int id) {
    }

    public List<Book> findByQuery(String query) {
        // use single query to search in author and title
        return booksRepository.findByTitleContainsIgnoreCaseOrAuthorContainsIgnoreCase(query, query);
    }
}

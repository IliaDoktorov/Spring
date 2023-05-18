package org.library.repositories;

import org.library.models.Book;
import org.library.models.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContainsIgnoreCaseOrAuthorContainsIgnoreCase(String title, String author);

    @Transactional
    @Modifying
    @Query("update Book b set b.owner = ?1, b.reservedAt = ?2 where b.id = ?3")
    void updateOwnerAndReservedAtById(@Nullable Person owner, @Nullable Date reservedAt, int id);





}

package com.library.repositories;

import com.library.models.Book;
import com.library.models.Person;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = "owner")
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = "owner")
    List<Book> findAll(Example bookExample);

    @Transactional
    @Modifying
    @Query("update Book b set b.owner = ?1, b.reservedAt = ?2 where b.id = ?3")
    void updateOwnerAndReservedAtById(@Nullable Person owner, @Nullable Date reservedAt, int id);
}

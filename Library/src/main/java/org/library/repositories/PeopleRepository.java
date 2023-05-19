package org.library.repositories;

import org.library.models.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "person-books-graph")
    List<Person> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "person-books-graph")
    List<Person> findByInitialsContainingIgnoreCaseOrYearOfBirth(String initials, int yearOfBirth);

    Optional<Person> findByInitials(String initials);
}

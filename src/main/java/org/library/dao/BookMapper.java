package org.library.dao;

import org.library.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper {
    private final boolean isFull;

    public BookMapper(boolean isFull) {
        this.isFull = isFull;
    }

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();


        book.setAuthor(rs.getString("author"));
        book.setReleaseYear(rs.getInt("release_year"));
        book.setTitle(rs.getString("title"));

        if(isFull) {
            book.setId(rs.getInt("id"));
//            book.setPersonId(rs.getInt("person_id"));
        }

        return book;
    }
}

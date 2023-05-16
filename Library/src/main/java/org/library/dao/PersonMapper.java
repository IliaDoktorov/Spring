package org.library.dao;

import org.library.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("person_id"));
        person.setInitials(rs.getString("initials"));
        person.setYearOfBirth(rs.getInt("year_of_birth"));
        return person;
    }
}

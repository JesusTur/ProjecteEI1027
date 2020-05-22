package es.projecteEI1027.dao;

import es.projecteEI1027.model.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VolunteerDao {

    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addVolunteer(Volunteer volunteer) {
        try{
            jdbcTemplate.update(
                    "INSERT INTO Volunteer VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)",
                    volunteer.getName(), volunteer.getSurname(), volunteer.getDni(), volunteer.getPhoneNumber(),
                    volunteer.getBirthDate(),volunteer.getApplicationDate(),volunteer.getAcceptationDate(),
                    volunteer.getAccepted(), volunteer.getHobbies(), volunteer.getEmail(),
                    volunteer.getUser(),volunteer.getPwd());
        }
        catch(DuplicateKeyException e) {
        }
    }

    public void deleteVolunteer(String dni) {
        try{
            jdbcTemplate.update("DELETE FROM Volunteer WHERE dni=?", dni);
        }
        catch(DataAccessException e){
        }
    }

    public void updateVolunteer(Volunteer volunteer) {
        try{
            jdbcTemplate.update("UPDATE Volunteer SET name = ?, surname = ?, phoneNumber = ?," +
                            "birthDate = ?, applicationDate = ?, acceptationDate = ?, accepted = ?, userVolunteer = ?, passwordVolunteer = ?, typeServiceVolunteer = ?, email = ? WHERE dni=?",
                    volunteer.getName(), volunteer.getSurname(), volunteer.getPhoneNumber(), volunteer.getBirthDate(), volunteer.getApplicationDate(), volunteer.getAcceptationDate(), volunteer.getAccepted(),
                    volunteer.getUser(), volunteer.getPwd(), volunteer.getHobbies(),
                    volunteer.getEmail(), volunteer.getDni());
        }
        catch (DataAccessException e){
        }
    }
    public List<Volunteer> getVolunteers() {
        try {
            return jdbcTemplate.query("SELECT * FROM Volunteer",
                    new VolunteerRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Volunteer>();
        }
    }


    public Volunteer getVolunteer(String dniVolunteer) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE dni = ?", new VolunteerRowMapper(),dniVolunteer);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public Volunteer getVolunteerPerUser(String userCas){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE userCAS = ?", new VolunteerRowMapper(),userCas);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }






}
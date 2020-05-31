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
                            "birthDate = ?, applicationDate = ?, acceptationDate = ?, accepted = ?, userVolunteer = ?, passwordVolunteer = ?, hobbie = ?, email = ? WHERE dni=?",
                    volunteer.getName(), volunteer.getSurname(), volunteer.getPhoneNumber(), volunteer.getBirthDate(), volunteer.getApplicationDate(), volunteer.getAcceptationDate(), volunteer.getAccepted(),
                    volunteer.getUser(), volunteer.getPwd(), volunteer.getHobbies(),
                    volunteer.getEmail(), volunteer.getDni());

        }
        catch (DataAccessException e){
        }
    }
    public void acceptVolunteer(Volunteer volunteer) {
        try {
            jdbcTemplate.update("UPDATE Volunteer SET acceptationDate = ?, accepted = ?WHERE dni=?",
                  volunteer.getAcceptationDate(), volunteer.getAccepted(), volunteer.getDni());

        } catch (DataAccessException e) {
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
    public Volunteer getVolunteerPerUser(String uservolunteer){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE uservolunteer = ?", new VolunteerRowMapper(),uservolunteer);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Volunteer> getUnacceptedVolunteers(){
        try {
            return jdbcTemplate.query("SELECT * FROM Volunteer WHERE accepted = 'f'",
                    new VolunteerRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Volunteer>();
        }

    }

    //Esto es para cogerlo por DNI
    public String getVolunteer2(String dniVolunteer) {
        String dni;
        try {
            Volunteer vol = jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE dni = ?", new VolunteerRowMapper(),dniVolunteer);
            dni = vol.getDni();
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
        return dni;
    }


}
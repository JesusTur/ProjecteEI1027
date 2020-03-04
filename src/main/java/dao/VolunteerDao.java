package dao;

import model.Volunteer;
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
                    "INSERT INTO Volunteer VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    volunteer.getNameVolunteer(), volunteer.getSurnameVolunteer(), volunteer.getDniVolunteer(), volunteer.getPhoneVolunteer(),
                    volunteer.getBirthDateVolunteer(), volunteer.getTypeServiceVolunteer(), volunteer.getEmail(), volunteer.getUser(),volunteer.getPwd());
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
            jdbcTemplate.update("UPDATE Volunteer SET nameVolunteer = ?, surnameVolunteer = ?, phoneVolunteer = ?," +
                            "birthDateVolunteer = ?, user = ?, pwd = ?, typeServiceVolunteer = ?, email = ? WHERE dniVolunteer=?",
                    volunteer.getNameVolunteer(), volunteer.getSurnameVolunteer(), volunteer.getPhoneVolunteer(), volunteer.getBirthDateVolunteer(),
                    volunteer.getUser(), volunteer.getPwd(), volunteer.getTypeServiceVolunteer(),
                    volunteer.getEmail(), volunteer.getDniVolunteer());
        }
        catch (DataAccessException e){
        }
    }






}
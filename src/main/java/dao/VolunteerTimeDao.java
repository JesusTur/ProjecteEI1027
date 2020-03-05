package dao;

import model.VolunteerTime;
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
public class VolunteerTimeDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addVolunteerTime(VolunteerTime volunteerTime) {
        try{
            jdbcTemplate.update(
                    "INSERT INTO VolunteerTime VALUES(?, ?, ?, ?, ?, ?)",
                    volunteerTime.getDate(),volunteerTime.getDniVolunteer(),volunteerTime.getDniBeneficiary(),volunteerTime.getBeginningHour(),volunteerTime.getEndingHour(),volunteerTime.isAvailable());

        }
        catch(DuplicateKeyException e) {
        }
    }

    public void deleteVolunteerTime(String dniBen, String dniVol) {
        try{
            jdbcTemplate.update("DELETE FROM VolunteerTime WHERE dniBeneficiary = ? AND dniVolunteer = ?", dniBen,dniVol);
        }
        catch(DataAccessException e){

        }
    }

    public void updateVolunteerTime(VolunteerTime volunteerTime) {
        try{
            jdbcTemplate.update("UPDATE VolunteerTime SET dateVolunteer = ?, beginningHour = ?," +
                            "endingHour = ?, availabe = ?, WHERE dniVolunteer =? AND dniBeneficiary = ?",
                    volunteerTime.getDate(),volunteerTime.getBeginningHour(),volunteerTime.getEndingHour(),volunteerTime.isAvailable(),volunteerTime.getDniVolunteer(),volunteerTime.getDniBeneficiary());
        }
        catch (DataAccessException e){
        }
    }


    public List<VolunteerTime> getVolunteerTimes() {
        try {
            return jdbcTemplate.query("SELECT * FROM VolunteerTime",
                    new VolunteerTimeRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<VolunteerTime>();
        }
    }


    public VolunteerTime getVolunteerTime(String dniVol,String dniBen) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary = ?", new VolunteerTimeRowMapper(),dniVol,dniBen);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

}


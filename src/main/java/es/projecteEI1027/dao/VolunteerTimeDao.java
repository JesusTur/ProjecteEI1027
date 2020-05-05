package es.projecteEI1027.dao;

import es.projecteEI1027.model.VolunteerTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
                    volunteerTime.getDate(), volunteerTime.getDniVolunteer(), volunteerTime.getDniBeneficiary(),
                    volunteerTime.getBeginningTime(), volunteerTime.getEndingTime(), volunteerTime.isAvailable());

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
                            "endingHour = ?, availabe = ? WHERE dniVolunteer = ? AND dniBeneficiary = ?",
                    volunteerTime.getDate(), volunteerTime.getBeginningTime(), volunteerTime.getEndingTime(),
                    volunteerTime.isAvailable(), volunteerTime.getDniVolunteer(), volunteerTime.getDniBeneficiary());
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


    public VolunteerTime getVolunteerTime(String dniVol, LocalDateTime tiempoIni, LocalDateTime tiempoFin) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND beginningHour =  ? AND endingHour = ?", new VolunteerTimeRowMapper(),dniVol,tiempoIni,tiempoFin);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public LocalDateTime getVolunteerTimeInit(String dniVol) {
        try {
            VolunteerTime vt = jdbcTemplate.queryForObject("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary IS NULL", new VolunteerTimeRowMapper(),dniVol);
            return vt.getBeginningTime();
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public LocalDateTime getVolunteerTimeFinal(String dniVol) {
        try {
            VolunteerTime vt = jdbcTemplate.queryForObject("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary IS NULL", new VolunteerTimeRowMapper(),dniVol);
            return vt.getEndingTime();
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }public void updateTime(String dni, LocalDateTime fechaIni, LocalDateTime fechaFin){
        try {
            VolunteerTime vt = jdbcTemplate.queryForObject("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary IS NULL", new VolunteerTimeRowMapper(),dni);
            if(vt.getEndingTime().equals(fechaFin)){
                jdbcTemplate.update("DELETE FROM VolunteerTime WHERE dniVolunteer= ? AND dniBeneficiary IS NULL", dni);
            }else{
                jdbcTemplate.update("UPDATE VolunteerTime SET beginningHour = ? WHERE dniVolunteer = ? AND dniBeneficiary IS NULL", fechaFin,dni);
            }

        }
        catch(EmptyResultDataAccessException e) {
        }
    }

}


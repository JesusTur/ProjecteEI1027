package es.projecteEI1027.dao;

import es.projecteEI1027.model.Beneficiary;
import es.projecteEI1027.model.Volunteer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void deleteVolunteerTime(String dniVol, LocalDateTime beginningInit) {
        try{
            jdbcTemplate.update("DELETE FROM VolunteerTime WHERE dniVolunteer = ? AND beginningHour = ?", dniVol,beginningInit);
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


    public VolunteerTime getVolunteerTime(String dniVol, LocalDateTime tiempoIni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND beginningHour =  ?", new VolunteerTimeRowMapper(),dniVol,tiempoIni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public Map<LocalDateTime,LocalDateTime> getVolunteerTime(String dniVol) {
        Map<LocalDateTime,LocalDateTime> lvt = new HashMap<>();
        try {
            List<VolunteerTime> vt = jdbcTemplate.query("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary IS NULL", new VolunteerTimeRowMapper(),dniVol);
            for(VolunteerTime vol : vt){
                lvt.put(vol.getBeginningTime(),vol.getEndingTime());
            }
            return lvt;

        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<LocalDateTime> getVolunteerTimeInit(String dniVol) {
        List<LocalDateTime> lvt = new ArrayList<>();
        try {
            List<VolunteerTime> vt = jdbcTemplate.query("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary IS NULL", new VolunteerTimeRowMapper(),dniVol);
           for(VolunteerTime vol : vt){
               lvt.add( vol.getBeginningTime());
           }
           return lvt;

        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<LocalDateTime> getVolunteerTimeFinal(String dniVol) {
        List<LocalDateTime> lvt = new ArrayList<>();
        try {
            List<VolunteerTime> vt = jdbcTemplate.query("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary IS NULL", new VolunteerTimeRowMapper(),dniVol);
            for(VolunteerTime vol : vt){
                lvt.add(vol.getEndingTime());
            }
            return lvt;
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public void updateTime(String dni, LocalDateTime fechaIni, LocalDateTime fechaFin){
        try {
            List<VolunteerTime> vt = jdbcTemplate.query("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary IS NULL", new VolunteerTimeRowMapper(),dni);
            for(VolunteerTime vol : vt){
                if(vol.getEndingTime().equals(fechaFin)){
                    jdbcTemplate.update("DELETE FROM VolunteerTime WHERE dniVolunteer= ? AND dniBeneficiary IS NULL AND DATE(endingHour) = ?", dni,fechaFin.toLocalDate());
                }else{
                    jdbcTemplate.update("UPDATE VolunteerTime SET beginningHour = ? WHERE dniVolunteer = ? AND dniBeneficiary IS NULL AND DATE(endingHour) = ?", fechaFin,dni, fechaFin.toLocalDate());
                }
            }
        }
        catch(EmptyResultDataAccessException e) {
        }
    }

    public List<Beneficiary> getRelatedBeneficiaries(String dniVol) {
        try {
            return jdbcTemplate.query("SELECT * FROM Beneficiary WHERE dni IN (SELECT dniBeneficiary FROM VolunteerTime WHERE dniVolunteer = ?)", new BeneficiaryRowMapper(),dniVol);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<VolunteerTime> getFreeTimeList(String dniVol) {
        try {
            return jdbcTemplate.query("SELECT * FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary IS NULL", new VolunteerTimeRowMapper(),dniVol);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }


    public void updateTime(String dni, LocalDateTime fechaIni, LocalDateTime fechaInitActualizada, LocalDateTime fechaFinActualizada) {
        try {
            jdbcTemplate.update("UPDATE VolunteerTime SET beginningHour = ?, endingHour = ? WHERE dniVolunteer = ? AND beginningHour = ? ", fechaInitActualizada,fechaFinActualizada,dni,fechaIni);
        }
        catch(EmptyResultDataAccessException e) {
        }
    }

    public void deleteVol(String dniVol, String dniBen ) {
        try {
            jdbcTemplate.update("DELETE FROM VolunteerTime WHERE dniVolunteer = ? AND dniBeneficiary = ? ", dniVol,dniBen);
        }
        catch(EmptyResultDataAccessException e) {
        }
    }



}


package es.projecteEI1027.dao;

import es.projecteEI1027.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.sql.Types.NULL;

@Repository
public class BeneficiaryDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addBeneficiary(Beneficiary beneficiary) {
        if(beneficiary.getSocialWorker().equals("")){
            try{
                jdbcTemplate.update(
                        "INSERT INTO Beneficiary VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        beneficiary.getDni(), beneficiary.getName(), beneficiary.getSurname(),
                        beneficiary.getHomeAddress(), beneficiary.getPhoneNumber(),beneficiary.getBankAccount(),beneficiary.getEmail(),
                        beneficiary.getDateCreation(),beneficiary.getBirthDate(), null ,beneficiary.getUser(), beneficiary.getPassword(), beneficiary.getDescription());

            }
            catch(DuplicateKeyException e) {
            }
        }
        else{
            try{
                jdbcTemplate.update(
                        "INSERT INTO Beneficiary VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        beneficiary.getDni(), beneficiary.getName(), beneficiary.getSurname(),
                        beneficiary.getHomeAddress(), beneficiary.getPhoneNumber(),beneficiary.getBankAccount(),beneficiary.getEmail(),
                        beneficiary.getDateCreation(),beneficiary.getBirthDate(), beneficiary.getSocialWorker(), beneficiary.getUser(), beneficiary.getPassword(), beneficiary.getDescription());

            }
            catch(DuplicateKeyException e) {
            }
        }

    }

    public void deleteBeneficiary(String dni) {
        try{
            jdbcTemplate.update("DELETE FROM Beneficiary WHERE dni=?", dni);
        }
        catch(DataAccessException e){

        }
    }

    public void updateBeneficiary(Beneficiary beneficiary) {
        if(beneficiary.getSocialWorker().equals("")){
            try{
                jdbcTemplate.update("UPDATE Beneficiary SET  name = ?, surname = ?, homeAddress = ?, phoneNumber = ?," +
                                "bankAccount = ?, email = ?, dateCreation = ?, birthDate = ?,socialWorker = ?, userBeneficiary = ?, passwordBeneficiary = ?, description = ? WHERE dni=?",
                        beneficiary.getName(), beneficiary.getSurname(), beneficiary.getHomeAddress(), beneficiary.getPhoneNumber(),
                        beneficiary.getBankAccount(),beneficiary.getEmail(),beneficiary.getDateCreation(), beneficiary.getBirthDate(), null,
                        beneficiary.getUser(), beneficiary.getPassword(),beneficiary.getDescription(), beneficiary.getDni());
            }
            catch (DataAccessException e){
            }
        }
        else{
            try{
                jdbcTemplate.update("UPDATE Beneficiary SET  name = ?, surname = ?, homeAddress = ?, phoneNumber = ?," +
                                "bankAccount = ?, email = ?, dateCreation = ?, birthDate = ?,socialWorker = ?, userBeneficiary = ?, passwordBeneficiary = ?, description = ? WHERE dni=?",
                        beneficiary.getName(), beneficiary.getSurname(), beneficiary.getHomeAddress(), beneficiary.getPhoneNumber(),
                        beneficiary.getBankAccount(),beneficiary.getEmail(),beneficiary.getDateCreation(), beneficiary.getBirthDate(), beneficiary.getSocialWorker(),
                        beneficiary.getUser(), beneficiary.getPassword(),beneficiary.getDescription(), beneficiary.getDni());
            }
            catch (DataAccessException e){
            }
        }

    }


    public List<Beneficiary> getBeneficiaries() {
        try {
            return jdbcTemplate.query("SELECT * FROM Beneficiary",
                    new BeneficiaryRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Beneficiary>();
        }
    }


    public Beneficiary getBeneficiary(String dniBeneficiary) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Beneficiary WHERE dni = ?",
                    new BeneficiaryRowMapper(), dniBeneficiary);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Beneficiary getBeneficiaryPerNom(String userBeneficiari) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Beneficiary WHERE userBeneficiary = ?",
                    new BeneficiaryRowMapper(), userBeneficiari);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Volunteer> getVolunteerServicesUser(String dni){
        try {
            return jdbcTemplate.query("SELECT * FROM Volunteer WHERE dni IN (SELECT dniVolunteer FROM VolunteerTime WHERE dniBeneficiary = ? AND available IS TRUE) AND accepted IS TRUE",
                    new VolunteerRowMapper(), dni);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Volunteer>();
        }
    }
    public List<Company> getCompanyPerTipus(String tipo, String user){
        try {
            return jdbcTemplate.query("SELECT * FROM Company WHERE cif IN (SELECT cif FROM Contract WHERE id IN (SELECT contractId " +
                            "FROM Request WHERE typeOfService = ? AND ((requestState NOT LIKE 'accepted' AND requestState NOT LIKE 'processing' AND dniBeneficiary " +
                            "= (SELECT dni FROM Beneficiary WHERE userBeneficiary = ?)) OR (SELECT dni FROM Beneficiary WHERE userBeneficiary = ?) NOT IN (SELECT dniBeneficiary FROM Request WHERE dniBeneficiary = (SELECT dni FROM Beneficiary " +
                            "WHERE userBeneficiary = ?  ))))) " +
                            "OR cif IN (SELECT cif FROM Contract WHERE id NOT IN (SELECT contractId FROM request))",
                    new CompanyRowMapper(), tipo, user, user, user);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Company>();
        }
    }
    public List<Request> getServicePerBen(String user){
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE requestState = 'accepted' AND dniBeneficiary = (SELECT dni FROM Beneficiary WHERE userBeneficiary = ?)",
                    new RequestRowMapper(), user);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }
    public List<Request> getServicePerBenPending(String user){
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE requestState = 'processing' AND dniBeneficiary = (SELECT dni FROM Beneficiary WHERE userBeneficiary = ?)",
                    new RequestRowMapper(), user);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }
    public Map<String,Float> getPrecioContract(List<Company> list){
        Map<String,Float> precios = new ConcurrentHashMap<>();

        for(Company company:list){
            try{
                Contract contr = jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE cif = ?",
                        new ContractRowMapper(), company.getCif());
                precios.put(company.getCif(),contr.getPriceUnit());
            } catch(EmptyResultDataAccessException e) {
                return null;
            }
        }
        return precios;
    }
    public List<Volunteer> getVolunteerPerBen(String ben){
        try {
            return jdbcTemplate.query("SELECT * FROM Volunteer WHERE accepted IS TRUE " +
                            "AND dni IN (SELECT dniVolunteer FROM VolunteerTime WHERE dniBeneficiary IS NULL) AND dni NOT IN (SELECT dniVolunteer FROM VolunteerTime WHERE dniBeneficiary = ?)",
                    new VolunteerRowMapper(), ben);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Volunteer>();
        }
    }

    public boolean yaTiene(String ben){
        try{
            Beneficiary beny = jdbcTemplate.queryForObject("Select * from beneficiary WHERE dni = ? AND dni IN(SELECT dniBeneficiary FROM volunteerTime where available IS TRUE)",
                    new BeneficiaryRowMapper(), ben);
            if(beny == null){
                return false;
            }
            else{
                return true;
            }
        }
        catch(EmptyResultDataAccessException e){
        }
        return false;
    }

    public Request servicioPendiente(String user){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE requestState LIKE 'processing' AND dniBeneficiary = (SELECT dni FROM Beneficiary WHERE userBeneficiary = ?) ",
                    new RequestRowMapper(), user);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Request servicioMismoTipo(String user,String tipo){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE typeOfService = ?  AND dniBeneficiary = (SELECT dni FROM Beneficiary WHERE userBeneficiary = ?) AND requestState NOT LIKE 'rejected'",
                    new RequestRowMapper(), tipo, user);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public VolunteerTime horarios(String ben){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM VolunteerTime WHERE dniBeneficiary = ? AND available IS TRUE",
                    new VolunteerTimeRowMapper(), ben);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }



}

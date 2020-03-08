package es.projecteEI1027.dao;

import es.projecteEI1027.model.Beneficiary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

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
        try{
            jdbcTemplate.update("UPDATE Beneficiary SET name = ?, surname = ?, homeAddress = ?, phoneNumber = ?," +
                    "bankAccount = ?, email = ?, dateCreation = ?, birthDate = ?,socialWorker = ?, userBeneficiary = ?, passwordBeneficiary = ?, description = ? WHERE dni=?",
                    beneficiary.getName(), beneficiary.getSurname(), beneficiary.getHomeAddress(), beneficiary.getPhoneNumber(),
                    beneficiary.getBankAccount(),beneficiary.getEmail(),beneficiary.getDateCreation(), beneficiary.getBirthDate(), beneficiary.getSocialWorker(),
                    beneficiary.getUser(), beneficiary.getPassword(),beneficiary.getDescription(), beneficiary.getDni());
        }
        catch (DataAccessException e){
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

}

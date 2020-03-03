package dao;

import model.Beneficiary;
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
public class BeneficiaryDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addBeneficiary(Beneficiary beneficiary) {
        try{
            jdbcTemplate.update(
                    "INSERT INTO Nadador VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    beneficiary.getName(), beneficiary.getDni(), beneficiary.getSurname(), beneficiary.getHomeAddress(), beneficiary.getPhoneNumber(),beneficiary.getBankAccount(),beneficiary.getBirthDate(),beneficiary.getSocialWorker(),beneficiary.getUsser(),beneficiary.getPassword());

        }
        catch(DuplicateKeyException e) {

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
            jdbcTemplate.update("UPDATE Beneficiary SET name = ?, surname = ?, homeAddress = ?, phoneNumber = ?, bankAccount = ?, birthDate = ?,socialWorker = ?, usser = ?, password = ?,WHERE dni=?",beneficiary.getName(), beneficiary.getSurname(), beneficiary.getHomeAddress(), beneficiary.getPhoneNumber(),beneficiary.getBankAccount(),beneficiary.getBirthDate(),beneficiary.getSocialWorker(),beneficiary.getUsser(),beneficiary.getPassword(),beneficiary.getDni());
        }
        catch (DataAccessException e){
        }
    }




}

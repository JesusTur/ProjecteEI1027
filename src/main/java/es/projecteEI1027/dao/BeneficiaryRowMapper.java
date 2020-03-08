package es.projecteEI1027.dao;

import es.projecteEI1027.model.Beneficiary;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public final class BeneficiaryRowMapper implements RowMapper<Beneficiary>{
    public Beneficiary mapRow(ResultSet rs, int rowNum) throws SQLException {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setDni(rs.getString("dni"));
        beneficiary.setName(rs.getString("name"));
        beneficiary.setSurname(rs.getString("surname"));
        beneficiary.setHomeAddress(rs.getString("homeaddress"));
        beneficiary.setPhoneNumber(rs.getString("phonenumber"));
        beneficiary.setBankAccount(rs.getString("bankaccount"));
        beneficiary.setEmail(rs.getString("email"));
        beneficiary.setDateCreation(rs.getDate("datecreation"));
        beneficiary.setBirthDate(rs.getDate("birthdate"));
        beneficiary.setSocialWorker(rs.getString("socialworker"));
        beneficiary.setUser(rs.getString("userbeneficiary"));
        beneficiary.setPassword(rs.getString("passwordbeneficiary"));
        beneficiary.setDescription(rs.getString("description"));
        return beneficiary;
    }

}

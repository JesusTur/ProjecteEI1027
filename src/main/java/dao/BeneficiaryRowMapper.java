package dao;

import model.Beneficiary;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public final class BeneficiaryRowMapper implements RowMapper<Beneficiary>{
    public Beneficiary mapRow(ResultSet rs, int rowNum) throws SQLException {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setDni(rs.getString("dni"));
        beneficiary.setName(rs.getString("name"));
        beneficiary.setSurname(rs.getString("surname"));
        beneficiary.setHomeAddress(rs.getString("homeAddress"));
        beneficiary.setPhoneNumber(rs.getString("phoneNumber"));
        beneficiary.setBankAccount(rs.getString("bankAccount"));
        beneficiary.setEmail(rs.getString("email"));
        beneficiary.setDateCreation(rs.getDate("dateCreation"));
        beneficiary.setBirthDate(rs.getDate("birthDate"));
        beneficiary.setSocialWorker(rs.getString("socialWorker"));
        beneficiary.setUser(rs.getString("userBeneficiary"));
        beneficiary.setPassword(rs.getString("passwordBeneficiary"));
        beneficiary.setDescription(rs.getString("description"));
        return beneficiary;
    }

}

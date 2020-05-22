package es.projecteEI1027.dao;

import es.projecteEI1027.model.Volunteer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class VolunteerRowMapper implements RowMapper<Volunteer> {
    @Override
    public Volunteer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Volunteer volunteer = new Volunteer();
        volunteer.setName(rs.getString("name"));
        volunteer.setSurname(rs.getString("surname"));
        volunteer.setDni(rs.getString("dni"));
        volunteer.setPhoneNumber(rs.getString("phoneNumber"));
        volunteer.setBirthDate(rs.getDate("birthDate"));
        volunteer.setAcceptationDate(rs.getDate("applicationDate"));
        volunteer.setAcceptationDate(rs.getDate("acceptationDate"));
        volunteer.setAccepted(rs.getBoolean("accepted"));
        volunteer.setHobbies(rs.getString("typeServiceVolunteer"));
        volunteer.setEmail(rs.getString("email"));
        volunteer.setUser(rs.getString("userVolunteer"));
        volunteer.setPwd(rs.getString("passwordVolunteer"));
        return volunteer;
    }
}

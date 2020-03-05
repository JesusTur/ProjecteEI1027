package dao;

import model.VolunteerTime;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VolunteerTimeRowMapper implements RowMapper<VolunteerTime> {

    @Override
    public VolunteerTime mapRow(ResultSet rs, int rowNum) throws SQLException {
        VolunteerTime volunteerTime = new VolunteerTime();
        volunteerTime.setDate(rs.getDate("dateVolunteer"));
        volunteerTime.setDniVolunteer(rs.getString("dniVolunteer"));
        volunteerTime.setDniBeneficiary(rs.getString("dniBeneficiary"));
        volunteerTime.setBeginningHour(rs.getTime("beginningHour"));
        volunteerTime.setEndingHour(rs.getTime("endingHour"));
        volunteerTime.setAvailable(rs.getBoolean("available"));
        return volunteerTime;
    }
}

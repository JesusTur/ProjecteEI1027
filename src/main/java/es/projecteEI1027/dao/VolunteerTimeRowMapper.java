package es.projecteEI1027.dao;

import es.projecteEI1027.model.VolunteerTime;
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
        volunteerTime.setBeginningTime(rs.getTimestamp("beginningHour").toLocalDateTime());
        volunteerTime.setEndingTime(rs.getTimestamp("endingHour").toLocalDateTime());
        volunteerTime.setAvailable(rs.getBoolean("available"));
        return volunteerTime;
    }
}

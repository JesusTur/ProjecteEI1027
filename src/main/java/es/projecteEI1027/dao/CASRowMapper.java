package es.projecteEI1027.dao;

import es.projecteEI1027.model.CAS;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CASRowMapper implements RowMapper<CAS> {
    public CAS mapRow(ResultSet rs, int rowNum) throws SQLException{
        CAS cas = new CAS();
        cas.setUser(rs.getString("userCAS"));
        cas.setPassword(rs.getString("passwordCAS"));
        return cas;
    }
}

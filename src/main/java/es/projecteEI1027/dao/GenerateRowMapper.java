package es.projecteEI1027.dao;
import es.projecteEI1027.model.Generate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public final class GenerateRowMapper implements RowMapper<Generate>{
    public Generate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Generate generate = new Generate();
        generate.setnFact(rs.getInt("nInv"));
        generate.setId(rs.getInt("id"));
        generate.setDate(rs.getDate("generateDate"));
        return generate;
    }
}

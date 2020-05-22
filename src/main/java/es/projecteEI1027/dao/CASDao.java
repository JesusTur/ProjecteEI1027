package es.projecteEI1027.dao;

import es.projecteEI1027.model.CAS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class CASDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public CAS getCASPerUser(String userCAS){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM CAS WHERE userCAS = ?",
                    new CASRowMapper(), userCAS);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}

package es.projecteEI1027.dao;

import es.projecteEI1027.model.SocialWorker;
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
public class SocialWorkerDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSocialWorker(SocialWorker socialWorker){
        try {
            jdbcTemplate.update("INSERT INTO SocialWorker VALUES(?, ?, ?, ?, ?)",
                    socialWorker.getUserCAS(), socialWorker.getPwd(), socialWorker.getName(),
                    socialWorker.getEmail(), socialWorker.getPhoneNumber());
        }
        catch (DuplicateKeyException e){

        }
    }

    public void updateSocialWorker(SocialWorker socialWorker){
        try {
            jdbcTemplate.update("UPDATE SocialWorker SET  passwordSocialWorker = ?, name = ?, email = ?, phoneNumber = ?" +
                    "WHERE userCAS = ?",
                    socialWorker.getPwd(), socialWorker.getName(), socialWorker.getEmail(),
                    socialWorker.getPhoneNumber(), socialWorker.getUserCAS());
        }
        catch (DataAccessException e){

        }
    }

    public List<SocialWorker> socialWorkers(){
        try {
            return jdbcTemplate.query("SELECT * FROM SocialWorker",
                    new SocialWorkerRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return new ArrayList<SocialWorker>();
        }
    }
    public SocialWorker getSocialWorker(String userCAS){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM SocialWorker WHERE userCAS = ?",
                    new SocialWorkerRowMapper(), userCAS);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}

package es.projecteEI1027.dao;

import es.projecteEI1027.model.Generate;
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
public class GenerateDao {
    private JdbcTemplate jdbcTemplate;
    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addGenerate(Generate generate){
        try{
            jdbcTemplate.update("INSERT INTO GeneratesInv VALUES(?, ?, ?)",
                    generate.getnFact(), generate.getId(), generate.getDate());
        }
        catch (DuplicateKeyException e){

        }
    }

    public void deleteGenerate(int nFact, int id ){
        try {
            jdbcTemplate.update("DELETE FROM Generate WHERE nInv = ? AND id = ?",
                    nFact, id);
        }
        catch(DataAccessException e){

        }
    }

    public void updateGenerate(Generate generate){
        try {
            jdbcTemplate.update("UPDATE Generate SET generateDate = ? WHERE nInv = ? AND id = ?",
                    generate.getDate(), generate.getnFact(), generate.getId());
        }
        catch (DataAccessException e){

        }
    }
    public List<Generate> getGenerates(){
        try {
           return jdbcTemplate.query("SELECT * FROM Generate",
                    new GenerateRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return new ArrayList<Generate>();
        }
    }

    public Generate getGenerate(int nInv, int id){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Generate WHERE nInv = ? AND id = ?",
                    new GenerateRowMapper(), nInv, id);
        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
    }
}

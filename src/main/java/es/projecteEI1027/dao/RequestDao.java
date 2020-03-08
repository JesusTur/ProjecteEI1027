package es.projecteEI1027.dao;
import es.projecteEI1027.model.Request;
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
public class RequestDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRequest(Request request) {
        try {
            jdbcTemplate.update("Insert INTO Request Values(?, ? ,?, ?, ?, ?, ?, ?, ?, ?)",
                    request.getId(), request.getDniBeneficiary(), request.getTypeOfService(), request.getContractid(),
                    request.getSchedule(), request.getRequestState().toString(), request.getDateAccept(), request.getDateReject(),
                    request.getComment(), request.getDateFinal());

        } catch (DuplicateKeyException e) {
        }
    }
    public void deleteRequest(int id){
        try{
            jdbcTemplate.update("DELETE FROM Request WHERE id = ?", id);
        }
        catch (DataAccessException e){

        }
    }
    public void updateRequest(Request request){
        try {
            jdbcTemplate.update("UPDATE Request SET id = ?, dniBeneficiary = ?, typeOfService = ?, contractId = ?," +
                    "creationDate = ?, requestState = ?, dateAccept = ?, dateReject = ?, comments = ?, dateFinal = ?",
                    request.getId(), request.getDniBeneficiary(), request.getTypeOfService(), request.getContractid(),
                    request.getSchedule(), request.getRequestState().toString(), request.getDateAccept(), request.getDateReject(),
                    request.getComment(), request.getDateFinal());
        }
        catch (DataAccessException e){

        }
    }
    public List<Request> getRequests(){
        try {
            return jdbcTemplate.query("SELECT * FROM Request",
                    new RequestRowMapper());
        }
        catch(EmptyResultDataAccessException e){
            return new ArrayList<Request>();
        }
    }
    public Request getRequest(int id){
        try {

            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE id = ?",
                    new RequestRowMapper(), id);
        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }

    }
}
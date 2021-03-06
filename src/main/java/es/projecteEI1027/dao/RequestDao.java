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
            jdbcTemplate.update(
                    "INSERT INTO Request VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
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
            jdbcTemplate.update("UPDATE Request SET  dniBeneficiary = ?, typeOfService = ?, contractId = ?," +
                    "creationDate = ?, requestState = ?, dateAccept = ?, dateReject = ?, comments = ?, dateFinal = ?" +
                            "WHERE id = ?",
                    request.getDniBeneficiary(), request.getTypeOfService(), request.getContractid(),
                    request.getSchedule(), request.getRequestState().toString(), request.getDateAccept(), request.getDateReject(),
                    request.getComment(), request.getDateFinal(), request.getId());
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
    public synchronized Integer getRequestid(){
        try {

            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Request",
                    Integer.class);
        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
    }
    public List<Request> getRequestsByContractIde(int id){
        try {

            return jdbcTemplate.query("SELECT * FROM Request WHERE contractId = ? AND requestState = 'accepted'",
                    new RequestRowMapper(), id);
        }
        catch (EmptyResultDataAccessException e){
            return  new ArrayList<Request>();
        }
    }
    public  List<Request> getPendentRequests() {
        try {

            return jdbcTemplate.query("SELECT * FROM Request WHERE requestState = 'processing'",
                    new RequestRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }
    public void rejectedService(String dni, String typeOfService ) {
        try {
            jdbcTemplate.update("UPDATE Request SET requestState = 'rejected' WHERE dniBeneficiary = ? AND typeOfService = ?", dni,typeOfService);
        }
        catch(EmptyResultDataAccessException e) {
        }
    }
    public void rejectRequest(int id){try {
        jdbcTemplate.update("UPDATE Request SET requestState = 'rejected' WHERE id = ?", id);
    }
    catch(EmptyResultDataAccessException e) {
    }}
}
package es.projecteEI1027.dao;

import es.projecteEI1027.model.Contract;
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
public class ContractDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addContract(Contract contract) {
        try{
            jdbcTemplate.update(
                    "INSERT INTO Contract VALUES(?, ?, ?, ?, ?, ?, ?)",
                    contract.getId(), contract.getCif(), contract.getTypeOfService(),
                    contract.getStartDate(), contract.getFinalDate(), contract.getQuantity(), contract.getPriceUnit());

        }
        catch(DuplicateKeyException e) {
        }
    }
    public void deleteRequest(int id){
        try{
            jdbcTemplate.update("DELETE FROM Contract WHERE id = ?", id);
        }
        catch (DataAccessException e){

        }
    }


    public void updateContract(Contract contract) {
        try{
            jdbcTemplate.update("UPDATE Contract SET cif = ?, typeOfService = ?,startDate = ?, finalDate = ?," +
                            " quantity = ? WHERE id = ?",
                    contract.getCif(), contract.getTypeOfService(), contract.getStartDate(),
                    contract.getFinalDate(), contract.getQuantity(), contract.getId());
        }
        catch (DataAccessException e){
        }
    }

    public Contract getContract(int id){
        try {

            return jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE id = ?",
                    new ContractRowMapper(), id);

        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
    }
    public Integer getContractByCompany(String cif){
        Integer id;
        try {
            Contract cont = jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE cif = (SELECT cif FROM Company WHERE cif = ?)",
                    new ContractRowMapper(), cif);
            id = cont.getId();

        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
        return id;
    }

    public List<Contract> getContracts() {
        try {
            return jdbcTemplate.query("SELECT * FROM Contract",
                    new ContractRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Contract>();
        }
    }
    public Integer getContractId(String cif){
        try {
            return jdbcTemplate.queryForObject("SELECT id FROM Contract WHERE cif = ?", Integer.class, cif);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public Integer getMAXId(){
        try {
            return jdbcTemplate.queryForObject("SELECT MAX(id) FROM Contract", Integer.class);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public Contract getContractbyCif(String cif){
        try {

            return jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE cif = ?",
                    new ContractRowMapper(), cif);

        }
        catch (EmptyResultDataAccessException e){
            return  null;
        }
    }

    public  void updateQuantity(int id){
        try{
            jdbcTemplate.update("UPDATE Contract SET quantity=quantity-1 WHERE id = ?", id);
        }
        catch (DataAccessException e){

        }
    }

}

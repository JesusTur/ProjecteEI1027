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
                    "INSERT INTO Nadador VALUES(?, ?, ?, ?, ?)",
                    contract.getTypeOfService(), contract.getPrice(), contract.getStartDate(), contract.getFinalDate(), contract.getQuantity());

        }
        catch(DuplicateKeyException e) {
        }
    }


    public void updateContract(Contract contract) {
        try{
            jdbcTemplate.update("UPDATE Contract SET price = ?, startDate = ?, finalDate = ?, quantity = ?",
                    contract.getPrice(), contract.getStartDate(), contract.getFinalDate(), contract.getQuantity());
        }
        catch (DataAccessException e){
        }
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

}

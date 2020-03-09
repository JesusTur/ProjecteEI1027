package es.projecteEI1027.dao;

import es.projecteEI1027.model.Company;
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
public class CompanyDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCompany(Company company){
        try {
            jdbcTemplate.update("INSERT INTO Company VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", company.getCif(), company.getNameCompany(), company.getAddress(), company.getUser(),
                    company.getPassword(), company.getRegisteredDate(), company.getContractPersonName(), company.getCompanyMail(), company.getServiceType().toString());

        } catch (DuplicateKeyException e){

        }
    }
    public void deleteCompany(String cif){
        try {
            jdbcTemplate.update("DELETE FROM Company WHERE cif = ?", cif);
        }
        catch (DataAccessException e){

        }
    }
    public void updateCompany(Company company){
        jdbcTemplate.update("UPDATE Company SET name = ?, address = ?, userCompany = ?, passwordCompany = ?, registeredDate = ?, contractPersonName = ?, email = ?," +
                "typeOfService = ? WHERE cif = ?", company.getNameCompany(), company.getAddress(), company.getUser(),  company.getPassword(), company.getRegisteredDate(),
                company.getContractPersonName(), company.getCompanyMail(), company.getServiceType().toString() , company.getCif());
    }
    public List<Company> getCompanies(){
        try {
            return jdbcTemplate.query("SELECT * FROM Company",
                    new CompanyRowMapper());
        }
        catch (EmptyResultDataAccessException e){
            return new ArrayList<Company>();
        }
    }
    public Company getCompany(String cifCompany){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Company WHERE cif = ?",
                    new CompanyRowMapper(), cifCompany);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}

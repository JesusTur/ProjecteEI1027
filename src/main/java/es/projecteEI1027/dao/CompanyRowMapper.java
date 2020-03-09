package es.projecteEI1027.dao;

import es.projecteEI1027.model.Company;
import es.projecteEI1027.model.ServiceType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class CompanyRowMapper implements RowMapper<Company> {
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        company.setCif(rs.getString("cif"));
        company.setNameCompany(rs.getString("name"));
        company.setAddress(rs.getString("address"));
        company.setUser(rs.getString("userCompany"));
        company.setPassword(rs.getString("passwordCompany"));
        company.setRegisteredDate(rs.getDate("registeredDate"));
        company.setContractPersonName(rs.getString("contractPersonName"));
        company.setCompanyMail(rs.getString("email"));
        company.setServiceType(ServiceType.valueOf(rs.getString("typeOfService")));
        return company;
    }
}

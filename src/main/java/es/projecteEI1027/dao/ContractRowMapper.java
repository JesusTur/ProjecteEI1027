package es.projecteEI1027.dao;

import es.projecteEI1027.model.Contract;
import es.projecteEI1027.model.ServiceType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ContractRowMapper implements RowMapper<Contract> {
    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contract contract = new Contract();
        contract.setId(rs.getInt("id"));
        contract.setCif(rs.getString("cif"));
        contract.setTypeOfService(rs.getString("typeOfService"));
        contract.setFinalDate(rs.getDate("finalDate"));
        contract.setStartDate(rs.getDate("startDate"));
        contract.setQuantity(rs.getInt("quantity"));
        contract.setPriceUnit(rs.getFloat("priceUnit"));
        return contract;
    }
}

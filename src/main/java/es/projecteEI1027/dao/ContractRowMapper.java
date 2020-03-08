package es.projecteEI1027.dao;

import es.projecteEI1027.model.Contract;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ContractRowMapper implements RowMapper<Contract> {
    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contract contract = new Contract();
        contract.setPrice(rs.getFloat("price"));
        contract.setStartDate(rs.getDate("startDate"));
        contract.setFinalDate(rs.getDate("finalDate"));
        contract.setQuantity(rs.getInt("quantity"));
        return contract;
    }
}

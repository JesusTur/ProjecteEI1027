package es.projecteEI1027.dao;

import es.projecteEI1027.model.Request;
import es.projecteEI1027.model.RequestState;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestRowMapper implements RowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request request = new Request();
        request.setId(rs.getInt("id"));
        request.setDniBeneficiary(rs.getString("dniBeneficiary"));
        request.setTypeOfService(rs.getString("requestState"));
        request.setContractid(rs.getInt("conractId"));
        request.setRequestState(RequestState.valueOf(rs.getString("requestState")));
        request.setDateAccept(rs.getDate("dateAccept"));
        request.setDateReject(rs.getDate("dateReject"));
        request.setComment(rs.getString("comments"));
        request.setDateFinal(rs.getDate("dateFinal"));
        return request;
    }
}

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
        request.setDniBeneficiary(rs.getString("dnibeneficiary"));
        request.setTypeOfService(rs.getString("typeofservice"));
        request.setContractid(rs.getInt("contractid"));
        request.setSchedule(rs.getDate("creationdate"));
        request.setRequestState(RequestState.valueOf(rs.getString("requeststate")));
        request.setDateAccept(rs.getDate("dateaccept"));
        request.setDateReject(rs.getDate("datereject"));
        request.setComment(rs.getString("comments"));
        request.setDateFinal(rs.getDate("datefinal"));
        return request;
    }
}

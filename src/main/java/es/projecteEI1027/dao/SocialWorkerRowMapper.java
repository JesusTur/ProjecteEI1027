package es.projecteEI1027.dao;

import es.projecteEI1027.model.SocialWorker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public final class SocialWorkerRowMapper implements RowMapper<SocialWorker> {
    @Override
    public SocialWorker mapRow(ResultSet rs, int i) throws SQLException {
        SocialWorker socialWorker = new SocialWorker();
        socialWorker.setUserCAS(rs.getString("userCAS"));
        socialWorker.setPwd(rs.getString("passwordSocialWorker"));
        socialWorker.setName(rs.getString("name"));
        socialWorker.setEmail(rs.getString("email"));
        socialWorker.setPhoneNumber(rs.getString("phoneNumber"));
        return socialWorker;
    }
}

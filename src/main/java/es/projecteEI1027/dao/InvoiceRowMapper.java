package es.projecteEI1027.dao;

import es.projecteEI1027.model.Invoice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public final class InvoiceRowMapper implements RowMapper<Invoice> {

    public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setNinv(rs.getInt("ninv"));
        invoice.setDateinv(rs.getDate("dateinv"));
        invoice.setTotalPrice(rs.getInt("totalprice"));
        invoice.setDescription(rs.getString("description"));
        invoice.setDnibeneficiary(rs.getString("dnibeneficiary"));
        return invoice;
    }
}
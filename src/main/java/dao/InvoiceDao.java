package dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import model.Invoice;


@Repository
public class InvoiceDao {
    private JdbcTemplate jdbcTemplate;

    // Obté el jdbcTemplate a partir del Data Source
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addInvoice(Invoice invoice) {
        try{
            jdbcTemplate.update(
                    "INSERT INTO Invoice VALUES(?, ?, ?, ?, ?)",
                   invoice.getNinv(), invoice.getDateinv(), invoice.getTotalPrice(), invoice.getDescription(), invoice.getDnibeneficiary());
        }
        catch(DuplicateKeyException e) {
        }
    }

    public void deleteInvoice(String ninv) {
        try{
            jdbcTemplate.update("DELETE FROM Invoice WHERE ninv =?", ninv);
        }
        catch(DataAccessException e){
        }
    }

    public void updateInvoice(Invoice invoice) {
        try{
            jdbcTemplate.update("UPDATE Invoice SET dateinv = ?, totalprice = ?, description = ?, dnibeneficiary = ?",
                                invoice.getDateinv(),invoice.getTotalPrice(), invoice.getDescription(),  invoice.getDnibeneficiary());

        }
        catch (DataAccessException e){
        }
    }

    public List<Invoice> getInvoices() {
        try {
            return jdbcTemplate.query("SELECT * FROM Invoice",
                    new InvoiceRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Invoice>();
        }
    }
}
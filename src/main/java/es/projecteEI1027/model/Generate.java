package es.projecteEI1027.model;

import java.sql.Date;

public class Generate {
    private int id;
    private int nFact;
    private Date date;
    public Generate(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getnFact() {
        return nFact;
    }

    public void setnFact(int nFact) {
        this.nFact = nFact;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

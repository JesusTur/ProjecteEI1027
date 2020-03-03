package model;

import java.util.Date;

public class Generate {
    private String id;
    private int nFact;
    private Date date;
    public Generate(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

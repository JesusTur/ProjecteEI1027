package model;

import java.util.Date;

public class Generate {
    private String dni;
    private int nFact;
    private Date date;
    public Generate(){}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

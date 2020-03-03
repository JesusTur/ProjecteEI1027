package model;

import java.util.Date;

public class Invoice {
    private int nFact;
    private Date date;
    private float totalPrice;
    public Invoice(){}

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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}

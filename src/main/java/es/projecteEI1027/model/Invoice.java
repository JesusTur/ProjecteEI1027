package es.projecteEI1027.model;

import java.sql.Date;

public class Invoice {

    private int ninv;
    private Date dateinv;
    private float totalPrice;
    private String description;
    private String dnibeneficiary;

    public Invoice(){}


    public int getNinv() {
        return ninv;
    }

    public void setNinv(int ninv) {
        this.ninv = ninv;
    }

    public Date getDateinv() {
        return dateinv;
    }

    public void setDateinv(Date dateinv) {
        this.dateinv = dateinv;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDnibeneficiary() {
        return dnibeneficiary;
    }

    public void setDnibeneficiary(String dnibeneficiary) {
        this.dnibeneficiary = dnibeneficiary;
    }




}

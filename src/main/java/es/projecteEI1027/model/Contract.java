package es.projecteEI1027.model;

import java.sql.Date;

public class Contract {
    private int id;
    private String cif;
    ServiceType typeOfService;
    private Date startDate;
    private Date finalDate;
    private int quantity;
    public Contract(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public ServiceType getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(ServiceType typeOfService) {
        this.typeOfService = typeOfService;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

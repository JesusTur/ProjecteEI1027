package es.projecteEI1027.model;

import java.util.Date;

public class Contract {
    ServiceType typeOfService;
    private float price;
    private Date startDate;
    private Date finalDate;
    private int quantity;
    public Contract(){}

    public ServiceType getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(ServiceType typeOfService) {
        this.typeOfService = typeOfService;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

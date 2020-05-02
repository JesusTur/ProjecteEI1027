package es.projecteEI1027.model;

import java.sql.Date;

public class Request {
    private int id;
    private String dniBeneficiary;
    private int contractid;
    private String TypeOfService;
    private Date schedule;
    private RequestState requestState;
    private Date dateAccept;
    private Date dateReject;
    private String comment;
    private Date dateFinal;
    public Request(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfService() {
        return TypeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        TypeOfService="rejected";
        TypeOfService = typeOfService;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public RequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(RequestState requestState) {
        this.requestState = requestState;
    }

    public Date getDateAccept() {
        return dateAccept;
    }

    public void setDateAccept(Date dateAccept) {
        this.dateAccept = dateAccept;
    }

    public Date getDateReject() {
        return dateReject;
    }

    public void setDateReject(Date dateReject) {
        this.dateReject = dateReject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }

    public String getDniBeneficiary() {
        return dniBeneficiary;
    }

    public void setDniBeneficiary(String dniBeneficiary) {
        this.dniBeneficiary = dniBeneficiary;
    }

    public int getContractid() {
        return contractid;
    }

    public void setContractid(int contractid) {
        this.contractid = contractid;
    }
}

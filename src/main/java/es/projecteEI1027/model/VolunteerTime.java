package es.projecteEI1027.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VolunteerTime {
    private Date date;
    private String dniVolunteer;
    private String dniBeneficiary;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime beginningTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endingTime;
    private boolean available;
    public VolunteerTime(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDniVolunteer() {
        return dniVolunteer;
    }

    public void setDniVolunteer(String dniVolunteer) {
        this.dniVolunteer = dniVolunteer;
    }

    public String getDniBeneficiary() {
        return dniBeneficiary;
    }

    public void setDniBeneficiary(String dniBeneficiary) {
        this.dniBeneficiary = dniBeneficiary;
    }

    public LocalDateTime getBeginningTime() {
        return beginningTime;
    }

    public void setBeginningTime(LocalDateTime beginningTime) {
        this.beginningTime = beginningTime;
    }

    public LocalDateTime getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(LocalDateTime endingTime) {
        this.endingTime = endingTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

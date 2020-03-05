package model;

import java.sql.Time;
import java.util.Date;

public class VolunteerTime {
    private Date date;
    private String dniVolunteer;
    private String dniBeneficiary;
    private Time beginningHour;
    private Time endingHour;
    private boolean available;
    public VolunteerTime(){}

    public Date getDate() {
        return date;
    }

    public String getDniVolunteer() {
        return dniVolunteer;
    }

    public void setDniVolunteer(String dniVolunteer) {
        this.dniVolunteer = dniVolunteer;
    }

    public String getDniBeneficiary() {
        return this.dniBeneficiary;
    }

    public void setDniBeneficiary(String dniBeneficiary) {
        this.dniBeneficiary = dniBeneficiary;
    }

    public Time getBeginningHour() {
        return beginningHour;
    }

    public void setBeginningHour(Time beginningHour) {
        this.beginningHour = beginningHour;
    }

    public Time getEndingHour() {
        return endingHour;
    }

    public void setEndingHour(Time endingHour) {
        this.endingHour = endingHour;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

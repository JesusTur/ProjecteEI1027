package model;

import java.util.Date;

public class Volunteer {
    private String nameVolunteer;
    private String surnameVolunteer;
    private String dniVolunteer;
    private String phoneVolunteer;
    private Date birthDateVolunteer;
    private String typeServiceVolunteer;
    private String email;
    private String user;
    private String pwd;
    public Volunteer(){}

    public String getNameVolunteer() {
        return nameVolunteer;
    }

    public void setNameVolunteer(String nameVolunteer) {
        this.nameVolunteer = nameVolunteer;
    }

    public String getSurnameVolunteer() {
        return surnameVolunteer;
    }

    public void setSurnameVolunteer(String surnameVolunteer) {
        this.surnameVolunteer = surnameVolunteer;
    }

    public String getDniVolunteer() {
        return dniVolunteer;
    }

    public void setDniVolunteer(String dniVolunteer) {
        this.dniVolunteer = dniVolunteer;
    }

    public String getPhoneVolunteer() {
        return phoneVolunteer;
    }

    public void setPhoneVolunteer(String phoneVolunteer) {
        this.phoneVolunteer = phoneVolunteer;
    }

    public Date getBirthDateVolunteer() {
        return birthDateVolunteer;
    }

    public void setBirthDateVolunteer(Date birthDateVolunteer) {
        this.birthDateVolunteer = birthDateVolunteer;
    }

    public String getTypeServiceVolunteer() {
        return typeServiceVolunteer;
    }

    public void setTypeServiceVolunteer(String typeServiceVolunteer) {
        this.typeServiceVolunteer = typeServiceVolunteer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

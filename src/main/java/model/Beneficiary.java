package model;

import java.util.Date;

public class Beneficiary {
    private String dni;
    private String name;
    private String surname;
    private String homeAddress;
    private String phoneNumber;
    private String bankAccount;
    private Date birthDate;
    private String socialWorker;
    private String user;
    private String password;
    public Beneficiary(){}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSocialWorker() {
        return socialWorker;
    }

    public void setSocialWorker(String socialWorker) {
        this.socialWorker = socialWorker;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String usser) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

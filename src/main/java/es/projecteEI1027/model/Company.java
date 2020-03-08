package es.projecteEI1027.model;

import java.sql.Date;

public class Company {
    private String cif;
    private String nameCompany;
    private String address;
    private String user;
    private String phone;
    private String password;
    private Date registeredDate;
    private String contractPersonName;
    private String companyMail;
    private int checkingAccountNumber;
    public Company(){}

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getContractPersonName() {
        return contractPersonName;
    }

    public void setContractPersonName(String contractPersonName) {
        this.contractPersonName = contractPersonName;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public int getCheckingAccountNumber() {
        return checkingAccountNumber;
    }

    public void setCheckingAccountNumber(int checkingAccountNumber) {
        this.checkingAccountNumber = checkingAccountNumber;
    }
}

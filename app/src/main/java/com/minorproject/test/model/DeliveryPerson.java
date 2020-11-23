package com.minorproject.test.model;

public class DeliveryPerson {
    String name;
    String aadharCardNumber;
    String bankAccountNumber;
    String bankIFSC;
    String email;
    String panCardNumber;
    String phoneNumber;

    public DeliveryPerson() {
    }

    public DeliveryPerson(String name, String aadharCardNumber, String bankAccountNumber, String bankIFSC, String email, String panCardNumber, String phoneNumber) {
        this.name = name;
        this.aadharCardNumber = aadharCardNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.bankIFSC = bankIFSC;
        this.email = email;
        this.panCardNumber = panCardNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadharCardNumber() {
        return aadharCardNumber;
    }

    public void setAadharCardNumber(String aadharCardNumber) {
        this.aadharCardNumber = aadharCardNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankIFSC() {
        return bankIFSC;
    }

    public void setBankIFSC(String bankIFSC) {
        this.bankIFSC = bankIFSC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPanCardNumber() {
        return panCardNumber;
    }

    public void setPanCardNumber(String panCardNumber) {
        this.panCardNumber = panCardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

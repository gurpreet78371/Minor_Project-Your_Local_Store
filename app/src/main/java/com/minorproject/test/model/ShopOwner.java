package com.minorproject.test.model;

public class ShopOwner {
    String primaryContactName;
    String aadharCardNumber;
    String bankAccountNumber;
    String bankIFSC;
    String businessAddress;
    String businessPhoneNumber;
    String companyRegistrationNumber;
    String imageUrl;
    int numberOfOrders;
    String panCardNumber;
    String personalPhoneNumber;
    String residentialAddress;
    String vendorName;

    public ShopOwner() {
    }

    public ShopOwner(String primaryContactName, String aadharCardNumber, String bankAccountNumber, String bankIFSC, String businessAddress, String businessPhoneNumber, String companyRegistrationNumber, String imageUrl, int numberOfOrders, String panCardNumber, String personalPhoneNumber, String residentialAddress, String vendorName) {
        this.primaryContactName = primaryContactName;
        this.aadharCardNumber = aadharCardNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.bankIFSC = bankIFSC;
        this.businessAddress = businessAddress;
        this.businessPhoneNumber = businessPhoneNumber;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.imageUrl = imageUrl;
        this.numberOfOrders = numberOfOrders;
        this.panCardNumber = panCardNumber;
        this.personalPhoneNumber = personalPhoneNumber;
        this.residentialAddress = residentialAddress;
        this.vendorName = vendorName;
    }

    public String getPrimaryContactName() {
        return primaryContactName;
    }

    public void setPrimaryContactName(String primaryContactName) {
        this.primaryContactName = primaryContactName;
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

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessPhoneNumber() {
        return businessPhoneNumber;
    }

    public void setBusinessPhoneNumber(String businessPhoneNumber) {
        this.businessPhoneNumber = businessPhoneNumber;
    }

    public String getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public String getPanCardNumber() {
        return panCardNumber;
    }

    public void setPanCardNumber(String panCardNumber) {
        this.panCardNumber = panCardNumber;
    }

    public String getPersonalPhoneNumber() {
        return personalPhoneNumber;
    }

    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}

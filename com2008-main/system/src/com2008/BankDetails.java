package com2008;

public class BankDetails {

    private int bankId;
    private String bankName;
    private String bankNo;
    private String expDate;
    private int cvv;
    private String userId;
    private String cardHolderName;
    private int Cvv;

    public BankDetails(String userId, String bankName, String bankNo, String expDate, int cvv, String cardHolderName) {
    }

    public int getBankId() {
        return bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public int getCvv() {
        return Cvv;
    }
}

package org.mesttra.pojo;

/*
    Os clientes, independente do tipo, tem um número de conta, agência, telefone, saldo e limite de cheque especial.
*/

public abstract class Client {
    private static int SEQUENTIAL = 1;
    private static final int DEFAULT_AGENCY = 1;
    private static final int START_AMOUNT = 0;
    private int accountNumber, agency;
    private String phoneNumber;
    private double amount;
    private double overDraft;

    public Client(String phoneNumber, double overDraft) {
        this.accountNumber = SEQUENTIAL++;
        this.agency = DEFAULT_AGENCY;
        this.phoneNumber = phoneNumber;
        this.amount = START_AMOUNT;
        this.overDraft = overDraft;
    }

    public Client() {

    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAgency() {
        return agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(double overDraft) {
        this.overDraft = overDraft;
    }


    public String ToString() {
        return ("[AccountNumber: " + this.accountNumber + " " +
                "Agency: " + this.agency + " " +
                "PhoneNumber: " + this.phoneNumber + " " +
                "Amount: " + this.amount + " " +
                "OverDraft: " + this.overDraft + "]"
        );
    };
}

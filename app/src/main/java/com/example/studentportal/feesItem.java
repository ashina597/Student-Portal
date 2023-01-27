package com.example.studentportal;

public class feesItem {
    private String Receipt_no;
    private String Date;
    private String Description;
    private String Debit;
    private String Credit;
    private String Balance;
    private String sem;

    public feesItem(String Receipt_no, String Date, String Description, String Debit, String Credit, String Balance, String sem){
        this.Receipt_no = Receipt_no;
        this.Date = Date;
        this.Description = Description;
        this.Debit = Debit;
        this.Credit = Credit;
        this.Balance = Balance;
        this.sem = sem;
    }

    public feesItem(){

    }

    public String getReceipt_no(){
        return Receipt_no;
    }

    public String getDate(){
        return Date;
    }

    public String getDescription(){
        return Description;
    }

    public String getDebit(){
        return Debit;
    }

    public String getCredit(){
        return Credit;
    }

    public String getBalance() {
        return Balance;
    }

    public String getSem(){
        return sem;
    }
}

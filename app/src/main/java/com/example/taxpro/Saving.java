package com.example.taxpro;

import java.util.Date;

public class Saving
{
    private String type;
    private double rate;
    private int amount;
    private Date dateOfRegistration;
    private int totalTerm;
    private int period;


    public Saving () { }

    public String getType() { return type; }
    public double getRate() { return rate; }
    public int getAmount() { return amount; }
    public Date getDateOfRegistration() { return dateOfRegistration; }
    public int getTotalTerm() { return totalTerm; }

    public void setType(String type) { this.type = type; }
    public void setRate(double rate) { this.rate = rate; }

    public void setAmount(int amount) { this.amount = amount; }
    public void setDateOfRegistration(Date dateOfRegistration) { this.dateOfRegistration = dateOfRegistration; }
    public void setTotalTerm(int totalTerm) { this.totalTerm = totalTerm; }
}

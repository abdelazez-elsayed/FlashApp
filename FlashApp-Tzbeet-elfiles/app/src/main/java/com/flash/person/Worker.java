package com.flash.person;

public class Worker extends Person {

    private String description, workerId, specialization;
    private int numOfTransactions;
    private double rate;
    private int lowestEstimatedPrice, highestEstimatedPrice;

    public Worker() {}

    public String getWorkerId() {
        return workerId;
    }

    public Worker setDescription(String description) {
        this.description = description;
        return this;
    }

    public Worker setWorkerId(String workerId) {
        this.workerId = workerId;
        return this;
    }

    public Worker setSpecialization(String specialization) {
        this.specialization = specialization;
        return this;
    }

    public Worker setRate(double rate) {
        this.rate = rate;
        return this;
    }

    public Worker setNumOfTransactions(int numOfTransactions) {
        this.numOfTransactions = numOfTransactions;
        return this;
    }

    public Worker setLowestEstimatedPrice(int lowestEstimatedPrice) {
        this.lowestEstimatedPrice = lowestEstimatedPrice;
        return this;
    }

    public Worker setHighestEstimatedPrice(int highestEstimatedPrice) {
        this.highestEstimatedPrice = highestEstimatedPrice;
        return this;
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getRate() {
        return rate;
    }

    public int getNumOfTransactions() {
        return numOfTransactions;
    }

    public int getLowestEstimatedPrice() {
        return lowestEstimatedPrice;
    }

    public int getHighestEstimatedPrice() {
        return highestEstimatedPrice;
    }

    public String getDescription() {
        return description;
    }


}

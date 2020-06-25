package com.flash.person;

public class User extends Person {

    private int numOfOrders;
    private String userId;
    public double pocket;

    public User(){}

    public int getNumOfOrders() {
        return numOfOrders;
    }

    public User setNumOfOrders(int numOfOrders) {
        this.numOfOrders = numOfOrders;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public User setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public double getPocket() {
        return pocket;
    }

    public User setPocket(double pocket) {
        this.pocket = pocket;
        return this;
    }

}

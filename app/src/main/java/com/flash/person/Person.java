package com.flash.person;

import android.graphics.Bitmap;


public abstract class Person {
    private String email;
    private String username;
    private String phone;
    private String postalCode;
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Person(String email, String username, String phone, String postalCode) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.postalCode = postalCode;
    }

    public Person() {

    }


    public String getPostalCode() {
        return postalCode;
    }

    public Person setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }


    public static boolean isValidMail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        if(!isValidMail(Email)) return;
        this.email = Email;
    }

    public String getUsername() {
        return username;
    }

    public Person setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Person setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getEmail().equals(person.getEmail());
    }


    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}

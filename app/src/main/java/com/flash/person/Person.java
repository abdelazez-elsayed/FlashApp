package com.flash.person;

public abstract class Person {
    public String name;
    public String Email;
    public String username;
    public String password;
    public String phone;


    Person() {}


    public static boolean isValidMail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        if(!isValidMail(email)) return;
        Email = email;

    }

    public String getUsername() {
        return username;
    }

    public Person setUsername(String username) {
        this.username = username;
        return this;
    }


    public Person setPassword(String password) {
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}

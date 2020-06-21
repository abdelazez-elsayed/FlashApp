package FlashApp.com.person;

public abstract class Person {
    protected String name;
    protected String Email;
    protected String username;
    protected String password;
    protected String phone;

    private boolean isValidMail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public boolean setEmail(String email) {
        if(!isValidMail(email))return false;
        Email = email;
        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

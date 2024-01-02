import java.util.Date;

public class User {
    private String userId, name, gender, address, emial, password, role;
    private Date dateOfBirth, registrationDate;
    private int phoneNumber;

    private String getUserId() {
        return userId;
    }

    private String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getEmial() {
        return emial;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    protected void updateUser(){

    }

    // public boolean login(){
        
    // }
    

}

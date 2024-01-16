import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import java.io.*;
import java.text.SimpleDateFormat;

public class User {
    private String name, gender, address, email, password, role, dateOfBirth, registrationDate, phoneNumber, department, specialization, OfficeAddress;
    private static final String DATABASE_FILE_NAME = "./Hospital_Registration_System/src/user_credential.txt";

    //for normal user
    public User(String name, String gender, String address, String email, String password, String role, String dateOfBirth, String registrationDate, String phoneNumber) {
        
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.registrationDate = registrationDate;
        this.phoneNumber = phoneNumber;
    }

    //for doctor
    public User(String name, String gender, String address, String email, String password, String role, String dateOfBirth, String registrationDate, String phoneNumber, String department, String specialization, String OfficeAddress) {
        
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.registrationDate = registrationDate;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.specialization = specialization;
        this.OfficeAddress = OfficeAddress;
    }

    public User(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public User(){

    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getOfficeAddress() {
        return OfficeAddress;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setOfficeAddress(String OfficeAddress) {
        this.OfficeAddress = OfficeAddress;
    }
    
    protected void updateUser(){

    }

    public static Vector<User> readUsersFromFile() {
        Vector<User> users = new Vector<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DATABASE_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    User user = new User(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(),
                            parts[4].trim(), parts[5].trim(), parts[6].trim(), parts[7].trim(),
                            parts[8].trim());
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static void writeUserToFile(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATABASE_FILE_NAME, true))) {

            String userRecord = user.getEmail() + "," + user.getPassword() + "," + user.getRole() + ","
                    + user.getDateOfBirth() + "," + user.getRegistrationDate()
                    + "," + user.getName() + "," + user.getGender() + ","
                    + user.getAddress() + "," + user.getPhoneNumber() + ",-,-,-";
            bw.write(userRecord);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User login(String inputEmail, String inputPassword) {
        try (BufferedReader br = new BufferedReader(new FileReader(DATABASE_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12) { 
                    String storedEmail = parts[0].trim(); 
                    String storedPassword = parts[1].trim(); 
                    String role = parts[2].trim(); 

                    if (storedEmail.equals(inputEmail) && storedPassword.equals(inputPassword)) {
                        return new User(parts[5].trim(), parts[6].trim(), parts[7].trim(),
                                storedEmail, storedPassword, role, parts[3].trim(), parts[4].trim(),
                                parts[8].trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return null;
    }
    

    public static void displayAllUsers() {
        Vector<User> users = readUsersFromFile();

        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.printf("%-10s %-20s %-10s %-40s %-10s %-15s %-10s\n",
                    "Name", "Gender", "Email", "Date of Birth", "Phone Number", "Address", "Registration Date", "Role");
            System.out.println("===================================================================");

            for (User user : users) {
                System.out.printf("%-20s %-10s %-30s %-20s %-30s %-40s %-15s %-10s\n",
                        user.getName(), user.getGender(), user.getEmail(), user.getDateOfBirth(),
                        user.getPhoneNumber(), user.getAddress(), user.getRegistrationDate(), user.getRole());
            }
        }
    }
    public static void displayLoggedInUserInfo(User loggedInUser) {
        //System.out.println("============================================================================================================================================================");
        System.out.printf("| %-15s | %-10s | %-20s | %-14s | %-15s | %-30s | %-17s | %-10s |\n",
                "Name", "Gender", "Email", "Date of Birth", "Phone Number", "Address", "Registration Date", "Role");
        System.out.println("============================================================================================================================================================");
    
        System.out.printf("| %-15s | %-10s | %-20s | %-14s | %-15s | %-30s | %-17s | %-10s |\n",
                loggedInUser.getName(), loggedInUser.getGender(), loggedInUser.getEmail(),
                loggedInUser.getDateOfBirth(), loggedInUser.getPhoneNumber(), loggedInUser.getAddress(),
                loggedInUser.getRegistrationDate(), loggedInUser.getRole());
    
        //System.out.println("============================================================================================================================================================\n");
    }
    

}

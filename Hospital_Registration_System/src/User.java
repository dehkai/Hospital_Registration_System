import java.util.Date;
import javax.swing.JOptionPane;
import java.io.*;

public class User {
    private String userId, name, gender, address, email, password, role;
    private Date dateOfBirth, registrationDate;
    private int phoneNumber;
    private static final String DATABASE_FILE_NAME = "src/user_credential.txt";

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

    public String getEmail() {
        return email;
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



    public boolean login(String inputEmail, String inputPassword) {
       
        try (BufferedReader br = new BufferedReader(new FileReader(DATABASE_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String storedEmail = parts[0].trim();
                    String storedPassword = parts[1].trim();

                    if (storedEmail.equals(inputEmail) && storedPassword.equals(inputPassword)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean register(String newEmail, String newPassword) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATABASE_FILE_NAME, true))) {
            String userRecord = newEmail + "," + newPassword;
            bw.write(userRecord);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser() {
        String username = JOptionPane.showInputDialog(null, "Enter a username for registration:");
        String password = JOptionPane.showInputDialog(null, "Enter a password for registration:");

        User newUser = new User();
        return newUser.register(username, password);
    }
    
    

}

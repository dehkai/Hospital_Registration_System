import java.sql.Time;
import java.util.Date;

import javax.swing.JOptionPane;

public class Appointment {

    private String AppointmentId, status;
    private Date date;
    private Patient patient;
    private Time time;

    public Appointment(String AppointmentId, String status, Date date, Patient patient, Time time) {
        this.AppointmentId = AppointmentId;
        this.status = status;
        this.date = date;
        this.patient = patient;
        this.time = time;
    }

    public String getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(String AppointmentId) {
        this.AppointmentId = AppointmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getAppoinmentStatus() {
        return status;
    }

    public void setAppoinmentStatus(String status) {
        this.status = status;
    }
    public static void main(String[] args) {
        User user = new User();

        Object[] registerOptions = {"Yes", "No"};
        int registerChoice = JOptionPane.showOptionDialog(
                null,
                "Do you want to register?",
                "Hospital Registration System",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                registerOptions,
                registerOptions[1]
        );

        if (registerChoice == JOptionPane.YES_OPTION) {
            // Register
            boolean registrationSuccess = User.registerUser();
            if (registrationSuccess) {
                JOptionPane.showMessageDialog(null, "Registration successful! You can now log in.");
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
            }
        } else {
            // Ask if the user wants to login
            Object[] loginOptions = {"Yes", "No"};
            int loginChoice = JOptionPane.showOptionDialog(
                    null,
                    "Do you want to log in?",
                    "Hospital Registration System",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    loginOptions,
                    loginOptions[1]
            );

            if (loginChoice == JOptionPane.YES_OPTION) {
                // Login
                String username = JOptionPane.showInputDialog(null, "Username:", "Enter your username");
                String password = JOptionPane.showInputDialog(null, "Password:", "Enter your password");

                if (user.login(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    // Perform actions after successful login
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Invalid credentials.");
                }
            } else {
                // User chose not to register or login
                JOptionPane.showMessageDialog(null, "Thank you for using Hospital Registration System. Goodbye!");
            }
        }
    }
    }


    




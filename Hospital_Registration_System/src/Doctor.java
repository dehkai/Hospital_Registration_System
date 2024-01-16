import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    private String department, specialization, OfficeAddress;
    private static final String APPOINTMENT_FILE_NAME = "./Hospital_Registration_System/src/AppointmentList.txt";
    private static final String USER_FILE_NAME = "./Hospital_Registration_System/src/user_credential.txt";
    private static final String PATIENT_MEDICAL_RECORD_FILE_NAME = "./Hospital_Registration_System/src/PatientMediacalRecord.txt";

    
    public Doctor(String email, String name, String gender, String department, String specialization, String phoneNumber, String OfficeAddress) {
        super(name, phoneNumber);
        this.department = department;
        this.specialization = specialization;
        this.OfficeAddress = OfficeAddress;
    }
    public Doctor(){

    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getOfficeAddress() {
        return OfficeAddress;
    }

    public void setOfficeAddress(String OfficeAddress) {
        this.OfficeAddress = OfficeAddress;
    }

    public static void viewAppointment(String email) {
        
    }

    public List<Appointment> getAvailableAppointmentSlots(){
        return null;
        
    }

    public static void displayDoctorInterface(String email, User loggedInUser) throws FileNotFoundException, IOException{
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
        System.out.println("\n\tDoctor Dashboard");
        System.out.println("\t================");
        System.out.println("\n1. View Personal Information");
        System.out.println("2. View Appointment");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Logout");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println();
                User.displayLoggedInUserInfo(loggedInUser);
                break;
                
            case 2:
                viewAppointment(email);
                break;
            case 3:
                
                break;
            case 4:
                System.out.println("\nThank you for using Hospital Management System. Goodbye!");
                Appointment.clearConsole(3);
                Appointment.displayUserInterface();
                break;
            
        }
        
        } while (choice != 5);
        scanner.close(); 
    }
}
    



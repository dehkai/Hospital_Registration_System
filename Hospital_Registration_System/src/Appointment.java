import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class Appointment {

    private String patientEmail, doctorEmail, status, date, time;
    // private Date date;
    private Patient patient;
    // private Time time;
    private static final String PATIENT_INFO_FILE = "./Hospital_Registration_System/src/patientInfo.txt";
    private static final String MEDICAL_RECORD_FILE = "./Hospital_Registration_System/src/MedicalRecord.txt";
    

    public Appointment(String patientEmail, String doctorEmail, String status, String date, String time) {
        this.patientEmail = patientEmail;
        this.doctorEmail = doctorEmail;
        this.status = status;
        this.date = date;
        this.time = time;
    }


    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateTime() {
        // Combine date and time strings and return a representation suitable for sorting
        return date + " " + time;
    }

    public String getAppoinmentStatus() {
        return status;
    }

    public void setAppoinmentStatus(String status) {
        this.status = status;
    }

    public static void registerUser(String email) {
        // Placeholder method for user registration

        // Assume the user is a patient for simplicity
        Patient patient = new Patient("email", "-", "-");

        // Save patient information to patientInfo.txt
        writePatientInfoToFile(patient, email);

        // Save medical record information to MedicalRecord.txt
        writeMedicalRecordToFile(email, "-");
    }

    private static void writePatientInfoToFile(Patient patient, String email) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENT_INFO_FILE, true))) {
            String patientInfo = email + ","
                    + patient.getMedicalRecord() + "," + patient.getInsuranceProvider();
            bw.write(patientInfo);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeMedicalRecordToFile(String email, String medicalRecord) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEDICAL_RECORD_FILE, true))) {
            String medicalRecordInfo = email + "," + medicalRecord;
            bw.write(medicalRecordInfo);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void clearConsole(int delayInSeconds) {
        try {
            final String os = System.getProperty("os.name");
    
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "timeout", "/t", String.valueOf(delayInSeconds), "&&", "cls")
                    .inheritIO().start().waitFor();
            } else {
                Thread.sleep(delayInSeconds * 1000);
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void displayUserInterface() throws FileNotFoundException, IOException{
        
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
        System.out.println("Welcome to Hospital Management System\n");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Quit");
        System.out.print("\nChoose an option (1-3): ");

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Your will be redirected to the registration page in 3 seconds.");
                clearConsole(3);
                System.out.println("\n\t\t\tRegister Account");
                System.out.println("\t\t\t----------------");
                System.out.println("\nPlease fill in the following details to register an account.");
                System.out.print("\nEnter your name: ");
                String name = scanner.nextLine();
                System.out.print("Enter your email: ");
                String email = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                System.out.print("Enter your gender (MALE/FEMALE): ");
                String gender = scanner.nextLine();
                System.out.print("Enter your address: ");
                String address = scanner.nextLine();
                System.out.print("Enter your phone number (Start with 60): ");
                String phoneNumber = scanner.nextLine();
                System.out.print("Enter your date of birth (YYYY-MM-DD): ");
                String dateOfBirth = scanner.nextLine();
                LocalDate registrationDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedRegistrationDate = registrationDate.format(formatter);

                User newUser = new User();
                
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setEmail(email);
                newUser.setAddress(address);
                newUser.setGender(gender);
                newUser.setPhoneNumber(phoneNumber);
                newUser.setDateOfBirth(dateOfBirth);
                newUser.setRegistrationDate(formattedRegistrationDate);
                newUser.setRole("Patient");

                User.writeUserToFile(newUser);
                registerUser(email);

                
                System.out.println("\nRegistration successful! Please proceed to login with your email and password.\n");
                clearConsole(3);
                
                break;
                
                case 2:
                System.out.println("Your will be redirected to the login page in 3 seconds.");
                clearConsole(3);
                System.out.println("\n\t\tLogin");
                System.out.println("\t\t-----");
                System.out.println("\nPlease fill in your email and password to login.");
                System.out.print("\nEnter your email: ");
                String loginEmail = scanner.nextLine();
                char[] passwordArray = System.console().readPassword("Enter your password: ");
                String loginPassword = new String(passwordArray);
                User loginUser = new User();
                User loggedInUser = loginUser.login(loginEmail, loginPassword);

                if (loggedInUser != null) {
                    System.out.println("\nLogin successful! Your will be redirected to your dashboard in 3 seconds.");
                    clearConsole(3);

                    if (loggedInUser.getRole().equals("Patient")) {
                        System.out.println("Welcome " + loggedInUser.getName() + ". User's role: " + loggedInUser.getRole());
                        Patient.displayPatientInterface(loginEmail, loggedInUser);
                    } else if (loggedInUser.getRole().equals("Doctor")) {
                        System.out.println("Welcome " + "Dr." + loggedInUser.getName() + ". User's role: " + loggedInUser.getRole());
                        Doctor.displayDoctorInterface(loginEmail, loggedInUser);
                    } else if (loggedInUser.getRole().equals("Admin")) {
                        System.out.println("Welcome " + loggedInUser.getName() + ". User's role: " + loggedInUser.getRole());
                        Admin.displayAdminInterface(loggedInUser);
                    }
                } else {
                    System.out.println("Login failed. Press any key to go back to the main menu.");
                    scanner.next(); // Wait for any key input
                }
                break;

            case 3:
                System.out.println("\nThank you for using Hospital Management System. Goodbye!");
                break; // No need to exit the program here
        }
               
    
    }
    while (choice != 3);
    //scanner.close();
    }
    public static void main(String[] args) throws FileNotFoundException, IOException   {

        displayUserInterface();
        
    }

    
    

    
}


    




import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.io.Console;

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
    
    public static void displayUserInterface(){
        
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
                        //Patient.displayPatientInterface();
                    } else if (loggedInUser.getRole().equals("Doctor")) {
                        System.out.println("Welcome " + "Dr."+loggedInUser.getName() + ". User's role: " + loggedInUser.getRole());
                        Doctor.displayDoctorInterface(loggedInUser);
                    } else if (loggedInUser.getRole().equals("Admin")) {
                        System.out.println("Welcome " + "Dr."+loggedInUser.getName() + ". User's role: " + loggedInUser.getRole());
                        Admin.displayAdminInterface(loggedInUser);
                    }
                } else {
                    System.out.println("Login failed. Invalid credentials.");
                }
                break;
            case 3:
                System.out.println("\nThank you for using Hospital Management System. Goodbye!");
                System.exit(0);
                break;
               
    
    }
    
    } while (choice != 2 && choice != 3);
    scanner.close(); 
    }
    public static void main(String[] args) {
        displayUserInterface();
        
    }
    }


    




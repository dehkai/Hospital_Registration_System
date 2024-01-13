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
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        int choice;
        do {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Hospital Management System\n");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Quit");
        System.out.print("\nChoose an option (1-3): ");

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
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

                clearConsole();
                System.out.println("\nRegistration successful! Please proceed to login with your email and password.");
                
                break;
                
            case 2:
                System.out.print("\nEnter your email: ");
                String loginEmail = scanner.nextLine();
                char[] passwordArray = System.console().readPassword("Enter your password: ");
                String loginPassword = new String(passwordArray);
                User loginUser = new User();
                User loggedInUser = loginUser.login(loginEmail, loginPassword);
                if (loggedInUser != null) {
                    clearConsole();
                    System.out.println("Login successful! Welcome " + loggedInUser.getName() + ". User's role: " + loggedInUser.getRole());
                    if (loggedInUser.getRole().equals("Patient")) {
                        //Patient.displayPatientInterface();
                    } else if (loggedInUser.getRole().equals("doctor")) {
                        // Handle doctor login
                    } else if (loggedInUser.getRole().equals("admin")) {
                        // Handle admin login
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
    }
    //     User user = new User();
    //     Console console = System.console();
        
    //     Object[] registerOptions = {"Yes", "No"};
    //     int registerChoice = JOptionPane.showOptionDialog(
    //             null,
    //             "Do you want to register?",
    //             "Hospital Registration System",
    //             JOptionPane.YES_NO_OPTION,
    //             JOptionPane.QUESTION_MESSAGE,
    //             null,
    //             registerOptions,
    //             registerOptions[1]
    //     );

    //     if (registerChoice == JOptionPane.YES_OPTION) {
    //         boolean registrationSuccess = User.registerUser();
    //         if (registrationSuccess) {
    //             JOptionPane.showMessageDialog(null, "Registration successful! You can now log in.");
    //         } else {
    //             JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
    //         }
    //     } else {
    //         Object[] loginOptions = {"Yes", "No"};
    //         int loginChoice = JOptionPane.showOptionDialog(
    //                 null,
    //                 "Do you want to log in?",
    //                 "Hospital Registration System",
    //                 JOptionPane.YES_NO_OPTION,
    //                 JOptionPane.QUESTION_MESSAGE,
    //                 null,
    //                 loginOptions,
    //                 loginOptions[1]
    //         );

    //         if (loginChoice == JOptionPane.YES_OPTION) {
    //             String username = JOptionPane.showInputDialog(null, "Username:", "Enter your username");
    //             String password = JOptionPane.showInputDialog(null, "Password:", "Enter your password");

    //             String role = user.login(username, password);

            //     if (role != null) {
            //         console.printf("Login successful! User's role: %s%n", role);
            //         //JOptionPane.showMessageDialog(null,"Login successful! User's role: " + role);   
            //         if(role.equals("patient")){
            //             Patient.displayPatientInterface();
            //         }else if(role.equals("doctor")){
                        
            //         }else if(role.equals("admin")){
                         
            //         }
            //     } else {
            //         JOptionPane.showMessageDialog(null, "Login failed. Invalid credentials.");
            //     }
            // } else {
            //     JOptionPane.showMessageDialog(null, "Thank you for using Hospital Registration System. Goodbye!");
            // }
    //     }
    // }
    }


    




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User{
    
    private static List<Doctor> doctors = new ArrayList<>();
    private static final String DATABASE_FILE_NAME = "src/doctorlist.txt";

    public static void addDoctor(String name, String gender, String department, String specialization, String phoneNumber, String officeAddress) {
        Doctor newDoctor = new Doctor(name, gender, department, specialization, phoneNumber, officeAddress);
        doctors.add(newDoctor);

        // Write doctor's information to doctorlist.txt
        writeDoctorToFile(newDoctor);

        System.out.println("Doctor added successfully.");
    }
    

    public void removeDoctor(){
        
    }

    public Doctor getDoctor(){
        return null;
    }

    public List<Appointment> getAppointment(){
        return null;
    }

    public void admitpatient(){
        
    }

    public void dischargePatient(){
        
    }

    public void addPatientRecord(){
        
    }
    private static void writeDoctorToFile(Doctor doctor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATABASE_FILE_NAME, true))) {
            String doctorList = doctor.getName() + "," + doctor.getDepartment() + "," + doctor.getSpecialization() + ","
                    + doctor.getPhoneNumber() + "," + doctor.getOfficeAddress();
            bw.write(doctorList);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to doctorlist.txt: " + e.getMessage());
        }
    }

    public static void displayAdminInterface(User loggedInUser){
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
        System.out.println("\n\tAdmin Dashboard");
        System.out.println("\t===============");
        System.out.println("\n1. Add Doctor");
        System.out.println("2. Delete Doctor");
        System.out.println("3. View Doctor List");
        System.out.println("4. View Appointment List");
        System.out.println("5. Admit Patient");
        System.out.println("6. Discharge Patient");
        System.out.println("7. Add Patient Record");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("\nEnter new doctor's name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new doctor's department: ");
                String department = scanner.nextLine();
                System.out.print("Enter new doctor's specialization: ");
                String specialization = scanner.nextLine();
                System.out.print("Enter doctor's email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                System.out.print("Enter doctor's gender (MALE/FEMALE): ");
                String gender = scanner.nextLine();
                System.out.print("Enter doctor's address: ");
                String address = scanner.nextLine();
                System.out.print("Enter doctor's office address: ");
                String officeAddress = scanner.nextLine();
                System.out.print("Enter doctor's phone number (Start with 60): ");
                String phoneNumber = scanner.nextLine();
                System.out.print("Enter doctor's date of birth (YYYY-MM-DD): ");
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
                newUser.setRole("Doctor");

                User.writeUserToFile(newUser);

                
                Doctor newDoctor = new Doctor();
                newDoctor.setName(name);
                newDoctor.setGender(gender);
                newDoctor.setDepartment(department);
                newDoctor.setSpecialization(specialization);
                newDoctor.setPhoneNumber(phoneNumber);
                newDoctor.setOfficeAddress(officeAddress);
                // Add the new doctor to the list
                addDoctor(name, gender, department, specialization, phoneNumber, officeAddress);
                break;
            
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
                
                break;
            case 5:
                System.out.println("\nThank you for using Hospital Management System. Goodbye!");
                Appointment.clearConsole(3);
                Appointment.displayUserInterface();
                break;
                
        
        }
        
        } while (choice != 5);
        scanner.close(); 
    }
    
}

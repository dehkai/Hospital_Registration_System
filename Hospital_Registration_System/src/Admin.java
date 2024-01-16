import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Admin extends User{
    
    private static List<Doctor> doctors = new ArrayList<>();
    private static final String DATABASE_FILE_NAME = "./Hospital_Registration_System/src/doctorlist.txt";

    public static void addDoctor() {
        Scanner scanner = new Scanner(System.in);
    
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
        newUser.setAddress(address);
        newUser.setGender(gender);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setDateOfBirth(dateOfBirth);
        newUser.setRegistrationDate(formattedRegistrationDate);
        newUser.setRole("Doctor");
        newUser.setSpecialization(specialization);
        newUser.setDepartment(department);
        newUser.setOfficeAddress(officeAddress);
    
        // Save doctor's information to user_credential.txt
        writeDoctorToFile(newUser);
    
        // Save doctor's information to doctorlist.txt
        writeDoctorListToFile(email, name, phoneNumber, department, specialization, officeAddress);
    
        System.out.println("Doctor added successfully.");
    }
    
    private static void writeDoctorListToFile(String email, String name, String phoneNumber, String department, String specialization, String officeAddress) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./Hospital_Registration_System/src/doctorlist.txt", true))) {
            String doctorInfo = email + "," + name + "," + phoneNumber + "," + department + "," + specialization + "," + officeAddress;
            bw.write(doctorInfo);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to doctorlist.txt: " + e.getMessage());
        }
    }
    

    public static void removeDoctor() throws IOException {
        Vector<User> doctors = readDoctorsFromFile();

        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            System.out.printf("%-10s %-20s %-30s %-15s\n", "Name", "Department", "Specialization", "Email");
            System.out.println("====================================================");

            for (User doctor : doctors) {
                System.out.printf("%-20s %-30s %-30s %-15s\n", doctor.getName(), doctor.getDepartment(),
                        doctor.getSpecialization(), doctor.getEmail());
            }

            System.out.print("\nEnter the email of the doctor to remove: ");
            Scanner scanner = new Scanner(System.in);
            String doctorEmailToRemove = scanner.nextLine();

            List<User> updatedDoctors = new ArrayList<>();
            for (User doctor : doctors) {
                if (!doctor.getEmail().equals(doctorEmailToRemove)) {
                    updatedDoctors.add(doctor);
                }
            }

            writeUsersToFile(updatedDoctors);

            System.out.println("Doctor removed successfully.");
        }
    }

    public static void viewDoctorList() {
        try (BufferedReader br = new BufferedReader(new FileReader(DATABASE_FILE_NAME))) {
            System.out.println("\nDoctor List:");
            System.out.printf("%-20s %-30s %-15s %-20s %-20s %-30s\n", "Email", "Name", "Phone Number", "Department", "Specialization", "Office Address");
            System.out.println("===================================================================");
    
            String line;
            while ((line = br.readLine()) != null) {
                String[] doctorInfo = line.split(",");
                String email = doctorInfo[0];
                String name = doctorInfo[1];
                String phoneNumber = doctorInfo[2];
                String department = doctorInfo[3];
                String specialization = doctorInfo[4];
                String officeAddress = doctorInfo[5];
    
                System.out.printf("%-20s %-30s %-15s %-20s %-20s %-30s\n", email, name, phoneNumber, department, specialization, officeAddress);
            }
        } catch (IOException e) {
            System.err.println("Error reading doctorlist.txt: " + e.getMessage());
        }
    }
    
    
    

    // Other methods...

    private static Vector<User> readDoctorsFromFile() {
        Vector<User> doctors = new Vector<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DATABASE_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12 && parts[2].equalsIgnoreCase("Doctor")) {
                    User doctor = new User(parts[5].trim(), parts[6].trim(), parts[7].trim(),
                            parts[3].trim(), parts[4].trim(), parts[2].trim(), parts[8].trim(),
                            parts[9].trim(), parts[10].trim(), parts[11].trim(), line, line);
                    doctors.add(doctor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    private static void writeUsersToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILE_NAME))) {
            for (User user : users) {
                writer.write(user.getEmail() + "," + user.getPassword() + "," + user.getRole() + ","
                        + user.getDateOfBirth() + "," + user.getRegistrationDate()
                        + "," + user.getName() + "," + user.getGender() + ","
                        + user.getAddress() + "," + user.getPhoneNumber() + "," + user.getDepartment() + "," + user.getSpecialization() + "," + user.getOfficeAddress());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDoctorToFile(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATABASE_FILE_NAME, true))) {

            String userRecord = user.getEmail() + "," + user.getPassword() + "," + user.getRole() + ","
                    + user.getDateOfBirth() + "," + user.getRegistrationDate()
                    + "," + user.getName() + "," + user.getGender() + ","
                    + user.getAddress() + "," + user.getPhoneNumber() + "," + user.getDepartment() + "," + user.getSpecialization() + "," + user.getOfficeAddress();
            bw.write(userRecord);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
            
    public static void viewAppointmentList(){
        
    }
               
    public static void admitPatient(){

    }
    
    public static void dischargePatient(){

    }
        
    public static void addPatientRecord(){
        
    }

    public static void displayAdminInterface(User loggedInUser) throws FileNotFoundException, IOException{
        System.out.println("loggedInUser:" + loggedInUser);
        int choice;
        Scanner scanner = new Scanner(System.in);

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
        System.out.println("8. Log out");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addDoctor();
                break;
            
            case 2:
                removeDoctor();
                break;

            case 3:
                viewDoctorList();
                break;

            case 4:
                viewAppointmentList();
                break;

            case 5:
                admitPatient();
                break;

            case 6:
                dischargePatient();
                break;

            case 7:
                addPatientRecord();
                break;

            case 8:
                System.out.println("\nThank you for using Hospital Management System. Goodbye!");
                Appointment.clearConsole(3);
                Appointment.displayUserInterface();
                break;
                
        
        }
        
        } while (choice != 5);
        scanner.close(); 
    }

    
    
}

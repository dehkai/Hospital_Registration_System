import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Admin extends User {

    private static final String DOCTOR_FILE_NAME = "./Hospital_Registration_System/src/doctorlist.txt";
    private static final String USER_FILE_NAME = "./Hospital_Registration_System/src/user_credential.txt";
    private static final String APPOINTMENT_FILE_NAME = "./Hospital_Registration_System/src/AppointmentList.txt";
    private static final String PATIENT_INFO_FILE = "./Hospital_Registration_System/src/PatientInfo.txt";

    //addDoctor()
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

    public static void writeDoctorToFile(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE_NAME, true))) {

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

    private static void writeDoctorListToFile(String email, String name, String phoneNumber, String department, String specialization, String officeAddress) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTOR_FILE_NAME, true))) {
            String doctorInfo = email + "," + name + "," + phoneNumber + "," + department + "," + specialization + "," + officeAddress;
            bw.write(doctorInfo);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to doctorlist.txt: " + e.getMessage());
        }
    }

    //removeDoctor()
    private static void removeDoctor() {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_FILE_NAME));
             BufferedWriter bw = new BufferedWriter(new FileWriter("tempDoctorList.txt"))) {
    
            System.out.println("\nDoctor List:");
            System.out.printf("%-5s %-30s %-20s %-15s %-20s %-20s %-30s\n", "No.", "Name", "Email", "Phone Number", "Department", "Specialization", "Office Address");
            System.out.println("===================================================================================");
    
            List<String> doctorList = new ArrayList<>();
            String line;
            int doctorNumber = 1;
    
            while ((line = br.readLine()) != null) {
                String[] doctorInfo = line.split(",");
                String email = doctorInfo[0];
                String name = doctorInfo[1];
                String phoneNumber = doctorInfo[2];
                String department = doctorInfo[3];
                String specialization = doctorInfo[4];
                String officeAddress = doctorInfo[5];
    
                System.out.printf("%-5d %-30s %-20s %-15s %-20s %-20s %-30s\n",
                        doctorNumber, name, email, phoneNumber, department, specialization, officeAddress);
                doctorList.add(line);
                doctorNumber++;
            }
    
            // Prompt the admin to select a doctor for removal
            System.out.print("\nEnter the doctor number to remove (enter '-' to go back): ");
            Scanner scanner = new Scanner(System.in);
            String selectedDoctorNumberInput = scanner.nextLine();
    
            if (selectedDoctorNumberInput.equals("-")) {
                System.out.println("\nGoing back to the last page.");
                return;
            }
    
            try {
                int selectedDoctorNumber = Integer.parseInt(selectedDoctorNumberInput);
    
                // Validate if the entered number corresponds to a valid doctor
                if (selectedDoctorNumber >= 1 && selectedDoctorNumber <= doctorList.size()) {
                    String selectedDoctor = doctorList.get(selectedDoctorNumber - 1);
    
                    // Display confirmation message
                    System.out.println("\nAre you sure you want to remove the following doctor?");
                    System.out.println(selectedDoctor);
                    System.out.print("Confirm removal (yes/no): ");
                    String confirmation = scanner.nextLine().trim().toLowerCase();
    
                    if (confirmation.equals("yes")) {
                        // Extract doctor email for removal
                        String[] doctorInfo = selectedDoctor.split(",");
                        String doctorEmail = doctorInfo[0].trim();
    
                        // Remove doctor from doctorlist.txt
                        doctorList.remove(selectedDoctor);
    
                        for (String doctor : doctorList) {
                            bw.write(doctor);
                            bw.newLine();
                        }
    
                        // Remove doctor from user_credential.txt
                        if (removeUser(doctorEmail)) {
                            System.out.println("Doctor removed successfully.");
                        } else {
                            System.out.println("Error removing doctor. Please try again.");
                        }
                    } else {
                        System.out.println("Doctor removal canceled.");
                    }
                } else {
                    System.out.println("Invalid input. Going back to the last page.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Going back to the last page.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean removeUser(String email) {
        List<String> userList = new ArrayList<>();
        boolean userRemoved = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE_NAME));
             BufferedWriter bw = new BufferedWriter(new FileWriter("tempUserList.txt"))) {
    
            String line;
            while ((line = br.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo.length == 12 && userInfo[0].trim().equals(email)) {
                    userRemoved = true;
                } else {
                    userList.add(line);
                }
            }
    
            for (String user : userList) {
                bw.write(user);
                bw.newLine();
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Rename the temporary file to the original file
        File tempFile = new File("tempUserList.txt");
        File originalFile = new File(USER_FILE_NAME);
    
        if (tempFile.renameTo(originalFile)) {
            return userRemoved;
        } else {
            return false;
        }
    }
    
    




    //viewDoctorList()
private static void viewDoctorList(){
        
    }


    //viewAppointment()
    private static void viewAppointment(){
        
    }


    //admitPatient
    private static void admitPatient(){
        
    }


    //dischargePatient()
    private static void dischargePatient(){
        
    }




    //viewAllUsers()
    private static void viewAllUsers(){
        
    }






    public static void displayAdminInterface(User loggedInUser) throws FileNotFoundException, IOException {
        System.out.println("loggedInUser:" + loggedInUser);
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\n\tAdmin Dashboard");
            System.out.println("\t===============");
            System.out.println("\n1. Add Doctor");
            System.out.println("2. Delete Doctor");
            System.out.println("3. View Doctor List");
            System.out.println("4. View Appointment List");
            System.out.println("5. Admit Patient");
            System.out.println("6. Discharge Patient");
            System.out.println("7. View All User");
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
                    viewAppointment();
                    break;

                case 5:
                    admitPatient();
                    break;

                case 6:
                    dischargePatient();
                    break;

                case 7:
                    viewAllUsers();
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



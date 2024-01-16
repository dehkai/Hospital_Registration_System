import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Admin extends User {

    private static final String DOCTOR_FILE_NAME = "./Hospital_Registration_System/src/doctorlist.txt";
    private static final String USER_FILE_NAME = "./Hospital_Registration_System/src/user_credential.txt";
    private static final String APPOINTMENT_FILE_NAME = "./Hospital_Registration_System/src/AppointmentList.txt";


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

    private static void writeDoctorListToFile(String email, String name, String phoneNumber, String department, String specialization, String officeAddress) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTOR_FILE_NAME, true))) {
            String doctorInfo = email + "," + name + "," + phoneNumber + "," + department + "," + specialization + "," + officeAddress;
            bw.write(doctorInfo);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to doctorlist.txt: " + e.getMessage());
        }
    }

    private static Vector<User> readDoctorsFromFile() {
        Vector<User> doctors = new Vector<>();

        try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_FILE_NAME))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE_NAME))) {
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


    public static void viewAppointmentList() {
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME));
             BufferedReader userReader = new BufferedReader(new FileReader(USER_FILE_NAME));
             BufferedReader doctorReader = new BufferedReader(new FileReader(DOCTOR_FILE_NAME))) {

            System.out.println("\nAppointment List:");
            System.out.printf("%-5s %-20s %-20s %-20s %-15s\n", "No.", "Patient Email", "Doctor Email", "Date", "Status");
            System.out.println("===================================================");

            String line;
            Vector<String> appointments = new Vector<>();

            int count = 1;
            while ((line = br.readLine()) != null) {
                String[] appointmentInfo = line.split(",");
                String patientEmail = appointmentInfo[0];
                String doctorEmail = appointmentInfo[1];
                String date = appointmentInfo[3];
                String status = appointmentInfo[2];

                System.out.printf("%-5d %-20s %-20s %-20s %-15s\n", count, patientEmail, doctorEmail, date, status);
                appointments.add(line);
                count++;
            }

            System.out.print("\nEnter the appointment number to view details (or '-' to go back): ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (!input.equals("-")) {
                try {
                    int appointmentNumber = Integer.parseInt(input);
                    if (appointmentNumber >= 1 && appointmentNumber <= appointments.size()) {
                        String selectedAppointment = appointments.get(appointmentNumber - 1);
                        String[] appointmentInfo = selectedAppointment.split(",");
                        String patientEmail = appointmentInfo[0];
                        String doctorEmail = appointmentInfo[1];

                        // Display patient information
                        displayUserInformation(userReader, patientEmail);

                        // Display doctor information
                        displayDoctorInformation(doctorReader, doctorEmail);

                        // Update appointment status
                        updateAppointmentStatus(br, appointmentNumber);
                    } else {
                        System.out.println("Invalid appointment number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayUserInformation(BufferedReader userReader, String email) throws IOException {
        String userLine;
        while ((userLine = userReader.readLine()) != null) {
            String[] userInfo = userLine.split(",");
            if (userInfo[0].equals(email)) {
                System.out.println("\nPatient Information:");
                System.out.printf("%-20s %-20s %-15s %-15s\n", "Name", "Gender", "Phone Number", "Date of Birth");
                System.out.println("===================================================");

                System.out.printf("%-20s %-20s %-15s %-15s\n", userInfo[5], userInfo[6], userInfo[8], userInfo[3]);
                break;
            }
        }
    }

    private static void displayDoctorInformation(BufferedReader doctorReader, String email) throws IOException {
        String doctorLine;
        while ((doctorLine = doctorReader.readLine()) != null) {
            String[] doctorInfo = doctorLine.split(",");
            if (doctorInfo[0].equals(email)) {
                System.out.println("\nDoctor Information:");
                System.out.printf("%-20s %-30s %-15s %-20s %-20s\n", "Name", "Department", "Specialization", "Phone Number", "Office Address");
                System.out.println("===================================================");

                System.out.printf("%-20s %-30s %-15s %-20s %-20s\n", doctorInfo[1], doctorInfo[3], doctorInfo[4], doctorInfo[2], doctorInfo[5]);
                break;
            }
        }
    }

    private static void updateAppointmentStatus(BufferedReader br, int appointmentNumber) throws IOException {
        System.out.print("\nEnter the new status (Pending/Approved/Not Approved): ");
        Scanner scanner = new Scanner(System.in);
        String newStatus = scanner.nextLine().trim();

        if (newStatus.equals("Pending") || newStatus.equals("Approved") || newStatus.equals("Not Approved")) {
            // Update the status in the appointment list
            Vector<String> updatedAppointments = new Vector<>();
            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                if (count == appointmentNumber) {
                    String[] appointmentInfo = line.split(",");
                    appointmentInfo[3] = newStatus;
                    line = String.join(",", appointmentInfo);
                }
                updatedAppointments.add(line);
                count++;
            }

            // Rewrite the updated appointments to the file
            rewriteAppointmentsToFile(updatedAppointments);
            System.out.println("Appointment status updated successfully.");
        } else {
            System.out.println("Invalid status. The status must be Pending, Approved, or Not Approved.");
        }
    }

    private static void rewriteAppointmentsToFile(Vector<String> appointments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPOINTMENT_FILE_NAME))) {
            for (String appointment : appointments) {
                writer.write(appointment);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
                       
            public static void admitPatient(){
        
            }
            
            public static void dischargePatient(){
        
            }
                
            public static void addPatientRecord(){
                
            }

    public static void viewDoctorList() {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_FILE_NAME))) {
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



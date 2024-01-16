import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Admin extends User {

    private static final String DOCTOR_FILE_NAME = "./Hospital_Registration_System/src/doctorlist.txt";
    private static final String USER_FILE_NAME = "./Hospital_Registration_System/src/user_credential.txt";
    private static final String APPOINTMENT_FILE_NAME = "./Hospital_Registration_System/src/AppointmentList.txt";
    private static final String PATIENT_INFO_FILE = "./Hospital_Registration_System/src/PatientInfo.txt";


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


    public static void viewAppointment() {
        Scanner scanner = new Scanner(System.in);

        // Display the appointment list
        System.out.println("\nAppointment List:");
        System.out.printf("%-5s %-25s %-25s %-15s %-10s %-20s %-10s\n", "No.", "Patient Name", "Doctor Name", "Date", "Time", "Status", "Patient Email");
        System.out.println("===================================================================================");

        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            int appointmentNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String patientName = getPatientName(parts[0].trim()); // Get patient name from user_credential.txt
                    String doctorName = getDoctorName(parts[1].trim()); // Get doctor name from doctorlist.txt

                    System.out.printf("%-5d %-25s %-25s %-15s %-10s %-20s %-10s\n",
                            appointmentNumber, patientName, doctorName, parts[3].trim(), parts[4].trim(), parts[2].trim(), parts[0].trim());
                    appointmentNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        

        // Prompt the user to select an appointment for detailed information
        System.out.print("\nEnter the appointment number for detailed information (enter '-' to go back): ");
        String selectedAppointmentNumberInput = scanner.nextLine();

        if (selectedAppointmentNumberInput.equals("-")) {
            System.out.println("\nGoing back to the last page.");
            return; // Exit the method
        }

        try {
            int selectedAppointmentNumber = Integer.parseInt(selectedAppointmentNumberInput);

            // Validate if the entered number corresponds to a valid appointment
            if (selectedAppointmentNumber >= 1 && selectedAppointmentNumber <= getAppointmentCount()) {
                displayAppointmentDetails(selectedAppointmentNumber);
            } else {
                System.out.println("Invalid input. Going back to the last page.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Going back to the last page.");
        }
    }

    private static int getAppointmentCount() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private static void displayAppointmentDetails(int appointmentNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            int currentAppointmentNumber = 1;
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    if (currentAppointmentNumber == appointmentNumber) {
                        // Display patient information
                        displayPatientInformation(parts[0].trim());
    
                        // Display doctor information
                        displayDoctorInformation(parts[1].trim());
    
                        // Display current appointment status
                        System.out.printf("\nCurrent Appointment Status: %s\n", parts[2].trim());
    
                        // Prompt user to change appointment status
                        System.out.print("\nEnter the new status (Pending/Approved/Not Approved): ");
                        Scanner scanner = new Scanner(System.in);
                        String newStatus = scanner.nextLine().trim();
    
                        // Validate and update status
                        if (isValidStatus(newStatus)) {
                            updateAppointmentStatus(appointmentNumber, newStatus);
                            System.out.println("Appointment status updated successfully.");
                        } else {
                            System.out.println("Invalid status. Appointment status not updated.");
                        }
                        return;
                    }
                    currentAppointmentNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Not Approved");
    }
    
    private static void updateAppointmentStatus(int appointmentNumber, String newStatus) {
        List<String> appointmentLines = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                appointmentLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (appointmentNumber >= 1 && appointmentNumber <= appointmentLines.size()) {
            String[] parts = appointmentLines.get(appointmentNumber - 1).split(",");
            if (parts.length == 5) {
                parts[2] = newStatus; // Update the status
                appointmentLines.set(appointmentNumber - 1, String.join(",", parts));
    
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE_NAME))) {
                    for (String appointmentLine : appointmentLines) {
                        bw.write(appointmentLine);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private static String getPatientName(String patientEmail) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12 && parts[0].trim().equals(patientEmail)) {
                    return parts[5].trim(); // Assuming that the patient's name is at index 5 in user_credential.txt
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }
    
    private static String getDoctorName(String doctorEmail) {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].trim().equals(doctorEmail)) {
                    return parts[1].trim(); // Assuming that the doctor's name is at index 1 in doctorlist.txt
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private static void displayPatientInformation(String patientEmail) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12 && parts[0].trim().equals(patientEmail)) {
                    // Display patient details
                    System.out.println("\nPatient Information:");
                    System.out.printf("1. Name: %s\n", parts[5].trim());
                    System.out.printf("2. Gender: %s\n", parts[6].trim());
                    System.out.printf("3. Email: %s\n", parts[0].trim());
                    System.out.printf("4. Date of Birth: %s\n", parts[3].trim());
                    System.out.printf("5. Phone Number: %s\n", parts[8].trim());
                    System.out.printf("6. Address: %s\n", parts[7].trim());
                    System.out.printf("7. Registration Date: %s\n", parts[4].trim());
                    System.out.printf("8. Role: %s\n", parts[2].trim());

                    // Fetch and display additional details from patientInfo.txt
                    displayPatientAdditionalInfo(patientEmail);

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // private static void displayPatientAdditionalInfo(String patientEmail) {
    //     try (BufferedReader brPatient = new BufferedReader(new FileReader(PATIENT_INFO_FILE))) {
    //         String patientLine;

    //         while ((patientLine = brPatient.readLine()) != null) {
    //             String[] patientParts = patientLine.split(",");
    //             if (patientParts.length == 3 && patientParts[0].trim().equals(patientEmail)) {
    //                 // Display patient-specific details
    //                 System.out.printf("9. Medical Record: %s\n", patientParts[1].trim());
    //                 System.out.printf("10. Insurance Provider: %s\n", patientParts[2].trim());
    //                 break;
    //             }
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    private static void displayDoctorInformation(String doctorEmail) {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_FILE_NAME))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].trim().equals(doctorEmail)) {
                    // Display doctor details
                    System.out.println("\nDoctor Information:");
                    System.out.printf("1. Name: %s\n", parts[1].trim());
                    System.out.printf("2. Department: %s\n", parts[2].trim());
                    System.out.printf("3. Email: %s\n", parts[0].trim());
                    System.out.printf("4. Date of Birth: %s\n", parts[3].trim());
                    System.out.printf("5. Phone Number: %s\n", parts[5].trim());
                    System.out.printf("6. Address: %s\n", parts[4].trim());
                    System.out.printf("7. Specialization: %s\n", parts[4].trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

                       
    public static void admitPatient() {
        Scanner scanner = new Scanner(System.in);

        // Display the list of pending appointments
        System.out.println("\nPending Admission Requests:");
        displayPendingAdmissionRequests();

        // Prompt the admin to select an appointment for admission
        System.out.print("\nEnter the appointment number to admit the patient (enter '-' to go back): ");
        String selectedAppointmentNumberInput = scanner.nextLine();

        if (selectedAppointmentNumberInput.equals("-")) {
            System.out.println("\nGoing back to the last page.");
            return; // Exit the metho-
            
        }

        try {
            int selectedAppointmentNumber = Integer.parseInt(selectedAppointmentNumberInput);

            // Validate if the entered number corresponds to a valid appointment
            if (selectedAppointmentNumber >= 1 && selectedAppointmentNumber <= getPendingAdmissionCount()) {
                processAdmission(selectedAppointmentNumber);
            } else {
                System.out.println("Invalid input. Going back to the last page.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Going back to the last page.");
        }
    }

    private static void displayPendingAdmissionRequests() {
        System.out.printf("%-5s %-25s %-25s %-15s %-10s %-20s %-10s\n", "No.", "Patient Name", "Doctor Name", "Date", "Time", "Status", "Patient Email");
        System.out.println("===================================================================================");

        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            int appointmentNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[2].equalsIgnoreCase("Pending")) {
                    String patientName = getPatientName(parts[0].trim());
                    String doctorName = getDoctorName(parts[1].trim());

                    System.out.printf("%-5d %-25s %-25s %-15s %-10s %-20s %-10s\n",
                            appointmentNumber, patientName, doctorName, parts[3].trim(), parts[4].trim(), parts[2].trim(), parts[0].trim());
                    appointmentNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getPendingAdmissionCount() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[2].equalsIgnoreCase("Pending")) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private static void processAdmission(int selectedAppointmentNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            int currentAppointmentNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[2].equalsIgnoreCase("Pending")) {
                    if (currentAppointmentNumber == selectedAppointmentNumber) {
                        // Update the appointment status to "Approved"
                        parts[2] = "Approved";

                        // Save the updated appointment information
                        saveUpdatedAppointment(line, String.join(",", parts));

                        // Display admission details
                        displayAdmissionDetails(parts[0].trim());
                        return;
                    }
                    currentAppointmentNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveUpdatedAppointment(String oldLine, String newLine) {
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME));
             BufferedWriter bw = new BufferedWriter(new FileWriter("tempAppointmentList.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(oldLine)) {
                    bw.write(newLine);
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original file
        File tempFile = new File("tempAppointmentList.txt");
        File originalFile = new File(APPOINTMENT_FILE_NAME);
        if (tempFile.renameTo(originalFile)) {
            System.out.println("Admission processed successfully.");
        } else {
            System.out.println("Error processing admission. Please try again.");
        }
    }

    private static void displayAdmissionDetails(String patientEmail) {
        System.out.println("\nAdmission Details:");
        displayPatientInformation(patientEmail);
    }

    public static void dischargePatient() {
        Scanner scanner = new Scanner(System.in);

        // Display the list of admitted patients
        System.out.println("\nAdmitted Patients List:");
        displayAdmittedPatients();

        // Prompt the admin to select a patient for discharge
        System.out.print("\nEnter the patient number to discharge (enter '-' to go back): ");
        String selectedPatientNumberInput = scanner.nextLine();

        if (selectedPatientNumberInput.equals("-")) {
            System.out.println("\nGoing back to the last page.");
            return; // Exit the method
        }

        try {
            int selectedPatientNumber = Integer.parseInt(selectedPatientNumberInput);

            // Validate if the entered number corresponds to a valid patient
            if (selectedPatientNumber >= 1 && selectedPatientNumber <= getAdmittedPatientCount()) {
                processDischarge(selectedPatientNumber);
            } else {
                System.out.println("Invalid input. Going back to the last page.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Going back to the last page.");
        }
    }

    private static void displayAdmittedPatients() {
        System.out.printf("%-5s %-25s %-25s %-15s %-10s %-20s %-10s\n", "No.", "Patient Name", "Doctor Name", "Date", "Time", "Status", "Patient Email");
        System.out.println("===================================================================================");

        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            int patientNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[2].equalsIgnoreCase("Approved")) {
                    String patientName = getPatientName(parts[0].trim());
                    String doctorName = getDoctorName(parts[1].trim());

                    System.out.printf("%-5d %-25s %-25s %-15s %-10s %-20s %-10s\n",
                            patientNumber, patientName, doctorName, parts[3].trim(), parts[4].trim(), parts[2].trim(), parts[0].trim());
                    patientNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getAdmittedPatientCount() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[2].equalsIgnoreCase("Approved")) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private static void processDischarge(int selectedPatientNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            int currentPatientNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[2].equalsIgnoreCase("Approved")) {
                    if (currentPatientNumber == selectedPatientNumber) {
                        // Update the appointment status to "Discharged"
                        parts[2] = "Discharged";

                        // Save the updated appointment information
                        saveUpdatedDischarge(line, String.join(",", parts));

                        // Display discharge details
                        displayDischargeDetails(parts[0].trim());
                        return;
                    }
                    currentPatientNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveUpdatedDischarge(String oldLine, String newLine) {
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME));
             BufferedWriter bw = new BufferedWriter(new FileWriter("tempAppointmentList.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(oldLine)) {
                    bw.write(newLine);
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the temporary file to the original file
        File tempFile = new File("tempAppointmentList.txt");
        File originalFile = new File(APPOINTMENT_FILE_NAME);
        if (tempFile.renameTo(originalFile)) {
            System.out.println("Discharge processed successfully.");
        } else {
            System.out.println("Error processing discharge. Please try again.");
        }
    }

    private static void displayDischargeDetails(String patientEmail) {
        System.out.println("\nDischarge Details:");
        displayPatientInformation(patientEmail);
    }

    public static void viewAllUsers() {
        Scanner scanner = new Scanner(System.in);

        // Display the list of users (patients and doctors)
        System.out.println("\nUser List:");
        System.out.printf("%-5s %-25s %-20s %-10s %-20s %-15s %-15s %-15s\n", "No.", "User Name", "Email", "Role", "Gender", "Date of Birth", "Registration Date", "Phone Number");
        System.out.println("===============================================================================");

        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE_NAME))) {
            String line;
            int userNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12) {
                    String role = parts[2].trim();
                    if (role.equalsIgnoreCase("Patient") || role.equalsIgnoreCase("Doctor")) {
                        System.out.printf("%-5d %-25s %-20s %-10s %-20s %-15s %-15s %-15s\n",
                                userNumber, parts[5].trim(), parts[0].trim(), role, parts[6].trim(), parts[3].trim(), parts[4].trim(), parts[8].trim());
                        userNumber++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Prompt the user to select a user for detailed information
        System.out.print("\nEnter the user number for detailed information (enter '-' to go back): ");
        String selectedUserNumberInput = scanner.nextLine();

        if (selectedUserNumberInput.equals("-")) {
            System.out.println("\nGoing back to the last page.");
            return; // Exit the method
        }

        try {
            int selectedUserNumber = Integer.parseInt(selectedUserNumberInput);

            // Validate if the entered number corresponds to a valid user
            if (selectedUserNumber >= 1 && selectedUserNumber <= getUserCount()) {
                displayUserDetails(selectedUserNumber);
            } else {
                System.out.println("Invalid input. Going back to the last page.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Going back to the last page.");
        }
    }

    private static void displayUserDetails(int userNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE_NAME))) {
            String line;
            int currentUserNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12) {
                    String role = parts[2].trim();
                    if (role.equalsIgnoreCase("Patient") || role.equalsIgnoreCase("Doctor")) {
                        if (currentUserNumber == userNumber) {
                            // Display common user details
                            displayCommonUserDetails(parts);

                            // Display additional details based on role
                            if (role.equalsIgnoreCase("Patient")) {
                                displayPatientAdditionalInfo(parts[0].trim());
                                // Allow changing the medical report
                                changeMedicalReport(parts[0].trim());
                            } else if (role.equalsIgnoreCase("Doctor")) {
                                displayDoctorAdditionalInfo(parts[0].trim());
                            }
                            return;
                        }
                        currentUserNumber++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayCommonUserDetails(String[] parts) {
        System.out.println("\nUser Information:");
        System.out.printf("1. Name: %s\n", parts[5].trim());
        System.out.printf("2. Gender: %s\n", parts[6].trim());
        System.out.printf("3. Email: %s\n", parts[0].trim());
        System.out.printf("4. Date of Birth: %s\n", parts[3].trim());
        System.out.printf("5. Phone Number: %s\n", parts[8].trim());
        System.out.printf("6. Address: %s\n", parts[7].trim());
        System.out.printf("7. Registration Date: %s\n", parts[4].trim());
        System.out.printf("8. Role: %s\n", parts[2].trim());
    }

    private static void displayPatientAdditionalInfo(String patientEmail) {
        try (BufferedReader brPatient = new BufferedReader(new FileReader(PATIENT_INFO_FILE))) {
            String patientLine;

            while ((patientLine = brPatient.readLine()) != null) {
                String[] patientParts = patientLine.split(",");
                if (patientParts.length == 3 && patientParts[0].trim().equals(patientEmail)) {
                    // Display patient-specific details
                    System.out.printf("9. Medical Record: %s\n", patientParts[1].trim());
                    System.out.printf("10. Insurance Provider: %s\n", patientParts[2].trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void changeMedicalReport(String patientEmail) {
        // Prompt the user to change the medical report
        System.out.print("\nDo you want to change the medical report for this patient? (yes/no): ");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            // Fetch current medical report
            String currentMedicalReport = getCurrentMedicalReport(patientEmail);
            System.out.printf("\nCurrent Medical Report: %s\n", currentMedicalReport);

            // Prompt user for the new medical report
            System.out.print("Enter the new medical report: ");
            String newMedicalReport = scanner.nextLine().trim();

            // Update medical report in patientInfo.txt
            updateMedicalReport(patientEmail, newMedicalReport);

            System.out.println("Medical report updated successfully.");
        } else {
            System.out.println("Medical report not updated.");
        }
    }

    private static String getCurrentMedicalReport(String patientEmail) {
        try (BufferedReader brPatient = new BufferedReader(new FileReader(PATIENT_INFO_FILE))) {
            String patientLine;

            while ((patientLine = brPatient.readLine()) != null) {
                String[] patientParts = patientLine.split(",");
                if (patientParts.length == 3 && patientParts[0].trim().equals(patientEmail)) {
                    // Return current medical report
                    return patientParts[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Unknown";
    }

    private static void updateMedicalReport(String patientEmail, String newMedicalReport) {
        List<String> patientInfoLines = new ArrayList<>();

        try (BufferedReader brPatient = new BufferedReader(new FileReader(PATIENT_INFO_FILE))) {
            String patientLine;

            while ((patientLine = brPatient.readLine()) != null) {
                String[] patientParts = patientLine.split(",");
                if (patientParts.length == 3 && patientParts[0].trim().equals(patientEmail)) {
                    // Update the medical report
                    patientParts[1] = newMedicalReport;
                }
                patientInfoLines.add(String.join(",", patientParts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite patientInfo.txt with updated medical report
        try (BufferedWriter bwPatient = new BufferedWriter(new FileWriter(PATIENT_INFO_FILE))) {
            for (String patientLine : patientInfoLines) {
                bwPatient.write(patientLine);
                bwPatient.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayDoctorAdditionalInfo(String doctorEmail) {
        try (BufferedReader brDoctor = new BufferedReader(new FileReader(DOCTOR_FILE_NAME))) {
            String doctorLine;

            while ((doctorLine = brDoctor.readLine()) != null) {
                String[] doctorParts = doctorLine.split(",");
                if (doctorParts.length == 6 && doctorParts[0].trim().equals(doctorEmail)) {
                    // Display doctor-specific details
                    System.out.println("\nDoctor Information:");
                    System.out.printf("9. Department: %s\n", doctorParts[2].trim());
                    System.out.printf("10. Specialization: %s\n", doctorParts[4].trim());
                    System.out.printf("11. Office Address: %s\n", doctorParts[5].trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getUserCount() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12) {
                    String role = parts[2].trim();
                    if (role.equalsIgnoreCase("Patient") || role.equalsIgnoreCase("Doctor")) {
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
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



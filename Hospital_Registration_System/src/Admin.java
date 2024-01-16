import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Admin extends User {

    private static final String DOCTOR_FILE_NAME = "./Hospital_Registration_System/src/doctorlist.txt";
    private static final String USER_FILE_NAME = "./Hospital_Registration_System/src/user_credential.txt";
    private static final String APPOINTMENT_FILE_NAME = "./Hospital_Registration_System/src/AppointmentList.txt";
    private static final String PATIENT_INFO_FILE = "./Hospital_Registration_System/src/PatientInfo.txt";
    private static final String PATIENT_RECORD_FILE = "./Hospital_Registration_System/src/AdmitDischarge.txt";

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

    //viewAppointment
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
                        System.out.print("\nEnter the new status (Pending/Approved/Not Approved/Done/Not Done): ");
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
    
    private static boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Not Approved") || status.equalsIgnoreCase("Done") || status.equalsIgnoreCase("Not Done");
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
    
        // Display the list of admitted patients
        System.out.println("\nList of Admitted Patients:");
        displayAdmittedPatients();
    
        // Prompt the admin to select an option
        System.out.println("\nAdmit Patient Options:");
        System.out.println("1. Add New Record");
        System.out.println("2. Update Record");
        System.out.println("3. Back to Previous Page");
        System.out.print("\nEnter your choice (1-3): ");
    
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
    
        switch (choice) {
            case 1:
                addNewPatientRecord();
                break;
            case 2:
                updatePatientRecordMenu();
                break;
            case 3:
                System.out.println("\nGoing back to the Admin Dashboard.");
                break;
            default:
                System.out.println("Invalid choice. Going back to the Admin Dashboard.");
        }
    }

    private static void addNewPatientRecord() {
        Scanner scanner = new Scanner(System.in);

        // Get patient information from the admin
        System.out.println("\nEnter Patient Information:");
        System.out.print("Patient Name: ");
        String patientName = scanner.nextLine();
        System.out.print("Identity Card Number: ");
        String identityCardNumber = scanner.nextLine();
        System.out.print("Doctor Name: ");
        String doctorName = scanner.nextLine();
        System.out.print("Sickness: ");
        String sickness = scanner.nextLine();
        System.out.print("Room Number (e.g., A305): ");
        String roomNumber = scanner.nextLine().toUpperCase(); // Convert to uppercase
        System.out.print("Status (Admitted/Discharged): ");
        String status = scanner.nextLine().toLowerCase(); // Convert to lowercase

        // Validate room number
        if (!isValidRoomNumber(roomNumber)) {
            System.out.println("Invalid room number. Going back to the Admin Dashboard.");
            return;
        }

        // Check if the room is full
        if (isRoomFull(roomNumber)) {
            System.out.println("The room is full. Cannot admit more patients to this room.");
            return;
        }

        // Create a new patient record
        String patientRecord = String.format("%s,%s,%s,%s,%s,%s", patientName, identityCardNumber, doctorName, sickness, roomNumber, status);

        // Save the new patient record to the file
        writePatientRecordToFile(patientRecord);

        System.out.println("Patient admitted successfully.");
    }

    private static void updatePatientRecordMenu() {
        Scanner scanner = new Scanner(System.in);
    
        // Display the list of admitted patients
        System.out.println("\nList of Admitted Patients:");
        displayAdmittedPatients();
    
        // Prompt the admin to select a patient for updating
        System.out.print("\nEnter the patient number to update the record (enter '-' to go back): ");
        String selectedPatientNumberInput = scanner.nextLine();
    
        if (selectedPatientNumberInput.equals("-")) {
            System.out.println("\nGoing back to the Admit Patient Options.");
            return; // Exit the method
        }
    
        try {
            int selectedPatientNumber = Integer.parseInt(selectedPatientNumberInput);
    
            // Validate if the entered number corresponds to a valid patient
            if (selectedPatientNumber >= 1 && selectedPatientNumber <= getAdmittedPatientCount()) {
                updatePatientRecord(selectedPatientNumber);
            } else {
                System.out.println("Invalid input. Going back to the Admit Patient Options.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Going back to the Admit Patient Options.");
        }
    }

    private static void updatePatientRecord(int selectedPatientNumber) {
        Scanner scanner = new Scanner(System.in);
    
        // Get the patient record to update
        String patientRecordToUpdate = getPatientRecordByNumber(selectedPatientNumber);
    
        // Extract patient information from the record
        String[] parts = patientRecordToUpdate.split(",");
        String patientName = parts[0];
        String identityCardNumber = parts[1];
        String doctorName = parts[2];
        String sickness = parts[3];
        String roomNumber = parts[4];
        String status = parts[5];
    
        // Display the current information
        System.out.println("\nCurrent Patient Information:");
        System.out.println("1. Patient Name: " + patientName);
        System.out.println("2. Identity Card Number: " + identityCardNumber);
        System.out.println("3. Doctor Name: " + doctorName);
        System.out.println("4. Sickness: " + sickness);
        System.out.println("5. Room Number: " + roomNumber);
        System.out.println("6. Status: " + status);
    
        // Prompt the admin to select a field to update
        System.out.print("\nEnter the field number to update (enter '-' to go back): ");
        String selectedFieldInput = scanner.nextLine();
    
        if (selectedFieldInput.equals("-")) {
            System.out.println("\nGoing back to the Admit Patient Options.");
            return; // Exit the method
        }
    
        try {
            int selectedField = Integer.parseInt(selectedFieldInput);
    
            // Validate if the entered number corresponds to a valid field
            if (selectedField >= 1 && selectedField <= 6) {
                // Prompt the admin to enter the new value
                System.out.print("Enter the new value: ");
                String newValue = scanner.nextLine();
    
                // Update the selected field
                switch (selectedField) {
                    case 1:
                        patientName = newValue;
                        break;
                    case 2:
                        identityCardNumber = newValue;
                        break;
                    case 3:
                        doctorName = newValue;
                        break;
                    case 4:
                        sickness = newValue;
                        break;
                    case 5:
                        // Validate and update the room number
                        if (isValidRoomNumber(newValue) && !isRoomFull(newValue)) {
                            roomNumber = newValue.toUpperCase();
                        } else {
                            System.out.println("Invalid room number or the room is full. Room number not updated.");
                        }
                        break;
                    case 6:
                        // Validate and update the status
                        if (newValue.equalsIgnoreCase("admitted") || newValue.equalsIgnoreCase("discharged")) {
                            status = newValue.toLowerCase();
                        } else {
                            System.out.println("Invalid status. Status not updated.");
                        }
                        break;
                }
    
                // Create the updated patient record
                String updatedPatientRecord = String.format("%s,%s,%s,%s,%s,%s", patientName, identityCardNumber, doctorName, sickness, roomNumber, status);
    
                // Update the patient record in the file
                updatePatientRecord(patientRecordToUpdate, updatedPatientRecord);
            } else {
                System.out.println("Invalid input. Going back to the Admit Patient Options.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Going back to the Admit Patient Options.");
        }
    }
    
    ///cant save updated data
    private static void updatePatientRecord(String oldRecord, String newRecord) {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_RECORD_FILE));
             BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENT_RECORD_FILE))) {
    
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(oldRecord)) {
                    bw.write(newRecord);
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Rename the temporary file to the original file
        File tempFile = new File("tempPatientRecord.txt");
        File originalFile = new File(PATIENT_RECORD_FILE);
        if (tempFile.renameTo(originalFile)) {
            System.out.println("Patient record updated successfully.");
        } else {
            System.out.println("Error updating patient record. Please try again.");
        }
    }
    
    
    
    private static void displayAdmittedPatients() {
        System.out.printf("%-5s %-20s %-20s %-15s %-10s %-10s %-10s\n", "No.", "Patient Name", "Doctor Name", "Sickness", "Room Number", "Status", "Identity Card Number");
        System.out.println("===================================================================================");
    
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_RECORD_FILE))) {
            String line;
            int patientNumber = 1;
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[5].equalsIgnoreCase("admitted")) {
                    System.out.printf("%-5d %-20s %-20s %-15s %-10s %-10s %-10s\n",
                            patientNumber, parts[0], parts[2], parts[3], parts[4], parts[5], parts[1]);
                    patientNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean isValidRoomNumber(String roomNumber) {
        // Add your validation logic for room numbers
        // For example, check if the room number follows a certain format
        return roomNumber.matches("[A-B]\\d{3}");
    }
    
    private static boolean isRoomFull(String roomNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_RECORD_FILE))) {
            String line;
            int count = 0;
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[4].equalsIgnoreCase(roomNumber) && parts[5].equalsIgnoreCase("admitted")) {
                    count++;
                    if (count >= 2) {
                        return true; // Room is full
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void writePatientRecordToFile(String patientRecord) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENT_RECORD_FILE, true))) {
            bw.write(patientRecord);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static String getPatientRecordByNumber(int selectedPatientNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_RECORD_FILE))) {
            String line;
            int currentPatientNumber = 1;
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[5].equalsIgnoreCase("admitted")) {
                    if (currentPatientNumber == selectedPatientNumber) {
                        return line;
                    }
                    currentPatientNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Return null if not found
    }
    
    private static int getAdmittedPatientCount() {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_RECORD_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[5].equalsIgnoreCase("admitted")) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }



    //viewAllUsers()
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
            System.out.println("6. View All User");
            System.out.println("7. Log out");
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
                    viewAllUsers();
                    break;

                case 7:
                    System.out.println("\nThank you for using Hospital Management System. Goodbye!");
                    Appointment.clearConsole(3);
                    Appointment.displayUserInterface();
                    break;
            }

        } while (choice != 10);

        scanner.close();
    }
    
}



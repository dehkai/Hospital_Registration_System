import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Patient extends User {
    private String email, medicalRecord, InsuranceProvider;
    private static final String PATIENT_INFO_FILE = "./Hospital_Registration_System/src/PatientInfo.txt";
    private static final String DATABASE_FILE_NAME = "./Hospital_Registration_System/src/user_credential.txt";
    private static final String DOCTOR_LIST_NAME = "./Hospital_Registration_System/src/doctorlist.txt";
    private static final String APPOINTMENT_FILE_NAME = "./Hospital_Registration_System/src/AppointmentList.txt";


    public Patient(String email, String medicalRecord, String InsuranceProvider) {
        super();
        this.email = email;
        this.medicalRecord = medicalRecord;
        this.InsuranceProvider = InsuranceProvider;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getInsuranceProvider() {
        return InsuranceProvider;
    }

    public void setInsuranceProvider(String InsuranceProvider) {
        this.InsuranceProvider = InsuranceProvider;
    }

    public static void displayLoggedInUserInfo(String email) {
        // Fetch and display user details from user_credential.txt
        try (BufferedReader brUser = new BufferedReader(new FileReader(DATABASE_FILE_NAME))) {
            String line;
            boolean foundUser = false;

            while ((line = brUser.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12 && parts[0].trim().equals(email)) {
                    foundUser = true;

                    // Display user details
                    System.out.println("User Information:");
                    System.out.printf("1. Name: %s\n", parts[5].trim());
                    System.out.printf("2. Gender: %s\n", parts[6].trim());
                    System.out.printf("3. Email: %s\n", parts[0].trim());
                    System.out.printf("4. Date of Birth: %s\n", parts[3].trim());
                    System.out.printf("5. Phone Number: %s\n", parts[8].trim());
                    System.out.printf("6. Address: %s\n", parts[7].trim());
                    System.out.printf("7. Registration Date: %s\n", parts[4].trim());
                    System.out.printf("8. Role: %s\n", parts[2].trim());

                    // Fetch and display additional details from patientInfo.txt
                    try (BufferedReader brPatient = new BufferedReader(new FileReader(PATIENT_INFO_FILE))) {
                        String patientLine;
                        boolean foundPatient = false;

                        while ((patientLine = brPatient.readLine()) != null) {
                            String[] patientParts = patientLine.split(",");
                            if (patientParts.length == 3 && patientParts[0].trim().equals(email)) {
                                foundPatient = true;

                                // Display patient-specific details
                                //System.out.printf("9. Medical Record Number: %s\n", patientParts[1].trim());
                                System.out.printf("9. Medical Record: %s\n", patientParts[1].trim());
                                System.out.printf("10. Insurance Provider: %s\n", patientParts[2].trim());
                                break;
                            }
                        }

                        if (!foundPatient) {
                            System.out.println("Patient details not found.");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                }
            }

            if (!foundUser) {
                System.out.println("User not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Prompt user to update information
        updatePatientInfo(email);
    }

    private static void updatePatientInfo(String email) {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("\nEnter the number corresponding to the information you want to update (or 0 to exit): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter new Name: ");
                    String newName = scanner.nextLine();
                    setUserField(email, 5, newName);
                    break;

                case 2:
                    System.out.print("Enter new Gender: ");
                    String newGender = scanner.nextLine();
                    setUserField(email, 6, newGender);
                    break;

                case 3:
                    System.out.print("Enter new Email: ");
                    String newEmail = scanner.nextLine();
                    setUserField(email, 0, newEmail);
                    break;

                case 4:
                    System.out.print("Enter new Date of Birth (YYYY-MM-DD): ");
                    String newDateOfBirth = scanner.nextLine();
                    setUserField(email, 3, newDateOfBirth);
                    break;

                case 5:
                    System.out.print("Enter new Phone Number: ");
                    String newPhoneNumber = scanner.nextLine();
                    setUserField(email, 8, newPhoneNumber);
                    break;

                case 6:
                    System.out.print("Enter new Address: ");
                    String newAddress = scanner.nextLine();
                    setUserField(email, 7, newAddress);
                    break;

                case 7:
                    // Registration Date is read-only
                    System.out.println("Registration Date cannot be updated.");
                    break;

                case 8:
                    System.out.println("Role cannot be updated.");
                    break;

                // case 9:
                //     System.out.print("Enter new Medical Record Number (or '-' to keep current): ");
                //     String newMedicalRecordNumber = scanner.nextLine();
                //     setPatientField(email, 1, newMedicalRecordNumber);
                //     break;

                case 9:
                    System.out.print("Enter new Medical Record (or '-' to keep current): ");
                    String newMedicalRecord = scanner.nextLine();
                    setPatientField(email, 1, newMedicalRecord);
                    break;

                case 10:
                    System.out.print("Enter new Insurance Provider (or '-' to keep current): ");
                    String newInsuranceProvider = scanner.nextLine();
                    setPatientField(email, 2, newInsuranceProvider);
                    break;

                case 0:
                    System.out.println("Exiting update page.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 11.");
            }

            System.out.print("Do you want to continue updating? (1: Yes, 0: No): ");
            int continueChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (continueChoice != 1) {
                break;
            }

        } while (true);

    }

    private static void setUserField(String email, int field, String value) {
        try (BufferedReader br = new BufferedReader(new FileReader(DATABASE_FILE_NAME));
             BufferedWriter bw = new BufferedWriter(new FileWriter("./Hospital_Registration_System/src/tempUser_credential.txt"))) {
    
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12 && parts[0].trim().equals(email)) {
                    parts[field] = value;
                    line = String.join(",", parts);
                }
                bw.write(line);
                bw.newLine();
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Rename the temp file to the original file
        try {
            java.nio.file.Files.move(
                    java.nio.file.Paths.get("./Hospital_Registration_System/src/tempUser_credential.txt"),
                    java.nio.file.Paths.get(DATABASE_FILE_NAME),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
            System.out.println("User information updated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private static void setPatientField(String email, int field, String value) {
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENT_INFO_FILE));
             BufferedWriter bw = new BufferedWriter(new FileWriter("./Hospital_Registration_System/src/tempPatientInfo.txt"))) {
    
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equals(email)) {
                    parts[field] = value;
                    line = String.join(",", parts);
                }
                bw.write(line);
                bw.newLine();
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Rename the temp file to the original file
        try {
            java.nio.file.Files.move(
                    java.nio.file.Paths.get("./Hospital_Registration_System/src/tempPatientInfo.txt"),
                    java.nio.file.Paths.get(PATIENT_INFO_FILE),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );
            System.out.println("Patient information updated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

        public static void requestAppointment(String patientEmail) {
            Scanner scanner = new Scanner(System.in);
    
            // Display available doctors from doctorlist.txt with list numbers
            System.out.println("\nAvailable Doctors:");
            System.out.printf("%-5s %-25s %-20s %-20s %-30s\n", "No.", "Doctor Name", "Department", "Specialization", "Office Address");
            System.out.println("===========================================================================");
    
            try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_LIST_NAME))) {
                String line;
                int doctorNumber = 1;
    
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 6) {
                        System.out.printf("%-5d %-25s %-20s %-20s %-30s\n", doctorNumber, parts[1].trim(), parts[2].trim(), parts[4].trim(), parts[5].trim());
                        doctorNumber++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            // Patient selects a doctor
            System.out.print("\nEnter the number of the doctor you want to schedule an appointment with (enter '-' to go back): ");
            String selectedDoctorNumberInput = scanner.nextLine();
    
            // Check if the user wants to go back
            if (selectedDoctorNumberInput.equals("-")) {
                System.out.println("\nGoing back to the last page.");
                return; // Exit the method
            }
    
            try {
                int selectedDoctorNumber = Integer.parseInt(selectedDoctorNumberInput);
    
                // Validate if the entered number corresponds to a valid doctor
                if (selectedDoctorNumber >= 1) {
                    String selectedDoctorEmail = getDoctorEmailByNumber(selectedDoctorNumber);
    
                    if (selectedDoctorEmail != null) {
                        // Input date and time for the appointment
                        System.out.print("Enter the date of the appointment (YYYY-MM-DD): ");
                        String appointmentDate = scanner.nextLine();
    
                        System.out.print("Enter the time of the appointment: ");
                        String appointmentTime = scanner.nextLine();
    
                        // Save appointment details to AppointmentList.txt
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE_NAME, true))) {
                            String appointmentInfo = patientEmail + "," + selectedDoctorEmail + ",Pending," + appointmentDate + "," + appointmentTime;
                            bw.write(appointmentInfo);
                            bw.newLine();
                            System.out.println("\nAppointment requested successfully!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Invalid input. Going back to the last page.");
                    }
                } else {
                    System.out.println("Invalid input. Going back to the last page.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Going back to the last page.");
            }
        }
    
        private static String getDoctorEmailByNumber(int doctorNumber) {
            try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_LIST_NAME))) {
                String line;
                int currentDoctorNumber = 1;
    
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 6) {
                        if (currentDoctorNumber == doctorNumber) {
                            return parts[0].trim(); // Doctor's email is at index 0 in doctorlist.txt
                        }
                        currentDoctorNumber++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            return null;
        }

    public static void viewAppointment(String patientEmail) {
        Scanner scanner = new Scanner(System.in);

        // Display the user's current appointments
        System.out.println("\nYour Appointments:");
        System.out.printf("%-5s %-25s %-15s %-10s %-20s %-10s\n", "No.", "Doctor Name", "Date", "Time", "Status", "Doctor Email");
        System.out.println("===============================================================================");

        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            int appointmentNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[0].trim().equals(patientEmail)) {
                    String doctorName = getDoctorName(parts[1].trim()); // Get doctor name from doctorlist.txt

                    System.out.printf("%-5d %-25s %-15s %-10s %-20s %-10s\n",
                            appointmentNumber, doctorName, parts[3].trim(), parts[4].trim(), parts[2].trim(), parts[1].trim());
                    appointmentNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Prompt the user to update or cancel an appointment
        System.out.print("\nTo update or cancel an appointment, enter the list number (type '-' to go back): ");
        String input = scanner.nextLine();

        if (input.equals("-")) {
            System.out.println("\nGoing back to the last page.");
            return; // Exit the method
        }

        try {
            int choice = Integer.parseInt(input);

            // Validate if the entered number corresponds to a valid appointment
            if (choice >= 1 && choice <= getAppointmentCount(patientEmail)) {
                System.out.print("Type 'update' to update the appointment or 'cancel' to cancel: ");
                String action = scanner.nextLine();

                if (action.equalsIgnoreCase("update")) {
                    // Call method to update appointment
                    updateAppointment(patientEmail, choice);
                } else if (action.equalsIgnoreCase("cancel")) {
                    // Call method to cancel appointment
                    cancelAppointment(patientEmail, choice);
                } else {
                    System.out.println("Invalid command. Going back to the last page.");
                }
            } else {
                System.out.println("Invalid input. Going back to the last page.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Going back to the last page.");
        }

    }

    // Helper method to get the doctor name based on doctor email
    private static String getDoctorName(String doctorEmail) {
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_LIST_NAME))) {
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

    // Helper method to get the count of appointments for a specific patient
    private static int getAppointmentCount(String patientEmail) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[0].trim().equals(patientEmail)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private static void updateAppointment(String patientEmail, int appointmentNumber) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter new date (YYYY-MM-DD): ");
        String newDate = scanner.nextLine();

        System.out.print("Enter new time: ");
        String newTime = scanner.nextLine();

        // Implement the logic to update the appointment in the file
        try {
            BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME));
            StringBuilder sb = new StringBuilder();
            String line;
            int currentAppointmentNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[0].trim().equals(patientEmail)) {
                    if (currentAppointmentNumber == appointmentNumber) {
                        // Update date and time for the selected appointment
                        line = parts[0] + "," + parts[1] + "," + parts[2] + "," + newDate + "," + newTime;
                    }
                    currentAppointmentNumber++;
                }
                sb.append(line).append("\n");
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE_NAME));
            bw.write(sb.toString());
            bw.close();

            System.out.println("Appointment updated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void cancelAppointment(String patientEmail, int appointmentNumber) {
        // Implement the logic to cancel the appointment in the file
        try {
            BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME));
            StringBuilder sb = new StringBuilder();
            String line;
            int currentAppointmentNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[0].trim().equals(patientEmail)) {
                    if (currentAppointmentNumber != appointmentNumber) {
                        // Exclude the canceled appointment from the file
                        sb.append(line).append("\n");
                    }
                    currentAppointmentNumber++;
                }
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE_NAME));
            bw.write(sb.toString());
            bw.close();

            System.out.println("Appointment canceled successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getSpecialization(String doctorEmail) {
        // Fetch and return specialization from doctorlist.txt for the given doctorEmail
        try (BufferedReader br = new BufferedReader(new FileReader("./Hospital_Registration_System/src/doctorlist.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].trim().equals(doctorEmail)) {
                    return parts[4].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void updateAppointment(String patientEmail, Scanner scanner) {
        try (BufferedReader br = new BufferedReader(new FileReader("./Hospital_Registration_System/src/AppointmentList.txt"))) {
            String line;
            boolean foundPendingAppointment = false;
    
            System.out.println("\nYour Pending Appointments:");
            System.out.printf("%-25s %-15s %-15s %-15s\n", "Doctor", "Date", "Time", "Status");
            System.out.println("================================================");
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[0].trim().equals(patientEmail) && parts[2].trim().equals("Pending")) {
                    foundPendingAppointment = true;
                    System.out.printf("%-25s %-15s %-15s %-15s\n", parts[1].trim(), parts[3].trim(), parts[4].trim(), parts[2].trim());
                }
            }
    
            if (!foundPendingAppointment) {
                System.out.println("No pending appointments found.");
                return;
            }
    
            System.out.print("Enter the name of the doctor for the appointment you want to update/cancel (or '-' to exit): ");
            String selectedDoctor = scanner.nextLine();
    
            if (selectedDoctor.equals("-")) {
                return; // Exit to the last page
            }
    
            System.out.print("Enter the new appointment date (or '-' to keep current): ");
            String newAppointmentDate = scanner.nextLine();
    
            System.out.print("Enter the new appointment time (or '-' to keep current): ");
            String newAppointmentTime = scanner.nextLine();
    
            try (BufferedReader brUpdate = new BufferedReader(new FileReader("./Hospital_Registration_System/src/AppointmentList.txt"));
                 BufferedWriter bwUpdate = new BufferedWriter(new FileWriter("./Hospital_Registration_System/src/tempAppointmentList.txt"))) {
    
                while ((line = brUpdate.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 5 && parts[0].trim().equals(patientEmail) && parts[1].trim().equals(selectedDoctor) && parts[2].trim().equals("Pending")) {
                        parts[3] = newAppointmentDate.equals("-") ? parts[3].trim() : newAppointmentDate.trim();
                        parts[4] = newAppointmentTime.equals("-") ? parts[4].trim() : newAppointmentTime.trim();
                    }
                    bwUpdate.write(String.join(",", parts));
                    bwUpdate.newLine();
                }
    
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            // Rename the temp file to the original file
            try {
                java.nio.file.Files.move(
                        java.nio.file.Paths.get("./Hospital_Registration_System/src/tempAppointmentList.txt"),
                        java.nio.file.Paths.get("./Hospital_Registration_System/src/AppointmentList.txt"),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );
                System.out.println("Appointment updated successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    //error, save in temp txt
    public static void displayPatientInterface(String email, User loggedInUser ) throws FileNotFoundException, IOException{
        System.out.println("email: " + email);
        int choice;
        Scanner scanner = new Scanner(System.in);

        do{
        System.out.println("\n\tPatient Dashboard");
        System.out.println("\t===============");
        System.out.println("\n1. View Personal Information");
        System.out.println("2. Request Appointment");
        System.out.println("3. View Appointment");
        System.out.println("4. Log out");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        //scanner.nextLine();

        switch (choice) {
            case 1:
                displayLoggedInUserInfo(email);
                break;
            
            case 2:
                requestAppointment(email);
                break;

            case 3:
                viewAppointment(email);
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

    

    

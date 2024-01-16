import java.io.*;
import java.util.*;

public class Doctor extends User {
    private String department, specialization, OfficeAddress;
    private static final String APPOINTMENT_FILE_NAME = "./Hospital_Registration_System/src/AppointmentList.txt";
    private static final String USER_FILE_NAME = "./Hospital_Registration_System/src/user_credential.txt";
    private static final String PATIENT_MEDICAL_RECORD_FILE_NAME = "./Hospital_Registration_System/src/PatientMediacalRecord.txt";
    private static final String DOCTOR_LIST_NAME = "./Hospital_Registration_System/src/doctorlist.txt";

    
    public Doctor(String email, String name, String gender, String department, String specialization, String phoneNumber, String OfficeAddress) {
        super(name, phoneNumber);
        this.department = department;
        this.specialization = specialization;
        this.OfficeAddress = OfficeAddress;
    }
    public Doctor(){

    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getOfficeAddress() {
        return OfficeAddress;
    }

    public void setOfficeAddress(String OfficeAddress) {
        this.OfficeAddress = OfficeAddress;
    }

    public static void viewAppointment(String email) {
        Scanner scanner = new Scanner(System.in);

        // Display the doctor's approved appointments
        System.out.println("\nYour Approved Appointments:");
        System.out.printf("%-5s %-25s %-15s %-10s %-20s %-10s\n", "No.", "Patient Name", "Date", "Time", "Status", "Patient Email");
        System.out.println("===============================================================================");

        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            int appointmentNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[1].trim().equals(email) && parts[2].trim().equals("Approved")) {
                    String patientName = getPatientName(parts[0].trim()); // Get patient name from user_credential.txt

                    System.out.printf("%-5d %-25s %-15s %-10s %-20s %-10s\n",
                            appointmentNumber, patientName, parts[3].trim(), parts[4].trim(), parts[2].trim(), parts[0].trim());
                    appointmentNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Prompt the doctor to update the appointment status
        System.out.print("\nEnter the appointment list number to update status (or '-' to go back): ");
        String input = scanner.nextLine();

        if (input.equals("-")) {
            System.out.println("\nGoing back to the previous page.");
            return; // Exit the method
        }

        try {
            int choice = Integer.parseInt(input);

            // Validate if the entered number corresponds to a valid appointment
            if (choice >= 1 && choice <= getAppointmentCount(email, "Approved")) {
                System.out.print("Type 'done' to mark the appointment as done or 'not done' to mark it as not done: ");
                String status = scanner.nextLine();

                if (status.equalsIgnoreCase("done") || status.equalsIgnoreCase("not done")) {
                    // Call method to update appointment status
                    updateAppointmentStatus(email, choice, status);
                } else {
                    System.out.println("Invalid status. Going back to the previous page.");
                }
            } else {
                System.out.println("Invalid input. Going back to the previous page.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Going back to the previous page.");
        }
    }

    // Helper method to get the patient name based on patient email
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

    // Helper method to get the count of appointments for a specific doctor with a given status
    private static int getAppointmentCount(String doctorEmail, String status) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[1].trim().equals(doctorEmail) && parts[2].trim().equals(status)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private static void updateAppointmentStatus(String doctorEmail, int appointmentNumber, String status) {
        // Implement the logic to update the appointment status in the file
        try {
            BufferedReader br = new BufferedReader(new FileReader(APPOINTMENT_FILE_NAME));
            StringBuilder sb = new StringBuilder();
            String line;
            int currentAppointmentNumber = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[1].trim().equals(doctorEmail) && parts[2].trim().equals("Approved")) {
                    if (currentAppointmentNumber == appointmentNumber) {
                        // Update status for the selected appointment
                        line = parts[0] + "," + parts[1] + "," + status + "," + parts[3] + "," + parts[4];
                    }
                    currentAppointmentNumber++;
                }
                sb.append(line).append("\n");
            }

            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE_NAME));
            bw.write(sb.toString());
            bw.close();

            System.out.println("Appointment status updated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> getAvailableAppointmentSlots(){
        return null;
        
    }
    private static void updateDoctorPersonalInfo(String email) {
        Scanner scanner = new Scanner(System.in);

        do {          
            System.out.print("\nEnter the number corresponding to the information you want to update (or 0 to exit): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("\nEnter new Name: ");
                    String newName = scanner.nextLine();
                    setUserField(email, 5, newName);
                    setDoctorListField(email, 1, newName);
                    break;

                case 2:
                    System.out.print("\nEnter new Gender: ");
                    String newGender = scanner.nextLine();
                    setUserField(email, 6, newGender);
                    break;

                case 3:
                    System.out.print("\nEnter new Email: ");
                    String newEmail = scanner.nextLine();
                    setUserField(email, 0, newEmail);
                    setDoctorListField(email, 0, newEmail);
                    break;

                case 4:
                    System.out.print("\nEnter new Date of Birth (YYYY-MM-DD): ");
                    String newDateOfBirth = scanner.nextLine();
                    setUserField(email, 3, newDateOfBirth);
                    break;

                case 5:
                    System.out.print("\nEnter new Phone Number: ");
                    String newPhoneNumber = scanner.nextLine();
                    setUserField(email, 8, newPhoneNumber);
                    setDoctorListField(email, 2, newPhoneNumber);
                    break;

                case 6:
                    System.out.print("\nEnter new Address: ");
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

                case 0:
                    System.out.println("Exiting update page.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 8.");
                    
            }
                    System.out.print("Do you want to continue updating? (1: Yes, 0: No): ");
                    int continueChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
        
                    if (continueChoice != 1) {
                        break;
                    }
        
                } while (true);
        
        }
        private static void updateDoctorInfo(String email) {
            Scanner scanner = new Scanner(System.in);
    
            do {
                System.out.println("\n\tUpdate Doctor Information");
                System.out.println("\t-------------------------");
                System.out.println("\nPlease select the information you want to update:");
                System.out.println("1. Name");
                System.out.println("2. Phone Number");
                System.out.println("3. Department");
                System.out.println("4. Specialization");
                System.out.println("5. Office Address");         
                System.out.print("\nEnter the number corresponding to the information you want to update (or 0 to exit): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
    
                switch (choice) {
                    case 1:
                        System.out.print("\nEnter new Name: ");
                        String newName = scanner.nextLine();
                        setUserField(email, 5, newName);
                        setDoctorListField(email, 1, newName);
                        break;
    
                    case 2:
                        System.out.print("\nEnter new Phone Number: ");
                        String newPhoneNumber = scanner.nextLine();
                        setUserField(email, 8, newPhoneNumber);
                        setDoctorListField(email, 2, newPhoneNumber);
                        break;
    
                    case 3:
                        System.out.print("\nEnter new department: ");
                        String newDepartment = scanner.nextLine();
                        setDoctorListField(email, 3, newDepartment);
                        break;
    
                    case 4:
                        System.out.print("\nEnter new specialization: ");
                        String newSpecialization = scanner.nextLine();
                        setDoctorListField(email, 4, newSpecialization);
                        break;
    
                    case 5:
                        System.out.print("\nEnter new office address: ");
                        String newOfficeAddress = scanner.nextLine();
                        setDoctorListField(email, 5, newOfficeAddress);
                        break;
    
                    case 0:
                        System.out.println("Exiting update page.");
                        break;
    
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 8.");
                        
                }
                        System.out.print("Do you want to continue updating? (1: Yes, 0: No): ");
                        int continueChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
            
                        if (continueChoice != 1) {
                            break;
                        }
            
                    } while (true);
            
            }
        public static void displayDoctorPersonalInfo(String email) {
            // Fetch and display user details from user_credential.txt
            try (BufferedReader brUser = new BufferedReader(new FileReader(USER_FILE_NAME))) {
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
                        
                }
                    
                }
                if (!foundUser) {
                    System.out.println("User not found.");
                } 
            }catch (IOException e) {
                    e.printStackTrace();
                }
            // Prompt user to update information
            updateDoctorPersonalInfo(email);
        }
        public static void displayDoctorInfo(String email) {
            // Fetch and display doctor details from doctorlist.txt
            try (BufferedReader brUser = new BufferedReader(new FileReader(DOCTOR_LIST_NAME))) {
                String line;
                boolean foundDoctor = false;
    
                while ((line = brUser.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 6 && parts[0].trim().equals(email)) {
                        foundDoctor = true;
    
                        // Display doctor details
                        // System.out.println("Doctor Information:");
                        // System.out.printf("1. Name: %s\n", parts[5].trim());
                        // System.out.printf("2. Gender: %s\n", parts[6].trim());
                        // System.out.printf("3. Email: %s\n", parts[0].trim());
                        // System.out.printf("4. Date of Birth: %s\n", parts[3].trim());
                        // System.out.printf("5. Phone Number: %s\n", parts[8].trim());
                        // System.out.printf("6. Address: %s\n", parts[7].trim());
                        // System.out.printf("7. Registration Date: %s\n", parts[4].trim());
                        // System.out.printf("8. Role: %s\n", parts[2].trim());
                        
                        System.out.printf("\n| %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                                "Name", "Phone Number", "Department", "Specialization", "Office Address");
                        System.out.println("===========================================================================================");
                    
                        System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                        parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(), parts[5].trim());
                        
                }
                    
                }
                if (!foundDoctor) {
                    System.out.println("Doctor not found.");
                } 
            }catch (IOException e) {
                    e.printStackTrace();
                }
            // Prompt user to update information
            //updateDoctorPersonalInfo(email);
        }
        private static void setDoctorListField(String email, int field, String value) {
            try (BufferedReader br = new BufferedReader(new FileReader(DOCTOR_LIST_NAME));
                 BufferedWriter bw = new BufferedWriter(new FileWriter("./Hospital_Registration_System/src/tempDoctorList.txt"))) {
        
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 6 && parts[0].trim().equals(email)) {
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
                        java.nio.file.Paths.get("./Hospital_Registration_System/src/tempDoctorList.txt"),
                        java.nio.file.Paths.get(DOCTOR_LIST_NAME),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );
                System.out.println("Doctor List information updated successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            
        

    public static void displayDoctorInterface(String email, User loggedInUser) throws FileNotFoundException, IOException{
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
        System.out.println("\n\tDoctor Dashboard");
        System.out.println("\t================");
        System.out.println("\n1. View Personal Information");
        System.out.println("2. View Doctor Information");
        System.out.println("3. Update Doctor Information");
        System.out.println("4. View Appointment");
        System.out.println("5. Logout");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println();
                displayDoctorPersonalInfo(email);
                break;
            
            case 2:
                displayDoctorInfo(email);
                break;
            case 3:
                updateDoctorInfo(email);
                break;                 
            case 4:
                viewAppointment(email);
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
    



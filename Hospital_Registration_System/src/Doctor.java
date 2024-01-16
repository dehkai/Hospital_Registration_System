import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    private String department, specialization, OfficeAddress;
    private static final String APPOINTMENT_FILE_NAME = "./Hospital_Registration_System/src/AppointmentList.txt";
    private static final String USER_FILE_NAME = "./Hospital_Registration_System/src/user_credential.txt";
    private static final String PATIENT_MEDICAL_RECORD_FILE_NAME = "./Hospital_Registration_System/src/PatientMediacalRecord.txt";

    
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

    public static void displayDoctorInterface(String email, User loggedInUser) throws FileNotFoundException, IOException{
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
        System.out.println("\n\tDoctor Dashboard");
        System.out.println("\t================");
        System.out.println("\n1. View Personal Information");
        System.out.println("2. View Appointment");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Logout");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println();
                User.displayLoggedInUserInfo(loggedInUser);
                break;
                
            case 2:
                viewAppointment(email);
                break;
            case 3:
                
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
    



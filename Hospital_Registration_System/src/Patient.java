import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Patient extends User {
    private String email, medicalRecordNumber, medicalRecord, InsuranceProvider;

    public Patient(String email, String medicalRecordNumber, String medicalRecord, String InsuranceProvider) {
        this.email = email;
        this.medicalRecordNumber = medicalRecordNumber;
        this.medicalRecord = medicalRecord;
        this.InsuranceProvider = InsuranceProvider;
    }

    public String getMedicalRecordNumber() {
        return medicalRecordNumber;
    }

    public void setMedicalRecordNumber(String medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber;
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

    public static void updatePatient(String email) {
        try (BufferedReader br = new BufferedReader(new FileReader("./Hospital_Registration_System/src/patientInfo.txt"))) {
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].trim().equals(email)) {
                    found = true;

                    System.out.println("\nUser Email: " + parts[0].trim());
                    System.out.println("Current Medical Record Number: " + parts[1].trim());
                    System.out.println("Current Medical Record: " + parts[2].trim());
                    System.out.println("Current Insurance Provider: " + parts[3].trim());

                    System.out.print("Enter new Medical Record Number (or '-' to keep current): ");
                    String newMedicalRecordNumber = new Scanner(System.in).nextLine();
                    if (!newMedicalRecordNumber.equals("-")) {
                        setRecord(email, 1, newMedicalRecordNumber);
                    }

                    System.out.print("Enter new Medical Record (or '-' to keep current): ");
                    String newMedicalRecord = new Scanner(System.in).nextLine();
                    if (!newMedicalRecord.equals("-")) {
                        setRecord(email, 2, newMedicalRecord);
                    }

                    System.out.print("Enter new Insurance Provider (or '-' to keep current): ");
                    String newInsuranceProvider = new Scanner(System.in).nextLine();
                    if (!newInsuranceProvider.equals("-")) {
                        setRecord(email, 3, newInsuranceProvider);
                    }
                    break;
                }
            }

            if (!found) {
                System.out.println("Patient not found in records.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////need to fix, have error
    private static void setRecord(String email, int field, String value) {
        
        try (BufferedReader br = new BufferedReader(new FileReader("./Hospital_Registration_System/src/patientInfo.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("./Hospital_Registration_System/src/tempPatientInfo.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].trim().equals(email)) {
                    parts[field] = value;
                    line = String.join(",", parts);
                }
                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void requestAppointment(String patientEmail) {
        try (BufferedReader br = new BufferedReader(new FileReader("./Hospital_Registration_System/src/doctorlist.txt"))) {
            String line;

            System.out.println("\nList of Available Doctors:");
            System.out.printf("%-25s %-15s %-20s %-30s\n", "Doctor", "Department", "Specialization", "Office Address");
            System.out.println("===========================================================================");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    System.out.printf("%-25s %-15s %-20s %-30s\n", parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[4].trim());
                }
            }

            System.out.print("Enter the name of the doctor for the appointment (or '-' to exit to the last page): ");
            String selectedDoctor = new Scanner(System.in).nextLine();

            if (selectedDoctor.equals("-")) {
                return; // Exit to the last page
            }

            System.out.print("Enter the desired appointment date: ");
            String appointmentDate = new Scanner(System.in).nextLine();

            System.out.print("Enter the desired appointment time: ");
            String appointmentTime = new Scanner(System.in).nextLine();

            // Append the new appointment to the AppointmentList.txt file
            try {
                java.nio.file.Files.write(
                        java.nio.file.Paths.get("./Hospital_Registration_System/src/AppointmentList.txt"),
                        (patientEmail + "," + selectedDoctor + ",Pending," + appointmentDate + "," + appointmentTime + System.lineSeparator()).getBytes(),
                        java.nio.file.StandardOpenOption.APPEND
                );
                System.out.println("Appointment requested successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewAppointment(String patientEmail) {
        // Fetch and display appointments from AppointmentList.txt for the given patientEmail
        try (BufferedReader br = new BufferedReader(new FileReader("./Hospital_Registration_System/src/AppointmentList.txt"))) {
            String line;
            boolean found = false;

            System.out.println("\nYour Appointments:");
            System.out.printf("%-25s %-25s %-15s %-15s %-15s\n", "Doctor", "Status", "Date", "Time", "Specialization");
            System.out.println("===================================================================");
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[0].trim().equals(patientEmail)) {
                    found = true;
                    System.out.printf("%-25s %-25s %-15s %-15s %-15s\n", parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim(), getSpecialization(parts[1].trim()));
                }
            }

            if (!found) {
                System.out.println("No appointments found.");
            }

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

    public static void updateAppointment(String patientEmail) {
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
            String selectedDoctor = new Scanner(System.in).nextLine();

            if (selectedDoctor.equals("-")) {
                return; // Exit to the last page
            }

            System.out.print("Enter the new appointment date (or '-' to keep current): ");
            String newAppointmentDate = new Scanner(System.in).nextLine();

            System.out.print("Enter the new appointment time (or '-' to keep current): ");
            String newAppointmentTime = new Scanner(System.in).nextLine();

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
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Appointment updated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //error, save in temp txt
    public static void displayPatientInterface(String email) throws FileNotFoundException, IOException{
        System.out.println("email: " + email);
        int choice;
        Scanner scanner = new Scanner(System.in);

        do{
        System.out.println("\n\tAdmin Dashboard");
        System.out.println("\t===============");
        System.out.println("\n1. Update Patient Information");
        System.out.println("2. Request Appointment");
        System.out.println("3. View Appointment");
        System.out.println("4. Update Appointment");
        System.out.println("5. Log out");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                updatePatient(email);
                break;
            
            case 2:
                requestAppointment(email);
                break;

            case 3:
                viewAppointment(email);
                break;

            case 4:
                updateAppointment(email);
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

    

    

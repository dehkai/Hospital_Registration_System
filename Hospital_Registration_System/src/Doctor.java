import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    private String department, specialization;

    public Doctor(String department, String specialization) {
        this.department = department;
        this.specialization = specialization;
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

    public void viewAppointment(){

    }

    public List<Appointment> getAvailableAppointmentSlots(){
        return null;
        
    }

    public static void displayDoctorInterface(User loggedInUser){
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
        System.out.println("\n1. View Personal Information");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Appointment");
        System.out.println("4. View Available Appointment Slots");
        System.out.println("5. Logout");
        System.out.print("\nEnter your choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println();
                User.displayLoggedInUserInfo(loggedInUser);
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
    



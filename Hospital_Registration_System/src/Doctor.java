import java.util.List;

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

    // public List<Appoinment> getAvailableAppointmentSlots(){
        
    // }
}
    



import java.sql.Time;
import java.util.Date;

public class Appointment {

    private String AppointmentId, status;
    private Date date;
    private Patient patient;
    private Time time;

    public Appointment(String AppointmentId, String status, Date date, Patient patient, Time time) {
        this.AppointmentId = AppointmentId;
        this.status = status;
        this.date = date;
        this.patient = patient;
        this.time = time;
    }

    public String getAppointmentId() {
        return AppointmentId;
    }

    public void setAppointmentId(String AppointmentId) {
        this.AppointmentId = AppointmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getAppoinmentStatus() {
        return status;
    }

    public void setAppoinmentStatus(String status) {
        this.status = status;
    }
}

    




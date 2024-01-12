import java.util.Scanner;

public class Patient extends User {
    private String medicalRecordNumber, medicalRecord, InsuranceProvider;

    public Patient(String medicalRecordNumber, String medicalRecord, String InsuranceProvider) {
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

    public void updatePatient() {
        
    }

    public void requestAppointment() {

    }

    public void viewAppointment() {

    }

    public void updateAppointment() {

    }
    
}

    

    

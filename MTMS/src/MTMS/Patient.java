/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MTMS;

/**
 *
 * @author hguo87
 * comments by Yuan He
 * 
 * This class define patient's attributes
 */

import java.util.Date;

public class Patient {
    public int PatientID;
    public String FirstName;
    public String LastName;
    public boolean Gender;      //T for Male and F for Female
    public Date BirthDate;
    public String Address;
    public String Zipcode;
    public String PhoneNumber;
    public String DoctorID;     // The Patient's Doctor
    
    public Patient(){
        this.PatientID = -1;
        this.FirstName = "";
        this.LastName = "";
        this.Gender = true;
        this.BirthDate = new Date();
        this.Address = "";
        this.Zipcode = "00000";
        this.PhoneNumber = "000-000-0000";
        this.DoctorID = "";
    }
    
    public Patient(int id, String First, String Last, boolean Gender, Date Birth, String Addr, String Zip, String Phone, String DID)
    {
        this.PatientID = id;
        this.FirstName = First;
        this.LastName = Last;
        this.Gender = Gender;
        this.BirthDate = Birth;
        this.Address = Addr;
        this.Zipcode = Zip;
        this.PhoneNumber = Phone;
        this.DoctorID = DID;
    }
}

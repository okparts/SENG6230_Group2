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
 * This is the user class, define user's attribute
 */

import java.util.Date;

public class User {
    
    // user type
    public enum U_Types{
        GUEST, LABTECH, DOCTOR, ADMIN
    }
    
    /*
     * user information
     */
    public String UserID;       
    public String FirstName;    
    public String LastName;
    public U_Types UserType;
    public Date BirthDate;
    public Date EnrollDate;
    public String Address;
    public String Zipcode;
    public String PhoneNumber;
    
    public User(){
        this.UserID = "";
        this.FirstName = "";
        this.LastName = "";
        this.UserType = U_Types.GUEST;
        this.BirthDate = new Date();
        this.EnrollDate = new Date();
        this.Address = "";
        this.Zipcode = "00000";
        this.PhoneNumber = "000-000-0000";
    }
    
    
    //return the user type
    public String getType()
    {
        String role = "";
        switch (this.UserType)
        {
            case LABTECH:
                role ="LABTECH";
                break;
            case DOCTOR:
                role ="DOCTOR";
                break;
            case ADMIN:
                role ="ADMIN";
                break;
            default: 
                role ="GUEST";
                break;
        }
        return role;
    }
}

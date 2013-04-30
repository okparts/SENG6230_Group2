/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MTMS;

import java.util.Date;

/**
 *
 * @author hguo87
 * comments by Yuan He
 * 
 * This is the test class, define test's attributes
 */

public class Test {
    public int TestID;
    public int PatientID;
    public String DoctorID;
    public Date TestDate;
    public String TestType;
    public String TestDes;
    public String TestRes;
    public String Report;
    public String Comment;          //Comment is used to determine the type of Result and Report.
    public String LastUpdatedBy;    //last user update the test
    public String Status;
    
    public Test(){
        this.TestID = -1;
        this.PatientID = -1;
        this.DoctorID = "";
        this.TestDate = new Date();
        this.TestType = "";
        this.TestDes = "";
        this.TestRes = "";
        this.Report = "";
        this.Comment = "";
        this.LastUpdatedBy = "";
        this.Status = "NONE";
    }
}

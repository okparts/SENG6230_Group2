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
 * This is test controller module
 */


    
/*
 * attribute comment will 
 * use the following format: Text,Text or JPG, PDF
 *  First is the type of the result, second is the type of the report.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Test_Controller {
    public boolean Is_Connected;        //check is connected
    public Connection con;
    public Statement st; 
    public String ConnectionString;
    public String err;
    
    public Test_Controller(){
        this.con = null;
        this.Is_Connected = false;
        this.ConnectionString = "jdbc:mysql://localhost:3306/MTMS?user=root&password=";
        this.err = "";
    }
    
    //initiate with a customized database connnection string
    public Test_Controller(String cs){
        this.con = null;  
        this.Is_Connected = false;
        this.ConnectionString = cs;  
        this.err = "";
    }
    
    //initiate with a customized database and information
    public Test_Controller(String Host, String DB, String UserID, String PWD){
        this.con = null;  
        this.Is_Connected = false;
        this.ConnectionString = "jdbc:mysql://"+Host+":3306/"+DB+"?user="+UserID+"&password="+PWD;   
        this.err = "";
    }  
        
    // connect to the database
    public void getConnection(){
        this.con = null;  
        try {  
            Class.forName("com.mysql.jdbc.Driver");
                this.con = DriverManager.getConnection(ConnectionString);   
                this.st = (Statement) con.createStatement();
                this.Is_Connected = true;
        } catch (Exception e) {  
            this.err = "Cannot connect to database.";
            this.Is_Connected = false;
        }  
    }
    
    
    // search the test by testid or patient
    // return a tests array list
    public ArrayList SearchTest(int TID, String IDType)
    {
        String sql = "";
        if (TID <= 0)
            sql = "select * from Test";
        else if (IDType.equals("Patient"))
            sql = "select * from Test where PatientID = " + String.valueOf(TID);
        else
            sql = "select * from Test where TestID = " + String.valueOf(TID);
        return ST(sql);
    }
    
    /*
     *  search the test under a doctor 
     *  return a tests array list
     */
    public ArrayList SearchTest(String DoctorID)
    {
        String sql = 
                "select * from Test where DoctorID like '%" + DoctorID + "%'";
        return ST(sql);
    }
    
    /*
     *  search the test by a query string 
     *  return a tests array list
     */
    private ArrayList ST(String sql){
        sql += " order by TestDate desc";
        ArrayList list = new ArrayList();
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return list;
        }    
        Test temp = null;
        try{
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                temp = new Test();
                temp.TestID = rs.getInt("TestID");
                temp.PatientID = rs.getInt("PatientID");
                temp.DoctorID = rs.getString("DoctorID");
                temp.TestDate = rs.getDate("TestDate");
                temp.TestType = rs.getString("TestType");
                temp.TestDes = rs.getString("TestDes");
                //temp.TestRes = rs.getString("TestRes");
                //temp.Report = rs.getString("Report");
                temp.TestRes = "";
                temp.Report = "";
                temp.Comment = rs.getString("Comment");
                String[] tt = temp.Comment.split(",");
                if (tt[0].equals("Text"))
                        temp.TestRes = rs.getString("TestResult");
                    else
                        temp.TestRes = tt[0];
                if (tt.length >=2)
                {
                    if (tt[1].equals("Text"))
                        temp.Report = rs.getString("Report");
                    else
                        temp.Report = tt[1];
                }
                temp.LastUpdatedBy = rs.getString("UpdatedBy");
                temp.Status = rs.getString("Status");
                list.add(temp);
            }
            con.close();
            Is_Connected = false;
        }
        catch (SQLException e){
            list = new ArrayList();
            this.err = "Cannot connect to database.";
            Is_Connected = false;
            return list;
        }
        return list;
    }
    
    
    /*
     *  initiate a test orders
     *  if success return the testID.
     */
    public int OrderTest(int PID, String DID, Date TestDate, String TestType, String TestDes)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(DID, "ID");
        if (list.isEmpty()) {this.err = "No such user. ";return -1;}
        User doc = (User)list.get(0);
        if ((doc.UserType != User.U_Types.DOCTOR)&&(doc.UserType != User.U_Types.ADMIN)) {
            this.err = "Not a doctor."; 
            return -1;
        }
        
        //set the patient info
        Patient_Controller PC = new Patient_Controller(ConnectionString);
        list = PC.SearchPatient(PID);
        if (list.size()<=0)
        {
            this.err = "This patient doesn't exist.";
            return -1;
        }
        
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return -1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(TestDate);
        String sql = "select max(TestID) as M from Test";
        
        int id = 0;
        try{
            ResultSet rs = st.executeQuery(sql);
            if(!rs.next())
            {
                id = 1;
            }
            else
            {
                id = rs.getInt("M");
                id++;
            }
            sql = "insert into Test values ("
                + String.valueOf(id) +","+String.valueOf(PID);
            sql += ",'"+DID+"','";
            sql += date1 + "','";
            sql += TestType +"','";
            sql += TestDes +"','','','','";
            sql += DID +"','ORDERED')";
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return id;
        }
        catch (SQLException e){
            Is_Connected = false;
            this.err = "Inserting error.";
            return -1;
        }
    }
    
    //update the a test in the database
    public boolean UpdateTest(int TID, int PID, String DID, Date TestDate, String TestType, String TestDes, String Res, String Rep, String Comm, String UID, String status)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(DID, "ID");
        if (list.isEmpty()) {this.err = "Invalid doctor ID. ";return false;}
        User doc = (User)list.get(0);
        if ((doc.UserType != User.U_Types.DOCTOR)&&(doc.UserType != User.U_Types.ADMIN)) {
            this.err = "Not a doctor."; 
            return false;
        }
        
        list = uc.SearchUser(UID, "ID");
        if (list.isEmpty()) {this.err = "Invalid user ID. ";return false;}

        
        Patient_Controller PC = new Patient_Controller(ConnectionString);
        list = PC.SearchPatient(PID);
        if (list.size()<=0)
        {
            this.err = "This patient doesn't exist.";
            return false;
        }
        
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        
        //get the update datee
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(TestDate);
        
        String sql  = "update Test set PatientID = "
                + String.valueOf(PID) +", DoctorID = '"+String.valueOf(DID);
            sql += "', TestDate = '"+date1+"', TestType = '";
            sql += TestType +"',TestDes = '";
            sql += TestDes +"', TestResult = '";
            sql += Res + "', Report = '" + Rep + "', Comment = '";
            sql += Comm + "', UpdatedBy = '" +UID +"', Status = '";
            sql += status +"' where TestID = ";
            sql += String.valueOf(TID);

        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            Is_Connected = false;
            this.err = "Updating error.";
            return false;
        }
    }
    
    //update the a test in the database
    public boolean UpdateTest(int TID, String TestType, String TestDes, String UID)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(UID, "ID");
        if (list.isEmpty()) {this.err = "Invalid user ID. ";return false;}
        User doc = (User)list.get(0);
        if ((doc.UserType != User.U_Types.DOCTOR)&&(doc.UserType != User.U_Types.ADMIN)) {
            this.err = "Pemission denied."; 
            return false;
        }
        
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        
        String sql  = "update Test set TestType = '";
            sql += TestType +"',TestDes = '";
            sql += TestDes +"', UpdatedBy = '" +UID +"' where TestID = ";
            sql += String.valueOf(TID);
            
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            Is_Connected = false;
            this.err = "Updating error.";
            return false;
        }
    }
    
    //get the comment of some test
    public String GetComment(int TestID)
    {
        String Comm = "";
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return Comm;
        }    
        String sql = "select Comment from Test where TestID = "+String.valueOf(TestID);
        try{
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                Comm = rs.getString("Comment");
            }
            con.close();
            Is_Connected = false;
        }
        catch (SQLException e){
            this.err = "Cannot connect to database.";
            Is_Connected = false;
        }
        return Comm;
    }
    
    // update the test result
    public boolean FinishTest(int TestID, String TestRes, String UID)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(UID, "ID");
        if (list.isEmpty()) {this.err = "No such user. ";return false;}

        String Comm = this.GetComment(TestID);
        String[] tt = Comm.split(",");
        if (tt.length>=2)
        {
            Comm = "Text,"+tt[1];
        }
        else
        {
            if (Comm.indexOf(",")==0)
            {
                Comm = "Text," + tt[0];
            }
            else
            {
                Comm = "Text,";
            }
        }
                
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        
        String sql =
                "update Test set TestResult = '"+TestRes+"', UpdatedBy = '";
        sql += UID + "', Comment = '"+Comm+ "', Status = 'FINISHED' where TestID = "+ String.valueOf(TestID);
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            Is_Connected = false;
            this.err = "Test finishing error.";
            return false;
        }
    }
    
    
/*
 * legacy method, unused
 *
    public boolean FinishTest(int TestID, FileInputStream f, String FileType, String UID)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(UID, "ID");
        if (list.isEmpty()) {this.err = "No such user. ";return false;}

        String Comm = this.GetComment(TestID);
        String[] tt = Comm.split(",");
        if (tt.length>=2)
        {
            Comm = FileType + "," + tt[1];
        }
        else
        {
            if (Comm.indexOf(",")==0)
            {
                Comm = FileType + "," + tt[0];
            }
            else
            {
                Comm = FileType + ",";
            }
        }
                
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        String sql =
                "update Test set TestResult = ?, UpdatedBy = '";
        sql += UID + "', Comment = '"+Comm+"', Status = 'FINISHED' where TestID = "+ String.valueOf(TestID);
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.setBinaryStream(1, f);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            Is_Connected = false;
            this.err = "Test finishing error.";
            return false;
        }
    }
*/
    
    public boolean ReportTest(int TestID, String Rep, String UID)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(UID, "ID");
        if (list.isEmpty()) {this.err = "No such user. ";return false;}

        String Comm = this.GetComment(TestID);
        String[] tt = Comm.split(",");
        if (tt.length>=2)
            Comm = tt[0]+",Text";
        else
        {
            if (Comm.indexOf(",")==0)
            {
                Comm = ",Text";
            }
            else
            {
                Comm = tt[0] +",Text";
            }
        }
                
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        String sql =
                "update Test set Report = '"+Rep+"', UpdatedBy = '";
        sql += UID + "', Comment = '"+Comm+"', Status = 'REPORTED' where TestID = "+ String.valueOf(TestID);
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            Is_Connected = false;
            this.err = "Reporting error.";
            return false;
        }
    }

    
    public boolean ReportTest(int TestID, FileInputStream f, String FileType, String UID)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(UID, "ID");
        if (list.isEmpty()) {this.err = "No such user. ";return false;}
        
        String Comm = this.GetComment(TestID);
        String[] tt = Comm.split(",");
        if (tt.length>=2)
            Comm = tt[0]+"," + FileType;
        else
        {
            if (Comm.indexOf(",")==0)
            {
                Comm = ","+FileType;
            }
            else
            {
                Comm = tt[0] +","+ FileType;
            }
        }
        
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        String sql =
                "update Test set Report = ?, UpdatedBy = '";
        sql += UID + "', Comment = '"+Comm+"', Status = 'REPORTED' where TestID = "+ String.valueOf(TestID);
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.setBinaryStream(1, f);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            Is_Connected = false;
            this.err = "Reporting error.";
            return false;
        }
    }
    
    
    //delete the test
    public boolean DeleteTest(int TestID)
    {
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        String sql = 
                "delete from Test where TestID = " + String.valueOf(TestID);
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            this.err = "Deleting error.";
            Is_Connected = false;
            return false;
        }
    }
    
    //get test status
    public String GetStatus(int TestID)
    {
        String Status = "NONE";
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return Status;
        }    
        String sql = "select Status from Test where TestID = "+String.valueOf(TestID);
        try{
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                Status = rs.getString("Status");
            }
            con.close();
            Is_Connected = false;
        }
        catch (SQLException e){
            this.err = "Cannot connect to database.";
            Is_Connected = false;
        }
        return Status;
    }
    
    //get the test result from the test
    public Blob GetResult(int TestID)
    {
        Blob res = null;
        String Status = GetStatus(TestID);
        if (Status.equals("NONE")||Status.equals("ORDERED"))
        {
            this.err = "This test is not finished.";
            return res;
        }
        
        String Comm = GetComment(TestID);
        String[] tt = Comm.split(",");
        if (tt.length<2)
        {
            this.err = "Type error.";
            return res;
        }
        else
        {
            this.err = tt[0];
        }
        String sql = "select TestRes from test where TestID = " + String.valueOf(TestID);
        try{
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                res = rs.getBlob("TestRes");
            }
            con.close();
            Is_Connected = false;
        }
        catch (SQLException e){
            this.err = "Cannot connect to database.";
            Is_Connected = false;
            return null;
        }
        return res;
    }
    
    //get the test report from the test
    public Blob GetReport(int TestID)
    {
        Blob rep = null;
        String Status = GetStatus(TestID);
        if (Status.equals("NONE")||Status.equals("ORDERED")||Status.equals("FINISHED"))
        {
            this.err = "This test is not reported yet.";
            return rep;
        }
        
        String Comm = GetComment(TestID);
        String[] tt = Comm.split(",");
        if (tt.length<2)
        {
            this.err = "Type error.";
            return rep;
        }
        else
        {
            this.err = tt[1];
        }
        String sql = "select Report from test where TestID = " + String.valueOf(TestID);
        try{
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                rep = rs.getBlob("Report");
            }
            con.close();
            Is_Connected = false;
        }
        catch (SQLException e){
            this.err = "Cannot connect to database.";
            Is_Connected = false;
            return null;
        }
        return rep;
    }
}

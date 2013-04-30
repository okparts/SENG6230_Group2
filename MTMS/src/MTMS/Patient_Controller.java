/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MTMS;

/**
 *
 * @author hguo87
 * 
 * comments by Yuan He
 * this is the patient control module
 */

import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class Patient_Controller {
    public boolean Is_Connected;
    public Connection con;
    public Statement st; 
    public String ConnectionString;
    public String err;
    
    public Patient_Controller(){
        this.con = null;
        this.Is_Connected = false;
        this.ConnectionString = "jdbc:mysql://localhost:3306/MTMS?user=root&password=";
        this.err = "";
    }
    
    public Patient_Controller(String cs){
        this.con = null;  
        this.Is_Connected = false;
        this.ConnectionString = cs;  
        this.err = "";
    }
    
    public Patient_Controller(String Host, String DB, String UserID, String PWD){
        this.con = null;  
        this.Is_Connected = false;
        this.ConnectionString = "jdbc:mysql://"+Host+":3306/"+DB+"?user="+UserID+"&password="+PWD;   
        this.err = "";
    }  
        
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
    
    public ArrayList SearchPatient(int PID)
    {
        String sql = 
                "select * from Patient where PatientID = " + String.valueOf(PID);
        return SP(sql);
    }
    
    public ArrayList SearchPatient(String DID)
    {
        String sql = 
                "select * from Patient where DoctorID = '" + DID +"'";
        return SP(sql);
    }
    
    public ArrayList SearchPatient(String First, String Last)
    {
        if ((First.equals(""))&&(Last.equals(""))) return (new ArrayList());
        String sql = "select * from Patient where ";
        if (Last .equals("")||First.equals(""))
        {
            String temp = Last+First;
            sql +="FirstName like '"+temp+"%' or LastName like '"+temp+"%'";
        }
        else
            sql +="FirstName like '" + First +"%' and LastName like '" +Last +"%'";
        return SP(sql);
    }
    
    public ArrayList SP(String sql){
        ArrayList list = new ArrayList();
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return list;
        }    
        Patient temp = null;
        try{
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                temp = new Patient();
                temp.PatientID = rs.getInt("PatientID");
                temp.FirstName = rs.getString("FirstName");
                temp.LastName = rs.getString("LastName");
                temp.Gender = (rs.getInt("Gender") == 1);
                temp.BirthDate = rs.getDate("BirthDate");
                temp.Address = rs.getString("Address");
                temp.Zipcode = rs.getString("Zipcode");
                temp.PhoneNumber = rs.getString("PhoneNo");
                temp.DoctorID = rs.getString("DoctorID");
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
    
    public boolean CreatePatient(int PID, String First, String Last, boolean Gender, Date Birth, String Addr, String Zip, String Phone, String DID)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(DID, "ID");
        if (list.isEmpty()) {this.err = "No such user. ";return false;}
        User doc = (User)list.get(0);
        if ((doc.UserType != User.U_Types.DOCTOR)&&(doc.UserType != User.U_Types.ADMIN)) {
            this.err = "Not a doctor."; 
            return false;
        }
        
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(Birth);
        String sql = "insert into Patient values ("
            + String.valueOf(PID) + ", '" + First +"', '" + Last +"', ";
        sql += Gender?"1":"0";
        sql += ", '" + date1 +"', '"+ Addr + "', '" + Zip + "', '" + Phone + "', '";
        sql += DID + "')";
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            Is_Connected = false;
            this.err = "Inserting error.";
            return false;
        }
    }
    
    public int CreatePatient(String First, String Last, boolean Gender, Date Birth, String Addr, String Zip, String Phone, String DID)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(DID, "ID");
        if (list.isEmpty()) {this.err = "No such user. ";return -1;}
        User doc = (User)list.get(0);
        if ((doc.UserType != User.U_Types.DOCTOR)&&(doc.UserType != User.U_Types.ADMIN)) {
            this.err = "Not a doctor."; 
            return -1;
        }
        
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return -1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(Birth);
        String sql = "select max(PatientID) as M from Patient";
        
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
            sql = "insert into Patient values ("
                + String.valueOf(id) + ", '" + First +"', '" + Last +"', '" + date1 + "', ";
            sql += Gender?"1":"0";
            sql += ", '"+ Addr + "', '" + Zip + "', '" + Phone + "', '";
            sql += DID + "')";
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
    
    public boolean UpdatePatient(int PID, String First, String Last, boolean Gender, Date Birth, String Addr, String Zip, String Phone, String DID)
    {
        User_Controller uc = new User_Controller(ConnectionString);
        ArrayList list = uc.SearchUser(DID, "ID");
        if (list.isEmpty()) {this.err = "No such user. ";return false;}
        User doc = (User)list.get(0);
        if (doc.UserType != User.U_Types.DOCTOR&&(doc.UserType != User.U_Types.ADMIN)) 
        {this.err = "Not a doctor."; return false;}
        
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(Birth);
        String sql = "update Patient set FirstName = '"
            + First +"', LastName = '" + Last +"', Gender = ";
        sql += Gender?"1":"0";
        sql += ", BirthDate = '" 
            + date1 + "', Address = '"
            + Addr + "', Zipcode = '" 
            + Zip + "', PhoneNo = '" 
            + Phone + "', DoctorID = '";
        sql += DID +"'";
        sql += " where PatientID = " + String.valueOf(PID);
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            this.err = "Updating error.";
            Is_Connected = false;
            return false;
        }
    }
    
    public boolean DeletePatient(int PID)
    {
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        String sql = 
                "delete from Patient where PatientID = " + String.valueOf(PID);
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
}

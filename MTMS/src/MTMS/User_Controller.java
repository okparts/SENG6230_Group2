/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MTMS;

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

/**
 *
 * @author hguo87
 */
public class User_Controller {
    
    public boolean Is_Connected;
    public Connection con;
    public Statement st; 
    public String ConnectionString;
    public String err;
    
    public User_Controller(){
        this.con = null;
        this.Is_Connected = false;
        this.ConnectionString = "jdbc:mysql://localhost:3306/MTMS?user=root&password=";
        this.err = "";
    }
    
    public User_Controller(String cs){
        this.con = null;  
        this.Is_Connected = false;
        this.ConnectionString = cs;  
        this.err = "";
    }
    
    public User_Controller(String Host, String DB, String UserID, String PWD){
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
    
    public User ValidateLogin(String UserID, String PWD){
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return null;
        }
        String sql = 
                "select * from User where UserID = '" + UserID +
                "' and Password = '" + PWD + "'";
        User temp = null;
        try{
            ResultSet rs = st.executeQuery(sql);
            if (!rs.next())
            {
                this.err = "Invalid user ID or password.";
                return null;
            }
            temp = new User();
            temp.UserID = UserID;
            temp.FirstName = rs.getString("FirstName");
            temp.LastName = rs.getString("LastName");
            temp.BirthDate = rs.getDate("BirthDate");
            temp.EnrollDate = rs.getDate("EnrollDate");
            temp.Address = rs.getString("Address");
            temp.Zipcode = rs.getString("Zipcode");
            temp.PhoneNumber = rs.getString("PhoneNo");
            String UT = rs.getString("UserType").toUpperCase();
            switch (UT)
            {
                case "LABTEC":
                    temp.UserType = User.U_Types.LABTECH;
                    break;
                case "DOCTOR":
                    temp.UserType = User.U_Types.DOCTOR;
                    break;
                case "ADMINS":
                    temp.UserType = User.U_Types.ADMIN;
                    break;
                default: temp.UserType = User.U_Types.GUEST;
                    break;
            }
            con.close();
            Is_Connected = false;
        }
        catch (SQLException e){
            this.err = "Cannot connect to database.";
            Is_Connected = false;
            return null;
        }
        return temp;
    }
    
    public ArrayList SearchUser(String key, String Type)
    {
        String sql = "";
        if (key.trim().equals(""))
        {
            sql = "select * from User";
            return SU(sql);
        }
        switch(Type)
        {
            case "ID":
                sql = "select * from User where UserID ='" + key + "'";
                break;
            case "Name":
                String[] names = key.split(";");
                String First = names[0];
                String Last = "";
                if(names.length>=2)
                    Last = names[1];
                if ((First.equals(""))&&(Last.equals(""))) return (new ArrayList());
                sql = "select * from User where ";
                if (Last.equals("")||First.equals(""))
                {
                    String temp = Last+First;
                    sql +="FirstName like '%"+temp+"%' or LastName like '%"+temp+"%'";
                }
                else
                    sql +="FirstName like '%" + First +"%' and LastName like '%" +Last +"%'";
                break;
            case "ID2":
                sql = "select * from User where UserID like '%" + key + "%'";
                break;
            default:
                return new ArrayList();
        }
        return SU(sql);
    }
    
    public ArrayList SU(String sql){
        ArrayList list = new ArrayList();
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return list;
        }
        User temp = null;
        try{
            ResultSet rs = st.executeQuery(sql);
            while(rs.next())
            {
                temp = new User();
                temp.UserID = rs.getString("UserID");
                temp.FirstName = rs.getString("FirstName");
                temp.LastName = rs.getString("LastName");
                temp.BirthDate = rs.getDate("BirthDate");
                temp.EnrollDate = rs.getDate("EnrollDate");
                temp.Address = rs.getString("Address");
                temp.Zipcode = rs.getString("Zipcode");
                temp.PhoneNumber = rs.getString("PhoneNo");
                String UT = rs.getString("UserType").toUpperCase();
                switch (UT)
                {
                    case "LABTEC":
                        temp.UserType = User.U_Types.LABTECH;
                        break;
                    case "DOCTOR":
                        temp.UserType = User.U_Types.DOCTOR;
                        break;
                    case "ADMINS":
                        temp.UserType = User.U_Types.ADMIN;
                        break;
                    default: temp.UserType = User.U_Types.GUEST;
                        break;
                }
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
    
    public boolean ChangePassword(String UserID, String OldPwd, String NewPwd)
    {
        User temp = ValidateLogin(UserID, OldPwd);
        if (temp == null)
        {
            this.err = "Wrong user ID or password.";
            return false;
        }
        
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        String sql = 
                "update User set Password = '" + NewPwd + "' where UserID = '" + UserID + "'";
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            this.err = "Password changing error.";
            Is_Connected = false;
            return false;
        }
    }
    
    public boolean ChangePassword(String UserID, String NewPwd)
    {
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        String sql = 
                "update User set Password = '" + NewPwd + "' where UserID = '" + UserID + "'";
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            this.err = "Password changing error.";
            Is_Connected = false;
            return false;
        }
    }
    
    public boolean CreateUser(String UserID, String Pwd, String First, String Last, Date Birth, Date Enroll, String Addr, String Zip, String Phone, User.U_Types UT)
    {
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(Birth);
        String date2 = sdf.format(Enroll);
        String sql = "insert into User values ('"
            + UserID + "', '" + Pwd + "', '" + First +"', '" + Last +"', '" + date1 + "', '" + date2 +"', '"
            + Addr + "', '" + Zip + "', '" + Phone + "', '";
        switch (UT)
        {
            case LABTECH:
                sql+="LABTEC')";
                break;
            case DOCTOR:
                sql+="DOCTOR')";
                break;
            case ADMIN:
                sql+="ADMINS')";
                break;
            default: 
                sql+="GUESTS')";
                break;           
        }
        try{
            PreparedStatement pstam = con.prepareStatement(sql);
            pstam.execute();
            con.close();
            Is_Connected = false;
            return true;
        }
        catch (SQLException e){
            this.err = "Inserting error.";
            Is_Connected = false;
            return false;
        }
    }
    
    public boolean UpdateUser(String UserID, String First, String Last, Date Birth, Date Enroll, String Addr, String Zip, String Phone, User.U_Types UT)
    {
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(Birth);
        String date2 = sdf.format(Enroll);
        String sql = "update User set FirstName = '"
            + First +"', LastName = '" 
            + Last +"', BirthDate = '" 
            + date1 + "', EnrollDate = '" 
            + date2 + "', Address = '"
            + Addr + "', Zipcode = '" 
            + Zip + "', PhoneNo = '" 
            + Phone + "', UserType = '";
        switch (UT)
        {
            case LABTECH:
                sql+="LABTEC'";
                break;
            case DOCTOR:
                sql+="DOCTOR'";
                break;
            case ADMIN:
                sql+="ADMINS'";
                break;
            default: 
                sql+="GUESTS'";
                break;           
        }
        sql += " where UserID = '" + UserID +"'";
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
    
    public boolean UpdateUser(String UserID, String First, String Last, Date Birth, String Addr, String Zip, String Phone)
    {
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = sdf.format(Birth);
        String sql = "update User set FirstName = '"
            + First +"', LastName = '" 
            + Last +"', BirthDate = '" 
            + date1 + "', Address = '"
            + Addr + "', Zipcode = '" 
            + Zip + "', PhoneNo = '" 
            + Phone + "'";
        sql += " where UserID = '" + UserID +"'";
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
    
    public boolean DeleteUser(String UserID)
    {
        getConnection();
        if (!Is_Connected)
        {
            this.err = "Cannot connect to database.";
            return false;
        }
        String sql = 
                "delete from User where UserID = '" + UserID + "'";
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

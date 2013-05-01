/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MTMS;

import MTMS.User.U_Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuan He
 */



public class User_ControllerTest {
    
    private User testUser;
    
    public User_ControllerTest() {
        testUser = new User();
        testUser.UserID = "testUserDoctor";
        String testPassword = "test";
        testUser.FirstName = "John";
        testUser.LastName = "Doe";
        testUser.UserType = U_Types.DOCTOR;
        testUser.Zipcode = "27858";
        testUser.Address = "E 10th st apt 2c";
        testUser.PhoneNumber = "252-412-8187";
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date testBirdDay = new Date();
        Date testEnrollDay = new Date();
        
        try{
             testUser.BirthDate = sdf.parse("1988-1-1");
             testUser.EnrollDate = sdf.parse("2012-01-02");
        }  catch(Exception e)
        {
            
        }
        
        User_Controller testConnection = new User_Controller();
        boolean isAddUser = testConnection.CreateUser(testUser.UserID, testPassword, testUser.FirstName, testUser.LastName, testUser.BirthDate, testUser.EnrollDate, testUser.Address, testUser.Zipcode,testUser.PhoneNumber, testUser.UserType);


      
    
    }
        
        
        
    }
    
    @BeforeClass
    public static void setUpClass() {

 
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getConnection method, of class User_Controller.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        User_Controller instance = new User_Controller();
        instance.getConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ValidateLogin method, of class User_Controller.
     */
    @Test
    public void testValidateLogin() {
        System.out.println("ValidateLogin");
        String UserID = "";
        String PWD = "";
        User_Controller instance = new User_Controller();
        User expResult = null;
        User result = instance.ValidateLogin(UserID, PWD);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SearchUser method, of class User_Controller.
     */
    @Test
    public void testSearchUser() {
        System.out.println("SearchUser");
        String key = "";
        String Type = "";
        User_Controller instance = new User_Controller();
        ArrayList expResult = null;
        ArrayList result = instance.SearchUser(key, Type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ChangePassword method, of class User_Controller.
     */
    @Test
    public void testChangePassword_3args() {
        System.out.println("ChangePassword");
        String UserID = "";
        String OldPwd = "";
        String NewPwd = "";
        User_Controller instance = new User_Controller();
        boolean expResult = false;
        boolean result = instance.ChangePassword(UserID, OldPwd, NewPwd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ChangePassword method, of class User_Controller.
     */
    @Test
    public void testChangePassword_String_String() {
        System.out.println("ChangePassword");
        String UserID = "";
        String NewPwd = "";
        User_Controller instance = new User_Controller();
        boolean expResult = false;
        boolean result = instance.ChangePassword(UserID, NewPwd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CreateUser method, of class User_Controller.
     */
    @Test
    public void testCreateUser() {
        System.out.println("CreateUser");
        String UserID = "";
        String Pwd = "";
        String First = "";
        String Last = "";
        Date Birth = null;
        Date Enroll = null;
        String Addr = "";
        String Zip = "";
        String Phone = "";
        U_Types UT = null;
        User_Controller instance = new User_Controller();
        boolean expResult = false;
        boolean result = instance.CreateUser(UserID, Pwd, First, Last, Birth, Enroll, Addr, Zip, Phone, UT);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of UpdateUser method, of class User_Controller.
     */
    @Test
    public void testUpdateUser_9args() {
        System.out.println("UpdateUser");
        String UserID = "";
        String First = "";
        String Last = "";
        Date Birth = null;
        Date Enroll = null;
        String Addr = "";
        String Zip = "";
        String Phone = "";
        U_Types UT = null;
        User_Controller instance = new User_Controller();
        boolean expResult = false;
        boolean result = instance.UpdateUser(UserID, First, Last, Birth, Enroll, Addr, Zip, Phone, UT);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of UpdateUser method, of class User_Controller.
     */
    @Test
    public void testUpdateUser_7args() {
        System.out.println("UpdateUser");
        String UserID = "";
        String First = "";
        String Last = "";
        Date Birth = null;
        String Addr = "";
        String Zip = "";
        String Phone = "";
        User_Controller instance = new User_Controller();
        boolean expResult = false;
        boolean result = instance.UpdateUser(UserID, First, Last, Birth, Addr, Zip, Phone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of DeleteUser method, of class User_Controller.
     */
    @Test
    public void testDeleteUser() {
        System.out.println("DeleteUser");
        String UserID = "";
        User_Controller instance = new User_Controller();
        boolean expResult = false;
        boolean result = instance.DeleteUser(UserID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}

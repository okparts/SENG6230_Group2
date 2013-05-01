/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MTMS;

import MTMS.User.U_Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuan He
 * 
 * This the JUnit test set of the User controller module
 */
public class User_ControllerTest {
    
    private User testUser;
    private String testPassword;

    
    public User_ControllerTest() {
        
        testUser = new User();
        testUser.UserID = "testUserDoctor";
        testPassword = "test";
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
             testUser.BirthDate = sdf.parse("1988/1/1");
             testUser.EnrollDate = sdf.parse("2012/01/02");
        }  catch(Exception e)
        {
            
        }

    }
    
    @BeforeClass
    public static void setUpClass() {

    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getConnection method, of class User_Controller.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        User_Controller instance = new User_Controller();
        boolean expResult = true;
        boolean result = instance.getConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    
    
        /**
     * Test of CreateUser method, of class User_Controller.
     */
    @Test
    public void testCreateUser() {
        System.out.println("CreateUser");
        boolean result;
        User_Controller instance = new User_Controller();
        boolean expResult = true;
        result = instance.CreateUser(testUser.UserID, testPassword, testUser.FirstName, testUser.LastName, testUser.BirthDate, testUser.EnrollDate, testUser.Address, testUser.Zipcode,testUser.PhoneNumber, testUser.UserType);
        System.out.print(instance.err);
        assertEquals(expResult, result);
       
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of ValidateLogin method, of class User_Controller.
     */
    @Test
    public void testValidateLogin() {
        System.out.println("ValidateLogin");
        User_Controller instance = new User_Controller();
        User expResult = testUser;
        User result = instance.ValidateLogin(testUser.UserID, testPassword);
        Assert.assertEquals(expResult.UserID, result.UserID);
        Assert.assertEquals(expResult.Address, result.Address);
        Assert.assertEquals(expResult.BirthDate, result.BirthDate);
        Assert.assertEquals(expResult.EnrollDate, result.EnrollDate);
        Assert.assertEquals(expResult.FirstName, result.FirstName);
        Assert.assertEquals(expResult.UserType, result.UserType);
    }

    /**
     * Test of SearchUser method, of class User_Controller.
     */
    @Test
    public void testSearchUser() {
        System.out.println("SearchUser");
        User_Controller instance = new User_Controller();
        User expResult = testUser;
        ArrayList result = instance.SearchUser(testUser.UserID, "ID");
        assertEquals(expResult.UserID, ((User)result.get(0)).UserID);
        assertEquals(expResult.UserType, ((User)result.get(0)).UserType);  
    }

    /**
     * Test of ChangePassword method, of class User_Controller.
     */
    @Test
    public void testChangePassword_3args() {
        System.out.println("ChangePassword");
        String UserID = testUser.UserID;
        String OldPwd = testPassword;
        String NewPwd = "newPassword";
        User_Controller instance = new User_Controller();
        boolean expResult = true;
        boolean result = instance.ChangePassword(UserID, OldPwd, NewPwd);
        assertEquals(expResult, result); 
        assertEquals(testUser.PhoneNumber, instance.ValidateLogin(UserID, NewPwd).PhoneNumber);
    }

    /**
     * Test of ChangePassword method, of class User_Controller.
     */
    @Test
    public void testChangePassword_String_String() {
        System.out.println("ChangePassword");
        String UserID = testUser.UserID;
        String NewPwd = "adminPassword";
        User_Controller instance = new User_Controller();
        boolean expResult = true;
        boolean result = instance.ChangePassword(UserID, NewPwd);
        assertEquals(expResult, result);
        assertEquals(testUser.PhoneNumber, instance.ValidateLogin(UserID, NewPwd).PhoneNumber);
    }

    /**
     * Test of UpdateUser method, of class User_Controller.
     */
    @Test
    public void testUpdateUser_9args() {
        System.out.println("UpdateUser");
        User temp = testUser;
        temp.FirstName = "Yuan";
        temp.LastName = "He";
        temp.UserType = U_Types.LABTECH;
        U_Types UT = null;
        User_Controller instance = new User_Controller();
        boolean expResult = true;
        boolean result = instance.UpdateUser(temp.UserID, temp.FirstName, temp.LastName, temp.BirthDate, temp.EnrollDate, temp.Address, temp.Zipcode, temp.PhoneNumber, temp.UserType);
        assertEquals(expResult, result);
        assertEquals(U_Types.LABTECH, instance.ValidateLogin(testUser.UserID, "adminPassword").UserType);
    }

    /**
     * Test of UpdateUser method, of class User_Controller.
     */
    @Test
    public void testUpdateUser_7args() {
        System.out.println("UpdateUser");
        User temp = testUser;
        temp.FirstName = "John";
        temp.LastName = "Doe";
        temp.UserType = U_Types.LABTECH;
        U_Types UT = null;
        User_Controller instance = new User_Controller();
        boolean expResult = true;
        boolean result = instance.UpdateUser(temp.UserID, temp.FirstName, temp.LastName, temp.BirthDate, temp.Address, temp.Zipcode, temp.PhoneNumber);
        assertEquals(expResult, result);
    }

    /**
     * Test of DeleteUser method, of class User_Controller.
     */

   @Test
   public void testDeleteUser() {
        System.out.println("DeleteUser");
        User_Controller instance = new User_Controller();
        boolean expResult = true;
        boolean result = instance.DeleteUser(testUser.UserID);
        assertEquals(expResult, result);
    }

}

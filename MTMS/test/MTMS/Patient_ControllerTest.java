/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MTMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yuan He
 * 
 * This is the JUnit tests set of patient controller
 */

public class Patient_ControllerTest {
    private Patient testPatient = new Patient();
    
    public Patient_ControllerTest() {
        testPatient.FirstName = "Yuan";
        testPatient.LastName = "He";
        testPatient.Gender = false;
        testPatient.Address = "E 10th apt 2c ";
        testPatient.Zipcode = "27858";
        testPatient.DoctorID = "doctorsmith";
        testPatient.PhoneNumber = "111-222-3383";
        testPatient.BirthDate = new Date();
        testPatient.PatientID = 000001;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");        
        try{
             testPatient.BirthDate = sdf.parse("1988/1/1");
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
     * Test of getConnection method, of class Patient_Controller.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        Patient_Controller instance = new Patient_Controller();
        instance.getConnection();
        assertEquals(true, instance.Is_Connected);
    }  
    
    
    /**
     * Test of CreatePatient method, of class Patient_Controller.
     */
    @Test
    public void testCreatePatient_8args() {
        System.out.println("CreatePatient");
        Patient_Controller instance = new Patient_Controller();
        int expResult = 000001;
        testPatient.PatientID = instance.CreatePatient(testPatient.FirstName, testPatient.LastName, testPatient.Gender, testPatient.BirthDate, testPatient.Address, testPatient.Zipcode, testPatient.PhoneNumber, testPatient.DoctorID);
        assertEquals(expResult, testPatient.PatientID);
    }

    
    /**
     * Test of SearchPatient by Patient ID, of class Patient_Controller.
     */
    @Test
    public void testSearchPatient_int() {
        System.out.println("SearchPatient by Patient ID");
        Patient_Controller instance = new Patient_Controller();
        ArrayList result = instance.SearchPatient(testPatient.PatientID);
        assertEquals(testPatient.FirstName, ((Patient)result.get(0)).FirstName);
        assertEquals(testPatient.LastName, ((Patient)result.get(0)).LastName);
    }

    /**
     * Test of SearchPatient method by Doctor ID, of class Patient_Controller.
     */
    @Test
    public void testSearchPatient_String() {
        System.out.println("SearchPatient by Doctor ID");
        Patient_Controller instance = new Patient_Controller();
        ArrayList result = instance.SearchPatient(testPatient.DoctorID);
        assertEquals(testPatient.FirstName, ((Patient)result.get(0)).FirstName);
        assertEquals(testPatient.LastName, ((Patient)result.get(0)).LastName);
    }

    /**
     * Test of SearchPatient method, of class Patient_Controller.
     */
    @Test
    public void testSearchPatient_String_String() {
        System.out.println("SearchPatient by First Name and Last Name");
        String First = testPatient.FirstName;
        String Last = testPatient.LastName;
        Patient_Controller instance = new Patient_Controller();
        ArrayList result = instance.SearchPatient(First, Last);
        assertEquals(testPatient.PatientID, ((Patient)result.get(0)).PatientID);
        assertEquals(testPatient.FirstName, ((Patient)result.get(0)).FirstName);
        assertEquals(testPatient.LastName, ((Patient)result.get(0)).LastName);
    }

   
    /**
     * Test of UpdatePatient method, of class Patient_Controller.
     */
    @Test
    public void testUpdatePatient() {
        System.out.println("UpdatePatient");
        testPatient.FirstName = "John";
        testPatient.LastName = "Doe";        
        Patient_Controller instance = new Patient_Controller();
        boolean expResult = true;
        boolean result = instance.UpdatePatient(testPatient.PatientID, testPatient.FirstName, testPatient.LastName, testPatient.Gender, testPatient.BirthDate, testPatient.Address, testPatient.Zipcode, testPatient.PhoneNumber, testPatient.DoctorID);
        assertEquals(expResult, result);
    }

    /**
     * Test of DeletePatient method, of class Patient_Controller.
     */
    @Test
    public void testDeletePatient() {
        System.out.println("DeletePatient");
        Patient_Controller instance = new Patient_Controller();
        boolean expResult = true;
        boolean result = instance.DeletePatient(0000001);
        assertEquals(expResult, result);
    }
}
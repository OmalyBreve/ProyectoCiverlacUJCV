/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Layouts;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Omaly Breve
 */
public class RegistroLayoutNGTest {
    
    public RegistroLayoutNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of getHash method, of class RegistroLayout.
     */
    @Test
    public void testGetHash() {
        System.out.println("getHash");
        String txt = "123456789";
        String hashType = "MD5";
        String expResult = "25f9e794323b453885f5181f1b624d0b";
        String result = RegistroLayout.getHash(txt, hashType);
        assertEquals(result, expResult);
        if (expResult == null ? result != null : !expResult.equals(result)) {
            fail("The test case is a prototype.");
        }
    }

   
}

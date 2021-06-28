/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civerlac;

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
public class RegistroDBNGTest {

    public RegistroDBNGTest() {
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
     * Test of RegistrarUsuario method, of class RegistroDB.
     */
    @Test
    public void testRegistrarUsuario() {
        System.out.println("RegistrarUsuario");
        int id = 17;
        String clave = "root123";
        if (id <= 0) {
            fail("The test case is a prototype.");
        } else if ("".equals(clave)) {
            fail("The test case is a prototype.");
        } else {
            int privilegio = 1;
            RegistroDB instance = new RegistroDB();
            boolean expResult = true;
            boolean result = instance.RegistrarUsuario(id, clave, privilegio);
            assertEquals(result, expResult);
            // TODO review the generated test code and remove the default call to fail.
            if (result != expResult) {
                fail("The test case is a prototype.");
            }

        }

    }

   
   

}

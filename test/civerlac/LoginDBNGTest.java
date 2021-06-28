/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civerlac;

import java.util.List;
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
public class LoginDBNGTest {

    public LoginDBNGTest() {
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
     * Test of registroLogLogin method, of class LoginDB.
     */
    @Test
    public void testRegistroLogLogin() {
        System.out.println("registroLogLogin");
        String fecha = "2021/06/20";
        String user = "omalybreve@gmail.com";
        String intento = "1";
        ///
        String[] partes = user.split("@");
        String upart1 = partes[0];
        String upart2 = partes[1];
        //
        String[] partes2 = upart2.split(".");
        String _upart1 = partes[0];
        String _upart2 = partes[1];
        //
        if (user.length() < 10) {
            fail("The test case is a prototype.");
        } else if (!user.contains("@")) {
            fail("The test case is a prototype.");
        } else if (!user.contains(".")) {
            fail("The test case is a prototype.");
        } else if (upart2.length() == 0 || upart2.length() < 5) {
            fail("The test case is a prototype.");
        } else if (_upart2.length() == 0 || _upart2.length() < 3) {
            fail("The test case is a prototype.");
        } else {
            LoginDB instance = new LoginDB();
            boolean expResult = true;
            boolean result = instance.registroLogLogin(fecha, user, intento);
            assertEquals(result, expResult);
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        }

    }

    @Test
    public void testRegistroLogLogin2() {
        System.out.println("registroLogLogin");
        String fecha = "2021/06/20";
        String user = "omalybreve@gmail.con";
        String intento = "1";
        ///
        String[] partes = user.split("@");
        String upart1 = partes[0];
        String upart2 = partes[1];
        //
        String[] partes2 = upart2.split(".");
        String _upart1 = partes[0];
        String _upart2 = partes[1];
        //
        if (Integer.parseInt(intento) <= 0 && Integer.parseInt(intento) > 3) {
            fail("The test case is a prototype.");
        } else if (!user.contains("@")) {
            fail("The test case is a prototype.");
        } else if (!user.contains(".")) {
            fail("The test case is a prototype.");
        } else if (upart2.length() == 0 || upart2.length() < 5) {
            fail("The test case is a prototype.");
        } else if (_upart2.length() == 0 || _upart2.length() < 3) {
            fail("The test case is a prototype.");
        } else {
            LoginDB instance = new LoginDB();
            boolean expResult = true;
            boolean result = instance.registroLogLogin(fecha, user, intento);
            assertEquals(result, expResult);
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of updateLogLogin method, of class LoginDB.
     */
    @Test
    public void testUpdateLogLogin() {
        System.out.println("updateLogLogin");
        String intento = "2";
        if (Integer.parseInt(intento) <= 0 && Integer.parseInt(intento) > 3) {
            fail("The test case is a prototype.");
        } else {
            LoginDB instance = new LoginDB();
            boolean expResult = true;
            boolean result = instance.updateLogLogin(intento);
            assertEquals(result, expResult);
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of ListaDatosEmpresa method, of class LoginDB.
     */
    @Test
    public void testListaDatosEmpresa() {
        System.out.println("ListaDatosEmpresa");
        LoginDB instance = new LoginDB();
        List expResult = null;
        List result = instance.ListaDatosEmpresa();
        if (result == null) {
            fail("The test case is a prototype.");
        }
    }

}

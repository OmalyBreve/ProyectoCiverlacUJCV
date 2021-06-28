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
public class ProveedorDBNGTest {

    public ProveedorDBNGTest() {
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
     * Test of ValidarTelefonoProovedor method, of class ProveedorDB.
     */
    @Test
    public void testValidarTelefonoProovedor() {
        System.out.println("ValidarTelefonoProovedor");
        String telefono = "98564356";
        if (telefono.length() == 8
                && telefono.startsWith("3")
                || telefono.startsWith("9")
                || telefono.startsWith("2")) {

            ProveedorDB instance = new ProveedorDB();
            boolean expResult = true;
            boolean result = instance.ValidarTelefonoProovedor(telefono);
            assertEquals(result, expResult);
            // TODO review the generated test code and remove the default call to fail.
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        } else {
            fail("The test case is a prototype.");
        }

    }

    /**
     * Test of ValidarUpdateTelefonoProveedor method, of class ProveedorDB.
     */
    @Test
    public void testValidarUpdateTelefonoProveedor() {
        System.out.println("ValidarUpdateTelefonoProveedor");
        String telefono = "98564356";
        int ID = 4;
        if (telefono.length() == 8
                && telefono.startsWith("3")
                || telefono.startsWith("9")
                || telefono.startsWith("2") && ID > 0) {
            ProveedorDB instance = new ProveedorDB();
            int expResult = 1;
            int result = instance.ValidarUpdateTelefonoProveedor(telefono, ID);
            assertEquals(result, expResult);
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        } else {
            fail("The test case is a prototype.");
        }

    }

    /**
     * Test of registrarProveedor method, of class ProveedorDB.
     */
    @Test
    public void testRegistrarProveedor() {
        System.out.println("registrarProveedor");
        Cliente cl = new Cliente();
        //
        cl.setIdCliente(99);
        cl.setNombre("Test Unitario ");
        cl.setDireccion("TestUnitario de prueba");
        cl.setCorreo("test@gmail.com");
        cl.setEstado(1);
        cl.setIdTelefonoCli(9999999);

        //
        if (cl.getNombre().length() < 10) {
            fail("The test case is a prototype.");

        } else if (!cl.getCorreo().contains("@")) {
            fail("The test case is a prototype.");
        } else if (!cl.getCorreo().contains("gmail.com")
                || !cl.getCorreo().contains("yahoo.com")
                || !cl.getCorreo().contains("hotmail.com")
                || !cl.getCorreo().contains("gmail.es")
                || !cl.getCorreo().contains("yahoo.es") || !cl.getCorreo().contains("hotmail.es")) {
            fail("The test case is a prototype.");
        } else if (cl.getDireccion().length() < 10) {
            fail("The test case is a prototype.");

        } else if (cl.getEstado() != 1 && cl.getEstado() != 2) {
            fail("The test case is a prototype.");

        } else if (String.valueOf(cl.getIdTelefonoCli()).length() == 8
                && String.valueOf(cl.getIdTelefonoCli()).startsWith("3")
                || String.valueOf(cl.getIdTelefonoCli()).startsWith("9")
                || String.valueOf(cl.getIdTelefonoCli()).startsWith("2")) {
        } else {
            ProveedorDB instance = new ProveedorDB();
            boolean expResult = true;
            boolean result = instance.registrarProveedor(cl);
            assertEquals(result, expResult);
            if (expResult != result) {
                fail("The test case is a prototype.");
            }
        }
    }

    /**
     * Test of UpdateProveedor method, of class ProveedorDB.
     */
    @Test
    public void testUpdateProveedor() {
        System.out.println("UpdateProveedor");
        //
        Cliente cl = new Cliente();
        cl.setIdCliente(99);
        cl.setNombre("Test Unitario ");
        cl.setDireccion("TestUnitario de prueba");
        cl.setCorreo("test@gmail.com");
        cl.setEstado(1);
        cl.setIdTelefonoCli(9999999);
        //
        if (cl.getNombre().length() < 10) {
            fail("The test case is a prototype.");

        } else if (!cl.getCorreo().contains("@")) {
            fail("The test case is a prototype.");
        } else if (!cl.getCorreo().contains("gmail.com")
                || !cl.getCorreo().contains("yahoo.com")
                || !cl.getCorreo().contains("hotmail.com")
                || !cl.getCorreo().contains("gmail.es")
                || !cl.getCorreo().contains("yahoo.es") || !cl.getCorreo().contains("hotmail.es")) {
            fail("The test case is a prototype.");
        } else if (cl.getDireccion().length() < 10) {
            fail("The test case is a prototype.");

        } else if (cl.getEstado() != 1 && cl.getEstado() != 2) {
            fail("The test case is a prototype.");

        } else if (String.valueOf(cl.getIdTelefonoCli()).length() == 8
                && String.valueOf(cl.getIdTelefonoCli()).startsWith("3")
                || String.valueOf(cl.getIdTelefonoCli()).startsWith("9")
                || String.valueOf(cl.getIdTelefonoCli()).startsWith("2")) {
        } else {
            ProveedorDB instance = new ProveedorDB();
            boolean expResult = true;
            boolean result = instance.UpdateProveedor(cl);
            assertEquals(result, expResult);
            // TODO review the generated test code and remove the default call to fail.
            if (expResult != result) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of listarClientes method, of class ProveedorDB.
     */
    @Test
    public void testListarClientes() {
        System.out.println("listarClientes");
        ProveedorDB instance = new ProveedorDB();
        List expResult = null;
        List result = instance.listarClientes();
        if (result == null) {
            fail("The test case is a prototype.");
        }
    }

}

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
public class EmpleadosDBNGTest {

    public EmpleadosDBNGTest() {
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
     * Test of ValidarTelefonoEmpleado method, of class EmpleadosDB.
     */
    @Test
    public void testValidarTelefonoEmpleado() {
        System.out.println("ValidarTelefonoEmpleado");
        String telefono = "98765432";
        if (telefono.length() == 8
                && telefono.startsWith("3")
                || telefono.startsWith("9")
                || telefono.startsWith("2")) {
            EmpleadosDB instance = new EmpleadosDB();
            boolean expResult = false;
            boolean result = instance.ValidarTelefonoEmpleado(telefono);
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
     * Test of registrarEmpleado method, of class EmpleadosDB.
     */
    @Test
    public void testRegistrarEmpleado() {
        System.out.println("registrarEmpleado");
        Empleado cl = new Empleado();
        //
        cl.setIdEmpleado(99);
        cl.setNombre("Test Unitario ");
        cl.setDireccion("TestUnitario de prueba");
        cl.setCorreo("test@gmail.com");
        cl.setEstado(1);
        cl.setTelefono(9999999);
        cl.setOcupacion("testUnitario");
        //
        if (cl.getNombre().length() < 10) {
            fail("The test case is a prototype.");

        } else if (cl.getDireccion().length() < 10) {
            fail("The test case is a prototype.");

        } else if (!cl.getCorreo().contains("@")) {
            fail("The test case is a prototype.");
        } else if (!cl.getCorreo().contains("gmail.com") || 
                !cl.getCorreo().contains("yahoo.com") || 
                !cl.getCorreo().contains("hotmail.com")
                || !cl.getCorreo().contains("gmail.es") || 
                !cl.getCorreo().contains("yahoo.es") || !
                cl.getCorreo().contains("hotmail.es")) {
            fail("The test case is a prototype.");
        } else if (cl.getEstado() != 1 && cl.getEstado() != 2) {
            fail("The test case is a prototype.");

        } else if (String.valueOf(cl.getTelefono()).length() == 8
                && String.valueOf(cl.getTelefono()).startsWith("3")
                || String.valueOf(cl.getTelefono()).startsWith("9")
                || String.valueOf(cl.getTelefono()).startsWith("2")) {
        } else {
            EmpleadosDB instance = new EmpleadosDB();
            boolean expResult = true;
            boolean result = instance.registrarEmpleado(cl);
            assertEquals(result, expResult);
            System.out.println("RegEmpl");
            if (expResult != result) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of UpdateEmpleado method, of class EmpleadosDB.
     */
    @Test
    public void testUpdateEmpleado() {
        System.out.println("UpdateEmpleado");
        Empleado cl = new Empleado();
        //
        cl.setIdEmpleado(5);
        cl.setNombre("Test Unitario ");
        cl.setDireccion("TestUnitario de prueba");
        cl.setCorreo("test@gmail.com");
        cl.setEstado(1);
        cl.setTelefono(9999999);
        cl.setOcupacion("testUnitario");
        //
        if (cl.getNombre().length() < 10) {
            fail("The test case is a prototype.");

        } else if (cl.getDireccion().length() < 10) {
            fail("The test case is a prototype.");

        }else if (!cl.getCorreo().contains("@")) {
            fail("The test case is a prototype.");
        } else if (!cl.getCorreo().contains("gmail.com") || 
                !cl.getCorreo().contains("yahoo.com") || 
                !cl.getCorreo().contains("hotmail.com")
                || !cl.getCorreo().contains("gmail.es") || 
                !cl.getCorreo().contains("yahoo.es") || !
                cl.getCorreo().contains("hotmail.es")) {
            fail("The test case is a prototype.");
        } else if (cl.getEstado() != 1 && cl.getEstado() != 2) {
            fail("The test case is a prototype.");

        } else if (String.valueOf(cl.getTelefono()).length() == 8
                && String.valueOf(cl.getTelefono()).startsWith("3")
                || String.valueOf(cl.getTelefono()).startsWith("9")
                || String.valueOf(cl.getTelefono()).startsWith("2")) {
        } else {
            EmpleadosDB instance = new EmpleadosDB();
            boolean expResult = true;
            boolean result = instance.UpdateEmpleado(cl);
            assertEquals(result, expResult);
            // TODO review the generated test code and remove the default call to fail.
            System.out.println(result);
            System.out.println(expResult);
            if (expResult != result) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of ListEmpleados method, of class EmpleadosDB.
     */
    @Test
    public void testListEmpleados() {
        System.out.println("ListEmpleados");
        EmpleadosDB instance = new EmpleadosDB();
        List expResult = null;
        List result = instance.ListEmpleados();
        if (result == null) {
            fail("The test case is a prototype.");
        }
    }

    /**
     * Test of listarClientesBusqueda method, of class EmpleadosDB.
     */
    @Test
    public void testListarClientesBusqueda() {
        System.out.println("listarClientesBusqueda");
        String filtro = "Nombre";
        String dato = "Test Unitario";
        EmpleadosDB instance = new EmpleadosDB();
        List expResult = null;
        List result = instance.listarClientesBusqueda(filtro, dato);
        if (result == null) {
            fail("The test case is a prototype.");
        }
    }

}

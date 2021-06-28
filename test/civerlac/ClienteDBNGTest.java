/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civerlac;

import java.util.List;
import javax.swing.JOptionPane;
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
public class ClienteDBNGTest {

    public ClienteDBNGTest() {
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
     * Test of ValidarTelefonoCliente method, of class ClienteDB.
     */
    @Test
    public void testValidarTelefonoCliente() {
        System.out.println("ValidarTelefonoCliente");
        String telefono = "98765432";
        if (telefono.length() == 8
                && telefono.startsWith("3")
                || telefono.startsWith("9")
                || telefono.startsWith("2")) {
            ClienteDB instance = new ClienteDB();
            boolean expResult = true;
            boolean result = instance.ValidarTelefonoCliente(telefono);

            assertEquals(result, expResult);
            // TODO review the generated test code and remove the default call to fail.
            if (result != expResult) {
                fail("The test case is a prototype.");
            }

        } else {
            fail("The test case is a prototype.");
        }

    }

//
////    /**
////     * Test of ValidarUpdateTelefonoCliente method, of class ClienteDB.
////     */
    @Test
    public void testValidarUpdateTelefonoCliente() {
        System.out.println("ValidarUpdateTelefonoCliente");
        String telefono = "98765432";
        int ID = 23;
        if (telefono.length() == 8
                && telefono.startsWith("3")
                || telefono.startsWith("9")
                || telefono.startsWith("2") && ID > 0) {
            ClienteDB instance = new ClienteDB();
            int expResult = 1;
            int result = instance.ValidarUpdateTelefonoCliente(telefono, ID);
            assertEquals(result, expResult);
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        } else {
            fail("The test case is a prototype.");
        }

        // TODO review the generated test code and remove the default call to fail.
    }

//    /**
//     * Test of registrarClientes method, of class ClienteDB.
//     */
    @Test
    public void testRegistrarClientes() {
        System.out.println("registrarClientes");
        Cliente cl = new Cliente();
        //
        //cl.setIdCliente(99);
        cl.setNombre("Test Unitario ");
        cl.setDireccion("TestUnitario de prueba");
        cl.setCorreo("test@gmail.com");
        cl.setEstado(1);
        cl.setIdTelefonoCli(9999999);

        //
        if (cl.getNombre().length() < 10) {
            fail("The test case is a prototype.");

        } else if (cl.getDireccion().length() < 10) {
            fail("The test case is a prototype.");

        }else if (!cl.getCorreo().contains("@") && !cl.getCorreo().contains(".")) {
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

        } else if (String.valueOf(cl.getIdTelefonoCli()).length() == 8
                && String.valueOf(cl.getIdTelefonoCli()).startsWith("3")
                || String.valueOf(cl.getIdTelefonoCli()).startsWith("9")
                || String.valueOf(cl.getIdTelefonoCli()).startsWith("2")) {
        } else {
            ClienteDB instance = new ClienteDB();
            boolean expResult = true;
            boolean result = instance.registrarClientes(cl);
            assertEquals(result, expResult);
            System.out.println("regicliente");

            if (expResult != result) {
                fail("The test case is a prototype.");
            }
            // TODO review the generated test code and remove the default call to fail.

        }

    }

//    /**
//     * Test of UpdateClientes method, of class ClienteDB.
//     */
    @Test
    public void testUpdateClientes() {
        System.out.println("UpdateClientes");
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

        } else if (String.valueOf(cl.getIdTelefonoCli()).length() == 8
                && String.valueOf(cl.getIdTelefonoCli()).startsWith("3")
                || String.valueOf(cl.getIdTelefonoCli()).startsWith("9")
                || String.valueOf(cl.getIdTelefonoCli()).startsWith("2")) {
        } else {
            ClienteDB instance = new ClienteDB();
            boolean expResult = true;
            boolean result = instance.UpdateClientes(cl);
            assertEquals(result, expResult);
            // TODO review the generated test code and remove the default call to fail.
            System.out.println(result);
            System.out.println(expResult);
            if (expResult != result) {
                fail("The test case is a prototype.");
            }
        }
    }

//    /**
//     * Test of listarClientesBusqueda method, of class ClienteDB.
//     */
    @Test
    public void testListarClientesBusqueda() {
        System.out.println("listarClientesBusqueda");
        String filtro = "Nombre";
        String dato = "Test Unitario";
        ClienteDB instance = new ClienteDB();
        List expResult = null;
        List result = instance.listarClientesBusqueda(filtro, dato);
//        assertEquals(result, expResult);
        if (result == null) {
            fail("The test case is a prototype.");
        }
        // TODO review the generated test code and remove the default call to fail.

    }

//    /**
//     * Test of listarClientes method, of class ClienteDB.
//     */
    @Test
    public void testListarClientes() {
        System.out.println("listarClientes");
        ClienteDB instance = new ClienteDB();
        List expResult = null;
        List result = instance.listarClientes();
        // TODO review the generated test code and remove the default call to fail.
        if (result == null) {
            fail("The test case is a prototype.");
        }
    }

    /**
     * Test of listarProveedorBusqueda method, of class ClienteDB.
     */
    @Test
    public void testListarProveedorBusqueda() {
        System.out.println("listarProveedorBusqueda");
        String filtro = "Nombre";
        String dato = "La Sula";
        ClienteDB instance = new ClienteDB();
        List result = instance.listarProveedorBusqueda(filtro, dato);
        // TODO review the generated test code and remove the default call to fail.
        if (result == null) {
            fail("The test case is a prototype.");
        }

    }

//    /**
//     * Test of UpdateDatosNegocio method, of class ClienteDB.
//     */
    @Test
    public void testUpdateDatosNegocio() {
        System.out.println("UpdateDatosNegocio");
        String nombre = "TestUnitario";
        String direc = "TestUnitario";
        String tel = "TestUnitario";
        String rtn = "TestUnitario";
        String cai = "TestUnitario";
        ///
        if (nombre.length() < 10) {
            fail("The test case is a prototype.");

        } else if (direc.length() < 10) {
            fail("The test case is a prototype.");

        } else if (tel.length() == 8
                && tel.startsWith("3")
                || tel.startsWith("9")
                || tel.startsWith("2")) {
        }else if (rtn.length()<14 || cai.length() <14){
            fail("The test case is a prototype.");
        } 
        else {
            ClienteDB instance = new ClienteDB();
            boolean expResult = true;
            boolean result = instance.UpdateDatosNegocio(nombre, direc, tel, rtn, cai);
            assertEquals(result, expResult);
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        }

        // TODO review the generated test code and remove the default call to fail.
    }
//    
}

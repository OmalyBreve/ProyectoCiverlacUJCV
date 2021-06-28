/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civerlac;

import java.text.DecimalFormat;
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
public class VentasDBNGTest {

    public VentasDBNGTest() {
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
     * Test of RegistrarVenta method, of class VentasDB.
     */
    DecimalFormat formatoPrecio = new DecimalFormat("0000.00");

    @Test
    public void testRegistrarVenta() {
        System.out.println("RegistrarVenta");
        Ventas cl = null;
        cl.setIdEmpleado(4);
        cl.setIdCliente(1);
        double total = Double.parseDouble(formatoPrecio.format("13900"));
        cl.setTotal(total);
        cl.setCambio(0);
        int formaPago = 1;
        String tipoPago = "Tarjeta";

        if (Integer.parseInt(cl.getEmpleado()) <= 0) {
            fail("The test case is a prototype.");
        } else if (cl.getIdCliente() <= 0) {
            fail("The test case is a prototype.");
        } else if (total <= 0) {
            fail("The test case is a prototype.");
        } else if (formaPago <= 0 || formaPago > 3) {
            fail("The test case is a prototype.");
        } else {
            VentasDB instance = new VentasDB();
            boolean expResult = true;
            boolean result = instance.RegistrarVenta(cl, formaPago, tipoPago);
            assertEquals(result, expResult);
            // TODO review the generated test code and remove the default call to fail.
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of ValidarStock method, of class VentasDB.
     */
    @Test
    public void testValidarStock() {
        System.out.println("ValidarStock");
        String id = "19";
        VentasDB instance = new VentasDB();
        int cantidad = 20;
        if (cantidad <= 0) {
        } else {
            int result = instance.ValidarStock(id);
            if (result < cantidad) {

                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of reducirStock method, of class VentasDB.
     */
    @Test
    public void testReducirStock() {
        System.out.println("reducirStock");
        String id = "19";
        int cantidad = 0;
        if (cantidad <= 0) {
        } else {
            try {
                VentasDB instance = new VentasDB();
                instance.reducirStock(id, cantidad);
            } catch (Exception e) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of plusStock method, of class VentasDB.
     */
    @Test
    public void testPlusStock() {
        System.out.println("plusStock");
        String id = "19";
        int cantidad = 20;
        if (cantidad <= 0) {
        } else {
            try {
                VentasDB instance = new VentasDB();
                instance.plusStock(id, cantidad);
            } catch (Exception e) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of RegistrarFactura method, of class VentasDB.
     */
    @Test
    public void testRegistrarFactura() {
        System.out.println("RegistrarFactura");
        String id = "125";
        String idPro = "19";
        String cant = "2";
        String total = "100";
        String fecha = "2021/06/15";
        if (Integer.parseInt(id) <= 0) {
            fail("The test case is a prototype.");
        } else if (Integer.parseInt(idPro) <= 0) {
            fail("The test case is a prototype.");
        } else if (Integer.parseInt(cant) <= 0) {
            fail("The test case is a prototype.");
        } else if (Integer.parseInt(total) < -0) {
            fail("The test case is a prototype.");
        } else {
            VentasDB instance = new VentasDB();
            boolean expResult = true;
            boolean result = instance.RegistrarFactura(id, idPro, cant, total, fecha);

            if (result != expResult) {
                fail("The test case is a prototype.");
            }

        }

    }

    /**
     * Test of ListarHistorialVenta method, of class VentasDB.
     */
    @Test
    public void testListarHistorialVenta() {
        System.out.println("ListarHistorialVenta");

        List expResult = null;

        try {
            VentasDB instance = new VentasDB();
            List result = instance.ListarHistorialVenta();
            if (result == null) {
                fail("The test case is a prototype.");
            }
        } catch (Exception e) {
        }
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ListarHistorialVentaCustom method, of class VentasDB.
     */
    @Test
    public void testListarHistorialVentaCustom() {
        System.out.println("ListarHistorialVentaCustom");
        String id = "95";
        VentasDB instance = new VentasDB();
        List expResult = null;
        List result = instance.ListarHistorialVentaCustom(id);
        if (result == null) {
            fail("The test case is a prototype.");
        }
        // TODO review the generated test code and remove the default call to fail.

    }

    /**
     * Test of UltimaFactura method, of class VentasDB.
     */
    @Test
    public void testUltimaFactura() {
        System.out.println("UltimaFactura");
        VentasDB instance = new VentasDB();
        String expResult = "";
        String result = instance.UltimaFactura();
        if (result == null || result == "") {
            fail("The test case is a prototype.");
        }

    }

    /**
     * Test of ListarHistorialVentaFactura method, of class VentasDB.
     */
    @Test
    public void testListarHistorialVentaFactura() {
        System.out.println("ListarHistorialVentaFactura");
        VentasDB instance = new VentasDB();
        List expResult = null;
        List result = instance.ListarHistorialVentaFactura();
        if (result == null) {
            fail("The test case is a prototype.");
        }

    }

    /**
     * Test of ListarHistorialVentaFacturaFecha method, of class VentasDB.
     */
    @Test
    public void testListarHistorialVentaFacturaFecha() {
        System.out.println("ListarHistorialVentaFacturaFecha");
        String fecha1 = "";
        String fecha2 = "";
        VentasDB instance = new VentasDB();
        List expResult = null;
        List result = instance.ListarHistorialVentaFacturaFecha(fecha1, fecha2);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listarVentaBusqueda method, of class VentasDB.
     */
    @Test
    public void testListarVentaBusqueda() {
        System.out.println("listarVentaBusqueda");
        String filtro = "Vendedor";
        String dato = "Omaly";
        if (filtro == null) {
        } else if (dato == null) {

        } else {
            VentasDB instance = new VentasDB();
            List expResult = null;
            List result = instance.listarVentaBusqueda(filtro, dato);
            if (result == null) {
                fail("The test case is a prototype.");
            }
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civerlac;

import java.text.DecimalFormat;
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
public class ComprasDBNGTest {

    public ComprasDBNGTest() {
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
     * Test of AumetarStock method, of class ComprasDB.
     */
    DecimalFormat formatoPrecio = new DecimalFormat("0000.00");

    @Test
    public void testAumetarStock() {
        System.out.println("AumetarStock");
        String id = "22";
        int cantidad = 0;
        try {
            cantidad = 10;
            if (cantidad <= 0) {
                fail("The test case is a prototype.");
            } else {
                ComprasDB instance = new ComprasDB();
                instance.AumetarStock(id, cantidad);
            }

        } catch (Exception e) {
            fail("The test case is a prototype.");
        }

    }

    /**
     * Test of RegistrarCompra method, of class ComprasDB.
     */
    @Test
    public void testRegistrarCompra() {
        System.out.println("RegistrarCompra");
        int vendedor = 4;
        int proveedor = 1;
        int formaPago = 1;
        double total = Double.parseDouble(formatoPrecio.format("30"));
        if (vendedor <= 0 || proveedor <= 0) {
            fail("The test case is a prototype.");
        } else if (formaPago <= 0) {
            fail("The test case is a prototype.");
        } else if (formaPago > 3) {
            fail("The test case is a prototype.");
        } else if (total <= 0) {
            fail("The test case is a prototype.");
        } else {
            ComprasDB instance = new ComprasDB();
            boolean expResult = true;
            boolean result = instance.RegistrarCompra(vendedor, proveedor, formaPago, total);
            assertEquals(result, expResult);
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of reducirStock method, of class ComprasDB.
     */
    @Test
    public void testReducirStock() {
        System.out.println("reducirStock");
        String id = "22";
        int cantidad = 2;
        if (Integer.parseInt(id) <= 0 || cantidad <= 0) {
            fail("The test case is a prototype.");
        } else {
            try {
                ComprasDB instance = new ComprasDB();
                instance.reducirStock(id, cantidad);
            } catch (Exception ex) {
                fail("The test case is a prototype.");
            }
        }

    }

}

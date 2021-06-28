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
public class ProductoDBNGTest {

    public ProductoDBNGTest() {
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
     * Test of registrarProducto method, of class ProductoDB.
     */
    DecimalFormat formatoPrecio = new DecimalFormat("#.##");

    @Test
    public void testRegistrarProducto() {
        System.out.println("registrarProducto");
        producto prod = new producto();
        //
        prod.setNombre("TestUnitario");
        try {
            double precio = Double.parseDouble(formatoPrecio.format(30)); 
            prod.setPreciol(precio);
            prod.setStock(20);
            //
            if (prod.getNombre().length() < 10) {
                fail("The test case is a prototype.");
            } else if (prod.getPreciol() < 0) {
                fail("The test case is a prototype.");
            } else if (prod.getStock() < 10) {
                fail("The test case is a prototype.");
            } else {
                ProductoDB instance = new ProductoDB();
                boolean expResult = true;
                boolean result = instance.registrarProducto(prod);
                assertEquals(result, expResult);
                // TODO review the generated test code and remove the default call to fail.
                if (result != expResult) {
                    fail("The test case is a prototype.");
                }
            }
        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
        //

    }

    /**
     * Test of registrarPrecioHistorico method, of class ProductoDB.
     */
    @Test
    public void testRegistrarPrecioHistorico() {
        System.out.println("registrarPrecioHistorico");

        String fecha = "2021/06/20";
        try {
            int id = 19;
            double precio = 40;
            ProductoDB instance = new ProductoDB();
            
        } catch (Exception e) {
            fail("The test case is a prototype.");
        }

    }

    /**
     * Test of VerificarPrecioHistorico method, of class ProductoDB.
     */
    @Test
    public void testVerificarPrecioHistorico() {
        System.out.println("VerificarPrecioHistorico");
        String fecha = "20/06/2021";
        int id = 100;
        double precio = Double.parseDouble(formatoPrecio.format(30));// recorda esto
        if (precio <= 0) {
            fail("The test case is a prototype.");
        } else if (id <= 0) {
            fail("The test case is a prototype.");
        } else {
            ProductoDB instance = new ProductoDB();
            boolean expResult = false;
            boolean result = instance.VerificarPrecioHistorico(fecha, id, precio);
            assertEquals(result, expResult);
            if (result != expResult) {
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of UpdatePrecioHistorico method, of class ProductoDB.
     */
    @Test
    public void testUpdatePrecioHistorico() {
        System.out.println("UpdatePrecioHistorico");
        String fecha = "2021/06/20";
        int id = 19;
        double precio = Double.parseDouble(formatoPrecio.format(30));
        if (precio <= 0 || id <= 0) {
            fail("The test case is a prototype.");
        } else {
            ProductoDB instance = new ProductoDB();
            instance.UpdatePrecioHistorico(fecha, id, precio);
            // TODO review the generated test code and remove the default call to fail.
        }
    }

    /**
     * Test of UpdateProducto method, of class ProductoDB.
     */
    @Test
    public void testUpdateProducto() {
        System.out.println("UpdateProducto");
        producto prod = new producto();
        prod.setId(22);
        prod.setNombre("testUpdate");
        double precio = Double.parseDouble(formatoPrecio.format(30));
        prod.setPreciol(precio);
        prod.setStock(100);
        if (prod.getId() <= 0
                || prod.getPreciol() <= 0
                || prod.getStock() <= 0) {
            fail("The test case is a prototype.");

        } else {
            ProductoDB instance = new ProductoDB();
            boolean expResult = true;
            boolean result = instance.UpdateProducto(prod);
            assertEquals(result, expResult);
            if (result != expResult) {
                // TODO review the generated test code and remove the default call to fail.
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of DeleteProducto method, of class ProductoDB.
     */
    @Test
    public void testDeleteProducto() {
        System.out.println("DeleteProducto");
        producto prod = new producto();
        prod.setId(23);
        if (prod.getId() <= 0) {
            fail("The test case is a prototype.");
        } else {
            ProductoDB instance = new ProductoDB();
            boolean expResult = true;
            boolean result = instance.DeleteProducto(prod);
            assertEquals(result, expResult);
            if (result != expResult) {
                // TODO review the generated test code and remove the default call to fail.
                fail("The test case is a prototype.");
            }
        }

    }

    /**
     * Test of listarProductos method, of class ProductoDB.
     */
    @Test
    public void testListarProductos() {
        System.out.println("listarProductos");
        ProductoDB instance = new ProductoDB();
        List expResult = null;
        List result = instance.listarProductos();
        if (result == null) {
            fail("The test case is a prototype.");
        }
    }

    /**
     * Test of listarPrecioHistorico method, of class ProductoDB.
     */
    @Test
    public void testListarPrecioHistorico() {
        System.out.println("listarPrecioHistorico");
        int id = 22;
        if (id <= 0) {
            fail("The test case is a prototype.");
        } else {
            ProductoDB instance = new ProductoDB();
            List expResult = null;
            List result = instance.listarPrecioHistorico(id);
            if (result == null) {
                fail("The test case is a prototype.");
            };
        }

    }

    /**
     * Test of listarPrecioHistorico2 method, of class ProductoDB.
     */
    @Test
    public void testListarPrecioHistorico2() {
        System.out.println("listarPrecioHistorico2");
        String date = "2021/06/20";
        int id = 22;
        ProductoDB instance = new ProductoDB();
        List expResult = null;
        List result = instance.listarPrecioHistorico2(date, id);
        if (result == null) {
            fail("The test case is a prototype.");
        }
    }

}

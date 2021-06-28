/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Layouts;

import Cliente.datosProductoCompra;
import java.util.List;
import javax.swing.JTable;
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
public class SistemaNGTest {
    
    public SistemaNGTest() {
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
     * Test of BloquearAcceso method, of class Sistema.
     */
    @Test
    public void testBloquearAcceso() {
        System.out.println("BloquearAcceso");
        Sistema instance = new Sistema();
        instance.BloquearAcceso();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getListaProd method, of class Sistema.
     */
    @Test
    public void testGetListaProd() {
        System.out.println("getListaProd");
        List expResult = null;
        List result = Sistema.getListaProd();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setListaProd method, of class Sistema.
     */
    @Test
    public void testSetListaProd() {
        System.out.println("setListaProd");
        List<datosProductoCompra> listaProd = null;
        Sistema.setListaProd(listaProd);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listaClientes method, of class Sistema.
     */
    @Test
    public void testListaClientes() {
        System.out.println("listaClientes");
        Sistema instance = new Sistema();
        instance.listaClientes();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listaProveedores method, of class Sistema.
     */
    @Test
    public void testListaProveedores() {
        System.out.println("listaProveedores");
        Sistema instance = new Sistema();
        instance.listaProveedores();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listaProductos method, of class Sistema.
     */
    @Test
    public void testListaProductos() {
        System.out.println("listaProductos");
        Sistema instance = new Sistema();
        instance.listaProductos();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listaProductoCustom method, of class Sistema.
     */
    @Test
    public void testListaProductoCustom() {
        System.out.println("listaProductoCustom");
        Sistema instance = new Sistema();
        instance.listaProductoCustom();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listarPrecios method, of class Sistema.
     */
    @Test
    public void testListarPrecios() {
        System.out.println("listarPrecios");
        int id = 0;
        Sistema instance = new Sistema();
        instance.listarPrecios(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listarPreciosMovimiento method, of class Sistema.
     */
    @Test
    public void testListarPreciosMovimiento() {
        System.out.println("listarPreciosMovimiento");
        String date = "";
        int id = 0;
        Sistema instance = new Sistema();
        instance.listarPreciosMovimiento(date, id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listaEmpleados method, of class Sistema.
     */
    @Test
    public void testListaEmpleados() {
        System.out.println("listaEmpleados");
        Sistema instance = new Sistema();
        instance.listaEmpleados();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listaHistorialVenta method, of class Sistema.
     */
    @Test
    public void testListaHistorialVenta() {
        System.out.println("listaHistorialVenta");
        Sistema instance = new Sistema();
        instance.listaHistorialVenta();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listaHistorialVentaCustom method, of class Sistema.
     */
    @Test
    public void testListaHistorialVentaCustom() {
        System.out.println("listaHistorialVentaCustom");
        String filtro = "";
        Sistema instance = new Sistema();
        instance.listaHistorialVentaCustom(filtro);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listaHistorialVentaFacutra method, of class Sistema.
     */
    @Test
    public void testListaHistorialVentaFacutra() {
        System.out.println("listaHistorialVentaFacutra");
        Sistema instance = new Sistema();
        instance.listaHistorialVentaFacutra();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listaHistorialVentaFacutrafecha method, of class Sistema.
     */
    @Test
    public void testListaHistorialVentaFacutrafecha() {
        System.out.println("listaHistorialVentaFacutrafecha");
        Sistema instance = new Sistema();
        instance.listaHistorialVentaFacutrafecha();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarTabla method, of class Sistema.
     */
    @Test
    public void testLimpiarTabla() {
        System.out.println("limpiarTabla");
        Sistema instance = new Sistema();
        instance.limpiarTabla();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarTablaEmpleado method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaEmpleado() {
        System.out.println("limpiarTablaEmpleado");
        Sistema instance = new Sistema();
        instance.limpiarTablaEmpleado();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarTablaProveedor method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaProveedor() {
        System.out.println("limpiarTablaProveedor");
        Sistema instance = new Sistema();
        instance.limpiarTablaProveedor();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarTablaProducto method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaProducto() {
        System.out.println("limpiarTablaProducto");
        Sistema instance = new Sistema();
        instance.limpiarTablaProducto();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarTablaHistorialV method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaHistorialV() {
        System.out.println("limpiarTablaHistorialV");
        Sistema instance = new Sistema();
        instance.limpiarTablaHistorialV();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarTablaVentas method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaVentas() {
        System.out.println("limpiarTablaVentas");
        Sistema instance = new Sistema();
        instance.limpiarTablaVentas();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarTablaVentasFacturas method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaVentasFacturas() {
        System.out.println("limpiarTablaVentasFacturas");
        Sistema instance = new Sistema();
        instance.limpiarTablaVentasFacturas();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LimpiarTablaPrecioHistorico method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaPrecioHistorico() {
        System.out.println("LimpiarTablaPrecioHistorico");
        Sistema instance = new Sistema();
        instance.LimpiarTablaPrecioHistorico();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LimpiarTablaPrecioHistoricoMov method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaPrecioHistoricoMov() {
        System.out.println("LimpiarTablaPrecioHistoricoMov");
        Sistema instance = new Sistema();
        instance.LimpiarTablaPrecioHistoricoMov();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LimpiarTablaProductoCustom method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaProductoCustom() {
        System.out.println("LimpiarTablaProductoCustom");
        Sistema instance = new Sistema();
        instance.LimpiarTablaProductoCustom();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LimpiarTablaCompra method, of class Sistema.
     */
    @Test
    public void testLimpiarTablaCompra() {
        System.out.println("LimpiarTablaCompra");
        Sistema instance = new Sistema();
        instance.LimpiarTablaCompra();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LimpiarTextfield method, of class Sistema.
     */
    @Test
    public void testLimpiarTextfield() {
        System.out.println("LimpiarTextfield");
        Sistema instance = new Sistema();
        instance.LimpiarTextfield();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ProvCancel method, of class Sistema.
     */
    @Test
    public void testProvCancel() {
        System.out.println("ProvCancel");
        Sistema instance = new Sistema();
        instance.ProvCancel();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LimpiarProveedor method, of class Sistema.
     */
    @Test
    public void testLimpiarProveedor() {
        System.out.println("LimpiarProveedor");
        Sistema instance = new Sistema();
        instance.LimpiarProveedor();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarVenta method, of class Sistema.
     */
    @Test
    public void testLimpiarVenta() {
        System.out.println("limpiarVenta");
        Sistema instance = new Sistema();
        instance.limpiarVenta();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of limpiarCompra method, of class Sistema.
     */
    @Test
    public void testLimpiarCompra() {
        System.out.println("limpiarCompra");
        Sistema instance = new Sistema();
        instance.limpiarCompra();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ClearVentaForm method, of class Sistema.
     */
    @Test
    public void testClearVentaForm() {
        System.out.println("ClearVentaForm");
        Sistema instance = new Sistema();
        instance.ClearVentaForm();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ClearFormEmpleado method, of class Sistema.
     */
    @Test
    public void testClearFormEmpleado() {
        System.out.println("ClearFormEmpleado");
        Sistema instance = new Sistema();
        instance.ClearFormEmpleado();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ClearFormProveedor method, of class Sistema.
     */
    @Test
    public void testClearFormProveedor() {
        System.out.println("ClearFormProveedor");
        Sistema instance = new Sistema();
        instance.ClearFormProveedor();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ClearClienteForm method, of class Sistema.
     */
    @Test
    public void testClearClienteForm() {
        System.out.println("ClearClienteForm");
        Sistema instance = new Sistema();
        instance.ClearClienteForm();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ActiveProducto method, of class Sistema.
     */
    @Test
    public void testActiveProducto() {
        System.out.println("ActiveProducto");
        Sistema instance = new Sistema();
        instance.ActiveProducto();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inActivProducto method, of class Sistema.
     */
    @Test
    public void testInActivProducto() {
        System.out.println("inActivProducto");
        Sistema instance = new Sistema();
        instance.inActivProducto();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LimpiarProducto method, of class Sistema.
     */
    @Test
    public void testLimpiarProducto() {
        
       
        System.out.println("LimpiarProducto");
        Sistema instance = new Sistema();
        instance.LimpiarProducto();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CancelProcess method, of class Sistema.
     */
    @Test
    public void testCancelProcess() {
        System.out.println("CancelProcess");
        Sistema instance = new Sistema();
        instance.CancelProcess();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Sistema.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Sistema.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrivilegioUser method, of class Sistema.
     */
    @Test
    public void testGetPrivilegioUser() {
        System.out.println("getPrivilegioUser");
        Sistema instance = new Sistema();
        int expResult = 0;
        int result = instance.getPrivilegioUser();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrivilegioUser method, of class Sistema.
     */
    @Test
    public void testSetPrivilegioUser() {
        System.out.println("setPrivilegioUser");
        int PrivilegioUser = 0;
        Sistema instance = new Sistema();
        instance.setPrivilegioUser(PrivilegioUser);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTbNuevaVenta method, of class Sistema.
     */
    @Test
    public void testGetTbNuevaVenta() {
        System.out.println("getTbNuevaVenta");
        Sistema instance = new Sistema();
        JTable expResult = null;
        JTable result = instance.getTbNuevaVenta();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTbNuevaVenta method, of class Sistema.
     */
    @Test
    public void testSetTbNuevaVenta() {
        System.out.println("setTbNuevaVenta");
        JTable tbNuevaVenta = null;
        Sistema instance = new Sistema();
        instance.setTbNuevaVenta(tbNuevaVenta);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

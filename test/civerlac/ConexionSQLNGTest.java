/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civerlac;

import java.sql.Connection;
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
public class ConexionSQLNGTest {
    
    public ConexionSQLNGTest() {
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
     * Test of getConnection method, of class ConexionSQL.
     */
    Connection con;
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        //
        String Coneccion = "com.mysql.cj.jdbc.ConnectionImpl@28dcca0c";
        //
        ConexionSQL instance = new ConexionSQL();
      
        Connection result = instance.getConnection();
        System.out.println(result);
        System.out.println(Coneccion);
        if(!Coneccion.equals(result.toString())){
        fail("The test case is a prototype.");
        }
        else{
            System.out.println(Coneccion+""+result);
        }
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civerlac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Omaly Breve
 */
public class VentaDB {

    ConexionSQL cn = new ConexionSQL();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ErrorLogs el = new ErrorLogs();

    public boolean registrarClientes(Ventas cl) {

        String sql = "INSERT INTO `factura`( `Cliente`, `Vendedor`, `total`) VALUES (?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, cl.getCliente());
            ps.setString(2, cl.getEmpleado());
            ps.setDouble(3, cl.getTotal());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro exitoso");
            el.LogBitacora("Registrar clientes tuvo exito");
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            el.LogBitacora("Error al registrr clientes");
            return false;
        }

    }

}

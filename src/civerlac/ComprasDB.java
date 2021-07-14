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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Omaly Breve
 */
public class ComprasDB {

    ConexionSQL cn = new ConexionSQL();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ErrorLogs el = new ErrorLogs();

    public void AumetarStock(String id, int cantidad) {
        String sql = "update producto set stock =(stock+?) where idProducto=?";
        int cantidadStock = 0;
       

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setString(2, id);
            ps.execute();
            el.LogBitacora("Aumentar stock tuvo exito");

        } catch (SQLException e) {
            System.out.println(e.toString());
            el.LogBitacora("Error al aumentar stock" + e);
        }

    }

    
    public boolean RegistrarCompra(int vendedor, int proveedor, int formaPago, double total) {

        String sql = "INSERT INTO `facturacompra`( `Fecha`, `FkVendedor`,`FkProveedor`, `Formapago`,`Total`) VALUES (?,?,?,?,?)";

        java.util.Date fecha = new java.util.Date();
        DateFormat f = new SimpleDateFormat("yyy-MM-dd");
        String fechaActual = f.format(fecha);
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, fechaActual);
            ps.setInt(2, vendedor);
            ps.setInt(3, proveedor);
            ps.setInt(4, formaPago);
            ps.setDouble(5, total);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro de compra exitoso");
            el.LogBitacora("Resgistrar compra tuvo exito");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No venta" + e.toString());
            el.LogBitacora("Error al registrar la venta"+ e);
            return false;
        }

    }

    public void reducirStock(String id, int cantidad) {
        String sql = "update producto set stock =(stock-?) where idProducto=?";
        int cantidadStock = 0;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setString(2, id);
            ps.execute();
            el.LogBitacora("Reducir stock tuvo exito");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            el.LogBitacora("Error al reducir stock" + e);
        }

    }
     
    public boolean RegistrarCompra(Ventas cl, int formaPago,String tipoPago) {

        String sql = "INSERT INTO `Compras`( `Proveedor`, `Vendedor`, `total`,`formapago`,`cambio`) VALUES (?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, cl.getIdCliente());
            ps.setInt(2, cl.getIdEmpleado());
            ps.setDouble(3, cl.getTotal());
            ps.setInt(4, formaPago);
            ps.setDouble(5, cl.getCambio());
            ps.execute();
            if("Mixto".equals(tipoPago)){
            }
            else{
                JOptionPane.showMessageDialog(null, "Registro de venta exitoso");
            }
            el.LogBitacora("Registrar compra tuvo exito");
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No venta" + e.toString());
            el.LogBitacora("Error al registrar venta" + e);
            return false;
        }
        

    }
    
    public boolean RegistrarDetalleCompra(String id, String idPro, String cant, String total, String fecha) {

        String sql = "INSERT INTO `detalleCompra`( `idFactura`,`idProducto`,`cant`,`total`) VALUES (?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, id);
            //ps.setString(2, fecha);
            ps.setString(2, idPro);
            ps.setString(3, cant);
            ps.setString(4, total);
            ps.execute();
            el.LogBitacora("Registrar detalle compra tuvo exito");
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se registra el producto" + e.toString());
            el.LogBitacora("Error al registrar detalle compra" + e);
            return false;
        }

    }
}

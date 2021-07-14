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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Roger Sifontes
 */
public class VentasDB {

    ConexionSQL cn = new ConexionSQL();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ErrorLogs el = new ErrorLogs();

   public boolean RegistrarVenta(Ventas cl, int formaPago,String tipoPago) {

        String sql = "INSERT INTO `factura`( `Cliente`, `Vendedor`, `total`,`formapago`,`cambio`) VALUES (?,?,?,?,?)";

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
            el.LogBitacora("Se registro una nueva venta");
            
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No venta" + e.toString());
            el.LogBitacora("La venta no tuvo exito" + e);
            return false;
            
        }

    }

    public int ValidarStock(String id) {
        String sql = "SELECT * FROM `producto` where idProducto=?";
        int cantidadStock = 0;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {

                cantidadStock = ((rs.getInt("stock")));
                
            }
            el.LogBitacora("La validacion de stock funciono correctamente");
        } catch (SQLException e) {
            System.out.println(e.toString());
            el.LogBitacora("Error al validar el stock" + e);
        }
        
        return cantidadStock;
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

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    public void plusStock(String id, int cantidad) {
        String sql = "update producto set stock =(stock+?) where idProducto=?";
        int cantidadStock = 0;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ps.setString(2, id);
            ps.execute();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }

    public boolean RegistrarFactura(String id, String idPro, String cant, String total, String fecha) {

        String sql = "INSERT INTO `facturaproducto`( `idFactura`,`fechaEmision`,`idProducto`,`cant`,`total`) VALUES (?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, fecha);
            ps.setString(3, idPro);
            ps.setString(4, cant);
            ps.setString(5, total);
            ps.execute();
            el.LogBitacora("Se registro una nueva factura");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se registra el producto" + e.toString());
            el.LogBitacora("Error al registrar la factura" + e);
            return false;
        }

    }

    public void InsertarFacturaProducto(String idFac, String idProd) {
        String sql = "INSERT INTO `facturaproducto`( `idFactura`, `idProcuto`) VALUES (?,?)";

    }

    public List ListarHistorialVenta() {

        List<Ventas> listaProd = new ArrayList();
        String sql = "SELECT a.idFactura,b.Nombre as Cliente,c.Nombre as Vendedor,a.total,d.descripcion as formaPago  FROM `factura` as a \n"
                + "join cliente as b on a.Cliente=b.idCliente \n"
                + "join empleado as c on a.Vendedor=c.idEmpleado\n"
                + "join formapago as d on a.formapago=d.idFormaPago;";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ventas cl = new Ventas();

                cl.setIdFactura((rs.getInt("idFactura")));
                cl.setCliente((rs.getString("Cliente")));
                cl.setEmpleado((rs.getString("Vendedor")));
                cl.setTotal((rs.getDouble("total")));
                listaProd.add(cl);
                
            }
            el.LogBitacora("Se listo con exito el historial de venta");
        } catch (SQLException e) {
            System.out.println(e.toString());
            el.LogBitacora("Error al listar el historial de venta" + e);
        }
        
        return listaProd;
    }

    public List ListarHistorialVentaCustom(String id) {

        List<Ventas> listaProd = new ArrayList();
        String sql = "SELECT a.idFactura,b.Nombre as Cliente,c.Nombre as Vendedor,a.total,d.descripcion as formaPago  FROM `factura` as a \n"
                + "join cliente as b on a.Cliente=b.idCliente \n"
                + "join empleado as c on a.Vendedor=c.idEmpleado\n"
                + "join formapago as d on a.formapago=d.idFormaPago where a.idFactura= ? ;";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Ventas cl = new Ventas();

                cl.setIdFactura((rs.getInt("idFactura")));
                cl.setCliente((rs.getString("Cliente")));
                cl.setEmpleado((rs.getString("Vendedor")));
                cl.setTotal((rs.getDouble("total")));
                listaProd.add(cl);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaProd;
    }

    //
    public String UltimaFactura() {
        String id = "";
        String sql = "SELECT idFactura FROM factura ORDER BY idFactura DESC LIMIT 1";
        System.out.println("Ejecutando ultima factura");
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {

                id = rs.getString("idFactura");
                System.out.println("ultimafactura es" + id);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            id = null;
        }
        return id;
        ///

    }

    ///
    public List ListarHistorialVentaFactura() {

        List<VentasFacturacionHistorial> listaProd = new ArrayList();
        String sql = "select DISTINCT a.idFactura,b.fechaEmision,c.Nombre as Vendedor,e.Nombre as Cliente from factura as a join facturaproducto as b on a.idFactura=b.idFactura join empleado as c on a.Vendedor=c.idEmpleado join cliente as e on a.Cliente=e.idCliente";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                VentasFacturacionHistorial cl = new VentasFacturacionHistorial();

                cl.setIdFactura((rs.getInt("idFactura")));

                cl.setCliente(rs.getString("Cliente"));
                cl.setEmpleado((rs.getString("Vendedor")));
                cl.setFecha(rs.getString("fechaEmision"));

                listaProd.add(cl);

            }
            el.LogBitacora("Se listo el historial de factura con exito");
        } catch (SQLException e) {
            System.out.println(e.toString());
            el.LogBitacora("Error al listar historial de venta de factura" + e);
        }
        return listaProd;
    }
    //

    public List ListarHistorialVentaFacturaFecha(String fecha1, String fecha2) {

        List<VentasFacturacionHistorial> listaProd = new ArrayList();
        String sql = "select DISTINCT a.idFactura,b.fechaEmision,a.vendedor,a.Cliente from factura as a join facturaproducto as b on a.idFactura=b.idFactura WHERE B.fechaEmision BETWEEN ? AND ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha1);
            ps.setString(2, fecha2);
            rs = ps.executeQuery();
            while (rs.next()) {
                VentasFacturacionHistorial cl = new VentasFacturacionHistorial();

                cl.setIdFactura((rs.getInt("idFactura")));

                cl.setCliente(rs.getString("Cliente"));
                cl.setEmpleado((rs.getString("Vendedor")));
                cl.setFecha(rs.getString("fechaEmision"));

                listaProd.add(cl);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaProd;
    }
    
     public List listarVentaBusqueda(String filtro, String dato) {

        String sql = null;
        if ("idFactura".equals(filtro)) {
              sql = "SELECT a.idFactura,b.Nombre as Cliente,c.Nombre as Vendedor,a.total,d.descripcion as formaPago  FROM `factura` as a \n"
                + "join cliente as b on a.Cliente=b.idCliente \n"
                + "join empleado as c on a.Vendedor=c.idEmpleado\n"
                + "join formapago as d on a.formapago=d.idFormaPago WHERE " + filtro + " LIKE '%" + dato + "%'";
        }
        if ("Cliente".equals(filtro)) {
              sql = "SELECT a.idFactura,b.Nombre as Cliente,c.Nombre as Vendedor,a.total,d.descripcion as formaPago  FROM `factura` as a \n"
                + "join cliente as b on a.Cliente=b.idCliente \n"
                + "join empleado as c on a.Vendedor=c.idEmpleado\n"
                + "join formapago as d on a.formapago=d.idFormaPago WHERE b.Nombre LIKE '%" + dato + "%'";
        }
        if ("Vendedor".equals(filtro)) {
             sql = "SELECT a.idFactura,b.Nombre as Cliente,c.Nombre as Vendedor,a.total,d.descripcion as formaPago  FROM `factura` as a \n"
                + "join cliente as b on a.Cliente=b.idCliente \n"
                + "join empleado as c on a.Vendedor=c.idEmpleado\n"
                + "join formapago as d on a.formapago=d.idFormaPago WHERE c.Nombre LIKE '%" + dato + "%'";
        }
        System.out.println(sql);
        List<Ventas> listaCL = new ArrayList();

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            
            rs = ps.executeQuery();
            while (rs.next()) {
                Ventas cl = new Ventas();

                cl.setIdFactura(rs.getInt("idFactura"));
                cl.setCliente(rs.getString("Cliente"));
                cl.setEmpleado(rs.getString("Vendedor"));
                    cl.setTotal(rs.getFloat("total"));
                listaCL.add(cl);

            }
            el.LogBitacora("Listar venta busqueda tuvo exito");
        } catch (SQLException e) {
            System.out.println(e.toString());
            el.LogBitacora("Error al listar venta busqueda");
        }
        return listaCL;
    }

}

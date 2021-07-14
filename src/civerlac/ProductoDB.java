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
public class ProductoDB {

    ConexionSQL cn = new ConexionSQL();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ErrorLogs el = new ErrorLogs();

    public boolean registrarProducto(producto prod) {

        String sql = "INSERT INTO `producto`( `Nombre`, `precio`,`stock`) VALUES (?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            // ps.setInt(1, cl.getIdCliente());
            ps.setString(1, prod.getNombre());
            ps.setDouble(2, prod.getPreciol());
            ps.setInt(3, prod.getStock());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro exitoso");
            el.LogBitacora("Registrar producto tuvo exito");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            el.LogBitacora("Error al registrar un producto"+ e);
            return false;
        }

    }

    public void registrarPrecioHistorico(int id, String fecha, double precio) {

        String sql = "INSERT INTO `precioHistorico`( `idProducto`, `fechaInicial`,`fechaFinal`,`precio`) VALUES (?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, fecha);
            ps.setString(3, "---");
            ps.setDouble(4, precio);
            ps.execute();
            System.out.println("precio historico insert");
            //return true;
            el.LogBitacora("Registrar precio historico tuvo exito");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            el.LogBitacora("Error al registrar precio historico"+ e);
            //return false;
        }

    }

    public boolean VerificarPrecioHistorico(String fecha, int id, double precio) {

        String sql = "SELECT fechaFinal FROM `precioHistorico` where idProducto=" + id + " order by codReg desc limit 1";
        boolean state = false;
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            System.out.println(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if ("---".equals(rs.getString("fechaFinal"))) {
                    state = true;
                } else {
                    state = false;
                }

            }
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "error insert" + e.toString());
            state = false;
            //return false;
        }
        return state;
    }

    public void UpdatePrecioHistorico(String fecha, int id, double precio) {

        String sql2 = "UPDATE `precioHistorico` SET fechaFinal =? where idProducto=? order by codReg desc limit 1";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql2);
            ps.setString(1, fecha);
            ps.setInt(2, id);
            ps.execute();
            System.out.println("precio historico update");
            //return true;
            el.LogBitacora("Update precio historico tuvo exito");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error insert" + e.toString());
            el.LogBitacora("Error al update precio historico" + e);
            //return false;
        }

    }

    public boolean UpdateProducto(producto prod) {

        String sql = "UPDATE  `producto` set  `Nombre`=?, `precio`=?, `stock`=? WHERE idProducto=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, prod.getNombre());
            ps.setDouble(2, prod.getPreciol());
            ps.setInt(3, prod.getStock());;
            ps.setInt(4, prod.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente");
            el.LogBitacora("Update producto tuvo exito");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            el.LogBitacora("Error al update producuto" + e);
            return false;
        }

    }

    public boolean DeleteProducto(producto prod) {

        String sql = "Delete FROM `producto` WHERE idProducto=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, prod.getId());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
            el.LogBitacora("Delete producto tuvo exito");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            el.LogBitacora("Errro al delete producto" + e);
            return false;
        }

    }

    public List listarProductos() {

        List<producto> listaProd = new ArrayList();
        String sql = "SELECT * FROM `producto`";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                producto cl = new producto();

                cl.setId((rs.getInt("idProducto")));
                cl.setNombre((rs.getString("Nombre")));
                cl.setPreciol((rs.getDouble("precio")));
                cl.setStock((rs.getInt("stock")));

                listaProd.add(cl);

            }
            el.LogBitacora("Listar productos tuvo exito");
        } catch (SQLException e) {
            System.out.println(e.toString());
             el.LogBitacora("Error al listar productos" + e);
        }
       
        return listaProd;
    }

    public List listarPrecioHistorico(int id) {

        List<PrecioHistorico> listaProd = new ArrayList();
        String sql = "SELECT CONCAT(fechaInicial,'>',fechaFinal) as fechaInicial FROM `precioHistorico` where idProducto=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                PrecioHistorico cl = new PrecioHistorico();

                cl.setFecha((rs.getString("fechaInicial")));
                
                listaProd.add(cl);

            }
            el.LogBitacora("Listar precio historico tuvo exito");
        } catch (SQLException e) {
            System.out.println("query1" + e.toString());
            el.LogBitacora("Error al listar precio historico" + e);
        }
        return listaProd;
    }

    public List listarPrecioHistorico2(String date, int id) {

        List<PrecioHistorico> listaProd = new ArrayList();
        String sql = "SELECT * FROM `precioHistorico` where fechaInicial LIKE '%" + date + "%' and idProducto=?  ";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                PrecioHistorico cl = new PrecioHistorico();

                cl.setPrecio((rs.getDouble("precio")));

                listaProd.add(cl);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaProd;
    }

}

package civerlac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

public class ProveedorDB {

    ConexionSQL cn = new ConexionSQL();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    public boolean ValidarTelefonoProovedor(String telefono) {

        String sql = "Select * from proveedor where TelefonoPro= ? ";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, telefono);
            rs= ps.executeQuery();
            if(rs.next()){
                System.out.println("El telefono Existe");
                return true;
            }else{
                return false;
            }
            
        } catch (SQLException e) {
           
            return false;
        }
        

    }
    public int ValidarUpdateTelefonoProveedor(String telefono, int ID) {

        String sql = "SELECT if ( idProveedor = ?, TRUE,FALSE )as Estado  FROM proveedor WHERE TelefonoPro = ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(2, telefono);
            ps.setInt(1, ID);
            rs= ps.executeQuery();
            System.out.println(rs);
            rs.next();
            String estado = rs.getString("Estado");
            System.out.println(estado);
            if( "1".equals(estado) ){
                return 1;
            }
            else{
                return 0;
            }
            
        } catch (SQLException e) {
           
            return 0;
        }
        

    }
    public boolean registrarProveedor(Cliente cl) {

        String sql = "INSERT INTO `proveedor`( `Nombre`, `Correo`, `Direccion`, `TelefonoPro`, `Estado`) VALUES (?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            // ps.setInt(1, cl.getIdCliente());
            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getCorreo());
            ps.setString(3, cl.getDireccion());
            ps.setInt(4, cl.getIdTelefonoCli());
            ps.setInt(5, cl.getEstado());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro exitoso");
            return true;
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }

    }

    public boolean UpdateProveedor(Cliente cl) {

        String sql = "UPDATE  `proveedor` set  `Nombre`=?, `Correo`=?, `Direccion`=?, `TelefonoPro`=?, `Estado`=? WHERE idProveedor=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getCorreo());
            ps.setString(3, cl.getDireccion());
            ps.setInt(4, cl.getIdTelefonoCli());
            ps.setInt(5, cl.getEstado());
            ps.setInt(6, cl.getIdCliente());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }

    }

    public List listarClientes() {

        List<Cliente> listaCL = new ArrayList();
        String sql = "SELECT * FROM `proveedor`";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cl = new Cliente();

                cl.setIdCliente(rs.getInt("idProveedor"));
                cl.setNombre(rs.getString("Nombre"));
                cl.setCorreo(rs.getString("Correo"));
                cl.setIdTelefonoCli(rs.getInt("TelefonoPro"));
                cl.setDireccion(rs.getString("Direccion"));
                cl.setEstado(rs.getInt("Estado"));
                listaCL.add(cl);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaCL;
    }

}

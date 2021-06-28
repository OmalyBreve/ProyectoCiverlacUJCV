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

public class ClienteDB {

    ConexionSQL cn = new ConexionSQL();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
     public boolean ValidarTelefonoCliente(String telefono) {

        String sql = "Select * from cliente where  idTelefonoCli= ?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, telefono);
            rs= ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
            
        } catch (SQLException e) {
           
            return false;
        }
        

    }
      public int ValidarUpdateTelefonoCliente(String telefono, int ID) {

        String sql = "SELECT if ( idCliente = ?, TRUE,FALSE )as Estado  FROM cliente WHERE idTelefonoCli = ?";

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
      
    public boolean registrarClientes(Cliente cl) {

        String sql = "INSERT INTO `cliente`( `Nombre`, `Correo`, `Direccion`, `idTelefonoCli`, `Estado`) VALUES (?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getCorreo());
            ps.setString(3, cl.getDireccion());
            ps.setInt(4, cl.getIdTelefonoCli());
            ps.setInt(5, cl.getEstado());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro Exitoso");
            return true;
        } catch (SQLException e) {
           
            return false;
        }

    }

    public boolean UpdateClientes(Cliente cl) {

        String sql = "UPDATE  `cliente` set  `Nombre`=?, `Correo`=?, `Direccion`=?, `idTelefonoCli`=?, `Estado`=? WHERE idCliente=?";

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
//            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }

    }

    public List listarClientesBusqueda(String filtro, String dato) {

        String sql = null;
        if ("Nombre".equals(filtro)) {
            sql = "SELECT * FROM `cliente` WHERE " + filtro + " LIKE '%" + dato + "%' ";
        }
        if ("idTelefonoCli".equals(filtro)) {
            sql = "SELECT * FROM `cliente` WHERE " + filtro + " LIKE '%" + dato + "%' ";
        }
        if ("idCliente".equals(filtro)) {
            sql = "SELECT * FROM `cliente` WHERE " + filtro + "=?";
        }
        System.out.println(sql);
        List<Cliente> listaCL = new ArrayList();

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            if ("idCliente".equals(filtro)) {
                ps.setString(1, dato);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cl = new Cliente();

                cl.setIdCliente(rs.getInt("idCliente"));
                cl.setNombre(rs.getString("Nombre"));
                cl.setCorreo(rs.getString("Correo"));
                cl.setDireccion(rs.getString("Direccion"));
                cl.setIdTelefonoCli(rs.getInt("idTelefonoCli"));
                cl.setEstado(rs.getInt("Estado"));
                listaCL.add(cl);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaCL;
    }

    public List listarClientes() {

        List<Cliente> listaCL = new ArrayList();
        String sql = "SELECT * FROM `cliente`";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cl = new Cliente();

                cl.setIdCliente(rs.getInt("idCliente"));
                cl.setNombre(rs.getString("Nombre"));
                cl.setCorreo(rs.getString("Correo"));
                cl.setDireccion(rs.getString("Direccion"));
                cl.setIdTelefonoCli(rs.getInt("idTelefonoCli"));
                cl.setEstado(rs.getInt("Estado"));
                listaCL.add(cl);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaCL;
    }

    //
    public List listarProveedorBusqueda(String filtro, String dato) {

        String sql = null;
        if ("Nombre".equals(filtro)) {
            sql = "SELECT * FROM `proveedor` WHERE " + filtro + " LIKE '%" + dato + "%' ";
        }
        if ("TelefonoPro".equals(filtro)) {
            sql = "SELECT * FROM `proveedor` WHERE " + filtro + " LIKE '%" + dato + "%' ";
        }
        if ("idProveedor".equals(filtro)) {
            sql = "SELECT * FROM `proveedor` WHERE " + filtro + "=?";
        }
        System.out.println(sql);
        List<Cliente> listaCL = new ArrayList();

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            if ("idProveedor".equals(filtro)) {
                ps.setString(1, dato);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cl = new Cliente();

                cl.setIdCliente(rs.getInt("idProveedor"));
                cl.setNombre(rs.getString("Nombre"));
                cl.setCorreo(rs.getString("Correo"));
                cl.setDireccion(rs.getString("Direccion"));
                cl.setIdTelefonoCli(rs.getInt("TelefonoPro"));
                cl.setEstado(rs.getInt("Estado"));
                listaCL.add(cl);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaCL;
    }
    //insert EmpresaParam
     public boolean UpdateDatosNegocio(String nombre,String direc,String tel,String rtn,String cai) {

        String sql = "UPDATE  `datosEmpresa` set  `Nombre`=?, `Direccion`=?, `Telefono`=?, `RTN`=?, `CAI`=?";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, direc);
            ps.setString(3, tel);
            ps.setString(4, rtn);
            ps.setString(5, cai);
            ps.execute();
//            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente");
            return true;
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }

    }
}

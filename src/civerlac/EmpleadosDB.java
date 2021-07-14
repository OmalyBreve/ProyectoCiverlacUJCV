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

public class EmpleadosDB {

    ConexionSQL cn = new ConexionSQL();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    ErrorLogs el = new ErrorLogs();
    public boolean ValidarTelefonoEmpleado(String telefono) {

        String sql = "Select * from empleado where TelefonoPro= ? ";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, telefono);
            rs= ps.executeQuery();
            if(rs.next()){
                System.out.println("El telefono Existe");
                el.LogBitacora("Validar telefono empelado tuvo exito");
                return true;
            }else{
                return false;
            }
            
        } catch (SQLException e) {
           el.LogBitacora("Error al validar telefono empleado" + e);
            return false;
        }
        

    }
    public boolean registrarEmpleado(Empleado cl) {

        String sql = "INSERT INTO `empleado`( `Nombre`, `Ocupacion` , `Correo`, `Direccion`, `idTelefonoEmp`, `Estado`) VALUES (?,?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getOcupacion());
            ps.setString(3, cl.getCorreo());
            ps.setString(4, cl.getDireccion());
            ps.setInt(5, cl.getTelefono());
            ps.setInt(6, cl.getEstado());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro exitoso");
            el.LogBitacora("Registrar empleado tuvo exito");
            return true;
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, e.toString());
            el.LogBitacora("Error al registrar empleado" + e);
            return false;
        }

    }

    public boolean UpdateEmpleado(Empleado cl) {

        String sql = "UPDATE  `empleado` set  `Nombre`=?, `Ocupacion`=?, `Correo`=?, `Direccion`=?, `idTelefonoEmp`=?, `Estado`=? WHERE idEmpleado=? ";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, cl.getNombre());
            ps.setString(2, cl.getOcupacion());
            ps.setString(3, cl.getCorreo());
            ps.setString(4, cl.getDireccion());
            ps.setInt(5, cl.getTelefono());
            ps.setInt(6, cl.getEstado());
            ps.setInt(7, cl.getIdEmpleado());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente");
            el.LogBitacora("Update empleado tuvo exito");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            el.LogBitacora("Error al update empleado" + e);
            return false;
        }

    }

    public List ListEmpleados() {

        List<Empleado> listaCL = new ArrayList();
        String sql = "SELECT * FROM `empleado`";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Empleado cl = new Empleado();

                cl.setIdEmpleado(rs.getInt("idEmpleado"));
                cl.setNombre(rs.getString("Nombre"));
                cl.setOcupacion(rs.getString("Ocupacion"));
                cl.setCorreo(rs.getString("Correo"));
                cl.setDireccion(rs.getString("Direccion"));
                cl.setTelefono(rs.getInt("idTelefonoEmp"));
                cl.setEstado(rs.getInt("Estado"));
                listaCL.add(cl);
                
            }
            el.LogBitacora("Listar empleados tuvo exito");

        } catch (SQLException e) {
            System.out.println(e.toString());
            el.LogBitacora("Error al listar empleados "+ e);
            
        }
        return listaCL;
    }

    public List listarClientesBusqueda(String filtro, String dato) {

        String sql = null;
        if ("Nombre".equals(filtro)) {
            sql = "SELECT * FROM `empleado` WHERE " + filtro + " LIKE '%" + dato + "%' ";
        }
        if ("idTelefonoEmp".equals(filtro)) {
            sql = "SELECT * FROM `empleado` WHERE " + filtro + " LIKE '%" + dato + "%' ";
        }
        if ("idEmpleado".equals(filtro)) {
            sql = "SELECT * FROM `empleado` WHERE " + filtro + "=?";
        }
        System.out.println(sql);
        List<Empleado> listaCL = new ArrayList();

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            if ("idEmpleado".equals(filtro)) {
                ps.setString(1, dato);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Empleado cl = new Empleado();

                cl.setIdEmpleado(rs.getInt("idEmpleado"));
                cl.setNombre(rs.getString("Nombre"));
                cl.setCorreo(rs.getString("Correo"));
                cl.setOcupacion(rs.getString("Ocupacion"));
                cl.setDireccion(rs.getString("Direccion"));
                cl.setTelefono(rs.getInt("idTelefonoEmp"));
                cl.setEstado(rs.getInt("Estado"));
                listaCL.add(cl);

            }
            el.LogBitacora("Listar cliente busqueda tuvo exito");
        } catch (SQLException e) {
            System.out.println(e.toString());
            el.LogBitacora("Error al listar cliente busqueda" + e);
        }
        return listaCL;
    }

}

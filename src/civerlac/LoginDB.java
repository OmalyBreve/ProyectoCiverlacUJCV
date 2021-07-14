
package civerlac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LoginDB {
    
    Connection con = null;
    PreparedStatement ps= null;
    ResultSet rs= null;
    ConexionSQL cn = new ConexionSQL();
    ErrorLogs el = new ErrorLogs();
    
    public Login log(String user, String pass){
        
        Login l = new Login();
        String sql = "SELECT * FROM usuarios WHERE idEmpleado = ? AND contrasena = ? AND Estado = 'Habilitado'";
        
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);//EN ESTA LINEA MUERE. ERROR = "Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            
            if(rs.next()){
                l.setUser(rs.getString("idEmpleado"));
                l.setPass(rs.getString("contrasena"));
            } 
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return l;
    }
    public boolean registroLogLogin(String fecha, String  user, String intento  ) {

        String sql = "INSERT INTO `bitacoralogin`( `Fecha`, `Usuario`, `Intentos`) VALUES (?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, user);
            ps.setString(3, intento);
            ps.execute();
//            JOptionPane.showMessageDialog(null, "Registro Exitoso");
            el.LogBitacora("El registro tuvo exito");
            return true;
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,"Error al registrar"+ e);
            return false;
        }
        
    }
    public boolean updateLogLogin(String intento) {

        String sql = "UPDATE  bitacoralogin set Intentos =? WHERE idLog =(SELECT idLog FROM bitacoralogin order by idLog desc LIMIT 1)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, intento);
            ps.execute();
//            JOptionPane.showMessageDialog(null, "update Exitoso");
            el.LogBitacora("Update loging tuvo exito");
            return true;
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,"Error al update loging"+ e);
            return false;
        }
       
    }
    
     public List ListaDatosEmpresa() {

        List<DatosEmpresa> listaCL = new ArrayList();
        String sql = "SELECT * FROM `datosempresa`";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DatosEmpresa cl = new DatosEmpresa();

                cl.setNombre(rs.getString("Nombre"));
                cl.setDireccion(rs.getString("Direccion"));
                cl.setTelefono(rs.getString("Telefono"));
                cl.setRtn(rs.getString("RTN"));
                cl.setCai(rs.getString("CAI"));
          
                listaCL.add(cl);

            }
            el.LogBitacora("Lista datos empresa tuvo exito");
        } catch (SQLException e) {
            System.out.println(e.toString());
            el.LogBitacora("Error al listar datos de empresa" + e);
            
        }
        return listaCL;
    }
}

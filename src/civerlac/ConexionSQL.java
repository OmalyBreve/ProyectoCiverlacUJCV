package civerlac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Roger Sifontes
 */
public class ConexionSQL {
    // capturando logs
    Connection con;
    String url = "jdbc:mysql://localhost:3306/cirverlacdb";
    String user = "root";
    String pass = "12345678";
    ErrorLogs el = new ErrorLogs();
    
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        } catch (ClassNotFoundException e) {
            System.out.println("Error al registrar el driver MySQL" + e);
          
            el.LogBitacora("Error al registrar el driver MySQL" + e);
            
        }
        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("CONECTADO");
            el.LogBitacora("Se realizo la conexion con exito");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
            // si aqui falla, es por que la
              el.LogBitacora("Error realizar la conexion con el servidor" + ex);
              //probemos
        }
        return con;
    }

}

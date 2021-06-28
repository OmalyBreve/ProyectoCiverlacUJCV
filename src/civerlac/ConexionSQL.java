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

    Connection con;
    String url = "jdbc:mysql://localhost:3306/cirverlacdb";
    String user = "root";
    String pass = "";

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("Error al registrar el driver MySQL" + e);
        }
        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("CONECTADO");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

}

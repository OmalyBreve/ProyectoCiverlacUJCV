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
public class RegistroDB {

    ConexionSQL cn = new ConexionSQL();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public boolean RegistrarUsuario(int id, String clave, int privilegio) {

        String sql = "INSERT INTO `EmpleadoUsuario`( `idUsuario`, `clave`,`privilegio` ) VALUES (?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, clave);
            ps.setInt(3, privilegio);
            ps.execute();
            JOptionPane.showMessageDialog(null, "Registro exitoso");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "El empleado ya cuenta con un usuario");
            return false;
        }

    }

}

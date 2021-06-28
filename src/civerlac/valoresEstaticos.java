/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civerlac;

/**
 *
 * @author Omaly Breve
 */
public class valoresEstaticos {

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    private static String usuario;

    public  int getIdUsuario() {
        return idUsuario;
    }

    public  void setIdUsuario(int idUsuario) {
        valoresEstaticos.idUsuario = idUsuario;
    }
    private static int idUsuario;
    private static int privilegio;
    
    public int getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(int privilegio) {
        this.privilegio = privilegio;
    }
    
}

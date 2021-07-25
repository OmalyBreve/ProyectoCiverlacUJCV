/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civerlac;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



/**
 *
 * @author Omaly Breve
 */
public class ErrorLogs {

    public void LogBitacora(String error) {
        java.util.Date fecha = new java.util.Date();
        DateFormat f = new SimpleDateFormat("yyy-MM-dd"); // hra min seg 
        String fileName = ".//Logs\\RegistroLog" + f.format(fecha) + ".txt";// se indica guardar el archivo unicamente en txt

        try {

            //Creamos un printstream sobre el archivo permitiendo añadir al
            //final para no sobreescribir.
            PrintStream ps = new PrintStream(new BufferedOutputStream(
                    new FileOutputStream(new File(fileName), true)), true);
            java.util.Date fecha2 = new java.util.Date();
            DateFormat f2 = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
            String fechaActual = f2.format(fecha2);
            ps.println(fechaActual + " -- " + error);

            ps.close();
            //Redirigimos entrada y salida estandar

            System.setOut(ps);

            System.setErr(ps);

        } catch (FileNotFoundException ex) {

            System.err.println("Se ha producido una excepción file fallo" + ex);

        }

    }
}

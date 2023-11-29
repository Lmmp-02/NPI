/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 34667
 */
public class Conexion {
    public static Connection getConexion(String database){
        String conexionUrl = "jdbc:sqlserver://localhost:1433;"
                + "database="+database+";"
                + "user=sa;"
                + "password=Ja16112002;"
                + "loginTimeout=30;"
                + "TrustServerCertificate=True;";
        
        try{
            Connection con = DriverManager.getConnection(conexionUrl);
            return con;
        } catch(SQLException ex){
            System.out.println(ex.toString());
            return null;
        }
    }
}

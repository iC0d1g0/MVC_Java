
package modelo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion {
    private final String url="jdbc:mysql://localhost:3306/usuarios";
    private final String root="root";
    private final String pass="adderlis"; 
   
    
    public Connection connect(){
        Connection con=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(url,root,pass);
            
                    
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
     public boolean getConnectionStatus(){
        Connection con=connect();
        if( con != null){
            try{
                con.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
           return true;
        
            }
        return false;
        }
}

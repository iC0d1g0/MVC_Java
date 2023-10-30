
package modelo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import java.util.Properties;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
public class Conexion {
     private static final String CONFIG_FILE = "config.cnf";
    private static final Properties properties = new Properties();
    

    private final String url="jdbc:mysql://localhost:3306/";
    private final String root="root";
    private static String pass=loadPassword();

    private String database="usuarios";
  
    
    public boolean adminDatabase(){
            Connection con=null;
            
            
            String sql="CREATE DATABASE IF NOT EXISTS "+database;
            String sql2="CREATE TABLE usuarios.user(name varchar(50), password varchar(50), usuario varchar(50))";
            JOptionPane.showMessageDialog(null, "Configurando tu base de datos, esperar");
            
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(url,root, pass);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.execute(sql);
            try{
                st.execute(sql2);
               
            }catch(SQLException user){
                System.out.println(user);
                
            }
            
            
            return true;
                    
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }finally{
            try{
                if(con !=null){
                    con.close();
                }else{
                    System.out.println("No need to close");
                } 
                
            }catch(SQLException ej){
                System.out.println("error al cerrar conexion");                
            }
           
        }
        
    }
    
    public Connection connect(){
       
            Connection con=null;
             
            try{
                pass=Conexion.loadPassword();
                Class.forName("com.mysql.cj.jdbc.Driver");
                con=DriverManager.getConnection(url+database,root,pass);


            }catch(SQLException e){
                System.out.println(e);
                if(adminDatabase()){
                    connect();
                }else{
                    JOptionPane.showMessageDialog(null, "Si en este punto hay error, es por que la conexion esta desabilitada o no tienes instalado mysQL. Error 402");
                }
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            return con;
        
    }


     public boolean getConnectionStatus(){
        Connection con=connect();
        if( con != null){
            try{
                con.close();
                
            } catch (SQLException ex) {
                //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
           return true;
        
            }
        
        return false;
        }
    
    public static void savePassword(String password) {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.setProperty("password", password);
            properties.store(fos, "Configuración del programa");
            
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }
    }
    
    public static String loadPassword() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            return properties.getProperty("password", "");
        } catch (IOException e) {
            String pass=JOptionPane.showInputDialog("Ingresa la contrasenia de tu base de datos");
            savePassword(pass);
            
            // Manejar la excepción según tus necesidades
            return "";
        }
    
    }
}


    



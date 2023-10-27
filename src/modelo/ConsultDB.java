
package modelo;

import java.sql.*;
public class ConsultDB extends Conexion{
    
    
    
    public boolean getBuscar(Modelo pro){
       
        PreparedStatement pre=null;
        Connection con=connect();
        String buscame="SELECT * FROM "+pro.getTabladb()+" WHERE usuario=?";     
        
        try{
            pre=con.prepareStatement(buscame);
           
            pre.setString(1, pro.getUsuario());
            
            pre.executeQuery();
            ResultSet rs=pre.getResultSet();
            if(rs.next()){
               pro.setPassword(rs.getString("password"));
               
               pro.setNombre(rs.getString("name"));
              return true;
            }
            return false;
            
            
        }catch(SQLException e){
            return false;
            
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
            
        }
        
       
    }
    
    public boolean setNewuser(Modelo pro){
        PreparedStatement pre=null;
        Connection con=connect();
        String insertar="INSERT INTO usr( name,password,usuario)VALUES(?,?,?)";
        try{
            pre=con.prepareStatement(insertar);
            pre.setString(1, pro.getNombre());
            pre.setString(2, pro.getPassword());
            pre.setString(3, pro.getUsuario());
            pre.execute();
                    
            return true;
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
            System.out.println(e);
            
            }
            
        }
        
        
        
    }
    public boolean creatTable(Modelo pro){
        PreparedStatement pre=null;
        Connection con=connect();
        String crea="CREATE TABLE "+pro.getUsuario()+"(name varchar(50), matricula varchar(50)); ";
        try{
            pre=con.prepareStatement(crea);
            
            pre.execute();
                    
            return true;
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
            System.out.println(e);
            
            }
            
        }
        
        
        
    }
    
}

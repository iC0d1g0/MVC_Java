
package modelo;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConDBestudiante extends Conexion {
    
  
        
    
    public boolean setNew(ModeloStd pro){
        
        PreparedStatement pre=null;
        Connection con=connect();
        String sql="INSERT INTO "+pro.getUsuarioauth()+"(name, matricula)VALUES(?,?)";
        try{
            pre=con.prepareStatement(sql);
            pre.setString(1, pro.getNombre());
            pre.setString(2, pro.getMatricula());
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
    public ResultSet getBuscartodo(ModeloStd pro){
        //Este metodo permite leer todo lo que esta en la base de datos, 
        //debuelve un objecto de tipo ResultSet para luego ser admnistrado por el controlador. 
        
        Connection con=connect();
        Statement st=null;
        ResultSet rs=null;
        String base=pro.getUsuarioauth();
        String sql="SELECT * FROM "+pro.getUsuarioauth()+" WHERE name LIKE '"+pro.getDatos()+"%' OR matricula LIKE '"+pro.getDatos()+"%';";
               
        try{
            
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(sql);
            
        } catch (SQLException ex) {
            Logger.getLogger(ConDBestudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public boolean getBuscarnombre(ModeloStd pro){
        Connection con=connect();
        PreparedStatement pre=null;
       String sql="SELECT * FROM "+pro.getUsuarioauth()+" where name=?";
        try{
            pre=con.prepareStatement(sql);
            pre.setString(1,pro.getNombre());
         
            
            pre.executeQuery();
            
            ResultSet rs=pre.getResultSet();
             while(rs.next()){
                pro.setNombre(rs.getString("name"));
                pro.setMatricula(rs.getString("matricula"));
                
                return true;
            }
            return false;
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
    public boolean eliminar(ModeloStd pro){
        Connection con=connect();
        PreparedStatement pre=null;
        String sql="DELETE FROM "+pro.getUsuarioauth()+" where name=?";
        
        try{
            pre=con.prepareStatement(sql);
            pre.setString(1, pro.getNombre());
            pre.executeUpdate();
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
    public ResultSet getMuestratodo(ModeloStd pro){
        //Este metodo permite leer todo lo que esta en la base de datos, 
        //debuelve un objecto de tipo ResultSet para luego ser admnistrado por el controlador. 
        
        Connection con=connect();
        Statement st=null;
        ResultSet rs=null;
        String base=pro.getUsuarioauth();
        String sql="SELECT * FROM "+base+"";
        
        try{
            
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(sql);
            
        } catch (SQLException ex) {
            Logger.getLogger(ConDBestudiante.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public boolean setUpdate(ModeloStd pro){
        Connection con=connect();
        PreparedStatement pre=null;
        String sql="UPDATE "+pro.getUsuarioauth()+" SET name = ?, matricula=? where name=?";
        try{
            pre=con.prepareStatement(sql);
            pre.setString(1, pro.getNombre());
            pre.setString(2, pro.getMatricula());
            pre.setString(3, pro.getDatos());
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

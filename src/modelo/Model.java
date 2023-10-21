/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import com.sun.jdi.connect.spi.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adder
 */
public class Model {
   
    
     private Connection con=null;
     private Statement st = null;
     private ResultSet res=null;
     
     public Model(){
         try {
           Class.forName("com.mysql.cj.jdbc.Driver");
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
         }
     
}    
    public void setCon(String con, String root, String password) throws SQLException {
        this.con =(Connection) DriverManager.getConnection(con, root, password);
    }


    public void setSt(Statement st) {
        this.st = st;
    }



    public void setRes(ResultSet res) {
        this.res = res;
    }
    
   public String[][] getTabla() throws SQLException{
       this.res.last();
       int row = this.res.getRow();
       int col = this.res.getMetaData().getColumnCount();   
       
       this.res.beforeFirst();
       String rowData[][]=new String[row][col];
       
       int r=0;
       
       while(res.next()){
           for(int i=0;i<col;i++){
               rowData[r][i]=this.res.getString((i+1));
           }
           r++;
       }
       
       return rowData;
       
   }
   public boolean setNew(String args,String values) throws SQLException{
       boolean b=this.st.execute("insert into emp('"+args+"')values('"+values+"')");      
       return b;
       
   }
    public boolean setBorrar(String id,String tabla) throws SQLException{
       boolean b=this.st.execute("insert into '"+tabla+"')where id='"+id+"'");      
       return b;
       
   }
    public String [][] getBusscar(String id, String tabla) throws SQLException{
       ResultSet res=this.st.executeQuery("select * from '"+tabla+"' where id='"+id+"'"); 
       res.last();
       int row = res.getRow();
       int col = res.getMetaData().getColumnCount();
       res.beforeFirst();
       String[][] b = new String[row][col];
       int r=0;
       while(res.next()){
           for(int i=0;i<col;i++){
               b[r][i]=res.getString((i+1));
           }
           r++;
                
       }
       
       
       return b;
       
   }
   
}

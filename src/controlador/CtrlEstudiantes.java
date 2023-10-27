
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.ConDBestudiante;
import modelo.ModeloStd;
import vista.notas_estudiantes;
import java.sql.*;
import javax.swing.JOptionPane;
import mvc.MVC;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultDB;
import modelo.Modelo;
import vista.login;

public class CtrlEstudiantes implements ActionListener{
    private ModeloStd moddb;
    private notas_estudiantes vista;
    private ConDBestudiante connn;
    
    public CtrlEstudiantes( ModeloStd moddb, notas_estudiantes vista, ConDBestudiante conn){
        this.moddb=moddb;
        this.vista=vista;
        this.connn=conn;
        
        this.vista.btnNew.addActionListener(this);
        this.vista.btn_buscar.addActionListener(this);
        this.vista.btn_eliminar.addActionListener(this);
        this.vista.btn_logout.addActionListener(this);
        
        //this.vista.tabla
     
    }
    public void manageRS(){
        try{
            ResultSet rs = connn.getMuestratodo(moddb);
            rs.last();
            int row = rs.getRow();
            int col=rs.getMetaData().getColumnCount();
            System.out.println("Row : "+row+" columns: "+col);
            rs.beforeFirst();
            String[][] rowData=new String[row][col];
            int con=0;
            while(rs.next()){
                for(int i=0;i<col;i++){
                    rowData[con][i]=rs.getString((i+1));
                    
                }
                con++;
            }
            for(String[] s:rowData){
                for(String i:s){
                    System.out.print(i+" ");
                }
                System.out.println();
            }
            DefaultTableModel model=(DefaultTableModel) vista.tabla.getModel();
            String[] tab_name={"Nombre", "Matricula"};
            model.setDataVector(rowData, tab_name);
            
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
        
    }
    public void init(String usuario, String nombre){
        moddb.setUsuarioauth(usuario);
        
        vista.setLocationRelativeTo(null);
        vista.setTitle("Welcome "+nombre);
        vista.setVisible(true);
        manageRS();
    }
        public boolean ValidaConex(){
            if(!connn.getConnectionStatus()){

                JOptionPane.showMessageDialog(null, "Error de conexion a base de datos!");
                return false;
                  }else{
                 JOptionPane.showMessageDialog(null, "Conexion Restablecida");
                 return true;
            }
       
    }   
    public void actionBtnNew(){
          System.out.println("Pulsaste boton new");
            moddb.setNombre(vista.txt_nombre.getText());
            moddb.setMatricula(vista.txt_matricula.getText());
            if(connn.setNew(moddb)){
                System.out.println("Datos guardados con exito!!"+"Nombre: "+moddb.getNombre()+" Matricula: "+moddb.getMatricula()+" en la tabla: "+
                        moddb.getUsuarioauth());
                        manageRS();
            }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.vista.btnNew){
            if(ValidaConex()) actionBtnNew();
          
            
        }
         if(e.getSource()==this.vista.btn_buscar){
              if(ValidaConex()) System.out.println("Pulsaste boton buscar");
            
        }
          if(e.getSource()==this.vista.btn_eliminar){
             if(ValidaConex()) System.out.println("Pulsaste boton eliminar");
             
        }
           if(e.getSource()==this.vista.btn_logout){
                    login log=new login();
                    Modelo mod=new Modelo();
                    ConsultDB db=new ConsultDB();
        
                    Ctrl control=new Ctrl(db, mod, log);
        
                    control.init();
                    vista.setVisible(false);
                    log.setVisible(true);
                    System.out.println("Pulsaste boton logout");
             
        }
     
    }
    
    
}
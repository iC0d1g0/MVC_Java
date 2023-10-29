package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import modelo.ConDBestudiante;
import modelo.ConsultDB;
import modelo.Modelo;
import modelo.ModeloStd;
import vista.login;
import vista.notas_estudiantes;
import vista.registrar;

public class Ctrl implements ActionListener {

    private ConsultDB db;
    private Modelo mod;
    private login log;
    private registrar reg;
    private notas_estudiantes nota;

    public Ctrl(ConsultDB db, Modelo mod, login log) {
        this.mod = mod;
        this.log = log;
        this.db = db;
        
        
        this.reg = new registrar();
        this.nota = new notas_estudiantes();

        log.login_btn.addActionListener(this);

        log.registro_btn.addActionListener(this);

        this.reg.jPasswordField2.addActionListener(this);
        this.reg.btn_registrar.addActionListener(this);
        this.reg.btn_atras.addActionListener(this);
        
        this.nota.btn_logout.addActionListener(this);

    }
    public void initStd(String usuario, String nombre){
        //Este metodo inicializa el Ctrl de estudiantes, 
        //
        ModeloStd modStd=new ModeloStd();
        ConDBestudiante dbStd=new ConDBestudiante();
        notas_estudiantes vistaEstu=new notas_estudiantes();
        
        CtrlEstudiantes ctrlStd=new CtrlEstudiantes(modStd,  vistaEstu,  dbStd);
        
        ctrlStd.init(usuario, nombre);
        
        
        
        
    }
    public boolean ValidaConex(){
        if(!db.getConnectionStatus()){
            JOptionPane.showMessageDialog(null, "Error de conexion a base de datos!");
            return false;
              }else{
             //JOptionPane.showMessageDialog(null, "Conexion Restablecida");
             return true;
        }
       
    }   

    public void init() {
       
        log.setTitle("Mi Titulo");
        log.setResizable(false);
        log.setLocationRelativeTo(null);
        ValidaConex();
    }

    public boolean validateLogin(String user, String pass) {
        mod.setUsuario(user);
        mod.setTabladb("user");
        if (db.getBuscar(mod)) {
            if (mod.getPassword().equals(pass)) {
                JOptionPane.showMessageDialog(null, "Login successfully!");
                return true;

            } else {
                JOptionPane.showMessageDialog(null, "Wrong password!");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado!");
            return false;
        }

    }

    public boolean validateUser(String user) {
        mod.setUsuario(user);
        mod.setTabladb("usr");
        if (db.getBuscar(mod)) {
            JOptionPane.showMessageDialog(null, "Usurio invalido, prueba otro usuario");
            return false;

        } else {

            return true;
        }

    }

    public void actionLogin() {
        String usuario = log.txtUsuario.getText();
        String password = log.txtPassword.getText();
        if ("".equals(usuario) || "".equals(password)) {
            JOptionPane.showMessageDialog(null, "Campos bacios");
            System.out.println("Los Usuario basio");
        } else {
            if (validateLogin(usuario, password)) {
                
                //Esta parte esta dudable. Deveria enverdad llamar a un objeto que 
                //reciba por parametros el nombre de usuario y el nombre del profesor. 
                // luego este metodo es quien debe iniciar nota.setVisible(true), y a partir de hay dejarlo todo en las
                //manos del otro Ctrl_Std
                //no ta.setLocationRelativeTo(null);
              
                initStd(mod.getUsuario(),mod.getNombre());
                //nota.setVisible(true);//temporal. 
                
                log.setVisible(false);
                limpiarEntradas(new JTextField[]{log.txtUsuario},log.txtPassword);
        
                
          
            }
        }
    }

    public void actionRegister() {
        this.reg.setLocationRelativeTo(null);
        this.reg.setResizable(false);
        this.reg.setTitle("Registro");
        this.reg.setVisible(true);
        this.reg.txt_passmatching.setVisible(false);
        log.setVisible(false);
        limpiarEntradas(new JTextField[]{log.txtUsuario},log.txtPassword);
        
        System.out.println("boton registrar pulsado");
    }
    public void volveraLogin(){
            log.setVisible(true);
            this.reg.setVisible(false);
            limpiarEntradas(new JTextField[]{reg.jTextField1,reg.jTextField2},reg.jPasswordField1,reg.jPasswordField2);
        
    }
    public void actionRegRegister() {
        String usuario = this.reg.jTextField1.getText();
        String nombre = this.reg.jTextField2.getText();
        String pass1 = this.reg.jPasswordField1.getText();
        String pass2 = this.reg.jPasswordField2.getText();
        
        if ("".equals(usuario) || "".equals(nombre) || "".equals(pass1) || "".equals(pass2)) {
            JOptionPane.showMessageDialog(null, "Verifica los espacios en blanco!");
        } else {
            if (validateUser(usuario)) {
                if (pass1.equals(pass2)) {
                    JOptionPane.showMessageDialog(null, "Saving information, please wait!!");
                    mod.setUsuario(usuario);
                    mod.setNombre(nombre);
                    mod.setPassword(pass2);
                    if (db.creatTable(mod)) {
                            JOptionPane.showMessageDialog(null, "Nuevo TABLA");
                         if (db.setNewuser(mod)) {
                            JOptionPane.showMessageDialog(null, "Nuevo usuario");
                            limpiarEntradas(new JTextField[]{reg.jTextField1,reg.jTextField2},reg.jPasswordField1,reg.jPasswordField2);
        
                            //Aqui el volver al login
                             volveraLogin();
                            }
                        }else{
                         JOptionPane.showMessageDialog(null, "Usuario no valido!");                        
                               }
                
                } else {

                    JOptionPane.showMessageDialog(null, "No match!!");
                }

            }

        }

    }
    public void limpiarEntradas(JTextField[] txt,JPasswordField... pass){
            for(JTextField tx: txt){
                tx.setText(null);
            }
            for(JPasswordField pss: pass){
                pss.setText(null);
            }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == log.login_btn) {
            if(ValidaConex())actionLogin();
            
        }
        if (e.getSource() == log.registro_btn) {
           
             if(ValidaConex()) actionRegister();
        }

        if (e.getSource() == this.reg.btn_atras) {
          
           if(ValidaConex()) volveraLogin();
        }
        if (e.getSource() == this.reg.btn_registrar) {
            if(ValidaConex())  actionRegRegister();
        }
        if (e.getSource() == this.reg.btn_atras) {
            log.setVisible(true);
            this.reg.setVisible(false);
        }
        if(e.getSource()==this.nota.btn_logout){
            this.nota.setVisible(false);
            log.setVisible(true);
        }

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mvc;

import controlador.Ctrl;
import modelo.ConsultDB;
import modelo.Modelo;
import vista.login;

/**
 *
 * @author adder
 */
public class MVC {

 
    public static void main(String[] args) {
        login log=new login();
        Modelo mod=new Modelo();
        ConsultDB db=new ConsultDB();
        
        Ctrl control=new Ctrl(db, mod, log);
        log.setVisible(true);
        control.init();
        
       
    }
    
}

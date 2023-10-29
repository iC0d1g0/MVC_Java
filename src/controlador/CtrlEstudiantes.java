package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import modelo.ConDBestudiante;
import modelo.ModeloStd;
import vista.notas_estudiantes;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import mvc.MVC;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultDB;
import modelo.Modelo;
import vista.login;
import controlador.Ctrl;
import javax.swing.JPasswordField;

public class CtrlEstudiantes implements ActionListener {

    private ModeloStd moddb;
    private notas_estudiantes vista;
    private ConDBestudiante connn;
    private DefaultTableModel model = null;

    public CtrlEstudiantes(ModeloStd moddb, notas_estudiantes vista, ConDBestudiante conn) {
        this.moddb = moddb;
        this.vista = vista;
        this.connn = conn;

        this.vista.btnNew.addActionListener(this);
        this.vista.btn_buscar.addActionListener(this);
        this.vista.btn_eliminar.addActionListener(this);
        this.vista.btn_logout.addActionListener(this);
        this.vista.btn_update.addActionListener(this);
        vista.tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });

    }

    public void manageRS(ResultSet rs) {
        try {
            ResultSet res = rs;
            rs.last();
            int row = rs.getRow();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("Row : " + row + " columns: " + col);
            rs.beforeFirst();
            String[][] rowData = new String[row][col];
            int con = 0;
            while (rs.next()) {
                for (int i = 0; i < col; i++) {
                    rowData[con][i] = rs.getString((i + 1));

                }
                con++;
            }
            for (String[] s : rowData) {
                for (String i : s) {
                    System.out.print(i + " ");
                }
                System.out.println();
            }
            DefaultTableModel model = (DefaultTableModel) vista.tabla.getModel();
            String[] tab_name = {"Nombre", "Matricula"};
            model.setDataVector(rowData, tab_name);

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    public void init(String usuario, String nombre) {
        moddb.setUsuarioauth(usuario);

        vista.setLocationRelativeTo(null);
        vista.setTitle("Welcome " + nombre);
        vista.setVisible(true);
        manageRS(connn.getMuestratodo(moddb));
    }

    public boolean ValidaConex() {
        if (!connn.getConnectionStatus()) {

            JOptionPane.showMessageDialog(null, "Error de conexion a base de datos!");
            return false;
        } else {
            //JOptionPane.showMessageDialog(null, "Conexion Restablecida");
            return true;
        }

    }

    public void actionBtnNew() {
        System.out.println("Pulsaste boton new");
        if ("".equals(vista.txt_nombre.getText()) || "".equals(vista.txt_matricula.getText())) {
            JOptionPane.showMessageDialog(null, "Seleciona algun nombre");
        } else {
            moddb.setNombre(vista.txt_nombre.getText());
            moddb.setMatricula(vista.txt_matricula.getText());
            if (connn.setNew(moddb)) {
                System.out.println("Datos guardados con exito!!" + "Nombre: " + moddb.getNombre() + " Matricula: " + moddb.getMatricula() + " en la tabla: "
                        + moddb.getUsuarioauth());
                manageRS(connn.getMuestratodo(moddb));
                limpiarEntradas(new JTextField[]{vista.txt_nombre, vista.txt_matricula});
            }
        }
    }

    public void tablaMouseClicked(MouseEvent e) {
        if (ValidaConex()) {
            System.out.println("Pulsaste boton eliminar");
        }
        int selecion = this.vista.tabla.getSelectedRow();

        if (selecion < 0) {
            System.out.println("No elementos selecionado");
        } else {
            Object nombre = this.vista.tabla.getValueAt(selecion, 0);
            Object matricula = this.vista.tabla.getValueAt(selecion, 1);

            moddb.setNombre(String.valueOf(nombre));
            moddb.setDatos(String.valueOf(nombre));
            moddb.setMatricula(String.valueOf(matricula));

            vista.txt_nombre.setText(String.valueOf(nombre));
            vista.txt_matricula.setText(String.valueOf(matricula));
            System.out.println(nombre + " " + matricula);
            System.out.println(selecion);
        }
    }

    public void limpiarEntradas(JTextField[] txt, JPasswordField... pass) {
        for (JTextField tx : txt) {
            tx.setText(null);
        }
        for (JPasswordField pss : pass) {
            pss.setText(null);
        }
    }

    public void btnUpdate() {
        if ("".equals(vista.txt_nombre.getText()) || "".equals(vista.txt_matricula.getText())) {
            JOptionPane.showMessageDialog(null, "Seleciona algun nombre");
        } else {
            moddb.setNombre(vista.txt_nombre.getText());
            moddb.setMatricula(vista.txt_matricula.getText());

            if (connn.setUpdate(moddb)) {
                JOptionPane.showMessageDialog(null, moddb.getNombre() + " " + moddb.getMatricula() + " " + "Ha sido actualizado");
                manageRS(connn.getMuestratodo(moddb));
                limpiarEntradas(new JTextField[]{vista.txt_nombre, vista.txt_matricula, vista.txt_Buscar});
            }
        }
    }

    public void actionBtnEliminar() {

        if ("".equals(vista.txt_nombre.getText()) || "".equals(vista.txt_matricula.getText())) {
            JOptionPane.showMessageDialog(null, "Seleciona algun nombre");
        } else {
            if (connn.eliminar(moddb)) {
                JOptionPane.showMessageDialog(null, "Eliminados");
            }
            manageRS(connn.getMuestratodo(moddb));

            limpiarEntradas(new JTextField[]{vista.txt_nombre, vista.txt_matricula});
            System.out.println(moddb.getNombre() + " " + moddb.getMatricula());

        }
    }

    public void actionBtnBuscar() {

        moddb.setDatos(vista.txt_Buscar.getText());

        ResultSet rs = connn.getBuscartodo(moddb);
        manageRS(rs);

    }
    void actionBtnLogout(){
         login log = new login();
         Modelo mod = new Modelo();
         ConsultDB db = new ConsultDB();

         Ctrl control = new Ctrl(db, mod, log);

         control.init();
         vista.setVisible(false);
         log.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.btnNew) {
            if (ValidaConex()) {
                actionBtnNew();
            }

        }
        if (e.getSource() == vista.btn_update) {
            if (ValidaConex()) {
                btnUpdate();
            }

        }

        if (e.getSource() == this.vista.btn_buscar) {
                if (ValidaConex()) actionBtnBuscar();
        }

        if (e.getSource() == this.vista.btn_eliminar) {
                if (ValidaConex())  actionBtnEliminar();
                }

            
        if (e.getSource() == this.vista.btn_logout) {
               if(ValidaConex()) actionBtnLogout();
        
            }

        }

    }

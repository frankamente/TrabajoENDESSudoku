package sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Panel {

    JPanel panel = new JPanel();
    String cadenaNombre = "Nombre?";
    String cadenaApellido = "Apellidos?";
    String cadenaLocalidad = "Poblacion?";
    String cadenaVacia = "";
    JLabel labelNombre = new JLabel("Nombre...:");
    JLabel labelApellido = new JLabel("Apellidos:");
    JLabel labelLocalidad = new JLabel("Localidad:");
    JTextField jTextFieldNombre = new JTextField(cadenaNombre);
    JTextField jTextFieldApellido = new JTextField(cadenaApellido);
    JTextField jTextFieldLocalidad = new JTextField(cadenaLocalidad);
    JButton botonGuardar = new JButton("Guardar");
    JButton botonCancelar = new JButton("Cancelar");
    ArrayList<String> listaPueblos = new ArrayList();
    JTable jTable;

    public Panel() {
        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(250, 80));
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black),
                "Alta Jugadores..."));

        jTextFieldNombre.setName(cadenaNombre);
        jTextFieldNombre.setColumns(15);
        jTextFieldApellido.setColumns(15);
        jTextFieldLocalidad.setColumns(25);
        jTextFieldApellido.setName(cadenaApellido);
        jTextFieldLocalidad.setName(cadenaLocalidad);
        jTextFieldNombre.addFocusListener(new ListenerFocus());
        jTextFieldApellido.addFocusListener(new ListenerFocus());
        jTextFieldLocalidad.addFocusListener(new ListenerFocus());

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String nombre = jTextFieldNombre.getText();
                String apellido = jTextFieldApellido.getText();
                String localidad = jTextFieldLocalidad.getText();
                String mensaje = "";
                if (nombre.equals(cadenaNombre)) {
                    nombre = "";
                }
                if (apellido.equals(cadenaApellido)) {
                    apellido = "";
                }
                Jugador jugador = new Jugador(nombre, apellido);
                jugador.setLocalidad(localidad);
                if (InterfazGrafica.comprobarApellidoObligatorio(apellido)) {
                    if (InterfazGrafica.comprobarNombreObligatorio(nombre)) {
                        if (InterfazGrafica.comprobarJugadorNoRegistrado(jugador)) {
                            for (String pueblo : listaPueblos) {
                                if (jugador.getLocalidad().equalsIgnoreCase(pueblo) || jugador.getLocalidad().equals(cadenaLocalidad) 
                                        || jugador.getLocalidad().equals(cadenaVacia)) {
                                    
                                    InterfazGrafica.añadirJugador(jugador);
                                    mensaje = "Jugador añadido con éxito";
                                    actualizarTabla();
                                    break;
                                } else{
                                    mensaje = "No existe ese pueblo";
                                }
                            }

                        } else {
                            mensaje = "Jugador ya existe";
                        }
                    } else {
                        mensaje = "Es necesario introducir un nombre. ";
                    }
                } else {
                    mensaje = "Es necesario introducir un apellido";
                }
                JOptionPane.showMessageDialog(null, mensaje);
            }

        });
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                jTextFieldNombre.setText(cadenaNombre);
                jTextFieldApellido.setText(cadenaApellido);
                jTextFieldLocalidad.setText(cadenaLocalidad);
            }
        });

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelNombre, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(jTextFieldNombre, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(labelApellido, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(jTextFieldApellido, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(30, 0, 0, 0);
        panel.add(labelLocalidad, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.insets = new Insets(0, 0, 0, 0);
        panel.add(jTextFieldLocalidad, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        panel.add(botonGuardar, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.insets = new Insets(0, 5, 0, 0);
        panel.add(botonCancelar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.weightx = 2;
        constraints.insets = new Insets(0, 5, 0, 0);
        panel.add(new JScrollPane(jTable), constraints);
        cargarTodosPueblosEnLista();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    private void actualizarTabla() {
        String[] columnas = {"Nombre", "Apellidos", "Localidad"};
        DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
        ArrayList<Jugador> listaJugadores = InterfazGrafica.getListaJugadores();
        for (Jugador jugador : listaJugadores) {
            String[] datos = {jugador.getNombre(), jugador.getApellido(), jugador.getLocalidad()};
            tableModel.addRow(datos);
        }
        jTable.setModel(tableModel);
    }

    private void cargarTodosPueblosEnLista() {

        Conexion conexion = new Conexion();
        conexion.conectar();
        Connection connection = conexion.conexion;
        try {

            Statement createStatement = connection.createStatement();
            ResultSet resultSet;
            resultSet = createStatement.executeQuery("select poblacion from poblaciones order by poblacion asc");
            while (resultSet.next()) {
                listaPueblos.add(resultSet.getString(1));
            }

            conexion.cerrar();

        } catch (SQLException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
            conexion.cerrar();
        }

        conexion.cerrar();
    }

}

class ListenerFocus implements FocusListener {

    @Override
    public void focusGained(FocusEvent fe) {
        String cadenaVacia = "";
        JTextField jTextField = (JTextField) fe.getSource();
        String cadena = jTextField.getName();
        if (jTextField.getText().trim().equals(cadena)) {
            jTextField.setText(cadenaVacia);
        }
    }

    @Override
    public void focusLost(FocusEvent fe) {
        String cadenaVacia = "";
        JTextField jTextField = (JTextField) fe.getSource();
        String cadena = jTextField.getName();
        if (jTextField.getText().trim().equals(cadenaVacia)) {
            jTextField.setText(cadena);
        }
    }

}

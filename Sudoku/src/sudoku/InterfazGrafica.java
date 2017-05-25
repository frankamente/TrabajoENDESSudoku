package sudoku;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import ventana.Ventana;

public class InterfazGrafica extends Ventana {

    private static ArrayList<Jugador> listaJugadores = new ArrayList();

    public InterfazGrafica(String titulo) {
        super(titulo);
        setSize(500,400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
        
    }
    
    public static void main(String[] args){
        InterfazGrafica interfazGrafica = new InterfazGrafica("Sudoku");
        Panel panel = new Panel();
        interfazGrafica.getContentPane().add(panel.getPanel());
        
        interfazGrafica.setVisible(true);
    }
    
    

    public static boolean comprobarNombreObligatorio(String nombre) {
        return longitudStringMayorQueCero(nombre);
    }

    public static boolean comprobarApellidoObligatorio(String apellido) {

        return longitudStringMayorQueCero(apellido);

    }

    public static boolean longitudStringMayorQueCero(String cadena) {

        boolean valor = false;

        if (cadena.length() > 0) {
            valor = true;
        }

        return valor;
    }

    public static boolean comprobarJugadorNoRegistrado(Jugador jugador) {
        boolean valor = true;
        
        for (Jugador jugadorLista : listaJugadores) {
            if(jugador.equals(jugadorLista)){
                valor = false;
            }
        }
        return valor;
    }

    public static ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public static void setListaJugadores(ArrayList<Jugador> listaJugadores) {
        InterfazGrafica.listaJugadores = listaJugadores;
    }
    
    public static void añadirJugador(Jugador jugador) {
        if(jugador.getLocalidad().equals("Poblacion?")){
            jugador.setLocalidad("");
        }
        listaJugadores.add(jugador);
    }
    
    
    
}

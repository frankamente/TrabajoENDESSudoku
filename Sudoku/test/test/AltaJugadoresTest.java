package test;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import sudoku.InterfazGrafica;
import sudoku.Jugador;

public class AltaJugadoresTest {

    private InterfazGrafica interfazGrafica;

    private Jugador JUGADOR_NOMBRE_NULL = new Jugador("", "MARQUEZ");

    private Jugador JUGADOR_APELLIDO_NULL = new Jugador("FRAN", "");

    private Jugador JUGADOR_NOMBRE_APELLIDO_NULL = new Jugador("", "");

    private Jugador JUGADOR_EXIST = new Jugador("FRAN", "MARQUEZ");

    private Jugador JUGADOR_NOT_EXIST = new Jugador("CARLOS", "RUIZ");

    private Jugador[] LISTA_JUGADORES = {
        new Jugador("FRAN", "MARQUEZ"), new Jugador("ALFREDO", "MARTIN"),
        new Jugador("ROCIO", "GARCIA"), new Jugador("LAURA", "PEREZ")
    };

    @Before
    public void prepararClase() {
        interfazGrafica = new InterfazGrafica("Test");
        ArrayList<Jugador> listaJugadores = new ArrayList();
        for (Jugador jugador : LISTA_JUGADORES) {
            listaJugadores.add(jugador);
        }
        interfazGrafica.setListaJugadores(listaJugadores);
    }

    @Test
    public void shouldReturnFalseIfNombreIsNull() {

        String nombre = JUGADOR_NOMBRE_NULL.getNombre();
        boolean valor = interfazGrafica.comprobarNombreObligatorio(nombre);

        assertEquals(false, valor);
    }

    @Test
    public void shouldReturnTrueIfNombreIsNotNull() {

        String nombre = JUGADOR_EXIST.getNombre();
        boolean valor = interfazGrafica.comprobarNombreObligatorio(nombre);

        assertEquals(true, valor);
    }

    @Test
    public void shouldReturnTrueIfApellidoIsNull() {

        String apellido = JUGADOR_APELLIDO_NULL.getApellido();
        boolean valor = interfazGrafica.comprobarApellidoObligatorio(apellido);

        assertEquals(false, valor);
    }

    @Test
    public void shouldReturnTrueIfApellidoIsNotNull() {

        String apellido = JUGADOR_EXIST.getApellido();
        boolean valor = interfazGrafica.comprobarApellidoObligatorio(apellido);

        assertEquals(true, valor);
    }

    @Test
    public void shouldReturnFalseIfReceiveAJugadorThatExist() {

        Jugador jugador = JUGADOR_EXIST;

        boolean valor = interfazGrafica.comprobarJugadorNoRegistrado(jugador);

        assertEquals(false, valor);
    }

    @Test
    public void shouldReturnTrueIfReceiveAJugadorThatNotExist() {

        Jugador jugador = JUGADOR_NOT_EXIST;

        boolean valor = interfazGrafica.comprobarJugadorNoRegistrado(jugador);

        assertEquals(true, valor);
    }

    @Test
    public void shouldReturnTrueIfAddedCorrectlyAJugador() {

        Jugador jugador = JUGADOR_NOT_EXIST;

        int longitudInicial = interfazGrafica.getListaJugadores().size();
        interfazGrafica.añadirJugador(jugador);
        int longitudFinal = interfazGrafica.getListaJugadores().size();
        boolean valor = false;
        if (longitudInicial < longitudFinal) {
            valor = true;
        }

        assertEquals(true, valor);
    }
}

package sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    public Connection conexion;

    Driver driver = new oracle.jdbc.driver.OracleDriver();

    private final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";

    private final String USER = "system";

    private final String PASS = "system";

    public void conectar() {

        try {
            DriverManager.registerDriver(driver);
            conexion = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("ERROR CONECTANDO");
        }
    }

    public void cerrar() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error CERRANDO BD");
            }
        }
    }

    public static void pintarResultado(ResultSet rset) {
        try {
            ResultSetMetaData metaData = null;
            try {
                metaData = rset.getMetaData();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            int cantidadColumnas = metaData.getColumnCount();
            int[] longitudColumnas = new int[cantidadColumnas];

            for (int i = 1; i <= cantidadColumnas; i++) {
                if (metaData.getPrecision(i) == 0) {

                    longitudColumnas[i - 1] = 21;
                } else if (metaData.getColumnName(i).length() >= metaData.getPrecision(i)) {
                    longitudColumnas[i - 1] = metaData.getColumnName(i).length();
                } else {
                    longitudColumnas[i - 1] = metaData.getPrecision(i);
                }
                String nombreColumna = metaData.getColumnName(i);
                System.out.print(nombreColumna + espaciosNecesarios(nombreColumna.length(), longitudColumnas[i - 1]) );
            }
            System.out.println("");
            System.out.println("");
            while (rset.next()) {
                for (int i = 1; i <= cantidadColumnas; i++) {
                    int longitud = 0;
                    String cadena = rset.getString(i);
                    if (cadena != null) {
                        longitud = cadena.length();
                    } else {
                        longitud = 4;
                    }
                    System.out.print(cadena + espaciosNecesarios(longitud, longitudColumnas[i - 1]) );
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String espaciosNecesarios(int longitud, int longitudColumna) {
        String salida = "";
        for (int i = 0; i < (longitudColumna - longitud); i++) {
            salida += " ";
        }
        return salida;
    }

    public static String rellenarDatos(String[] datosFichero) {
        String salida = "";

        int longitud = datosFichero.length;
        for (int i = 0; i < longitud; i++) {
            try {
                double doble = Double.parseDouble(datosFichero[i]);
                salida += datosFichero[i];
            } catch (Exception e) {
                salida += "'" + datosFichero[i] + "'";
            }

            if (i != (longitud - 1)) {
                salida += ",";
            }
        }
        return salida;
    }

    public static void insertarDatosDeFichero(String nombreTabla, String rutaFichero){
        Conexion conexion = new Conexion();
        conexion.conectar();
        Connection conn = conexion.conexion;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(rutaFichero));
            String linea = "";
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datosFichero = linea.split(",");
                String sentencia = "INSERT INTO " + nombreTabla
                        + " VALUES (" + Conexion.rellenarDatos(datosFichero) + ")";
                PreparedStatement st = conn.prepareStatement(sentencia);
                int executeUpdate = st.executeUpdate();
                st.close();

            }
            conn.close();
            System.out.println("Todo insertado");
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado");
        } catch (IOException ex) {
            System.out.println("Error entrada Salida");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void crearTablas(String nombreTabla, String sentencia) throws SQLException {
        Conexion conexion = new Conexion();
        conexion.conectar();
        Connection conn = conexion.conexion;
        Statement createStatement = null;
        try {
            createStatement = conn.createStatement();
            createStatement.execute(sentencia);
        } catch (SQLException ex) {
            createStatement.execute("DROP TABLE " + nombreTabla);
            createStatement.executeQuery(sentencia);
        }
    }
}

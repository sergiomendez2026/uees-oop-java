/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ConexionSQLite {

    private static final String RUTA_DB =
            "data/proformas.db";

    private static final String URL =
            "jdbc:sqlite:" + RUTA_DB;

    public static Connection conectar()
            throws SQLException {
        
        try {

            Files.createDirectories(
                    Path.of("data")
            );

        } catch (IOException e) {
            
            throw new SQLException(
                    "No se pudo crear la carpeta data.",
                    e
            );
        }

        Connection conexion =
                DriverManager.getConnection(
                        URL
                );

        try (
                Statement sentencia =
                        conexion.createStatement()
        ) {

            sentencia.execute(
                    "PRAGMA foreign_keys = ON"
            );

            try (
                    ResultSet resultado =
                            sentencia.executeQuery(
                                    "PRAGMA foreign_keys"
                            )
            ) {
                
                if (
                        !resultado.next()
                        || resultado.getInt(1) != 1
                ) {

                    conexion.close();

                    throw new SQLException(
                            "No se pudieron activar las claves foraneas de SQLite."
                    );
                }
            }
        }
        
        return conexion;
    }

    public static void crearTablas()
            throws SQLException {

        String sqlProductos = """
                CREATE TABLE IF NOT EXISTS productos (
                     codigo TEXT PRIMARY KEY,
                     nombre TEXT NOT NULL,
                     precio REAL NOT NULL CHECK (precio >= 0),
                     stock INTEGER NOT NULL CHECK (stock >= 0),
                     tipo TEXT NOT NULL,
                     peso REAL,
                     ubicacion_almacen TEXT,
                     tamano_mb REAL,
                     url_descarga TEXT
                )
                """;

        String sqlClientes = """
                CREATE TABLE IF NOT EXISTS clientes (
                     email TEXT PRIMARY KEY,
                     nombre TEXT NOT NULL,
                     ciudad TEXT NOT NULL,
                     tipo TEXT NOT NULL
                )
                """;
        
        String sqlProformas = """
                CREATE TABLE IF NOT EXISTS proformas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    cliente_email TEXT NOT NULL,
                    fecha TEXT NOT NULL,
                    subtotal REAL NOT NULL CHECK (subtotal >= 0),
                    descuento REAL NOT NULL CHECK (descuento >= 0),
                    total REAL NOT NULL CHECK (total >= 0),
                    FOREIGN KEY (cliente_email)
                        REFERENCES clientes(email)
                )
                """;
        
        String sqlDetalleProforma = """
                CREATE TABLE IF NOT EXISTS detalle_proforma (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    proforma_id INTEGER NOT NULL,
                    producto_codigo TEXT NOT NULL,
                    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
                    precio_unitario REAL NOT NULL CHECK (precio_unitario >= 0),
                    subtotal REAL NOT NULL CHECK (subtotal >= 0),
                    FOREIGN KEY (proforma_id)
                        REFERENCES proformas(id),
                    FOREIGN KEY (producto_codigo)
                        REFERENCES productos(codigo)
                )
                """;

        try (
                Connection conexion =
                        conectar();

                Statement sentencia =
                        conexion.createStatement()
        ) {

            sentencia.execute(
                    sqlProductos
            );

            sentencia.execute(
                    sqlClientes
            );
            
            sentencia.execute(
                    sqlProformas
            );
            
            sentencia.execute(
                    sqlDetalleProforma
            );
        }
    }
}
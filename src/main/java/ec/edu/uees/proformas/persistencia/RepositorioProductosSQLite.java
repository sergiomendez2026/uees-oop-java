/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.persistencia;

import ec.edu.uees.proformas.modelo.Producto;
import ec.edu.uees.proformas.modelo.ProductoDigital;
import ec.edu.uees.proformas.modelo.ProductoFisico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepositorioProductosSQLite {

    public void guardar(
            String codigo,
            Producto producto
    ) throws SQLException {

        String sql = """
                INSERT INTO productos (
                    codigo,
                    nombre,
                    precio,
                    stock,
                    tipo,
                    peso,
                    ubicacion_almacen,
                    tamano_mb,
                    url_descarga
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conexion =
                        ConexionSQLite.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setString(
                    1,
                    codigo
            );

            sentencia.setString(
                    2,
                    producto.getNombre()
            );

            sentencia.setDouble(
                    3,
                    producto.getPrecio()
            );

            sentencia.setInt(
                    4,
                    producto.getStock()
            );

            if (producto instanceof ProductoFisico fisico) {

                sentencia.setString(
                        5,
                        "FISICO"
                );

                sentencia.setDouble(
                        6,
                        fisico.getPeso()
                );

                sentencia.setString(
                        7,
                        fisico.getUbicacionAlmacen()
                );

                sentencia.setNull(
                        8,
                        Types.REAL
                );

                sentencia.setNull(
                        9,
                        Types.VARCHAR
                );

            } else if (
                    producto instanceof ProductoDigital digital
            ) {

                sentencia.setString(
                        5,
                        "DIGITAL"
                );

                sentencia.setNull(
                        6,
                        Types.REAL
                );

                sentencia.setNull(
                        7,
                        Types.VARCHAR
                );

                sentencia.setDouble(
                        8,
                        digital.getTamanoMB()
                );

                sentencia.setString(
                        9,
                        digital.getUrlDescarga()
                );

            } else {

                sentencia.setString(
                        5,
                        "GENERAL"
                );

                sentencia.setNull(
                        6,
                        Types.REAL
                );

                sentencia.setNull(
                        7,
                        Types.VARCHAR
                );

                sentencia.setNull(
                        8,
                        Types.REAL
                );

                sentencia.setNull(
                        9,
                        Types.VARCHAR
                );
            }

            sentencia.executeUpdate();
        }
    }

    public Producto buscarPorCodigo(
            String codigo
    ) throws SQLException {

        String sql = """
                SELECT *
                FROM productos
                WHERE codigo = ?
                """;

        try (
                Connection conexion =
                        ConexionSQLite.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setString(
                    1,
                    codigo
            );

            try (
                    ResultSet resultado =
                            sentencia.executeQuery()
            ) {

                if (resultado.next()) {
                    return crearProducto(
                            resultado
                    );
                }

                return null;
            }
        }
    }

    public List<Producto> listar()
            throws SQLException {

        String sql = """
                SELECT *
                FROM productos
                ORDER BY codigo
                """;

        List<Producto> productos =
                new ArrayList<>();

        try (
                Connection conexion =
                        ConexionSQLite.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql);

                ResultSet resultado =
                        sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                Producto producto =
                        crearProducto(
                                resultado
                        );

                productos.add(
                        producto
                );
            }
        }

        return productos;
    }

    private Producto crearProducto(
            ResultSet resultado
    ) throws SQLException {

        String tipo =
                resultado.getString(
                        "tipo"
                );

        String nombre =
                resultado.getString(
                        "nombre"
                );

        double precio =
                resultado.getDouble(
                        "precio"
                );

        int stock =
                resultado.getInt(
                        "stock"
                );

        if ("FISICO".equals(tipo)) {

            return new ProductoFisico(
                    nombre,
                    precio,
                    stock,
                    resultado.getDouble(
                            "peso"
                    ),
                    resultado.getString(
                            "ubicacion_almacen"
                    )
            );
        }

        if ("DIGITAL".equals(tipo)) {

            return new ProductoDigital(
                    nombre,
                    precio,
                    stock,
                    resultado.getDouble(
                            "tamano_mb"
                    ),
                    resultado.getString(
                            "url_descarga"
                    )
            );
        }

        return new Producto(
                nombre,
                precio,
                stock
        );
    }
    
    public boolean actualizar(
            String codigo,
            Producto producto
            
    ) throws SQLException {
        
        String sql = """
                UPDATE productos
                SET nombre = ?,
                    precio = ?,
                    stock = ?,
                    tipo = ?,
                    peso = ?,
                    ubicacion_almacen = ?,
                    tamano_mb = ?,
                    url_descarga = ?
                WHERE codigo = ?
                """;
        
        try (
                Connection conexion =
                        ConexionSQLite.conectar();
                
                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {
            sentencia.setString(
                    1,
                    producto.getNombre()
            );

            sentencia.setDouble(
                    2,
                    producto.getPrecio()
            );

            sentencia.setInt(
                    3,
                    producto.getStock()
            );

            if (producto instanceof ProductoFisico fisico) {
                
                sentencia.setString(
                        4,
                        "FISICO"
                );

                sentencia.setDouble(
                        5,
                        fisico.getPeso()
                );

                sentencia.setString(
                        6,
                        fisico.getUbicacionAlmacen()
                );

                sentencia.setNull(
                        7,
                        Types.REAL
                );

                sentencia.setNull(
                        8,
                        Types.VARCHAR
                );

            } else if (
                    producto instanceof ProductoDigital digital
            ) {

                sentencia.setString(
                        4,
                        "DIGITAL"
                );

                sentencia.setNull(
                        5,
                        Types.REAL
                );

                sentencia.setNull(
                        6,
                        Types.VARCHAR
                );

                sentencia.setDouble(
                        7,
                        digital.getTamanoMB()
                );

                sentencia.setString(
                        8,
                        digital.getUrlDescarga()
                );

            } else {

                sentencia.setString(
                        4,
                        "GENERAL"
                );

                sentencia.setNull(
                        5,
                        Types.REAL
                );

                sentencia.setNull(
                        6,
                        Types.VARCHAR
                );

                sentencia.setNull(
                        7,
                        Types.REAL
                );

                sentencia.setNull(
                        8,
                        Types.VARCHAR
                );
            }

            sentencia.setString(
                    9,
                    codigo
            );

            int filasActualizadas =
                    sentencia.executeUpdate();
            
            return filasActualizadas > 0;
        }
    }
    
    public boolean eliminar(
            String codigo
    ) throws SQLException {

        String sql = """
                DELETE FROM productos
                WHERE codigo = ?
                """;
        
        try (
                Connection conexion =
                        ConexionSQLite.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setString(
                    1,
                    codigo
            );

            int filasEliminadas =
                    sentencia.executeUpdate();

            return filasEliminadas > 0;
        }
    }
    public Map<String, Producto> listarPorCodigo()
            throws SQLException {
        String sql = """
                SELECT *
                FROM productos
                ORDER BY codigo
                """;
        
        Map<String, Producto> productos =
                new LinkedHashMap<>();
        
        try (
                Connection conexion =
                        ConexionSQLite.conectar();
                
                PreparedStatement sentencia =
                        conexion.prepareStatement(sql);
                
                ResultSet resultado =
                        sentencia.executeQuery()
        ) {
            
            while (resultado.next()) {
                
                String codigo =
                        resultado.getString(
                                "codigo"
                        );
                
                Producto producto =
                        crearProducto(
                                resultado
                        );
                
                productos.put(
                        codigo,
                        producto
                );
            }
        }
        
        return productos;
    }
}            
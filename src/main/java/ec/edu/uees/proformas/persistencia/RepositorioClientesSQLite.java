/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.persistencia;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.ClienteCorporativo;
import ec.edu.uees.proformas.modelo.ClienteMayorista;
import ec.edu.uees.proformas.modelo.ClienteMinorista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class RepositorioClientesSQLite {

    public void guardar(
            Cliente cliente
    ) throws SQLException {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        String sql = """
                INSERT INTO clientes (
                    email,
                    nombre,
                    ciudad,
                    tipo
                )
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conexion =
                        ConexionSQLite.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setString(
                    1,
                    cliente.getEmail()
            );

            sentencia.setString(
                    2,
                    cliente.getNombre()
            );

            sentencia.setString(
                    3,
                    cliente.getCiudad()
            );

            sentencia.setString(
                    4,
                    obtenerTipo(cliente)
            );

            sentencia.executeUpdate();
        }
    }

    public Cliente buscarPorEmail(
            String email
    ) throws SQLException {

        if (email == null || email.isBlank()) {
            return null;
        }

        String sql = """
                SELECT
                    email,
                    nombre,
                    ciudad,
                    tipo
                FROM clientes
                WHERE email = ?
                """;

        try (
                Connection conexion =
                        ConexionSQLite.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setString(
                    1,
                    email
            );

            try (
                    ResultSet resultado =
                            sentencia.executeQuery()
            ) {

                if (resultado.next()) {

                    return convertirCliente(
                            resultado
                    );
                }
            }
        }

        return null;
    }

    public List<Cliente> listar()
            throws SQLException {

        List<Cliente> clientes =
                new ArrayList<>();

        String sql = """
                SELECT
                    email,
                    nombre,
                    ciudad,
                    tipo
                FROM clientes
                ORDER BY nombre
                """;

        try (
                Connection conexion =
                        ConexionSQLite.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql);

                ResultSet resultado =
                        sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                clientes.add(
                        convertirCliente(
                                resultado
                        )
                );
            }
        }

        return clientes;
    }

    public boolean actualizar(
            String email,
            Cliente cliente
    ) throws SQLException {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "El email no puede estar vacio."
            );
        }

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        String sql = """
                UPDATE clientes
                SET
                    nombre = ?,
                    ciudad = ?,
                    tipo = ?
                WHERE email = ?
                """;

        try (
                Connection conexion =
                        ConexionSQLite.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setString(
                    1,
                    cliente.getNombre()
            );

            sentencia.setString(
                    2,
                    cliente.getCiudad()
            );

            sentencia.setString(
                    3,
                    obtenerTipo(cliente)
            );

            sentencia.setString(
                    4,
                    email
            );

            int filas =
                    sentencia.executeUpdate();

            return filas > 0;
        }
    }

    public boolean eliminar(
            String email
    ) throws SQLException {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "El email no puede estar vacio."
            );
        }

        String sql = """
                DELETE FROM clientes
                WHERE email = ?
                """;

        try (
                Connection conexion =
                        ConexionSQLite.conectar();

                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setString(
                    1,
                    email
            );

            int filas =
                    sentencia.executeUpdate();

            return filas > 0;
        }
    }

    private String obtenerTipo(
            Cliente cliente
    ) {

        if (cliente instanceof ClienteMayorista) {
            return "MAYORISTA";
        }

        if (cliente instanceof ClienteCorporativo) {
            return "CORPORATIVO";
        }

        if (cliente instanceof ClienteMinorista) {
            return "MINORISTA";
        }

        throw new IllegalArgumentException(
                "Tipo de cliente no reconocido."
        );
    }

    private Cliente convertirCliente(
            ResultSet resultado
    ) throws SQLException {

        String email =
                resultado.getString(
                        "email"
                );

        String nombre =
                resultado.getString(
                        "nombre"
                );

        String ciudad =
                resultado.getString(
                        "ciudad"
                );

        String tipo =
                resultado.getString(
                        "tipo"
                );

        return switch (tipo) {

            case "MAYORISTA" ->
                    new ClienteMayorista(
                            nombre,
                            email,
                            ciudad
                    );

            case "CORPORATIVO" ->
                    new ClienteCorporativo(
                            nombre,
                            email,
                            ciudad
                    );

            case "MINORISTA" ->
                    new ClienteMinorista(
                            nombre,
                            email,
                            ciudad
                    );

            default ->
                    throw new SQLException(
                            "Tipo de cliente desconocido: "
                            + tipo
                    );
        };
    }
}
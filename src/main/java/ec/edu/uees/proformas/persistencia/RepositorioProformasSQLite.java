package ec.edu.uees.proformas.persistencia;

import ec.edu.uees.proformas.modelo.ItemProforma;
import ec.edu.uees.proformas.modelo.Proforma;
import ec.edu.uees.proformas.modelo.ProformaResumen;
import ec.edu.uees.proformas.modelo.DetalleProformaResumen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioProformasSQLite {

    public int guardar(
            Proforma proforma
    ) throws SQLException {

        if (proforma == null) {

            throw new IllegalArgumentException(
                    "La proforma no puede ser nula."
            );
        }

        if (proforma.getItems().isEmpty()) {

            throw new IllegalArgumentException(
                    "La proforma debe tener al menos un producto."
            );
        }

        String sqlProforma = """
                INSERT INTO proformas (
                    cliente_email,
                    fecha,
                    subtotal,
                    descuento,
                    total
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        String sqlDetalle = """
                INSERT INTO detalle_proforma (
                    proforma_id,
                    producto_codigo,
                    cantidad,
                    precio_unitario,
                    subtotal
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        Connection conexion =
                ConexionSQLite.conectar();

        try {

            /*
             * La operacion completa se ejecutara
             * como una sola transaccion.
             */
            conexion.setAutoCommit(
                    false
            );

            double subtotal =
                    proforma.calcularSubtotal();

            double total =
                    proforma.calcularTotal();

            /*
             * Guardamos el valor monetario
             * descontado.
             */
            double descuento =
                    subtotal - total;

            int proformaId;

            try (
                    PreparedStatement sentenciaProforma =
                            conexion.prepareStatement(
                                    sqlProforma,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                sentenciaProforma.setString(
                        1,
                        proforma
                                .getCliente()
                                .getEmail()
                );

                sentenciaProforma.setString(
                        2,
                        LocalDate
                                .now()
                                .toString()
                );

                sentenciaProforma.setDouble(
                        3,
                        subtotal
                );

                sentenciaProforma.setDouble(
                        4,
                        descuento
                );

                sentenciaProforma.setDouble(
                        5,
                        total
                );

                sentenciaProforma.executeUpdate();

                try (
                        ResultSet claves =
                                sentenciaProforma
                                        .getGeneratedKeys()
                ) {

                    if (!claves.next()) {

                        throw new SQLException(
                                "No se pudo obtener el ID de la proforma."
                        );
                    }

                    proformaId =
                            claves.getInt(1);
                }
            }

            /*
             * Ahora insertamos cada producto
             * perteneciente a la proforma.
             */
            try (
                    PreparedStatement sentenciaDetalle =
                            conexion.prepareStatement(
                                    sqlDetalle
                            )
            ) {

                for (
                        ItemProforma item
                        : proforma.getItems()
                ) {

                    if (
                            item.getCodigoProducto()
                            == null
                            || item
                                    .getCodigoProducto()
                                    .isBlank()
                    ) {

                        throw new IllegalArgumentException(
                                "El item no tiene codigo de producto."
                        );
                    }

                    sentenciaDetalle.setInt(
                            1,
                            proformaId
                    );

                    sentenciaDetalle.setString(
                            2,
                            item.getCodigoProducto()
                    );

                    sentenciaDetalle.setInt(
                            3,
                            item.getCantidad()
                    );

                    sentenciaDetalle.setDouble(
                            4,
                            item
                                    .getProducto()
                                    .getPrecio()
                    );

                    sentenciaDetalle.setDouble(
                            5,
                            item.calcularSubtotal()
                    );

                    sentenciaDetalle.addBatch();
                }

                sentenciaDetalle.executeBatch();
            }

            conexion.commit();

            return proformaId;

        } catch (
                SQLException
                | RuntimeException error
        ) {

            try {

                conexion.rollback();

            } catch (SQLException errorRollback) {

                error.addSuppressed(
                        errorRollback
                );
            }

            throw error;

        } finally {

            try {

                conexion.setAutoCommit(
                        true
                );

            } finally {

                conexion.close();
            }
        }
    }
    
        public List<ProformaResumen> listarProformas()
                throws SQLException {
            
            String sql = """
                SELECT
                    id,
                    cliente_email,
                    fecha,
                    subtotal,
                    descuento,
                    total
                FROM proformas
                ORDER BY id DESC
                """;

            List<ProformaResumen> proformas =
                    new ArrayList<>();

            try (
                    Connection conexion =
                            ConexionSQLite.conectar();

                    PreparedStatement sentencia =
                            conexion.prepareStatement(
                                    sql
                            );

                    ResultSet resultado =
                            sentencia.executeQuery()
            ) {

                while (resultado.next()) {

                    ProformaResumen proforma =
                            new ProformaResumen(
                                    resultado.getInt(
                                            "id"
                                    ),
                                    resultado.getString(
                                            "cliente_email"
                                    ),
                                    resultado.getString(
                                            "fecha"
                                    ),
                                    resultado.getDouble(
                                            "subtotal"
                                    ),
                                    resultado.getDouble(
                                            "descuento"
                                    ),
                                    resultado.getDouble(
                                            "total"
                                    )
                            );
                    
                    proformas.add(
                            proforma
                    );
                }
            }

        return proformas;
    }
    
    public List<DetalleProformaResumen> listarDetalle(
            int proformaId
    ) throws SQLException {

        if (proformaId <= 0) {
            throw new IllegalArgumentException(
                    "El ID de la proforma no es valido."
            );
        }

        String sql = """
                SELECT
                    d.producto_codigo,
                    p.nombre AS nombre_producto,
                    d.cantidad,
                    d.precio_unitario,
                    d.subtotal
                FROM detalle_proforma d
                LEFT JOIN productos p
                    ON p.codigo = d.producto_codigo
                WHERE d.proforma_id = ?
                ORDER BY d.id
                """;

        List<DetalleProformaResumen> detalles =
                new ArrayList<>();

        try (
                Connection conexion =
                        ConexionSQLite.conectar();
                
                PreparedStatement sentencia =
                        conexion.prepareStatement(sql)
        ) {

            sentencia.setInt(
                    1,
                    proformaId
            );

            try (
                    ResultSet resultado =
                            sentencia.executeQuery()
            ) {

                while (resultado.next()) {
                    
                    String nombreProducto =
                            resultado.getString(
                                    "nombre_producto"
                            );

                    if (nombreProducto == null) {
                        nombreProducto =
                                "Producto no disponible";
                    }

                    DetalleProformaResumen detalle =
                            new DetalleProformaResumen(
                                    resultado.getString(
                                            "producto_codigo"
                                    ),
                                    nombreProducto,
                                    resultado.getInt(
                                            "cantidad"
                                    ),
                                    resultado.getDouble(
                                            "precio_unitario"
                                    ),
                                    resultado.getDouble(
                                            "subtotal"
                                    )
                            );

                    detalles.add(
                            detalle
                    );
                }
            }
        }

        return detalles;
    }
}
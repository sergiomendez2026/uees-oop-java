/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.servicio;

import ec.edu.uees.proformas.modelo.Producto;
import ec.edu.uees.proformas.persistencia.RepositorioProductosSQLite;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CatalogoProductos {

    private final List<Producto> productos;
    private final Map<String, Producto> productosPorCodigo;
    private final Set<String> codigosRegistrados;
    private final RepositorioProductosSQLite repositorio;

    public CatalogoProductos(
            RepositorioProductosSQLite repositorio
    ) throws SQLException {

        this.productos =
                new ArrayList<>();

        this.productosPorCodigo =
                new HashMap<>();

        this.codigosRegistrados =
                new HashSet<>();

        this.repositorio =
                repositorio;

        cargarDesdeBaseDeDatos();
    }

    private void cargarDesdeBaseDeDatos()
            throws SQLException {

        Map<String, Producto> productosPersistidos =
                repositorio.listarPorCodigo();

        for (
                Map.Entry<String, Producto> entrada
                : productosPersistidos.entrySet()
        ) {

            String codigo =
                    entrada.getKey();

            Producto producto =
                    entrada.getValue();

            productos.add(
                    producto
            );

            productosPorCodigo.put(
                    codigo,
                    producto
            );

            codigosRegistrados.add(
                    codigo
            );
        }
    }

    public void agregarProducto(
            String codigo,
            Producto producto
    ) throws SQLException {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El codigo no puede estar vacio."
            );
        }

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        if (codigosRegistrados.contains(codigo)) {
            throw new IllegalArgumentException(
                    "Ya existe un producto con el codigo "
                    + codigo
            );
        }

        repositorio.guardar(
                codigo,
                producto
        );

        productos.add(
                producto
        );

        productosPorCodigo.put(
                codigo,
                producto
        );

        codigosRegistrados.add(
                codigo
        );
    }

    public Producto buscarProducto(
            String codigo
    ) {

        return productosPorCodigo.get(
                codigo
        );
    }

    public List<Producto> listarProductos() {

        return new ArrayList<>(
                productos
        );
    }

    public boolean actualizarProducto(
            String codigo,
            Producto producto
    ) throws SQLException {

        if (!codigosRegistrados.contains(codigo)) {
            return false;
        }

        boolean actualizado =
                repositorio.actualizar(
                        codigo,
                        producto
                );

        if (!actualizado) {
            return false;
        }

        Producto productoAnterior =
                productosPorCodigo.get(
                        codigo
                );

        int indice =
                productos.indexOf(
                        productoAnterior
                );

        if (indice >= 0) {
            productos.set(
                    indice,
                    producto
            );
        }

        productosPorCodigo.put(
                codigo,
                producto
        );

        return true;
    }

    public boolean eliminarProducto(
            String codigo
    ) throws SQLException {

        if (!codigosRegistrados.contains(codigo)) {
            return false;
        }

        boolean eliminado =
                repositorio.eliminar(
                        codigo
                );

        if (!eliminado) {
            return false;
        }

        Producto producto =
                productosPorCodigo.remove(
                        codigo
                );

        productos.remove(
                producto
        );

        codigosRegistrados.remove(
                codigo
        );

        return true;
    }
}
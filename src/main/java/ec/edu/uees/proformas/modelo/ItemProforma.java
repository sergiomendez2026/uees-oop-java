/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.modelo;

public class ItemProforma {

    private String codigoProducto;
    private Producto producto;
    private int cantidad;

    /*
     * Constructor anterior.
     */
    public ItemProforma(
            Producto producto,
            int cantidad
    ) {

        validar(
                producto,
                cantidad
        );

        this.producto = producto;
        this.cantidad = cantidad;
    }

    /*
     * Nuevo constructor
     */
    public ItemProforma(
            String codigoProducto,
            Producto producto,
            int cantidad
    ) {

        if (
                codigoProducto == null
                || codigoProducto.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "El codigo del producto no puede estar vacio."
            );
        }

        validar(
                producto,
                cantidad
        );

        this.codigoProducto =
                codigoProducto;

        this.producto =
                producto;

        this.cantidad =
                cantidad;
    }

    private void validar(
            Producto producto,
            int cantidad
    ) {

        if (producto == null) {

            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        if (cantidad <= 0) {

            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero."
            );
        }
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {

        return producto.getPrecio()
                * cantidad;
    }
}
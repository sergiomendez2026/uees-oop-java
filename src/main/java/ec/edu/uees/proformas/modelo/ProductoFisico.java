/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.modelo;

/**
 *
 * @author ASUS
 */

public class ProductoFisico extends Producto {

    private double peso;
    private String ubicacionAlmacen;

    public ProductoFisico(
            String nombre,
            double precio,
            int stock,
            double peso,
            String ubicacionAlmacen) {

        super(nombre, precio, stock);
        setPeso(peso);
        setUbicacionAlmacen(ubicacionAlmacen);
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException(
                    "El peso no puede ser negativo."
            );
        }

        this.peso = peso;
    }

    public String getUbicacionAlmacen() {
        return ubicacionAlmacen;
    }

    public void setUbicacionAlmacen(String ubicacionAlmacen) {
        if (ubicacionAlmacen == null || ubicacionAlmacen.isBlank()) {
            throw new IllegalArgumentException(
                    "La ubicación del almacén no puede estar vacía."
            );
        }

        this.ubicacionAlmacen = ubicacionAlmacen;
    }
}
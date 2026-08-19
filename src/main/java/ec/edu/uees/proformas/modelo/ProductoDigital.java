/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.modelo;

/**
 *
 * @author ASUS
 */

public class ProductoDigital extends Producto {

    private double tamanoMB;
    private String urlDescarga;

    public ProductoDigital(
            String nombre,
            double precio,
            int stock,
            double tamanoMB,
            String urlDescarga) {

        super(nombre, precio, stock);
        setTamanoMB(tamanoMB);
        setUrlDescarga(urlDescarga);
    }

    public double getTamanoMB() {
        return tamanoMB;
    }

    public void setTamanoMB(double tamanoMB) {
        if (tamanoMB < 0) {
            throw new IllegalArgumentException(
                    "El tamaño no puede ser negativo."
            );
        }

        this.tamanoMB = tamanoMB;
    }

    public String getUrlDescarga() {
        return urlDescarga;
    }

    public void setUrlDescarga(String urlDescarga) {
        if (urlDescarga == null || urlDescarga.isBlank()) {
            throw new IllegalArgumentException(
                    "La URL de descarga no puede estar vacía."
            );
        }

        this.urlDescarga = urlDescarga;
    }
}
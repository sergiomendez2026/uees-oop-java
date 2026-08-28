/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.modelo;

public class ClienteMayorista extends Cliente {

    private final double descuento = 0.20;

    public ClienteMayorista(String nombre, String email, String ciudad) {
        super(nombre, email, ciudad);
    }

    @Override
    public double calcularDescuento() {
        return descuento;
    }
}
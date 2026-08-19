/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.modelo;

/**
 *
 * @author ASUS
 */
public class Cliente {

    private String nombre;
    private String email;
    private String ciudad;

    public Cliente(String nombre, String email, String ciudad) {
        setNombre(nombre);
        setEmail(email);
        setCiudad(ciudad);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío."
            );
        }

        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "El email no puede estar vacío."
            );
        }

        this.email = email;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException(
                    "La ciudad no puede estar vacía."
            );
        }

        this.ciudad = ciudad;
    }

    public void comprar(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        if (producto.hayStock()) {
            producto.setStock(producto.getStock() - 1);

            System.out.println(
                    nombre + " compro " + producto.getNombre()
            );
        } else {
            System.out.println(
                    "No hay stock disponible de " + producto.getNombre()
            );
        }
    }
}
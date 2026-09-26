package ec.edu.uees.proformas.tda;

import ec.edu.uees.proformas.modelo.Cliente;

public class ColaClientes {

    private final Cliente[] elementos;
    private int frente;
    private int cantidad;

    public ColaClientes(int capacidad) {

        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero."
            );
        }

        elementos = new Cliente[capacidad];
        frente = 0;
        cantidad = 0;
    }

    public void encolar(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        if (cantidad == elementos.length) {
            throw new IllegalStateException(
                    "La cola está llena."
            );
        }

        int posicionFinal =
                (frente + cantidad) % elementos.length;

        elementos[posicionFinal] = cliente;
        cantidad++;
    }

    public Cliente desencolar() {

        if (estaVacia()) {
            throw new IllegalStateException(
                    "La cola está vacía."
            );
        }

        Cliente cliente = elementos[frente];

        elementos[frente] = null;

        frente =
                (frente + 1) % elementos.length;

        cantidad--;

        return cliente;
    }

    public Cliente consultarSiguiente() {

        if (estaVacia()) {
            throw new IllegalStateException(
                    "La cola está vacía."
            );
        }

        return elementos[frente];
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public int getCantidad() {
        return cantidad;
    }
}
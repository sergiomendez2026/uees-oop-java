/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.modelo;

/**
 *
 * @author ASUS
 */
import java.util.ArrayList;
import java.util.List;

public class Proforma {

    private Cliente cliente;
    private List<ItemProforma> items;

    public Proforma(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    public void agregarItem(ItemProforma item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "El item no puede ser nulo."
            );
        }

        items.add(item);
    }

    public double calcularTotal() {
        double subtotal = 0;

        for (ItemProforma item : items) {
            subtotal += item.calcularSubtotal();
        }

        double descuento = cliente.calcularDescuento();

        return subtotal * (1 - descuento);
    }
}
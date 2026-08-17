/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.uees.proformas;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.Producto;
/**
 *
 * @author ASUS
 */
public class Proformas {

    public static void main(String[] args) {

        Producto producto = new Producto(
                "Laptop",
                850.00,
                2
        );

        Cliente cliente = new Cliente(
                "Sergio",
                "sergio@email.com",
                "Guayaquil"
        );

        System.out.println(
                "Stock inicial: " + producto.getStock()
        );

        cliente.comprar(producto);

        System.out.println(
                "Stock final: " + producto.getStock()
        );
    }
}
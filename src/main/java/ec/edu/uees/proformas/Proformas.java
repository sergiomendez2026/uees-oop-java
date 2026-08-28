/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.uees.proformas;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.ClienteMayorista;
import ec.edu.uees.proformas.modelo.ClienteMinorista;
import ec.edu.uees.proformas.modelo.ItemProforma;
import ec.edu.uees.proformas.modelo.ProductoDigital;
import ec.edu.uees.proformas.modelo.ProductoFisico;
import ec.edu.uees.proformas.modelo.Proforma;

public class Proformas {

    public static void main(String[] args) {

        Cliente clienteMayorista = new ClienteMayorista(
        "Sergio",
        "sergio@email.com",
        "Guayaquil"
        );

        Cliente clienteMinorista = new ClienteMinorista(
        "Ana",
        "ana@email.com",
        "Quito"
        );

        System.out.println(
                "Descuento cliente mayorista: "
                + clienteMayorista.calcularDescuento() * 100
                + "%"
        );

        System.out.println(
                "Descuento cliente minorista: "
                + clienteMinorista.calcularDescuento() * 100
                + "%"
        );

        System.out.println();

        ProductoFisico laptop = new ProductoFisico(
                "Laptop",
                850.00,
                2,
                2.1,
                "Bodega A"
        );

        ProductoDigital curso = new ProductoDigital(
                "Curso Java",
                120.00,
                100,
                1500.0,
                "https://ejemplo.com/curso"
        );

        ItemProforma itemLaptopMayorista = new ItemProforma(
                laptop,
                1
        );

        ItemProforma itemCursoMayorista = new ItemProforma(
                curso,
                2
        );

        ItemProforma itemLaptopMinorista = new ItemProforma(
                laptop,
                1
        );

        ItemProforma itemCursoMinorista = new ItemProforma(
                curso,
                2
        );

        Proforma proformaMayorista = new Proforma(clienteMayorista);

        proformaMayorista.agregarItem(itemLaptopMayorista);
        proformaMayorista.agregarItem(itemCursoMayorista);

        Proforma proformaMinorista = new Proforma(clienteMinorista);

        proformaMinorista.agregarItem(itemLaptopMinorista);
        proformaMinorista.agregarItem(itemCursoMinorista);

        System.out.println("Producto fisico: " + laptop.getNombre());
        System.out.println("Peso: " + laptop.getPeso() + " kg");
        System.out.println(
                "Ubicacion: " + laptop.getUbicacionAlmacen()
        );

        System.out.println();

        System.out.println(
                "Producto digital: " + curso.getNombre()
        );
        System.out.println(
                "Tamano: " + curso.getTamanoMB() + " MB"
        );
        System.out.println(
                "URL: " + curso.getUrlDescarga()
        );

        System.out.println();

        System.out.println("=== PROFORMA CLIENTE MAYORISTA ===");

        System.out.println(
                "Cliente: " + clienteMayorista.getNombre()
        );

        System.out.println(
                "Descuento: "
                + clienteMayorista.calcularDescuento() * 100
                + "%"
        );

        System.out.println(
                "Subtotal Laptop: $"
                + itemLaptopMayorista.calcularSubtotal()
        );

        System.out.println(
                "Subtotal Curso: $"
                + itemCursoMayorista.calcularSubtotal()
        );

        System.out.println(
                "Total Proforma: $"
                + proformaMayorista.calcularTotal()
        );

        System.out.println();

        System.out.println("=== PROFORMA CLIENTE MINORISTA ===");

        System.out.println(
                "Cliente: " + clienteMinorista.getNombre()
        );

        System.out.println(
                "Descuento: "
                + clienteMinorista.calcularDescuento() * 100
                + "%"
        );

        System.out.println(
                "Subtotal Laptop: $"
                + itemLaptopMinorista.calcularSubtotal()
        );

        System.out.println(
                "Subtotal Curso: $"
                + itemCursoMinorista.calcularSubtotal()
        );

        System.out.println(
                "Total Proforma: $"
                + proformaMinorista.calcularTotal()
        );
    }
}

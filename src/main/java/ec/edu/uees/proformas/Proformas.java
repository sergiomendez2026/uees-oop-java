/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.uees.proformas;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.ItemProforma;
import ec.edu.uees.proformas.modelo.ProductoDigital;
import ec.edu.uees.proformas.modelo.ProductoFisico;
import ec.edu.uees.proformas.modelo.Proforma;

public class Proformas {

    public static void main(String[] args) {

        Cliente cliente = new Cliente(
                "Sergio",
                "sergio@email.com",
                "Guayaquil"
        );

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

        ItemProforma itemLaptop = new ItemProforma(
                laptop,
                1
        );

        ItemProforma itemCurso = new ItemProforma(
                curso,
                2
        );

        Proforma proforma = new Proforma(cliente);

        proforma.agregarItem(itemLaptop);
        proforma.agregarItem(itemCurso);

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

        System.out.println(
                "Subtotal Laptop: $" + itemLaptop.calcularSubtotal()
        );

        System.out.println(
                "Subtotal Curso: $" + itemCurso.calcularSubtotal()
        );

        System.out.println(
                "Total Proforma: $" + proforma.calcularTotal()
        );
    }
}
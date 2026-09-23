/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.uees.proformas;
 
import ec.edu.uees.proformas.excepciones.StockInsuficienteException;
import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.ClienteMayorista;
import ec.edu.uees.proformas.modelo.ClienteMinorista;
import ec.edu.uees.proformas.modelo.ClienteCorporativo;
import ec.edu.uees.proformas.modelo.ItemProforma;
import ec.edu.uees.proformas.modelo.Producto;
import ec.edu.uees.proformas.modelo.ProductoDigital;
import ec.edu.uees.proformas.modelo.ProductoFisico;
import ec.edu.uees.proformas.modelo.Proforma;
import ec.edu.uees.proformas.persistencia.ConexionSQLite;
import java.sql.SQLException;
import ec.edu.uees.proformas.persistencia.RepositorioProductosSQLite;
import java.util.List;
import ec.edu.uees.proformas.servicio.CatalogoProductos;

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
        
        Cliente clienteCorporativo = new ClienteCorporativo(
        "Empresa ABC",
        "contacto@empresa.com",
        "Guayaquil"
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
        System.out.println("=== PRUEBA OCP - SEMANA 4 ===");
        
        System.out.println(
                "Cliente: "
                + clienteCorporativo.getNombre()
        );
        
        System.out.println(
                "Descuento corporativo: "
                + clienteCorporativo.calcularDescuento() * 100
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
        
        Proforma proformaCorporativa = new Proforma(
                clienteCorporativo
        );
        
        proformaCorporativa.agregarItem(
                new ItemProforma(laptop, 1)
        );
        
        System.out.println(
                "Total proforma corporativa: $"
                + proformaCorporativa.calcularTotal()
        );
        
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
        
        System.out.println();
        System.out.println(
                "=== PRUEBA DE EXCEPCIONES - SEMANA 4 ==="
        );
        
        Producto productoPrueba = new Producto(
                "Monitor",
                250.00,
                3
        );
        
        try {

            System.out.println(
                    "Stock inicial: "
                    + productoPrueba.getStock()
            );

            productoPrueba.venderUnidades(0);

            System.out.println(
                    "Venta realizada."
            );

            System.out.println(
                    "Stock restante: "
                    + productoPrueba.getStock()
            );

        } catch (StockInsuficienteException e) {

            System.out.println(
                    "Error de stock: "
                    + e.getMessage()
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Dato invalido: "
                    + e.getMessage()
            );

        } finally {

            System.out.println(
                    "Operacion de venta finalizada."
            );

            System.out.println(
                    "Stock final: "
                    + productoPrueba.getStock()
            );
        }

        System.out.println();
        System.out.println(
                "=== PRUEBA SQLITE - SEMANA 5 ==="
        );
        
        try {
            
            ConexionSQLite.crearTablas();
            
            System.out.println(
                    "Base de datos SQLite creada correctamente."
            );
            
            System.out.println(
                    "Tabla productos disponible."
            );
            
            RepositorioProductosSQLite repositorio =
                    new RepositorioProductosSQLite();
            
            Producto productoEncontrado =
                    repositorio.buscarPorCodigo(
                            "P001"
                    );
            
            if (productoEncontrado != null) {
                
                System.out.println(
                        "Producto encontrado: "
                        + productoEncontrado.getNombre()
                );
                
                System.out.println(
                        "Precio: $"
                        + productoEncontrado.getPrecio()
                );
                
                System.out.println(
                        "Stock: "
                        + productoEncontrado.getStock()
                );
            
            } else {
                
                System.out.println(
                        "Producto P001 no encontrado."
                );
            }
            
            System.out.println();
            System.out.println(
                    "=== LISTADO DE PRODUCTOS SQLITE ==="
            );
            
                                    List<Producto> productosSQLite =
                    repositorio.listar();

            for (Producto producto : productosSQLite) {

                System.out.println(
                        producto.getNombre()
                        + " - $"
                        + producto.getPrecio()
                        + " - Stock: "
                        + producto.getStock()
                );
            }

            System.out.println();
            System.out.println(
                    "=== ACTUALIZAR PRODUCTO SQLITE ==="
            );

            ProductoFisico laptopActualizada =
                    new ProductoFisico(
                            "Laptop Pro",
                            900.00,
                            5,
                            2.3,
                            "Bodega B"
                    );

            boolean actualizado =
                    repositorio.actualizar(
                            "P001",
                            laptopActualizada
                    );

            if (actualizado) {

                System.out.println(
                        "Producto P001 actualizado correctamente."
                );

            } else {

                System.out.println(
                        "Producto P001 no existe."
                );
            }

            Producto productoActualizado =
                    repositorio.buscarPorCodigo(
                            "P001"
                    );

            if (productoActualizado != null) {

                System.out.println(
                        "Nombre actualizado: "
                        + productoActualizado.getNombre()
                );

                System.out.println(
                        "Precio actualizado: $"
                        + productoActualizado.getPrecio()
                );

                System.out.println(
                        "Stock actualizado: "
                        + productoActualizado.getStock()
                );
            }

            System.out.println();
            System.out.println(
                    "=== ELIMINAR PRODUCTO SQLITE ==="
            );

            boolean eliminado =
                    repositorio.eliminar(
                            "P002"
                    );

            if (eliminado) {

                System.out.println(
                        "Producto P002 eliminado correctamente."
                );

            } else {

                System.out.println(
                        "Producto P002 no existe."
                );
            }

            Producto productoEliminado =
                    repositorio.buscarPorCodigo(
                            "P002"
                    );

            if (productoEliminado == null) {

                System.out.println(
                        "Verificacion: P002 ya no existe."
                );

            } else {

                System.out.println(
                        "Error: P002 continua registrado."
                );
            }

            // -----------------------------------------
            // PRUEBA INTEGRADA DE CATALOGO - SEMANA 5
            // -----------------------------------------

            System.out.println();
            System.out.println(
                    "=== CATALOGO INTEGRADO - SEMANA 5 ==="
            );

            CatalogoProductos catalogo =
                    new CatalogoProductos(
                            repositorio
                    );

            System.out.println(
                    "Productos cargados desde SQLite: "
                    + catalogo.listarProductos().size()
            );

            Producto productoCatalogo =
                    new Producto(
                            "Monitor",
                            250.00,
                            3
                    );

            if (
                    catalogo.buscarProducto(
                            "P003"
                    ) == null
            ) {

                catalogo.agregarProducto(
                        "P003",
                        productoCatalogo
                );

                System.out.println(
                        "P003 agregado mediante CatalogoProductos."
                );
            }

            // Prueba de duplicado con HashSet
            try {

                catalogo.agregarProducto(
                        "P003",
                        productoCatalogo
                );

            } catch (
                    IllegalArgumentException errorDuplicado
            ) {

                System.out.println(
                        "Duplicado rechazado: "
                        + errorDuplicado.getMessage()
                );
            }

            // Buscar mediante HashMap
            Producto productoBuscado =
                    catalogo.buscarProducto(
                            "P003"
                    );

            if (productoBuscado != null) {

                System.out.println(
                        "Busqueda P003: "
                        + productoBuscado.getNombre()
                );
            }

            // Listar mediante ArrayList
            System.out.println();
            System.out.println(
                    "=== CATALOGO EN MEMORIA ==="
            );

            for (
                    Producto producto
                    : catalogo.listarProductos()
            ) {

                System.out.println(
                        producto.getNombre()
                        + " - $"
                        + producto.getPrecio()
                        + " - Stock: "
                        + producto.getStock()
                );
            }

            // Actualizar mediante CatalogoProductos
            Producto monitorActualizado =
                    new Producto(
                            "Monitor Gamer",
                            300.00,
                            8
                    );

            boolean catalogoActualizado =
                    catalogo.actualizarProducto(
                            "P003",
                            monitorActualizado
                    );

            if (catalogoActualizado) {

                System.out.println(
                        "P003 actualizado mediante CatalogoProductos."
                );
            }

            Producto monitorVerificado =
                    catalogo.buscarProducto(
                            "P003"
                    );

            if (monitorVerificado != null) {

                System.out.println(
                        "Nuevo nombre: "
                        + monitorVerificado.getNombre()
                );

                System.out.println(
                        "Nuevo precio: $"
                        + monitorVerificado.getPrecio()
                );

                System.out.println(
                        "Nuevo stock: "
                        + monitorVerificado.getStock()
                );
            }

            // Eliminar mediante CatalogoProductos
            boolean catalogoEliminado =
                    catalogo.eliminarProducto(
                            "P003"
                    );

            if (catalogoEliminado) {

                System.out.println(
                        "P003 eliminado mediante CatalogoProductos."
                );
            }

            if (
                    catalogo.buscarProducto(
                            "P003"
                    ) == null
            ) {

                System.out.println(
                        "Verificacion final: P003 ya no existe."
                );
            }

        } catch (SQLException errorSql) {

            System.out.println(
                    "Error SQLite: "
                    + errorSql.getMessage()
            );
        }
    }
}
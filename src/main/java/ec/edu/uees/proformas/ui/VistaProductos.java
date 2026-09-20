/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.ui;

import ec.edu.uees.proformas.modelo.Producto;
import ec.edu.uees.proformas.servicio.CatalogoProductos;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VistaProductos extends VBox {

    private final CatalogoProductos catalogo;

    private final TextField campoCodigo;
    private final TextField campoNombre;
    private final TextField campoPrecio;
    private final TextField campoStock;

    private final TableView<Producto> tablaProductos;
    private final ObservableList<Producto> datosProductos;

    private final Label estado;

    public VistaProductos(
            CatalogoProductos catalogo
    ) {

        this.catalogo = catalogo;

        setSpacing(20);
        setPadding(
                new Insets(30)
        );

        setAlignment(
                Pos.TOP_CENTER
        );

        Label titulo = new Label(
                "Catalogo de Productos"
        );

        Label subtitulo = new Label(
                "Administracion de productos"
        );

        campoCodigo = new TextField();
        campoCodigo.setPromptText(
                "Codigo"
        );

        campoNombre = new TextField();
        campoNombre.setPromptText(
                "Nombre"
        );

        campoPrecio = new TextField();
        campoPrecio.setPromptText(
                "Precio"
        );

        campoStock = new TextField();
        campoStock.setPromptText(
                "Stock"
        );

        HBox filaCodigoNombre = new HBox(
                10,
                campoCodigo,
                campoNombre
        );

        filaCodigoNombre.setAlignment(
                Pos.CENTER
        );

        HBox filaPrecioStock = new HBox(
                10,
                campoPrecio,
                campoStock
        );

        filaPrecioStock.setAlignment(
                Pos.CENTER
        );

        Button botonAgregar =
                new Button(
                        "Agregar"
                );

        Button botonBuscar =
                new Button(
                        "Buscar"
                );

        Button botonActualizar =
                new Button(
                        "Actualizar"
                );

        Button botonEliminar =
                new Button(
                        "Eliminar"
                );

        Button botonLimpiar =
                new Button(
                        "Limpiar"
                );

        HBox filaBotones = new HBox(
                10,
                botonAgregar,
                botonBuscar,
                botonActualizar,
                botonEliminar,
                botonLimpiar
        );

        filaBotones.setAlignment(
                Pos.CENTER
        );

        tablaProductos =
                new TableView<>();

        TableColumn<Producto, String> columnaNombre =
                new TableColumn<>(
                        "Nombre"
                );

        columnaNombre.setCellValueFactory(
                new PropertyValueFactory<>(
                        "nombre"
                )
        );

        TableColumn<Producto, Double> columnaPrecio =
                new TableColumn<>(
                        "Precio"
                );

        columnaPrecio.setCellValueFactory(
                new PropertyValueFactory<>(
                        "precio"
                )
        );

        TableColumn<Producto, Integer> columnaStock =
                new TableColumn<>(
                        "Stock"
                );

        columnaStock.setCellValueFactory(
                new PropertyValueFactory<>(
                        "stock"
                )
        );

        tablaProductos.getColumns().add(
                columnaNombre
        );

        tablaProductos.getColumns().add(
                columnaPrecio
        );

        tablaProductos.getColumns().add(
                columnaStock
        );

        tablaProductos.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        tablaProductos.setPrefHeight(
                400
        );

        datosProductos =
                FXCollections.observableArrayList();

        tablaProductos.setItems(
                datosProductos
        );

        estado = new Label(
                "Estado: Listo"
        );

        botonAgregar.setOnAction(
                evento -> agregarProducto()
        );

        botonBuscar.setOnAction(
                evento -> buscarProducto()
        );

        botonActualizar.setOnAction(
                evento -> actualizarProducto()
        );

        botonEliminar.setOnAction(
                evento -> eliminarProducto()
        );

        botonLimpiar.setOnAction(
                evento -> limpiarCampos()
        );

        getChildren().addAll(
                titulo,
                subtitulo,
                filaCodigoNombre,
                filaPrecioStock,
                filaBotones,
                tablaProductos,
                estado
        );

        refrescarTabla();
    }

    private void agregarProducto() {

        try {

            String codigo =
                    campoCodigo.getText().trim();

            String nombre =
                    campoNombre.getText().trim();

            String textoPrecio =
                    campoPrecio.getText().trim();

            String textoStock =
                    campoStock.getText().trim();

            if (
                    codigo.isBlank()
                    || nombre.isBlank()
                    || textoPrecio.isBlank()
                    || textoStock.isBlank()
            ) {

                estado.setText(
                        "Error: Complete todos los campos."
                );

                return;
            }

            double precio;
            int stock;

            try {

                precio =
                        Double.parseDouble(
                                textoPrecio
                        );

                stock =
                        Integer.parseInt(
                                textoStock
                        );

            } catch (NumberFormatException error) {

                estado.setText(
                        "Error: Precio o stock no son validos."
                );

                return;
            }

            Producto producto =
                    new Producto(
                            nombre,
                            precio,
                            stock
                    );

            catalogo.agregarProducto(
                    codigo,
                    producto
            );

            refrescarTabla();
            limpiarCampos();

            estado.setText(
                    "Estado: Producto agregado correctamente."
            );

        } catch (
                IllegalArgumentException
                | SQLException error
        ) {

            estado.setText(
                    "Error: "
                    + error.getMessage()
            );
        }
    }

    private void buscarProducto() {

        String codigo =
                campoCodigo.getText().trim();

        if (codigo.isBlank()) {

            estado.setText(
                    "Error: Ingrese un codigo."
            );

            return;
        }

        Producto producto =
                catalogo.buscarProducto(
                        codigo
                );

        if (producto == null) {

            estado.setText(
                    "Error: Producto no encontrado."
            );

            return;
        }

        campoNombre.setText(
                producto.getNombre()
        );

        campoPrecio.setText(
                String.valueOf(
                        producto.getPrecio()
                )
        );

        campoStock.setText(
                String.valueOf(
                        producto.getStock()
                )
        );

        estado.setText(
                "Estado: Producto encontrado."
        );
    }

    private void actualizarProducto() {

        try {

            String codigo =
                    campoCodigo.getText().trim();

            String nombre =
                    campoNombre.getText().trim();

            String textoPrecio =
                    campoPrecio.getText().trim();

            String textoStock =
                    campoStock.getText().trim();

            if (
                    codigo.isBlank()
                    || nombre.isBlank()
                    || textoPrecio.isBlank()
                    || textoStock.isBlank()
            ) {

                estado.setText(
                        "Error: Complete todos los campos."
                );

                return;
            }

            double precio;
            int stock;

            try {

                precio =
                        Double.parseDouble(
                                textoPrecio
                        );

                stock =
                        Integer.parseInt(
                                textoStock
                        );

            } catch (NumberFormatException error) {

                estado.setText(
                        "Error: Precio o stock no son validos."
                );

                return;
            }

            Producto productoActualizado =
                    new Producto(
                            nombre,
                            precio,
                            stock
                    );

            boolean actualizado =
                    catalogo.actualizarProducto(
                            codigo,
                            productoActualizado
                    );

            if (!actualizado) {

                estado.setText(
                        "Error: Producto no encontrado."
                );

                return;
            }

            refrescarTabla();

            estado.setText(
                    "Estado: Producto actualizado correctamente."
            );

        } catch (
                IllegalArgumentException
                | SQLException error
        ) {

            estado.setText(
                    "Error: "
                    + error.getMessage()
            );
        }
    }

    private void eliminarProducto() {

        try {

            String codigo =
                    campoCodigo.getText().trim();

            if (codigo.isBlank()) {

                estado.setText(
                        "Error: Ingrese un codigo."
                );

                return;
            }

            boolean eliminado =
                    catalogo.eliminarProducto(
                            codigo
                    );

            if (!eliminado) {

                estado.setText(
                        "Error: Producto no encontrado."
                );

                return;
            }

            refrescarTabla();
            limpiarCampos();

            estado.setText(
                    "Estado: Producto eliminado correctamente."
            );

        } catch (SQLException error) {

            estado.setText(
                    "Error: "
                    + error.getMessage()
            );
        }
    }

    private void limpiarCampos() {

        campoCodigo.clear();
        campoNombre.clear();
        campoPrecio.clear();
        campoStock.clear();

        campoCodigo.requestFocus();

        estado.setText(
                "Estado: Campos limpiados"
        );
    }

    private void refrescarTabla() {

        datosProductos.setAll(
                catalogo.listarProductos()
        );
    }
}
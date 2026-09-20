/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.ui;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.ClienteCorporativo;
import ec.edu.uees.proformas.modelo.ClienteMayorista;
import ec.edu.uees.proformas.modelo.ClienteMinorista;
import ec.edu.uees.proformas.servicio.CatalogoClientes;

import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VistaClientes extends VBox {

    private final CatalogoClientes catalogo;

    private final TextField campoNombre;
    private final TextField campoEmail;
    private final TextField campoCiudad;

    private final ComboBox<String> campoTipo;

    private final TableView<Cliente> tablaClientes;

    private final ObservableList<Cliente> datosClientes;

    private final Label estado;

    public VistaClientes(
            CatalogoClientes catalogo
    ) {

        this.catalogo = catalogo;

        campoNombre = new TextField();
        campoEmail = new TextField();
        campoCiudad = new TextField();

        campoTipo = new ComboBox<>();

        tablaClientes = new TableView<>();

        datosClientes =
                FXCollections.observableArrayList();

        estado = new Label(
                "Estado: Listo"
        );

        configurarVista();
    }

    private void configurarVista() {

        setSpacing(18);

        setPadding(
                new Insets(
                        35
                )
        );

        setAlignment(
                Pos.TOP_CENTER
        );

        Label titulo =
                new Label(
                        "Clientes"
                );

        titulo.setStyle(
                "-fx-font-size: 24px;"
                + "-fx-font-weight: bold;"
        );

        Label subtitulo =
                new Label(
                        "Administracion de clientes"
                );

        campoNombre.setPromptText(
                "Nombre"
        );

        campoEmail.setPromptText(
                "Correo electronico"
        );

        campoCiudad.setPromptText(
                "Ciudad"
        );

        campoTipo.getItems().addAll(
                "MAYORISTA",
                "MINORISTA",
                "CORPORATIVO"
        );

        campoTipo.setPromptText(
                "Tipo"
        );

        campoNombre.setPrefWidth(
                190
        );

        campoEmail.setPrefWidth(
                220
        );

        campoCiudad.setPrefWidth(
                190
        );

        campoTipo.setPrefWidth(
                160
        );

        HBox filaCampos =
                new HBox(
                        12,
                        campoNombre,
                        campoEmail,
                        campoCiudad,
                        campoTipo
                );

        filaCampos.setAlignment(
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

        HBox filaBotones =
                new HBox(
                        12,
                        botonAgregar,
                        botonBuscar,
                        botonActualizar,
                        botonEliminar,
                        botonLimpiar
                );

        filaBotones.setAlignment(
                Pos.CENTER
        );

        configurarTabla();

        botonAgregar.setOnAction(
                evento ->
                        agregarCliente()
        );

        botonBuscar.setOnAction(
                evento ->
                        buscarCliente()
        );

        botonActualizar.setOnAction(
                evento ->
                        actualizarCliente()
        );

        botonEliminar.setOnAction(
                evento ->
                        eliminarCliente()
        );

        botonLimpiar.setOnAction(
                evento ->
                        limpiarCampos()
        );

        tablaClientes
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (
                                observable,
                                anterior,
                                seleccionado
                        ) -> {

                            if (
                                    seleccionado != null
                            ) {

                                mostrarCliente(
                                        seleccionado
                                );
                            }
                        }
                );

        refrescarTabla();

        getChildren().addAll(
                titulo,
                subtitulo,
                filaCampos,
                filaBotones,
                tablaClientes,
                estado
        );

        VBox.setVgrow(
                tablaClientes,
                Priority.ALWAYS
        );
    }

    private void configurarTabla() {

        TableColumn<Cliente, String>
                columnaNombre =
                new TableColumn<>(
                        "Nombre"
                );

        columnaNombre.setCellValueFactory(
                datos ->
                        new SimpleStringProperty(
                                datos
                                        .getValue()
                                        .getNombre()
                        )
        );

        TableColumn<Cliente, String>
                columnaEmail =
                new TableColumn<>(
                        "Correo"
                );

        columnaEmail.setCellValueFactory(
                datos ->
                        new SimpleStringProperty(
                                datos
                                        .getValue()
                                        .getEmail()
                        )
        );

        TableColumn<Cliente, String>
                columnaCiudad =
                new TableColumn<>(
                        "Ciudad"
                );

        columnaCiudad.setCellValueFactory(
                datos ->
                        new SimpleStringProperty(
                                datos
                                        .getValue()
                                        .getCiudad()
                        )
        );

        TableColumn<Cliente, String>
                columnaTipo =
                new TableColumn<>(
                        "Tipo"
                );

        columnaTipo.setCellValueFactory(
                datos ->
                        new SimpleStringProperty(
                                obtenerTipo(
                                        datos.getValue()
                                )
                        )
        );

        columnaNombre.setPrefWidth(
                250
        );

        columnaEmail.setPrefWidth(
                300
        );

        columnaCiudad.setPrefWidth(
                220
        );

        columnaTipo.setPrefWidth(
                180
        );

        tablaClientes
                .getColumns()
                .addAll(
                        columnaNombre,
                        columnaEmail,
                        columnaCiudad,
                        columnaTipo
                );

        tablaClientes.setItems(
                datosClientes
        );

        tablaClientes.setPrefHeight(
                430
        );

        tablaClientes.setPlaceholder(
                new Label(
                        "Tabla sin contenido"
                )
        );
    }

    private void agregarCliente() {

        try {

            Cliente cliente =
                    crearClienteFormulario();

            catalogo.agregarCliente(
                    cliente
            );

            refrescarTabla();

            limpiarCampos();

            estado.setText(
                    "Estado: Cliente agregado correctamente."
            );

        } catch (
                IllegalArgumentException error
        ) {

            estado.setText(
                    "Error: "
                    + error.getMessage()
            );

        } catch (
                SQLException error
        ) {

            estado.setText(
                    "Error SQLite: "
                    + error.getMessage()
            );
        }
    }

    private void buscarCliente() {

        String email =
                campoEmail
                        .getText()
                        .trim();

        if (email.isBlank()) {

            estado.setText(
                    "Error: Ingrese el correo del cliente."
            );

            return;
        }

        Cliente cliente =
                catalogo.buscarCliente(
                        email
                );

        if (cliente == null) {

            estado.setText(
                    "Error: Cliente no encontrado."
            );

            return;
        }

        mostrarCliente(
                cliente
        );

        estado.setText(
                "Estado: Cliente encontrado."
        );
    }

    private void actualizarCliente() {

        String email =
                campoEmail
                        .getText()
                        .trim();

        if (email.isBlank()) {

            estado.setText(
                    "Error: Ingrese el correo del cliente."
            );

            return;
        }

        try {

            Cliente cliente =
                    crearClienteFormulario();

            boolean actualizado =
                    catalogo.actualizarCliente(
                            email,
                            cliente
                    );

            if (!actualizado) {

                estado.setText(
                        "Error: Cliente no encontrado."
                );

                return;
            }

            refrescarTabla();

            estado.setText(
                    "Estado: Cliente actualizado correctamente."
            );

        } catch (
                IllegalArgumentException error
        ) {

            estado.setText(
                    "Error: "
                    + error.getMessage()
            );

        } catch (
                SQLException error
        ) {

            estado.setText(
                    "Error SQLite: "
                    + error.getMessage()
            );
        }
    }

    private void eliminarCliente() {

        String email =
                campoEmail
                        .getText()
                        .trim();

        if (email.isBlank()) {

            estado.setText(
                    "Error: Ingrese el correo del cliente."
            );

            return;
        }

        try {

            boolean eliminado =
                    catalogo.eliminarCliente(
                            email
                    );

            if (!eliminado) {

                estado.setText(
                        "Error: Cliente no encontrado."
                );

                return;
            }

            refrescarTabla();

            limpiarCampos();

            estado.setText(
                    "Estado: Cliente eliminado correctamente."
            );

        } catch (
                SQLException error
        ) {

            estado.setText(
                    "Error SQLite: "
                    + error.getMessage()
            );
        }
    }

    private Cliente crearClienteFormulario() {

        String nombre =
                campoNombre
                        .getText()
                        .trim();

        String email =
                campoEmail
                        .getText()
                        .trim();

        String ciudad =
                campoCiudad
                        .getText()
                        .trim();

        String tipo =
                campoTipo
                        .getValue();

        if (
                nombre.isBlank()
                || email.isBlank()
                || ciudad.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Complete todos los campos."
            );
        }

        if (tipo == null) {

            throw new IllegalArgumentException(
                    "Seleccione el tipo de cliente."
            );
        }

        return switch (tipo) {

            case "MAYORISTA" ->
                    new ClienteMayorista(
                            nombre,
                            email,
                            ciudad
                    );

            case "MINORISTA" ->
                    new ClienteMinorista(
                            nombre,
                            email,
                            ciudad
                    );

            case "CORPORATIVO" ->
                    new ClienteCorporativo(
                            nombre,
                            email,
                            ciudad
                    );

            default ->
                    throw new IllegalArgumentException(
                            "Tipo de cliente invalido."
                    );
        };
    }

    private void mostrarCliente(
            Cliente cliente
    ) {

        campoNombre.setText(
                cliente.getNombre()
        );

        campoEmail.setText(
                cliente.getEmail()
        );

        campoCiudad.setText(
                cliente.getCiudad()
        );

        campoTipo.setValue(
                obtenerTipo(
                        cliente
                )
        );
    }

    private String obtenerTipo(
            Cliente cliente
    ) {

        if (
                cliente
                instanceof ClienteMayorista
        ) {

            return "MAYORISTA";
        }

        if (
                cliente
                instanceof ClienteMinorista
        ) {

            return "MINORISTA";
        }

        if (
                cliente
                instanceof ClienteCorporativo
        ) {

            return "CORPORATIVO";
        }

        return "DESCONOCIDO";
    }

    private void refrescarTabla() {

        datosClientes.setAll(
                catalogo.listarClientes()
        );
    }

    private void limpiarCampos() {

        campoNombre.clear();

        campoEmail.clear();

        campoCiudad.clear();

        campoTipo.setValue(
                null
        );

        tablaClientes
                .getSelectionModel()
                .clearSelection();

        campoNombre.requestFocus();
    }
}
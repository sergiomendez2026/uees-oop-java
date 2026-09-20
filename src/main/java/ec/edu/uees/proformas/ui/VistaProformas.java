package ec.edu.uees.proformas.ui;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.ItemProforma;
import ec.edu.uees.proformas.modelo.Producto;
import ec.edu.uees.proformas.modelo.Proforma;
import ec.edu.uees.proformas.modelo.ProformaResumen;
import ec.edu.uees.proformas.modelo.DetalleProformaResumen;
import ec.edu.uees.proformas.persistencia.RepositorioProformasSQLite;
import ec.edu.uees.proformas.servicio.CatalogoClientes;
import ec.edu.uees.proformas.servicio.CatalogoProductos;

import java.sql.SQLException;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VistaProformas extends VBox {

    private final CatalogoClientes catalogoClientes;
    private final CatalogoProductos catalogoProductos;
    private final RepositorioProformasSQLite repositorioProformas;

    private final TextField campoEmailCliente =
            new TextField();

    private final TextField campoCodigoProducto =
            new TextField();

    private final TextField campoCantidad =
            new TextField();

    private final Label clienteSeleccionadoLabel =
            new Label(
                    "Ningun cliente seleccionado"
            );

    private final Label subtotalLabel =
            new Label(
                    "Subtotal: $0.00"
            );

    private final Label descuentoLabel =
            new Label(
                    "Descuento: $0.00"
            );

    private final Label totalLabel =
            new Label(
                    "Total: $0.00"
            );

    private final Label estado =
            new Label(
                    "Estado: Listo"
            );

    private final TableView<ItemProforma> tablaItems =
            new TableView<>();

    private final ObservableList<ItemProforma> datosItems =
            FXCollections.observableArrayList();
    
    private TableView<ProformaResumen> tablaProformasGuardadas;
    
    private ObservableList<ProformaResumen> datosProformasGuardadas;

    private Cliente clienteSeleccionado;

    public VistaProformas(
            CatalogoClientes catalogoClientes,
            CatalogoProductos catalogoProductos,
            RepositorioProformasSQLite repositorioProformas
    ) {

        this.catalogoClientes =
                catalogoClientes;

        this.catalogoProductos =
                catalogoProductos;

        this.repositorioProformas =
                repositorioProformas;

        construirVista();
    }

    private void construirVista() {

        setSpacing(18);

        setPadding(
                new Insets(40)
        );

        setAlignment(
                Pos.TOP_CENTER
        );

        Label titulo =
                new Label(
                        "Proformas"
                );

        titulo.setStyle(
                "-fx-font-size: 26px;"
                + "-fx-font-weight: bold;"
        );

        Label subtitulo =
                new Label(
                        "Creacion de proformas"
                );

        /*
         * CLIENTE
         */

        campoEmailCliente.setPromptText(
                "Correo electronico del cliente"
        );

        campoEmailCliente.setPrefWidth(
                350
        );

        Button botonBuscarCliente =
                new Button(
                        "Buscar cliente"
                );

        botonBuscarCliente.setOnAction(
                evento -> buscarCliente()
        );

        HBox filaCliente =
                new HBox(
                        10,
                        campoEmailCliente,
                        botonBuscarCliente,
                        clienteSeleccionadoLabel
                );

        filaCliente.setAlignment(
                Pos.CENTER
        );

        /*
         * PRODUCTO
         */

        campoCodigoProducto.setPromptText(
                "Codigo producto"
        );

        campoCantidad.setPromptText(
                "Cantidad"
        );

        campoCodigoProducto.setPrefWidth(
                200
        );

        campoCantidad.setPrefWidth(
                100
        );

        Button botonAgregarItem =
                new Button(
                        "Agregar item"
                );

        botonAgregarItem.setOnAction(
                evento -> agregarItem()
        );

        HBox filaProducto =
                new HBox(
                        10,
                        campoCodigoProducto,
                        campoCantidad,
                        botonAgregarItem
                );

        filaProducto.setAlignment(
                Pos.CENTER
        );

        /*
         * TABLA
         */

        configurarTabla();

        tablaItems.setItems(
                datosItems
        );

        tablaItems.setPrefHeight(
                350
        );

        VBox.setVgrow(
                tablaItems,
                Priority.ALWAYS
        );

        /*
         * TOTALES
         */

        HBox filaTotales =
                new HBox(
                        30,
                        subtotalLabel,
                        descuentoLabel,
                        totalLabel
                );

        filaTotales.setAlignment(
                Pos.CENTER_RIGHT
        );

        /*
         * BOTONES
         */

        Button botonQuitar =
                new Button(
                        "Quitar seleccionado"
                );

        botonQuitar.setOnAction(
                evento -> quitarItem()
        );

        Button botonLimpiar =
                new Button(
                        "Limpiar"
                );

        botonLimpiar.setOnAction(
                evento -> limpiar()
        );

        Button botonGuardar =
                new Button(
                        "Guardar proforma"
                );

        botonGuardar.setOnAction(
                evento -> guardarProforma()
        );

        HBox filaBotones =
                new HBox(
                        10,
                        botonQuitar,
                        botonLimpiar,
                        botonGuardar
                );

        filaBotones.setAlignment(
                Pos.CENTER
        );
        
        VBox seccionProformasGuardadas =
                crearSeccionProformasGuardadas();

        getChildren().addAll(
                titulo,
                subtitulo,
                filaCliente,
                filaProducto,
                tablaItems,
                filaTotales,
                filaBotones,
                estado,
                seccionProformasGuardadas
        );
        
        /*
        * Cargamos las proformas que ya
        * existen en SQLite.
        */
        refrescarProformasGuardadas();
    }

    private void configurarTabla() {

        TableColumn<ItemProforma, String> columnaCodigo =
                new TableColumn<>(
                        "Codigo"
                );

        columnaCodigo.setCellValueFactory(
                dato ->
                        new ReadOnlyStringWrapper(
                                dato
                                        .getValue()
                                        .getCodigoProducto()
                        )
        );

        TableColumn<ItemProforma, String> columnaProducto =
                new TableColumn<>(
                        "Producto"
                );

        columnaProducto.setCellValueFactory(
                dato ->
                        new ReadOnlyStringWrapper(
                                dato
                                        .getValue()
                                        .getProducto()
                                        .getNombre()
                        )
        );

        TableColumn<ItemProforma, Integer> columnaCantidad =
                new TableColumn<>(
                        "Cantidad"
                );

        columnaCantidad.setCellValueFactory(
                dato ->
                        new ReadOnlyObjectWrapper<>(
                                dato
                                        .getValue()
                                        .getCantidad()
                        )
        );

        TableColumn<ItemProforma, Double> columnaPrecio =
                new TableColumn<>(
                        "Precio"
                );

        columnaPrecio.setCellValueFactory(
                dato ->
                        new ReadOnlyObjectWrapper<>(
                                dato
                                        .getValue()
                                        .getProducto()
                                        .getPrecio()
                        )
        );

        TableColumn<ItemProforma, Double> columnaSubtotal =
                new TableColumn<>(
                        "Subtotal"
                );

        columnaSubtotal.setCellValueFactory(
                dato ->
                        new ReadOnlyObjectWrapper<>(
                                dato
                                        .getValue()
                                        .calcularSubtotal()
                        )
        );

        columnaCodigo.setPrefWidth(
                150
        );

        columnaProducto.setPrefWidth(
                300
        );

        columnaCantidad.setPrefWidth(
                120
        );

        columnaPrecio.setPrefWidth(
                150
        );

        columnaSubtotal.setPrefWidth(
                150
        );

        tablaItems.getColumns().addAll(
                columnaCodigo,
                columnaProducto,
                columnaCantidad,
                columnaPrecio,
                columnaSubtotal
        );
    }

    private void buscarCliente() {

        String email =
                campoEmailCliente
                        .getText()
                        .trim();

        if (email.isBlank()) {

            estado.setText(
                    "Error: Ingrese el correo del cliente."
            );

            return;
        }

        Cliente cliente =
                catalogoClientes.buscarCliente(
                        email
                );

        if (cliente == null) {

            clienteSeleccionado =
                    null;

            clienteSeleccionadoLabel.setText(
                    "Cliente no encontrado"
            );

            estado.setText(
                    "Error: Cliente no encontrado."
            );

            actualizarTotales();

            return;
        }

        clienteSeleccionado =
                cliente;

        clienteSeleccionadoLabel.setText(
                "Cliente: "
                + cliente.getNombre()
        );

        estado.setText(
                "Estado: Cliente seleccionado."
        );

        actualizarTotales();
    }

    private void agregarItem() {

        if (clienteSeleccionado == null) {

            estado.setText(
                    "Error: Primero seleccione un cliente."
            );

            return;
        }

        String codigo =
                campoCodigoProducto
                        .getText()
                        .trim();

        String cantidadTexto =
                campoCantidad
                        .getText()
                        .trim();

        if (
                codigo.isBlank()
                || cantidadTexto.isBlank()
        ) {

            estado.setText(
                    "Error: Complete codigo y cantidad."
            );

            return;
        }

        int cantidad;

        try {

            cantidad =
                    Integer.parseInt(
                            cantidadTexto
                    );

        } catch (NumberFormatException error) {

            estado.setText(
                    "Error: La cantidad debe ser un numero entero."
            );

            return;
        }

        if (cantidad <= 0) {

            estado.setText(
                    "Error: La cantidad debe ser mayor que cero."
            );

            return;
        }

        Producto producto =
                catalogoProductos.buscarProducto(
                        codigo
                );

        if (producto == null) {

            estado.setText(
                    "Error: Producto no encontrado."
            );

            return;
        }

        if (
                cantidad
                > producto.getStock()
        ) {

            estado.setText(
                    "Error: Stock insuficiente."
            );

            return;
        }

        for (
                ItemProforma item
                : datosItems
        ) {

            if (
                    codigo.equals(
                            item.getCodigoProducto()
                    )
            ) {

                estado.setText(
                        "Error: El producto ya fue agregado."
                );

                return;
            }
        }

        ItemProforma item =
                new ItemProforma(
                        codigo,
                        producto,
                        cantidad
                );

        datosItems.add(
                item
        );

        campoCodigoProducto.clear();
        campoCantidad.clear();

        actualizarTotales();

        estado.setText(
                "Estado: Producto agregado a la proforma."
        );
    }

    private void quitarItem() {

        ItemProforma seleccionado =
                tablaItems
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            estado.setText(
                    "Error: Seleccione un item."
            );

            return;
        }

        datosItems.remove(
                seleccionado
        );

        actualizarTotales();

        estado.setText(
                "Estado: Item eliminado de la proforma."
        );
    }

    private void actualizarTotales() {

        double subtotal =
                0;

        for (
                ItemProforma item
                : datosItems
        ) {

            subtotal +=
                    item.calcularSubtotal();
        }

        double porcentajeDescuento =
                0;

        if (clienteSeleccionado != null) {

            porcentajeDescuento =
                    clienteSeleccionado
                            .calcularDescuento();
        }

        double descuento =
                subtotal
                * porcentajeDescuento;

        double total =
                subtotal
                - descuento;

        subtotalLabel.setText(
                String.format(
                        "Subtotal: $%.2f",
                        subtotal
                )
        );

        descuentoLabel.setText(
                String.format(
                        "Descuento: $%.2f",
                        descuento
                )
        );

        totalLabel.setText(
                String.format(
                        "Total: $%.2f",
                        total
                )
        );
    }

    private void guardarProforma() {

        if (clienteSeleccionado == null) {

            estado.setText(
                    "Error: Seleccione un cliente."
            );

            return;
        }

        if (datosItems.isEmpty()) {

            estado.setText(
                    "Error: Agregue al menos un producto."
            );

            return;
        }

        try {

            Proforma proforma =
                    new Proforma(
                            clienteSeleccionado
                    );

            for (
                    ItemProforma item
                    : datosItems
            ) {

                proforma.agregarItem(
                        item
                );
            }

            int id =
                    repositorioProformas.guardar(
                            proforma
                    );
                        
            refrescarProformasGuardadas();

            estado.setText(
                    "Estado: Proforma guardada. ID: "
                    + id
            );

            datosItems.clear();

            campoCodigoProducto.clear();
            campoCantidad.clear();

            actualizarTotales();

        } catch (
                SQLException
                | IllegalArgumentException error
        ) {

            estado.setText(
                    "Error: "
                    + error.getMessage()
            );
        }
    }

    private void limpiar() {

        clienteSeleccionado =
                null;

        campoEmailCliente.clear();

        campoCodigoProducto.clear();

        campoCantidad.clear();

        datosItems.clear();

        clienteSeleccionadoLabel.setText(
                "Ningun cliente seleccionado"
        );

        actualizarTotales();

        estado.setText(
                "Estado: Campos limpiados."
        );
    }
    
    private VBox crearSeccionProformasGuardadas() {
        
        Label titulo =
                new Label(
                        "Proformas guardadas"
                );

        titulo.setStyle(
                "-fx-font-size: 18px;"
                + "-fx-font-weight: bold;"
        );

        tablaProformasGuardadas =
                new TableView<>();

        datosProformasGuardadas =
                FXCollections.observableArrayList();
        
        TableColumn<ProformaResumen, Integer> columnaId =
                new TableColumn<>(
                        "ID"
                );

        columnaId.setCellValueFactory(
                new PropertyValueFactory<>(
                        "id"
                )
        );

        TableColumn<ProformaResumen, String> columnaCliente =
                new TableColumn<>(
                        "Cliente"
                );

        columnaCliente.setCellValueFactory(
                new PropertyValueFactory<>(
                        "clienteEmail"
                )
        );

        TableColumn<ProformaResumen, String> columnaFecha =
                new TableColumn<>(
                        "Fecha"
                );

        columnaFecha.setCellValueFactory(
                new PropertyValueFactory<>(
                        "fecha"
                )
        );

        TableColumn<ProformaResumen, Double> columnaSubtotal =
                new TableColumn<>(
                        "Subtotal"
                );

        columnaSubtotal.setCellValueFactory(
                new PropertyValueFactory<>(
                        "subtotal"
                )
        );
        
        columnaSubtotal.setCellFactory(
                columna -> new TableCell<>() {

                    @Override
                    protected void updateItem(
                            Double valor,
                            boolean vacio
                    ) {
                        super.updateItem(
                                valor,
                                vacio
                        );

                        if (
                                vacio
                                || valor == null
                        ) {

                            setText(null);

                        } else {

                            setText(
                                    String.format(
                                            "$%.2f",
                                            valor
                                    )
                            );
                        }
                    }
                }
        );

        TableColumn<ProformaResumen, Double> columnaDescuento =
                new TableColumn<>(
                        "Descuento"
                );

        columnaDescuento.setCellValueFactory(
                new PropertyValueFactory<>(
                        "descuento"
                )
        );
        
        columnaDescuento.setCellFactory(
                columna -> new TableCell<>() {
                    
                    @Override
                    protected void updateItem(
                            Double valor,
                            boolean vacio
                    ) {
                        super.updateItem(
                                valor,
                                vacio
                        );

                        if (
                                vacio
                                || valor == null
                        ) {

                            setText(null);

                        } else {

                            setText(
                                    String.format(
                                            "$%.2f",
                                            valor
                                    )
                            );
                        }
                    }
                }
        );

        TableColumn<ProformaResumen, Double> columnaTotal =
                new TableColumn<>(
                        "Total"
                );

        columnaTotal.setCellValueFactory(
                new PropertyValueFactory<>(
                        "total"
                )
        );
        
        columnaTotal.setCellFactory(
                columna -> new TableCell<>() {
                    
                    @Override
                    protected void updateItem(
                            Double valor,
                            boolean vacio
                    ) {
                        super.updateItem(
                                valor,
                                vacio
                        );

                        if (
                                vacio
                                || valor == null
                        ) {

                            setText(null);

                        } else {

                            setText(
                                    String.format(
                                            "$%.2f",
                                            valor
                                    )
                            );
                        }
                    }
                }
        );

        tablaProformasGuardadas
                .getColumns()
                .addAll(
                        columnaId,
                        columnaCliente,
                        columnaFecha,
                        columnaSubtotal,
                        columnaDescuento,
                        columnaTotal
                );

        tablaProformasGuardadas.setItems(
                datosProformasGuardadas
        );

        tablaProformasGuardadas.setPrefHeight(
                180
        );
        
        Button botonVerDetalle =
                new Button(
                        "Ver detalle"
                );

        botonVerDetalle.setOnAction(
                evento ->
                        mostrarDetalleProforma()
        );

        VBox contenedor =
                new VBox(
                        10,
                        titulo,
                        botonVerDetalle,
                        tablaProformasGuardadas
                );

        contenedor.setAlignment(
                Pos.CENTER
        );
        
        return contenedor;
    }
    
    private void refrescarProformasGuardadas() {

        try {

            datosProformasGuardadas.setAll(
                    repositorioProformas
                            .listarProformas()
            );

        } catch (Exception error) {

            estado.setText(
                    "Error al cargar las proformas guardadas: "
                    + error.getMessage()
            );
        }
    }
    
    private void mostrarDetalleProforma() {
        
        ProformaResumen seleccionada =
                tablaProformasGuardadas
                        .getSelectionModel()
                        .getSelectedItem();

        if (seleccionada == null) {

            estado.setText(
                    "Error: Seleccione una proforma guardada."
            );

            return;
        }

        try {

            var detalles =
                    repositorioProformas.listarDetalle(
                            seleccionada.getId()
                    );

            if (detalles.isEmpty()) {
                
                estado.setText(
                        "Error: La proforma no tiene detalles."
                );

                return;
            }

            TableView<DetalleProformaResumen> tablaDetalle =
                    new TableView<>();
            
            /*
             * COLUMNA CODIGO
            */

            TableColumn<DetalleProformaResumen, String>
                    columnaCodigo =
                    new TableColumn<>(
                            "Codigo"
                    );

            columnaCodigo.setCellValueFactory(
                    new PropertyValueFactory<>(
                            "codigoProducto"
                    )
            );

            /*
             * COLUMNA PRODUCTO
             */

            TableColumn<DetalleProformaResumen, String>
                    columnaProducto =
                    new TableColumn<>(
                            "Producto"
                    );

            columnaProducto.setCellValueFactory(
                    new PropertyValueFactory<>(
                            "nombreProducto"
                    )
            );

            /*
            * COLUMNA CANTIDAD
            */

            TableColumn<DetalleProformaResumen, Integer>
                    columnaCantidad =
                    new TableColumn<>(
                            "Cantidad"
                    );

            columnaCantidad.setCellValueFactory(
                    new PropertyValueFactory<>(
                            "cantidad"
                    )
            );

            /*
             * COLUMNA PRECIO
             */

            TableColumn<DetalleProformaResumen, String>
                    columnaPrecio =
                    new TableColumn<>(
                            "Precio"
                    );

            columnaPrecio.setCellValueFactory(
                    dato ->
                            new SimpleStringProperty(
                                    String.format(
                                            "$%.2f",
                                            dato
                                                    .getValue()
                                                    .getPrecioUnitario()
                                    )
                            )
            );

            /*
             * COLUMNA SUBTOTAL
             */

            TableColumn<DetalleProformaResumen, String>
                    columnaSubtotal =
                    new TableColumn<>(
                            "Subtotal"
                    );

            columnaSubtotal.setCellValueFactory(
                    dato ->
                            new SimpleStringProperty(
                                    String.format(
                                            "$%.2f",
                                            dato
                                                    .getValue()
                                                    .getSubtotal()
                                    )
                            )
            );

            tablaDetalle
                    .getColumns()
                    .addAll(
                            columnaCodigo,
                            columnaProducto,
                            columnaCantidad,
                            columnaPrecio,
                            columnaSubtotal
                    );

            tablaDetalle.setItems(
                    FXCollections.observableArrayList(
                            detalles
                    )
            );

            /*
             * Tamaño de columnas.
             */

            columnaCodigo.setPrefWidth(
                    100
            );

            columnaProducto.setPrefWidth(
                    280
            );

            columnaCantidad.setPrefWidth(
                    100
            );

            columnaPrecio.setPrefWidth(
                    130
            );

            columnaSubtotal.setPrefWidth(
                    130
            );

            Label titulo =
                    new Label(
                            "Detalle de Proforma #"
                                    + seleccionada.getId()
                    );

            titulo.setStyle(
                    "-fx-font-size: 22px;"
                    + "-fx-font-weight: bold;"
            );

            VBox contenido =
                    new VBox(
                            20,
                            titulo,
                            tablaDetalle
                    );

            contenido.setPadding(
                    new Insets(25)
            );

            contenido.setAlignment(
                    Pos.CENTER
            );

            Stage ventanaDetalle =
                    new Stage();

            ventanaDetalle.initModality(
                    Modality.APPLICATION_MODAL
            );

            ventanaDetalle.setTitle(
                    "Detalle de Proforma #"
                    + seleccionada.getId()
            );
            
            Scene escena =
                    new Scene(
                            contenido,
                            800,
                            450
                    );

            ventanaDetalle.setScene(
                    escena
            );

            ventanaDetalle.showAndWait();
            
            estado.setText(
                    "Estado: Detalle de proforma consultado."
            );

        } catch (SQLException error) {

            estado.setText(
                    "Error: "
                    + error.getMessage()
            );
        }
    }
}
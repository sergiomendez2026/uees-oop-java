package ec.edu.uees.proformas.ui;

import ec.edu.uees.proformas.persistencia.ConexionSQLite;
import ec.edu.uees.proformas.persistencia.RepositorioProductosSQLite;
import ec.edu.uees.proformas.servicio.CatalogoProductos;
import ec.edu.uees.proformas.persistencia.RepositorioClientesSQLite;
import ec.edu.uees.proformas.servicio.CatalogoClientes;
import ec.edu.uees.proformas.persistencia.RepositorioProformasSQLite;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AplicacionJavaFX extends Application {

    @Override
    public void start(Stage escenario) throws Exception {

        /*
         * 1. Inicializar SQLite.
         */
        ConexionSQLite.crearTablas();

        RepositorioProductosSQLite repositorio =
                new RepositorioProductosSQLite();

        CatalogoProductos catalogo =
                new CatalogoProductos(
                        repositorio
                );

        RepositorioClientesSQLite repositorioClientes =
                new RepositorioClientesSQLite();

        CatalogoClientes catalogoClientes =
                new CatalogoClientes(
                        repositorioClientes
                );
        
        /*
         * 2. Contenedor principal.
         *
         * BorderPane permite dividir la ventana en:
         *
         * TOP
         * LEFT
         * CENTER
         * RIGHT
         * BOTTOM
         */
        BorderPane raiz =
                new BorderPane();

        /*
         * 3. Encabezado.
         */
        Label tituloPrincipal =
                new Label(
                        "Sistema de Gestion de Proformas"
                );

        tituloPrincipal.setStyle(
                "-fx-font-size: 20px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;"
        );

        VBox encabezado =
                new VBox(
                        tituloPrincipal
                );

        encabezado.setPadding(
                new Insets(18)
        );

        encabezado.setAlignment(
                Pos.CENTER_LEFT
        );

        encabezado.setStyle(
                "-fx-background-color: #123c36;"
        );

        raiz.setTop(
                encabezado
        );

        /*
         * 4. Menu lateral.
         */
        Label navegacion =
                new Label(
                        "NAVEGACION"
                );

        navegacion.setStyle(
                "-fx-text-fill: #b8ccc8;"
                + "-fx-font-size: 11px;"
                + "-fx-font-weight: bold;"
        );

        Button botonProductos =
                new Button(
                        "Productos"
                );

        Button botonClientes =
                new Button(
                        "Clientes"
                );

        Button botonProformas =
                new Button(
                        "Proformas"
                );

        botonProductos.setMaxWidth(
                Double.MAX_VALUE
        );

        botonClientes.setMaxWidth(
                Double.MAX_VALUE
        );

        botonProformas.setMaxWidth(
                Double.MAX_VALUE
        );

        botonProductos.setStyle(
                "-fx-background-color: #e8f0ee;"
                + "-fx-text-fill: #123c36;"
                + "-fx-font-weight: bold;"
        );

        botonClientes.setStyle(
                "-fx-background-color: white;"
                + "-fx-text-fill: #123c36;"
        );

        botonProformas.setStyle(
                "-fx-background-color: white;"
                + "-fx-text-fill: #123c36;"
        );

        VBox menuLateral =
                new VBox(
                        10,
                        navegacion,
                        botonProformas,
                        botonProductos,
                        botonClientes
                );

        menuLateral.setPadding(
                new Insets(20)
        );

        menuLateral.setPrefWidth(
                180
        );

        menuLateral.setStyle(
                "-fx-background-color: #123c36;"
        );

        raiz.setLeft(
                menuLateral
        );

        /*
         * 5. Vista inicial:
         * Productos.
         */
        VistaProductos vistaProductos =
                new VistaProductos(
                        catalogo
                );
        
        VistaClientes vistaClientes =
                new VistaClientes(
                        catalogoClientes
                );
        
        RepositorioProformasSQLite repositorioProformas =
                new RepositorioProformasSQLite();
        
        VistaProformas vistaProformas =
                new VistaProformas(
                        catalogoClientes,
                        catalogo,
                        repositorioProformas
                );

        raiz.setCenter(
                vistaProductos
        );

        /*
         * 6. Eventos de navegacion.
         */
        botonProductos.setOnAction(
                evento -> {

                    raiz.setCenter(
                            vistaProductos
                    );

                    botonProductos.setStyle(
                            "-fx-background-color: #e8f0ee;"
                            + "-fx-text-fill: #123c36;"
                            + "-fx-font-weight: bold;"
                    );

                    botonClientes.setStyle(
                            "-fx-background-color: white;"
                            + "-fx-text-fill: #123c36;"
                    );

                    botonProformas.setStyle(
                            "-fx-background-color: white;"
                            + "-fx-text-fill: #123c36;"
                    );
                }
        );

        botonClientes.setOnAction(
                evento -> {

                    raiz.setCenter(
                            vistaClientes
                    );

                    botonClientes.setStyle(
                            "-fx-background-color: #e8f0ee;"
                            + "-fx-text-fill: #123c36;"
                            + "-fx-font-weight: bold;"
                    );

                    botonProductos.setStyle(
                            "-fx-background-color: white;"
                            + "-fx-text-fill: #123c36;"
                    );

                    botonProformas.setStyle(
                            "-fx-background-color: white;"
                            + "-fx-text-fill: #123c36;"
                    );
                }
        );

        botonProformas.setOnAction(
                evento -> {

                    raiz.setCenter(
                            vistaProformas
                    );

                    botonProformas.setStyle(
                            "-fx-background-color: #e8f0ee;"
                            + "-fx-text-fill: #123c36;"
                            + "-fx-font-weight: bold;"
                    );

                    botonProductos.setStyle(
                            "-fx-background-color: white;"
                            + "-fx-text-fill: #123c36;"
                    );

                    botonClientes.setStyle(
                            "-fx-background-color: white;"
                            + "-fx-text-fill: #123c36;"
                    );
                }
        );

        /*
         * 7. Crear escena.
         */
        Scene escena =
                new Scene(
                        raiz,
                        1100,
                        700
                );

        escenario.setTitle(
                "Gestion de Proformas"
        );

        escenario.setMinWidth(
                900
        );

        escenario.setMinHeight(
                600
        );

        escenario.setScene(
                escena
        );

        escenario.show();
    }

    /*
     * Vista temporal para los modulos
     * que construiremos posteriormente.
     */
    private VBox crearVistaTemporal(
            String titulo,
            String descripcion
    ) {

        Label etiquetaTitulo =
                new Label(
                        titulo
                );

        etiquetaTitulo.setStyle(
                "-fx-font-size: 24px;"
                + "-fx-font-weight: bold;"
        );

        Label etiquetaDescripcion =
                new Label(
                        descripcion
                );

        VBox contenedor =
                new VBox(
                        15,
                        etiquetaTitulo,
                        etiquetaDescripcion
                );

        contenedor.setPadding(
                new Insets(30)
        );

        contenedor.setAlignment(
                Pos.TOP_LEFT
        );

        return contenedor;
    }

    public static void main(String[] args) {

        launch(args);
    }
}
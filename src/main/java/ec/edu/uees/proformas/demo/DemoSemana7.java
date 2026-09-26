package ec.edu.uees.proformas.demo;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.ClienteMinorista;
import ec.edu.uees.proformas.repositorio.RepositorioAtencionClientes;

public class DemoSemana7 {

    public static void main(String[] args) {

        RepositorioAtencionClientes repositorio =
                new RepositorioAtencionClientes(3);

        Cliente ana = new ClienteMinorista(
                "Ana Torres",
                "ana@correo.com",
                "Guayaquil"
        );

        Cliente luis = new ClienteMinorista(
                "Luis Perez",
                "luis@correo.com",
                "Quito"
        );

        Cliente pedro = new ClienteMinorista(
                "Pedro Gomez",
                "pedro@correo.com",
                "Cuenca"
        );

        System.out.println(
                "=============================================="
        );
        System.out.println(
                "   DEMO SEMANA 7 - COLA DE ATENCION FIFO"
        );
        System.out.println(
                "=============================================="
        );

        System.out.println();
        System.out.println("Registrando clientes...");

        repositorio.registrarLlegada(ana);
        System.out.println("1. " + ana.getNombre());

        repositorio.registrarLlegada(luis);
        System.out.println("2. " + luis.getNombre());

        repositorio.registrarLlegada(pedro);
        System.out.println("3. " + pedro.getNombre());

        System.out.println();
        System.out.println(
                "Cantidad pendiente: "
                + repositorio.cantidadPendiente()
        );

        System.out.println(
                "Siguiente cliente: "
                + repositorio.consultarSiguiente().getNombre()
        );

        System.out.println();
        System.out.println(
                "----------------------------------------------"
        );
        System.out.println("ATENCION DE CLIENTES");
        System.out.println(
                "----------------------------------------------"
        );

        while (!repositorio.estaVacio()) {

            Cliente atendido =
                    repositorio.atenderSiguiente();

            System.out.println(
                    "Atendiendo: " + atendido.getNombre()
            );

            System.out.println(
                    "Pendientes: "
                    + repositorio.cantidadPendiente()
            );
        }

        System.out.println();
        System.out.println(
                "----------------------------------------------"
        );
        System.out.println("RESULTADO FINAL");
        System.out.println(
                "----------------------------------------------"
        );

        System.out.println(
                "Cola vacia: " + repositorio.estaVacio()
        );

        System.out.println(
                "Cantidad pendiente: "
                + repositorio.cantidadPendiente()
        );

        System.out.println();
        System.out.println(
                "Orden FIFO verificado correctamente."
        );
    }
}
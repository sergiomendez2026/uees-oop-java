package ec.edu.uees.proformas.repositorio;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.ClienteMinorista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioAtencionClientesTest {

    private RepositorioAtencionClientes repositorio;
    private Cliente ana;
    private Cliente luis;

    @BeforeEach
    void configurar() {

        repositorio = new RepositorioAtencionClientes(3);

        ana = new ClienteMinorista(
                "Ana Torres",
                "ana@correo.com",
                "Guayaquil"
        );

        luis = new ClienteMinorista(
                "Luis Perez",
                "luis@correo.com",
                "Quito"
        );
    }

    @Test
    void repositorioNuevoEstaVacio() {

        assertTrue(repositorio.estaVacio());
        assertEquals(0, repositorio.cantidadPendiente());
    }

    @Test
    void registrarLlegadaIncrementaCantidad() {

        repositorio.registrarLlegada(ana);

        assertEquals(1, repositorio.cantidadPendiente());
        assertFalse(repositorio.estaVacio());
    }

    @Test
    void consultarSiguienteNoEliminaCliente() {

        repositorio.registrarLlegada(ana);

        Cliente siguiente =
                repositorio.consultarSiguiente();

        assertSame(ana, siguiente);
        assertEquals(1, repositorio.cantidadPendiente());
    }

    @Test
    void atenderSiguienteRespetaOrdenFIFO() {

        repositorio.registrarLlegada(ana);
        repositorio.registrarLlegada(luis);

        assertSame(
                ana,
                repositorio.atenderSiguiente()
        );

        assertSame(
                luis,
                repositorio.atenderSiguiente()
        );

        assertTrue(repositorio.estaVacio());
    }

    @Test
    void atenderRepositorioVacioLanzaExcepcion() {

        assertThrows(
                IllegalStateException.class,
                () -> repositorio.atenderSiguiente()
        );
    }
}

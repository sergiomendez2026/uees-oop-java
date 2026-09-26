package ec.edu.uees.proformas.tda;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.modelo.ClienteMinorista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColaClientesTest {

    private ColaClientes cola;
    private Cliente ana;
    private Cliente luis;
    private Cliente pedro;

    @BeforeEach
    void configurar() {

        cola = new ColaClientes(3);

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

        pedro = new ClienteMinorista(
                "Pedro Gomez",
                "pedro@correo.com",
                "Cuenca"
        );
    }

    @Test
    void colaNuevaEstaVacia() {
        assertTrue(cola.estaVacia());
    }

    @Test
    void colaNuevaTieneCantidadCero() {
        assertEquals(0, cola.getCantidad());
    }

    @Test
    void encolarIncrementaCantidad() {

        cola.encolar(ana);

        assertEquals(1, cola.getCantidad());
        assertFalse(cola.estaVacia());
    }

    @Test
    void consultarSiguienteNoEliminaElemento() {

        cola.encolar(ana);

        Cliente siguiente = cola.consultarSiguiente();

        assertSame(ana, siguiente);
        assertEquals(1, cola.getCantidad());
    }

    @Test
    void desencolarRespetaOrdenFIFO() {

        cola.encolar(ana);
        cola.encolar(luis);
        cola.encolar(pedro);

        assertSame(ana, cola.desencolar());
        assertSame(luis, cola.desencolar());
        assertSame(pedro, cola.desencolar());

        assertTrue(cola.estaVacia());
    }

    @Test
    void desencolarColaVaciaLanzaExcepcion() {

        assertThrows(
                IllegalStateException.class,
                () -> cola.desencolar()
        );
    }

    @Test
    void encolarColaLlenaLanzaExcepcion() {

        cola.encolar(ana);
        cola.encolar(luis);
        cola.encolar(pedro);

        Cliente maria = new ClienteMinorista(
                "Maria Lopez",
                "maria@correo.com",
                "Guayaquil"
        );

        assertThrows(
                IllegalStateException.class,
                () -> cola.encolar(maria)
        );
    }

    @Test
    void colaCircularReutilizaEspacio() {

        cola.encolar(ana);
        cola.encolar(luis);
        cola.encolar(pedro);

        assertSame(ana, cola.desencolar());

        Cliente maria = new ClienteMinorista(
                "Maria Lopez",
                "maria@correo.com",
                "Guayaquil"
        );

        cola.encolar(maria);

        assertSame(luis, cola.desencolar());
        assertSame(pedro, cola.desencolar());
        assertSame(maria, cola.desencolar());

        assertTrue(cola.estaVacia());
    }
}
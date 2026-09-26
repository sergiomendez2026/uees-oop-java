package ec.edu.uees.proformas.repositorio;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.tda.ColaClientes;

public class RepositorioAtencionClientes {

    private final ColaClientes cola;

    public RepositorioAtencionClientes(int capacidad) {
        cola = new ColaClientes(capacidad);
    }

    public void registrarLlegada(Cliente cliente) {
        cola.encolar(cliente);
    }

    public Cliente atenderSiguiente() {
        return cola.desencolar();
    }

    public Cliente consultarSiguiente() {
        return cola.consultarSiguiente();
    }

    public boolean estaVacio() {
        return cola.estaVacia();
    }

    public int cantidadPendiente() {
        return cola.getCantidad();
    }
}

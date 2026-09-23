/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proformas.servicio;

import ec.edu.uees.proformas.modelo.Cliente;
import ec.edu.uees.proformas.persistencia.RepositorioClientesSQLite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CatalogoClientes {

    private final List<Cliente> clientes;

    private final Map<String, Cliente> clientesPorEmail;

    private final Set<String> emailsRegistrados;

    private final RepositorioClientesSQLite repositorio;

    public CatalogoClientes(
            RepositorioClientesSQLite repositorio
    ) throws SQLException {

        this.clientes =
                new ArrayList<>();

        this.clientesPorEmail =
                new HashMap<>();

        this.emailsRegistrados =
                new HashSet<>();

        this.repositorio =
                repositorio;

        cargarDesdeBaseDeDatos();
    }

    private void cargarDesdeBaseDeDatos()
            throws SQLException {

        List<Cliente> clientesPersistidos =
                repositorio.listar();

        for (Cliente cliente : clientesPersistidos) {

            clientes.add(
                    cliente
            );

            clientesPorEmail.put(
                    cliente.getEmail(),
                    cliente
            );

            emailsRegistrados.add(
                    cliente.getEmail()
            );
        }
    }

    public void agregarCliente(
            Cliente cliente
    ) throws SQLException {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        String email =
                cliente.getEmail();

        if (
                email == null
                || email.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "El email no puede estar vacio."
            );
        }

        if (
                emailsRegistrados.contains(
                        email
                )
        ) {

            throw new IllegalArgumentException(
                    "Ya existe un cliente con el email "
                    + email
            );
        }

        repositorio.guardar(
                cliente
        );

        clientes.add(
                cliente
        );

        clientesPorEmail.put(
                email,
                cliente
        );

        emailsRegistrados.add(
                email
        );
    }

    public Cliente buscarCliente(
            String email
    ) {

        if (
                email == null
                || email.isBlank()
        ) {

            return null;
        }

        return clientesPorEmail.get(
                email
        );
    }

    public List<Cliente> listarClientes() {

        return new ArrayList<>(
                clientes
        );
    }

    public boolean actualizarCliente(
            String email,
            Cliente cliente
    ) throws SQLException {

        if (
                email == null
                || email.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "El email no puede estar vacio."
            );
        }

        if (cliente == null) {

            throw new IllegalArgumentException(
                    "El cliente no puede ser nulo."
            );
        }

        if (
                !emailsRegistrados.contains(
                        email
                )
        ) {

            return false;
        }

        boolean actualizado =
                repositorio.actualizar(
                        email,
                        cliente
                );

        if (!actualizado) {
            return false;
        }

        Cliente clienteAnterior =
                clientesPorEmail.get(
                        email
                );

        int indice =
                clientes.indexOf(
                        clienteAnterior
                );

        if (indice >= 0) {

            clientes.set(
                    indice,
                    cliente
            );
        }

        clientesPorEmail.put(
                email,
                cliente
        );

        return true;
    }

    public boolean eliminarCliente(
            String email
    ) throws SQLException {

        if (
                email == null
                || email.isBlank()
        ) {

            throw new IllegalArgumentException(
                    "El email no puede estar vacio."
            );
        }

        if (
                !emailsRegistrados.contains(
                        email
                )
        ) {

            return false;
        }

        boolean eliminado =
                repositorio.eliminar(
                        email
                );

        if (!eliminado) {
            return false;
        }

        Cliente cliente =
                clientesPorEmail.remove(
                        email
                );

        clientes.remove(
                cliente
        );

        emailsRegistrados.remove(
                email
        );

        return true;
    }
}
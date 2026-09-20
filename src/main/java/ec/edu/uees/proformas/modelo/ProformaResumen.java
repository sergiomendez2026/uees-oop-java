package ec.edu.uees.proformas.modelo;

public class ProformaResumen {

    private int id;
    private String clienteEmail;
    private String fecha;
    private double subtotal;
    private double descuento;
    private double total;

    public ProformaResumen(
            int id,
            String clienteEmail,
            String fecha,
            double subtotal,
            double descuento,
            double total
    ) {

        this.id = id;
        this.clienteEmail = clienteEmail;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public String getFecha() {
        return fecha;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }
}
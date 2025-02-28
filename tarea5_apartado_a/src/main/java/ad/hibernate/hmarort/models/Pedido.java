package ad.hibernate.hmarort.models;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "fecha")
    private LocalDate fecha;
    
    @Column(name = "importe")
    private double importe;
    
    @Column(name = "id_cliente")
    private int idCliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
    private Cliente cliente;

    /**
     * Constructor por defecto.
     */
    public Pedido() {
    }

    /**
     *  Constructor con parámetros.
     * @param id
     * @param fecha
     * @param importe
     * @param idCliente
     */
    public Pedido(int id, LocalDate fecha, double importe, int idCliente) {
        this.id = id;
        this.fecha = fecha;
        this.importe = importe;
        this.idCliente = idCliente;
    }

    /**
     * Devuelve el id del pedido.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el id del pedido.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve la fecha del pedido.
     * @return
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del pedido.
     * @param fecha
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve el importe del pedido.
     * @return
     */
    public double getImporte() {
        return importe;
    }

    /**
     * Establece el importe del pedido.
     * @param importe
     */
    public void setImporte(double importe) {
        this.importe = importe;
    }
    
    /**
     * Devuelve el id del cliente asociado al pedido.
     * @return
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el id del cliente asociado al pedido.
     * @param idCliente
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    /**
     * Devuelve el cliente asociado al pedido.
     * @return
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente asociado al pedido.
     * @param cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
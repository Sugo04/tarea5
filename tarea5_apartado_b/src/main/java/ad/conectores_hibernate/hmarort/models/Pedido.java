package ad.conectores_hibernate.hmarort.models;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad que representa un pedido en el sistema de gestión comercial.
 * 
 * Esta clase es una entidad JPA que mapea a la tabla "Pedidos" en la base de datos.
 * Almacena información sobre los pedidos realizados por los clientes, incluyendo
 * la fecha de realización, el importe total y la referencia al cliente asociado.
 * 
 * @author hmarort
 * @version 1.0
 * @see ad.hibernate.hmarort.models.Cliente
 */
@Entity
@Table(name = "Pedidos")
public class Pedido {
    /**
     * Identificador único del pedido.
     * 
     * Este campo es la clave primaria de la tabla Pedidos,
     * generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int idPedido;
    
    /**
     * Fecha en que se realizó el pedido.
     * 
     * Este campo no puede ser nulo y se valida para asegurar
     * que no sea una fecha futura.
     */
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    /**
     * Importe total del pedido en la moneda predeterminada.
     * 
     * Este valor representa la suma de todos los productos
     * incluidos en el pedido, incluyendo impuestos aplicables.
     * Está limitado a un máximo de 999999.99.
     */
    @Column(name = "importe_total", nullable = false)
    private double importeTotal;
    
    /**
     * Identificador del cliente que realizó el pedido.
     * 
     * Este campo es una referencia a la entidad Cliente.
     * En una implementación con relaciones completas, podría
     * ser reemplazado por una relación ManyToOne.
     */
    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    /**
     * Constructor completo para la clase Pedido.
     * 
     * Inicializa todos los atributos del pedido con los valores proporcionados.
     * No realiza validaciones, las cuales se ejecutan en los métodos setter.
     *
     * @param idPedido      Identificador único del pedido
     * @param fecha         Fecha en que se realizó el pedido
     * @param importeTotal  Importe total del pedido
     * @param idCliente     ID del cliente que realizó el pedido
     */
    public Pedido(int idPedido, LocalDate fecha, double importeTotal, int idCliente) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.importeTotal = importeTotal;
        this.idCliente = idCliente;
    }

    /**
     * Constructor vacío para la clase Pedido.
     * 
     * Requerido por JPA para la creación de instancias mediante reflection.
     * También facilita la creación de objetos en código cliente que luego
     * serán inicializados mediante los métodos setter.
     */
    public Pedido() {
    }

    /**
     * Obtiene el identificador único del pedido.
     *
     * @return Identificador del pedido
     */
    public int getIdPedido() {
        return idPedido;
    }

    /**
     * Establece el identificador único del pedido.
     * 
     * Generalmente este método solo debería ser utilizado por el framework ORM,
     * ya que el identificador normalmente es generado por la base de datos.
     *
     * @param idPedido Nuevo identificador del pedido
     */
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    /**
     * Obtiene la fecha en que se realizó el pedido.
     *
     * @return Fecha del pedido
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha en que se realizó el pedido.
     * 
     * Valida que la fecha no sea nula y que no sea futura, ya que
     * no se permite registrar pedidos con fechas posteriores al día actual.
     *
     * @param fecha Nueva fecha del pedido
     * @throws IllegalArgumentException Si la fecha es nula o futura
     */
    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura");
        }
        this.fecha = fecha;
    }

    /**
     * Obtiene el importe total del pedido.
     *
     * @return Importe total del pedido en la moneda predeterminada
     */
    public double getImporteTotal() {
        return importeTotal;
    }

    /**
     * Establece el importe total del pedido.
     * 
     * Valida que el importe no sea negativo y que no exceda el valor máximo
     * permitido por restricciones de la base de datos (999999.99).
     *
     * @param importeTotal Nuevo importe total del pedido
     * @throws IllegalArgumentException Si el importe es negativo o excede el máximo permitido
     */
    public void setImporteTotal(double importeTotal) {
        if (importeTotal < 0) {
            throw new IllegalArgumentException("El importe no puede ser negativo");
        }
        if (importeTotal > 999999.99) {
            throw new IllegalArgumentException("El importe excede el máximo permitido");
        }
        this.importeTotal = importeTotal;
    }

    /**
     * Obtiene el ID del cliente que realizó el pedido.
     *
     * @return ID del cliente asociado al pedido
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el ID del cliente que realizó el pedido.
     * 
     * Valida que el ID del cliente sea un valor positivo, ya que
     * los identificadores en la base de datos comienzan desde 1.
     *
     * @param idCliente Nuevo ID del cliente
     * @throws IllegalArgumentException Si el ID es no positivo
     */
    public void setIdCliente(int idCliente) {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("El ID de cliente debe ser positivo");
        }
        this.idCliente = idCliente;
    }

    /**
     * Devuelve una representación en cadena del objeto Pedido.
     * 
     * Útil para depuración y logging, muestra todos los atributos
     * del pedido en un formato legible.
     *
     * @return Cadena que representa al objeto Pedido
     */
    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", fecha=" + fecha +
                ", importeTotal=" + importeTotal +
                ", idCliente=" + idCliente +
                '}';
    }
}
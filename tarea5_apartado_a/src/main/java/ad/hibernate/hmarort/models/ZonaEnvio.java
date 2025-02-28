package ad.hibernate.hmarort.models;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "zonas_envio")
public class ZonaEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "precio")
    private double precio;
    
    @OneToMany(mappedBy = "zonaEnvio", cascade = CascadeType.ALL)
    private List<Cliente> clientes;

    /**
     * Constructor por defecto.
     */
    public ZonaEnvio() {
    }

    /**
     * Constructor con parámetros.
     * @param id
     * @param nombre
     * @param precio
     */
    public ZonaEnvio(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Devuelve el id de la zona de envío.
     * @return
     */
    public int getId() {
        return id;
    }
    
    /**
     * Establece el id de la zona de envío.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la zona de envío.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la zona de envío.
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el precio de la zona de envío.
     * @return
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la zona de envío.
     * @param precio
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    /**
     * Devuelve la lista de clientes de esta zona.
     * @return
     */
    public List<Cliente> getClientes() {
        return clientes;
    }

    /**
     * Establece la lista de clientes de esta zona.
     * @param clientes
     */
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
package ad.hibernate.hmarort.models;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "id_zona_envio")
    private int idZonaEnvio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona_envio", insertable = false, updatable = false)
    private ZonaEnvio zonaEnvio;

    /**
     * Constructor por defecto.
     */
    public Cliente() {
    }

    /**
     * Constructor con parámetros.
     * @param id
     * @param nombre
     * @param email
     * @param telefono
     * @param idZonaEnvio
     */
    public Cliente(int id, String nombre, String email, String telefono, int idZonaEnvio) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.idZonaEnvio = idZonaEnvio;
    }

    /**
     * Devuelve el id del cliente.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el id del cliente.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del cliente.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el email del cliente.
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del cliente.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el teléfono del cliente.
     * @return
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve el id de la zona de envío del cliente.
     * @return
     */
    public int getIdZonaEnvio() {
        return idZonaEnvio;
    }

    /**
     * Establece el id de la zona de envío del cliente.
     * @param idZonaEnvio
     */
    public void setIdZonaEnvio(int idZonaEnvio) {
        this.idZonaEnvio = idZonaEnvio;
    }    
    
    /**
     * Devuelve la zona de envío del cliente.
     * @return
     */
    public ZonaEnvio getZonaEnvio() {
        return zonaEnvio;
    }

    /**
     * Establece la zona de envío del cliente.
     * @param zonaEnvio
     */
    public void setZonaEnvio(ZonaEnvio zonaEnvio) {
        this.zonaEnvio = zonaEnvio;
    }
}
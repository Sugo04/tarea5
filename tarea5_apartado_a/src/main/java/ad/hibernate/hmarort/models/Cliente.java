package ad.hibernate.hmarort.models;

import jakarta.persistence.*;

/**
 * Clase que representa un cliente en la base de datos.
 */
@Entity
@Table(name = "Clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int id_Cliente;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    
    @Column(name = "telefono", length = 15)
    private String telf;
    
    @Column(name = "id_zona", nullable = false)
    private int id_zona;

    /**
     * Constructor completo para crear un nuevo cliente.
     *
     * @param id_cliente Identificador único del cliente
     * @param nombre Nombre del cliente
     * @param email Email del cliente
     * @param telf Teléfono del cliente
     * @param id_zona ID de la zona a la que pertenece el cliente
     */
    public Cliente(int id_cliente, String nombre, String email, String telf, int id_zona) {
        this.id_Cliente = id_cliente;
        this.nombre = nombre;
        this.email = email;
        this.telf = telf;
        this.id_zona = id_zona;
    }

    /**
     * Constructor vacío.
     */
    public Cliente() {
    }

    /**
     * Obtiene el identificador único del cliente.
     *
     * @return Identificador del cliente
     */
    public int getId_Cliente() {
        return id_Cliente;
    }

    /**
     * Establece el identificador único del cliente.
     *
     * @param idCliente El nuevo identificador del cliente
     */
    public void setId_Cliente(int idCliente) {
        this.id_Cliente = idCliente;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return Nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente, validando que no esté vacío y no supere los 100 caracteres.
     *
     * @param nombre El nuevo nombre del cliente
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre no puede exceder 100 caracteres");
        }
        this.nombre = nombre.trim();
    }

    /**
     * Obtiene el email del cliente.
     *
     * @return Email del cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del cliente, validando que no esté vacío y tenga un formato válido.
     *
     * @param email El nuevo email del cliente
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        if (email.length() > 100) {
            throw new IllegalArgumentException("El email no puede exceder 100 caracteres");
        }
        this.email = email.trim();
    }

    /**
     * Obtiene el teléfono del cliente.
     *
     * @return Teléfono del cliente
     */
    public String getTelf() {
        return telf;
    }

    /**
     * Establece el teléfono del cliente, validando que tenga exactamente 9 dígitos.
     *
     * @param telefono El nuevo teléfono del cliente
     */
    public void setTelf(String telefono) {
        if (telefono != null) {
            telefono = telefono.trim();
            if (!telefono.matches("^[0-9]{9}$")) {
                throw new IllegalArgumentException("El teléfono debe tener 9 dígitos");
            }
        }
        this.telf = telefono;
    }

    /**
     * Obtiene el ID de la zona a la que pertenece el cliente.
     *
     * @return ID de la zona
     */
    public int getId_zona() {
        return id_zona;
    }

    /**
     * Establece el ID de la zona, validando que sea positivo.
     *
     * @param idZona El nuevo ID de la zona
     */
    public void setId_zona(int idZona) {
        if (idZona <= 0) {
            throw new IllegalArgumentException("El ID de zona debe ser positivo");
        }
        this.id_zona = idZona;
    }

    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }
}